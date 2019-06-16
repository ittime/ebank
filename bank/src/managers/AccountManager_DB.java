package managers;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mBankProperties.MBankProperties;


import valueObject.Account;
import valueObject.BalanceAction;
import valueObject.Client;




public class AccountManager_DB extends AccountManager {

	BankBalanceManager_DB _bankBalanceManager;

	public AccountManager_DB(BankBalanceManager_DB bankBalanceManager) {_bankBalanceManager = bankBalanceManager;}

	//add-remove accounts methods

	public void addAccount(Account account, int clientId, Connection connection) throws SQLException 

	{
		Statement statement = getStatement(connection);

		int lastId = getLastAccountId(connection);

		try {
			String insertValues = 
				"INSERT INTO accounts " +
				"VALUES (" + 
				lastId + ", " +
				clientId + ", " +
				account.getAmmount() + ", " +
				account.get_creditRestriction() + ", '" +
				account.get_accountComments() + "');";

			statement.executeUpdate(insertValues);

			statement.executeQuery("SELECT * FROM accounts");

			//document the action
			BalanceAction log = new BalanceAction ("create a new account (CID:"+clientId+")", 0, 0);
			_bankBalanceManager.addRecord
			(connection, log);

			System.out.println ("account no. " + lastId + " was added successfuly.");

		}catch (SQLException sqle)
		{
			System.out.println ("unable to add an account: " + sqle.getMessage());
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}
	}


	public void removeAccount (int id, Connection connection) throws SQLException
	{
		Statement statement = getStatement(connection);

		String command = ("from ACCOUNTS where cust_id = " + id);

		//check if the customer has a minus
		String find = ("select * " + command);
		int debt = checkDebt (find, connection);

		//delete the account
		String delete = ("delete " + command);
		statement.executeUpdate(delete);

		System.out.println ("the account (client id: " + id + ") was removed");

		//document the action
		BalanceAction log = new BalanceAction ("removed an account (client id: " + id + ")", Math.abs(debt), 0);

		_bankBalanceManager.addRecord
		(connection, log);

	}

	private int checkDebt(String find, Connection connection) throws SQLException 
	{
		Statement statement = getStatement(connection);
		int debt = 0;
		ResultSet rs = statement.executeQuery(find);
		rs.next();
		int amount = rs.getInt("amount");
		if (amount < 0)
			debt = amount;

		return debt;
	}

	//deposit-withdraw methods
	public void deposit(double amount, Connection connection, Client client, boolean needCommission) 
	{

		Statement statement = getStatement(connection);
		int clientId = client.get_clientId();

		try
		{

			if (exist("accounts", "cust_id", clientId, connection))
			{


				ResultSet rs = statement.executeQuery("SELECT * FROM accounts WHERE cust_id = " + clientId ); // choose the deposit
				rs.next();
				int oldAmount = rs.getInt("amount"); // get the old amount of money in the deposit
				double totalAmount = oldAmount + amount; //calculate the new amount


				String update = ("UPDATE accounts " + 
						"SET amount = " +  + totalAmount +
						" WHERE cust_id = " + clientId);

				statement.executeUpdate(update);
				statement.execute("select * from accounts");

				//document the action
				BalanceAction log = new BalanceAction ("deposit to account ("+ amount + ")", 0, clientId);
				_bankBalanceManager.addRecord
				(connection, log);


				if (needCommission)
				{
					double commission = makeCommission(client, amount, totalAmount, connection);

					BalanceAction log2 = new BalanceAction ("a commission fee taken for a depositing action", commission, clientId);

					_bankBalanceManager.addRecord
					(connection, log2);
				}

				System.out.println ("added " + amount + " to the account");
				statement.close();

			}else
				System.out.println ("couldn't find the account");

		}catch(SQLException sqle){
			System.out.println("couldn't add to the account: "+ sqle.getMessage());
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}
	}		

	public void deposit(int amount, Connection connection, Client client, boolean needCommission)
	{
		deposit((double)amount, connection, client, needCommission);
	}


	public void withdraw(int amount, Connection connection, Client client) 
	{
		Statement statement = getStatement(connection);
		int clientId = client.get_clientId();

		if (statement != null)
		{		
			try {

				ResultSet rs = statement.executeQuery("SELECT * FROM accounts WHERE cust_id = " + clientId ); // choose the deposit
				rs.next();
				int oldAmount = rs.getInt("amount"); // get the old amount of money in the deposit
				int totalAmount = oldAmount - amount; //calculate the new amount

				if (checkCredit(statement, clientId, totalAmount))
				{

					String update = ("UPDATE accounts " + 
							"SET amount = " +  + totalAmount +
							" WHERE cust_id = " + clientId);

					statement.executeUpdate(update);
					statement.execute("select * from accounts");

					double commission = makeCommission(client, amount, totalAmount, connection);

					System.out.println ("withdrawed " + amount + " from the account");

					//document the action
					BalanceAction log = new BalanceAction ("withdraw from the account ("+ amount + ")", 0, clientId);
					BalanceAction log2 = new BalanceAction ("a commission fee taken for a withdrawing action" , commission, clientId);

					_bankBalanceManager.addRecord
					(connection, log);

					_bankBalanceManager.addRecord
					(connection, log2);

				}
				else 
					System.out.println ("couldn't withdraw from the account: you have reached your credit restriction.");

				statement.close();


			}catch(SQLException sqle){
				System.out.println("couldn't withdraw from the account: "+ sqle.getMessage());
			}finally {
				try{
					statement.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
				}
			}	
		}
	}

	public double getCommission (Client client, int amount, Connection connection){

		Statement statement = getStatement(connection);
		int clientId = client.get_clientId();

		try {

			ResultSet rs = statement.executeQuery("SELECT * FROM accounts WHERE cust_id = " + clientId ); // choose the deposit
			rs.next();
			int oldAmount = rs.getInt("amount"); // get the old amount of money in the deposit
			int totalAmount = oldAmount - amount; //calculate the new amount

			if (checkCredit(statement, clientId, totalAmount))
			{
				double clientCommission = MBankProperties.getCommission (client.get_clientRank());
				return clientCommission;
			}
			else
				return -1.0;
		}catch(SQLException sqle){
			System.out.println("couldn't withdraw from the account: "+ sqle.getMessage());
			return (0.0);
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}

	}




	private double makeCommission (Client client, double amount, double oldAmount, Connection connection) throws SQLException
	{
		Statement statement = getStatement(connection);
		int clientId = client.get_clientId();
		double clientCommission = MBankProperties.getCommission (client.get_clientRank());
		double commission = clientCommission*amount;

		System.out.println ("you will be charged with " + commission + " commission fee");

		amount = oldAmount - commission;

		String update = ("UPDATE accounts " + 
				"SET amount = " +  amount +
				" WHERE cust_id = " + clientId);

		statement.executeUpdate(update);
		statement.execute("select * from accounts");

		return commission;
	}

	private boolean checkCredit(Statement statement, int clientId, int amount) throws SQLException  
	{
		if (amount > 0)
			return true;
		else
		{
			ResultSet rs = statement.executeQuery("SELECT * FROM accounts WHERE cust_id = " + clientId ); // choose the deposit
			rs.next();
			int credit = rs.getInt("credit");
			if ( Math.abs(amount) <= credit)	//check if it within the limits of the credit
				return true;
			else return false;
		}
	}

	//reports methods

	public Account reportAccount (Client client, Connection connection) 
	{
		Statement statement = getStatement(connection);

		Account account = null;

		int clientId = client.get_clientId();

		try

		{

			if (exist("accounts", "cust_id", clientId, connection))
			{

				ResultSet rs = statement.executeQuery("select * from accounts where cust_id =" + clientId);
				rs.next();
				int accountId = rs.getInt("id");
				statement.close();
				account =(reportAccount(accountId, connection));

			}else
				System.out.println ("this account doesn't exist");

		}catch (SQLException sqle)
		{
			System.out.println ("error reporting on account: " + sqle);
		}finally{
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}

		return account;

	}


	private Account reportAccount (int accountId, Connection connection) throws SQLException
	{
		Statement statement = getStatement(connection);

		String find = ("select * from accounts where id = "+ accountId);
		ResultSet rs = statement.executeQuery(find);

		rs.next();

		Account account = new Account
		(
				rs.getInt("id"),
				rs.getInt("cust_id"), 
				rs.getInt("amount"), 
				rs.getString ("comments")
		);

		account.set_creditRestriction(rs.getInt("credit"));

		return (account);

	}


	public ArrayList<Account> reportAccountList (Connection connection)
	{
		Statement statement = getStatement(connection);

		ArrayList<Account> accounts = new ArrayList<Account>();

		try{

			String find = "select * from accounts";

			ResultSet rsCheck = statement.executeQuery(find);

			if (rsCheck.next())
			{
				ResultSet rs = statement.executeQuery(find);
				while ((rs != null) && (rs.next()))
				{
					accounts.add(reportAccount(rs.getInt("id"), connection));
				}
			}else
				System.out.println ("no accounts");

		}catch (SQLException sqle)
		{
			System.out.println ("error reporting on account: " + sqle);
		}finally{
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}

		return accounts;
	}

	private int getLastAccountId(Connection connection)
	{
		int lastAccountId = 11111;

		Statement statement = getStatement(connection);

		try{
			ResultSet rs = statement.executeQuery("select Max(id) from accounts;");

			rs.next();

			lastAccountId = rs.getInt(1);

			lastAccountId++;


		}catch (SQLException sqle){
			System.out.println (sqle.getMessage());

		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}


		return lastAccountId;                                                                                                         

	}

	//this method check for a value on the DB
	private boolean exist (String table, String field, int id, Connection connection) throws SQLException
	{
		Statement statement = getStatement(connection);

		String find = 
			("select * from " + table + " where " + field + " = " + id);

		ResultSet rs = statement.executeQuery(find);

		if (rs.next())
			return true;
		else 
			return false;

	}


}









