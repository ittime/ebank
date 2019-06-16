package mBank_files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class DepositManager_DB extends DepositManager {
	
	private BankBalanceManager _bankBalanceManager;
	private AccountManager _accountManager;
		
	public DepositManager_DB(BankBalanceManager bankBalanceManager, AccountManager_DB accountManager) {_bankBalanceManager = bankBalanceManager; _accountManager = accountManager;}


	public synchronized void addDeposit(Deposit deposit, Connection connection, MBank bank) 
	{
		Statement statement = getStatement(connection);
		int id = getId(connection);
		
		String type = deposit.get_interestType();
		int amount = deposit.get_amount();
		double interest = decideInterest (type);
		
		double penalty = MBankProperties.getDeposit_penalty();
		long ammountOfDays = calc_ammountOfDays (deposit.get_openDate(), deposit.get_closureDate());
		
		double [] estimatedAmmount = calc_estimatedAmmount (type, amount, penalty, ammountOfDays);
		
		try{
						String insertValues = 
							  "INSERT INTO deposits " +
							  "VALUES (" + 
							  id + " , '" +
							  deposit.get_interestType() + "' , " +
							  deposit.get_clientId()+ " , '" +
							  formatDate(deposit.get_openDate()) + "' , '" +
							  formatDate(deposit.get_closureDate()) + "' , " +
							  deposit.get_amount() + " , " +
							  estimatedAmmount[0] + " , " +
							  estimatedAmmount[1] + " , " +
							  interest +
							  ")";
		
						statement.executeUpdate(insertValues);
						
						//document the action
						BalanceAction log = new BalanceAction ("added a deposit (ID: "+id+" /CID: "+deposit.get_clientId()+")",deposit.get_amount(), deposit.get_clientId());
						_bankBalanceManager.addRecord
						(connection, log);
		
						System.out.println ("the deposit was added successfuly!");
						
										
			statement.close();
			}catch (SQLException sqle){
				System.out.println ("unable to add a deposit: " + sqle.getMessage());
			}finally {
				try{
					statement.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
			}
				
			}

	}


	public void removeDeposits (int id, Connection connection)
	{
		Statement statement = getStatement(connection);
						
		try{
			
			if (exist ("DEPOSITS", "cust_id", id, connection))
			{
			
			String find = ("select * from DEPOSITS where cust_id =" + id);
			ResultSet rs = statement.executeQuery (find);
			rs.next();
			
			while ((rs.getRow())!= 0)	
					{
				int amount = rs.getInt("amount");
				int depositId = rs.getInt("id");
				removeDeposit (depositId, (amount*(-1)), connection, "delete");
				rs.next();
					}
			System.out.println ("the customer's deposits were deleted");
			
			}
			else
				System.out.println ("this client doesn't have any deposits");
			
		}catch (SQLException sqle){
			System.out.println ("unable to delete a deposit: " + sqle.getMessage());
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			
			}
		}
	}
	
	public synchronized void closeOrRemove_Deposit (int id, Connection connection, String message)
	{
			Statement statement = getStatement(connection);
							
			try{
				
				if (exist ("DEPOSITS", "id", id, connection))
				{
					String findAmount = ("select * from deposits where id =" + id);
					ResultSet rs = statement.executeQuery(findAmount);
					rs.next();
					int amount = rs.getInt("amount");
					int clientId = rs.getInt("cust_id");
					
					_accountManager.deposit(amount, connection, new Client (clientId), false);
					
					removeDeposit (id, (amount*(-1)), connection, message);
				}
				else
					System.out.println ("this deposit doesn't exist");
				
			}catch (SQLException sqle){
				System.out.println ("unable to delete a deposit: " + sqle.getMessage());
			}finally {
				try{
					statement.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
				
				}
			}
	}
	
	private void removeDeposit (int id, int amount, Connection connection, String message)throws SQLException 
	{
		removeDeposit(id, (double)amount, connection, message);
	}
	
	private void removeDeposit (int id, double amount, Connection connection, String message) throws SQLException 
	{
		Statement statement = getStatement(connection);
		
		String command = ("delete from DEPOSITS where id = " + id);
		statement.executeUpdate(command);
		System.out.println ("deposit (id: " + id + ") was succesfuly deleted");
		
		//document the action
		BalanceAction log = new BalanceAction (message + " a deposit ("+id+")",amount, 0);
		_bankBalanceManager.addRecord
		(connection, log);
	}
		



	private synchronized int getId(Connection connection) 
	{
		int lastDepositId = 11111;
		
		Statement statement = getStatement(connection);
		
		try{
		ResultSet rs = statement.executeQuery("select Max(id) from deposits;");
		
		rs.next();
		
		lastDepositId = rs.getInt(1); // the old id
		
		lastDepositId++; // the new id
		
		
		}catch (SQLException sqle){
			System.out.println (sqle.getMessage());
			
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}
		
		
		return lastDepositId;                                                                                                         
			
		}
			
	

	private synchronized boolean exist (String table, String field, int id, Connection connection) throws SQLException
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

	public void breakDeposit(Deposit deposit, Connection connection) 
	{
		Statement statement = getStatement(connection);
		int depositId = deposit.get_depositId();
		
		
		try{
			
			if (exist ("DEPOSITS", "id", depositId, connection))
			{
			
			String find = ("select * from DEPOSITS where id =" + depositId);
			ResultSet rs = statement.executeQuery (find);
			rs.next();
			
			int amount = rs.getInt("amount");
			int clientId = rs.getInt("cust_id");
			double estimated = rs.getDouble("estimated_amount");
			
			double difference = amount - estimated;
			
			BalanceAction log = new BalanceAction ("a penalty for breaking a deposit ("+depositId+")",difference, clientId);
			_bankBalanceManager.addRecord
			(connection, log);
			
			_accountManager.deposit(estimated, connection, new Client (clientId), false);
			
			removeDeposit (depositId, (amount*(-1)), connection, "break");
			
			System.out.println ("the deposit was broken");
			
			}
			else
				System.out.println ("this deposit doesn't exist");
			
		}catch (SQLException sqle){
			System.out.println ("unable to delete a deposit: " + sqle.getMessage());
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			
			}
		}
		
			
		
	}
	
	public Deposit reportDeposit (int depositId, Connection connection)
	{
	
		Statement statement = getStatement(connection);
		
		Deposit newDeposit = new Deposit();
		
		try
		{
		
	    if (exist("deposits", "id", depositId, connection))
	    {
		
		String find = ("select * from deposits where id = "+ depositId);
		ResultSet rs = statement.executeQuery(find);
		
		rs.next();
		
		Date tempOpen = rs.getDate("opening_date");
		Calendar calOpen = Calendar.getInstance();
		calOpen.setTime(tempOpen);
		Date tempClose = rs.getDate("ending_date");
		Calendar calClose = Calendar.getInstance();
		calClose.setTime(tempClose);
		
		
		newDeposit = new Deposit
		(
				rs.getInt("id"),
				rs.getInt("cust_id"), 
				rs.getInt("amount"), 
				rs.getInt("estimated_amount"),
				rs.getInt("final_estimated_amount"),
				rs.getString("deposite_type"),
				calOpen, calClose
				);
	    }else
	    {
	    	System.out.println ("deposit does not exist");
	    	return null;
	    }
		}catch (SQLException sqle){
			System.out.println ("unable to report deposit: " + sqle.getMessage());
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			
			}
		}
		
			return (newDeposit);
		
		
	}
	
	
	
	public ArrayList<Deposit> reportDepositsList (Connection connection, String type, String condition)
	{
		Statement statement = getStatement(connection);
		
		ArrayList<Deposit> deposits = new ArrayList<Deposit>();
		
		condition = conditionCheck(type, condition);
		
		try{
			
			String find = "select * from deposits " + condition;
			
			ResultSet rsCheck = statement.executeQuery(find);
			
			if (rsCheck.next())
			{
				ResultSet rs = statement.executeQuery(find);
				while ((rs != null) && (rs.next()))
				{
					deposits.add(reportDeposit(rs.getInt("id"), connection));
				}
			}else
				System.out.println ("no deposits");
			
		}catch (SQLException sqle)
		{
			System.out.println ("error reporting on deposit: " + sqle);
		}finally{
			try{
				statement.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
				}
				 }
		
		return deposits;
	
	
	}
	
	public synchronized void indexUpdate (Connection connection, Double indexChange)
	{
		Statement statement = getStatement(connection);
		Statement statement2 = getStatement(connection);
		
		try{
			
			String find = "select * from deposits where deposite_type = 'Index'";
			
			ResultSet rsCheck = statement.executeQuery(find);
			
			if (rsCheck.next())
			{
				ResultSet rs = statement.executeQuery(find);
				while ((rs != null) && (rs.next()))
				{
					int id = rs.getInt("id");
					double amount = rs.getDouble("amount");
					double estimatedAmount = rs.getDouble("estimated_amount");
					double finalEstimatedAmount = rs.getDouble("final_estimated_amount");
					double interestRate = rs.getDouble("interest_rate");
					double penalty = MBankProperties.getDeposit_penalty();
					double dailyInterest;
					
					Date tempOpen = rs.getDate("opening_date");
					Calendar calOpen = Calendar.getInstance();
					calOpen.setTime(tempOpen);
					Date tempClose = rs.getDate("ending_date");
					Calendar calClose = Calendar.getInstance();
					calClose.setTime(tempClose);
					
					long amountOfDays = calc_ammountOfDays(calOpen, calClose);
					long currentAmountOfDays = calc_ammountOfDays(calOpen, Calendar.getInstance());
					
					interestRate += indexChange;
					
					dailyInterest = calc_dailyInterest(interestRate);
					
					finalEstimatedAmount = ( amount + (dailyInterest*amountOfDays*amount) ); 
					estimatedAmount = (amount + (dailyInterest*amount*currentAmountOfDays) - (penalty*amount) );
					
					String update = 
						("update deposits set " +
								"interest_rate = " + interestRate +
								", estimated_amount = " + estimatedAmount +
								", final_estimated_amount = " + finalEstimatedAmount +
								" where id = " + id
						);
						
				statement2.executeUpdate(update);
				statement2.execute("select * from deposits");
				
			}
				//document the action
				BalanceAction log = new BalanceAction ("updated monthly index rate",0, 0);
				_bankBalanceManager.addRecord
				(connection, log);
				
			}else
				System.out.println ("no index deposits");
			
		}catch (SQLException sqle)
		{
			System.out.println ("error reporting on deposit: " + sqle);
		}finally{
			try{
				statement.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
				}
				 }
		
	}
	
	public synchronized void depositCheck (Connection connection)
	{
		Statement statement = getStatement(connection);
		
		try{
			
			String find = "select * from deposits";
			boolean due = false;
			
			ResultSet rsCheck = statement.executeQuery(find);
			
			if (rsCheck.next())
			{
				ResultSet rs = statement.executeQuery(find);
				System.out.println ("checking for and closing dued deposits");
				while ((rs != null) && (rs.next()))
				{
					int id = rs.getInt("id");
					Date tempClose = rs.getDate("ending_date");
					Calendar calClose = Calendar.getInstance();
					calClose.setTime(tempClose);
					
					if (calClose.compareTo(Calendar.getInstance()) == -1)
					{
						System.out.println ("closing a due deposit");
						closeOrRemove_Deposit(id, connection, "closing");
						due = true;
					}
				}
				//document the action
				if (due)
				{
					BalanceAction log = new BalanceAction ("daily check for dued deposits",0, 0);
					_bankBalanceManager.addRecord
					(connection, log);
				}
			}else
			System.out.println ("no deposits");
		
	}catch (SQLException sqle)
	{
		System.out.println ("error reporting on deposit: " + sqle);
	}finally{
		try{
			statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
			 }
	}
	
	public synchronized void foreignUpdate (Connection connection, double dollarForeignRate)
	{
		Statement statement = getStatement(connection);
		Statement statement2 = getStatement(connection);
		
		try{
			
			String find = "select * from deposits where deposite_type = 'Foreign'";
			
			ResultSet rsCheck = statement.executeQuery(find);
			
			if (rsCheck.next())
			{
				ResultSet rs = statement.executeQuery(find);
				System.out.println ("updating the daily dollar rate change");
				while ((rs != null) && (rs.next()))
				{
					int id = rs.getInt("id");
					double amount = rs.getDouble("amount");
					double estimatedAmount = rs.getDouble("estimated_amount");
					double finalEstimatedAmount = rs.getDouble("final_estimated_amount");
					double dailyDollarRate = rs.getDouble("interest_rate");
					double penalty = MBankProperties.getDeposit_penalty();
										
					Date tempOpen = rs.getDate("opening_date");
					Calendar calOpen = Calendar.getInstance();
					calOpen.setTime(tempOpen);
					Date tempClose = rs.getDate("ending_date");
					Calendar calClose = Calendar.getInstance();
					calClose.setTime(tempClose);
					
					long amountOfDays = calc_ammountOfDays(calOpen, calClose);
					long currentAmountOfDays = calc_ammountOfDays(calOpen, Calendar.getInstance());
					
					dailyDollarRate =+ dollarForeignRate;			
					finalEstimatedAmount = (amount + (dailyDollarRate*amountOfDays*amount) );
					estimatedAmount = (amount + (dailyDollarRate*amount*currentAmountOfDays) - (penalty*amount) );
					
					String update = 
						("update deposits set " +
								"interest_rate = " + dailyDollarRate +
								", estimated_amount = " + estimatedAmount +
								", final_estimated_amount = " + finalEstimatedAmount +
								" where id = " + id
						);
						
				statement2.executeUpdate(update);
				statement2.execute("select * from deposits");
				
			}
				//document the action
				BalanceAction log = new BalanceAction ("updated daily dollar rate",0, 0);
				_bankBalanceManager.addRecord
				(connection, log);
				
			}else
				System.out.println ("no foreign deposits");
			
		}catch (SQLException sqle)
		{
			System.out.println ("error reporting on deposit: " + sqle);
		}finally{
			try{
				statement.close();
				statement2.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
				}
				 }
		
	}
	

	private synchronized String conditionCheck(String type, String condition) {
		
		if (type != "")
		{
			if (type.equals("customers"))
				type = "cust_id";
			else if (type.equals("type"))
			{
				type = "deposite_type";
				condition = "'"+condition+"'";
			}
			
			condition = "where " + type + " = " + condition;
		}
		return condition;
	}
		
		
}
