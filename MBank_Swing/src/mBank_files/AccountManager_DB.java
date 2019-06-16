package mBank_files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;


import Swing_Files.MessagesAndQuestions;



public class AccountManager_DB extends AccountManager {
	
	BankBalanceManager_DB _bankBalanceManager;
	
	public AccountManager_DB(BankBalanceManager_DB bankBalanceManager) {_bankBalanceManager = bankBalanceManager;}



	@Override
	public void accountDetails(Client client, Connection connection) 
	{
		Statement statement = getStatement(connection);
		
		int clientId = client.get_clientId();
		
		try
		
		{
			
		if (exist("accounts", "cust_id", client.get_clientId(), connection))
		{
						
				ResultSet rs = statement.executeQuery("SELECT * FROM accounts WHERE cust_id = " + clientId ); // choose the deposit 
				
				rs.next();
				
				Account thisAccount = new Account (
						rs.getInt("id"), 
						rs.getInt("cust_id"),
						rs.getInt("amount"),
						rs.getString("comments") );
				System.out.println (thisAccount);
				statement.close();
		}else
			System.out.println ("this account doesn't exist");
						
			}catch(SQLException sqle){
				System.out.println("couldn't print the account: "+ sqle.getMessage());
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



	public void deposit(int amount, Connection connection, Client client, boolean needCommission)
	{
		deposit((double)amount, connection, client, needCommission);
	}
	
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
				
				boolean agreeToCommission = false;
				if (needCommission)
				{
					agreeToCommission = commissionQuery (client, amount, totalAmount, connection);
				}
				
				if ((!needCommission) || (needCommission && agreeToCommission))
				{
					String update = ("UPDATE accounts " +
									 "SET amount = " +  + totalAmount +
									 " WHERE cust_id = " + clientId);
				
					statement.executeUpdate(update);
					statement.execute("select * from accounts");
				
					//document the action
					BalanceAction log = new BalanceAction ("deposit to account ("+ amount + ")", 0, clientId);
					_bankBalanceManager.addRecord
					(connection, log);
				
					System.out.println ("added " + amount + " to the account");
					statement.close();
				}else 
					System.out.println ("operation was canceled");
		
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
	

	
	public boolean withdraw(int amount, Connection connection, Client client) 
	{
		
		Statement statement = getStatement(connection);
		int clientId = client.get_clientId();
		
		if (statement != null)
			{		
				try {
					if (exist ("accounts", "cust_id", clientId, connection))
					{
						ResultSet rs = statement.executeQuery("SELECT * FROM accounts WHERE cust_id = " + clientId ); // choose the deposit
						rs.next();
						int oldAmount = rs.getInt("amount"); // get the old amount of money in the deposit
						int totalAmount = oldAmount - amount; //calculate the new amount
					
						if (checkCredit(statement, clientId, totalAmount))
						{
				
							boolean agreeToCommission = commissionQuery (client, amount, totalAmount, connection);
					
							if (agreeToCommission)
							{
						
								String update = ("UPDATE accounts " + 
								 "SET amount = " +  + totalAmount +
								 " WHERE cust_id = " + clientId);

								statement.executeUpdate(update);
								statement.execute("select * from accounts");
										
								//document the action
								BalanceAction log = new BalanceAction ("withdraw from the account ("+ amount + ")", 0, clientId);
					
					
								_bankBalanceManager.addRecord
								(connection, log);
								
								statement.close();
					
							}else
									{ MessagesAndQuestions.showWarning("The operation was cancelled");
									statement.close();
									return false;}
					
						}else {
							MessagesAndQuestions.showWarning("Can't withdraw from the account: you have reached your credit restriction.");
							statement.close();
							return false; }
					}else {
						MessagesAndQuestions.showWarning("This account doesn't exist");
						return false;}
		
				}catch(SQLException sqle){
					MessagesAndQuestions.showWarning("Can't withdraw from the account: "+ sqle.getMessage());
					try{
						statement.close();
					}catch (SQLException sqle2){
						System.out.println (sqle.getMessage());
					}
					return false;
				}
				
				return true;
			}
		
		MessagesAndQuestions.showWarning("There was some technical problem. Please try again later");
		return false;
	}
	
	private boolean commissionQuery (Client client, double amount, double oldAmount, Connection connection)throws SQLException
	{
		double clientCommission = MBankProperties.getCommission (client.get_clientRank());
		double commission = clientCommission*amount;
		
		String format = "0.###";
		DecimalFormat df = new DecimalFormat (format);
		String formatCommission = df.format(commission);
		
		if (
		MessagesAndQuestions.showMessage("This operation involves a commision of " + formatCommission + " Shekels. Are you sure you want to continue?")
			)
		{
			amount = oldAmount - commission;
			makeCommission(client, amount, commission, connection);
		
			return true;
		}else
			return false;
		
	}
	
	private void makeCommission (Client client, double amount, double commission, Connection connection) throws SQLException
	{
		

		Statement statement = getStatement(connection);
		int clientId = client.get_clientId();
		
		
		String update = ("UPDATE accounts " + 
				 "SET amount = " +  amount +
				 " WHERE cust_id = " + clientId);

		statement.executeUpdate(update);
		statement.execute("select * from accounts");
		
		BalanceAction log = new BalanceAction ("a commission fee taken for a withdrawing action" , commission, clientId);
		
		_bankBalanceManager.addRecord
		(connection, log);
		
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
		

	
	
	@Override
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

	
	
	
	
	
	


