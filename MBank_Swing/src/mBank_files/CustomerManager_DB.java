package mBank_files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;


public class CustomerManager_DB extends CustomerManager {
	
BankBalanceManager _bankBalanceManager;
	
	public CustomerManager_DB(BankBalanceManager bankBalanceManager) {_bankBalanceManager = bankBalanceManager;}


	public Client checkExistence(int password, Connection connection) 
	{
		Statement statement = getStatement(connection);
		
		Client client = null;
		
		if (statement != null)
		{
			try {
				ResultSet rs = statement.executeQuery("select * from customers");
				boolean found = false;
				while( (rs.next()) && (!found))
						{
							int passwordDB = rs.getInt ("password");
						    
							if (password == passwordDB) //if the password was found
						    	{
						    	
						    	//extract a Client from the rs 
						    	
						    	int id =  rs.getInt("id");
								Client tempClient = new Client (id);
																
								tempClient.set_clientName(rs.getString("name"));
								tempClient.set_clientRank(rs.getString("rank"));
								tempClient.set_clientPassword(passwordDB);
								tempClient.set_clientPhoneNumber(rs.getInt("phone"));
								tempClient.set_clientMail(rs.getString("email"));
								tempClient.set_clientAddress(rs.getString("address"));
								tempClient.set_clientComments(rs.getString("comments"));
																
								client = tempClient;
								
						    	found = true;
						    	}
						}
						rs.close();
						statement.close();
						}catch(SQLException sqle){
							System.out.println("error finding client"  + sqle.getMessage());
						}finally {
							try{
								statement.close();
								connection.close();
							}catch (SQLException sqle){
								System.out.println ("error closing connection " + sqle.getMessage());
							}
						}
		
					}
		
		return client;
		
		
		
						
						
				
	}

	
	@Override
	public void UpdateDetails(Client client, Connection connection) 
	{
		Statement statement = getStatement(connection);
		
		if (statement != null)
		{
			try {
				
				String insert = ("UPDATE customers " + 
								 "SET phone = " + client.get_clientPhoneNumber() + 
								 	",email = '" + client.get_clientMail() +
								   "',address = '" + client.get_clientAddress() +
							   "' WHERE id = " + client.get_clientId());

				statement.executeUpdate(insert);
				statement.execute("select * from customers");
				
				//document the action
				BalanceAction log = new BalanceAction ("updated personal details",0, client.get_clientId());
				_bankBalanceManager.addRecord
				(connection, log);
				
				System.out.println ("the new details were successfully updated!");
				statement.close();
		
			}catch(SQLException sqle){
				System.out.println(sqle.getMessage());
			}finally {
				try{
					statement.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
				}
			}}		
	}

	public void addCustomer(Client client, Account account, Connection connection, AccountManager accountManager)
	{
		Statement statement = getStatement(connection);
		
		int lastId = getLastCustomerId(connection);
		
		try {
			
			if (checkPassword(client.get_clientPassword(), connection))
			{
				String insertValues = 
					"INSERT INTO customers " +
					"VALUES (" + 
					lastId + ", '" +
					client.get_clientName() + "', '" +
					client.get_clientRank() + "', " +
					client.get_clientPassword() + ", " +
					client.get_clientPhoneNumber() + ", '" +
					client.get_clientMail() + "', '" + 
					client.get_clientAddress() + "', '" +
					client.get_clientComments() + "');";
				
				statement.executeUpdate(insertValues);
				
				statement.executeQuery("SELECT * FROM customers");
				
				accountManager.addAccount(account, lastId, connection);
				
				//document the action
				BalanceAction log = new BalanceAction ("add new client (CID: "+lastId+", " + client.get_clientName() +")",0, 0);
				_bankBalanceManager.addRecord
				(connection, log);
				
				System.out.println ("client no. " + lastId + " was added successfuly.");
				
			}else
				System.out.println ("this password already exist. Please change it.");
				}catch (SQLException sqle)
				{
					System.out.println ("unable to add a client: " + sqle.getMessage());
				}finally {
					try{
						statement.close();
					}catch (SQLException sqle){
						System.out.println (sqle.getMessage());
					}
				}
			}
			
				
				
	
	public void removeCustomer (Client remvClient, Connection connection, AccountManager_DB actManager, DepositManager_DB dpsManager)
	{
		Statement statement = getStatement(connection);
		
		int clientId = remvClient.get_clientId();
		
		try {
			
			if (exist ("CUSTOMERS","id", clientId, connection))
			{
				
				
			System.out.println ("deleting customer (id no. "+ clientId + ")");
				
			
			System.out.println ("deleting the customer's deposits");
			dpsManager.removeDeposits(clientId, connection);
			System.out.println ("removing the customer's account");
			actManager.removeAccount(clientId, connection);
		
		
			String command = ("delete from CUSTOMERS where id = " + clientId);
			
			statement.executeUpdate(command);
			System.out.println ("the customer (id = "+clientId+") was succesfuly deleted");
			
			//document the action
			BalanceAction log = new BalanceAction ("removed a client (CID: " + clientId + ")", 0, 0);
			
			_bankBalanceManager.addRecord
			(connection, log);
			
			}
			else
				System.out.println ("this user doesn't exist");
			
		}catch (SQLException sqle)
		{
			System.out.println ("unable to add a client: " + sqle.getMessage());
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}
	}
	
	
	@Override
	public void changeDetails(Client client, Connection connection) 
	{
		Statement statement = getStatement(connection);
		
		if (statement != null)
		{
			try {
				
				String insert = ("UPDATE customers " + 
								 "SET rank = '" + client.get_clientRank() + 
								 	"', comments = '" + client.get_clientComments() +
							   "' WHERE id = " + client.get_clientId());

				statement.executeUpdate(insert);
				statement.execute("select * from CUSTOMERS");
				
				//document the action
				BalanceAction log = new BalanceAction ("updating clients details (CID:"+client.get_clientId()+")",0, 0);
				_bankBalanceManager.addRecord
				(connection, log);
				
				System.out.println ("the new details were successfully updated!");
				statement.close();
		
			}catch(SQLException sqle){
				System.out.println(sqle.getMessage());
			}finally {
				try{
					statement.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
				}
			}}		
	}

	private Client report_client (int clientId, Connection connection) throws SQLException
	{
		Statement statement = getStatement(connection);
		
		Client client;
		
		ResultSet rs = statement.executeQuery("select * from customers where id =" +clientId);
		rs.next();
		
		Client tempClient = new Client (clientId);
		
		tempClient.set_clientName(rs.getString("name"));
		tempClient.set_clientRank(rs.getString("rank"));
		tempClient.set_clientPassword(rs.getInt("password"));
		tempClient.set_clientPhoneNumber(rs.getInt("phone"));
		tempClient.set_clientMail(rs.getString("email"));
		tempClient.set_clientAddress(rs.getString("address"));
		tempClient.set_clientComments(rs.getString("comments"));
										
		client = tempClient;
				
		return client;
	}
	
	public Client reportClient (int clientId, Connection connection)
	{
		Statement statement = getStatement(connection);
		
		Client client = null;
		
		try{
			if (exist("customers", "id", clientId, connection))
			{
				client = report_client(clientId, connection);
			}
			else{
				System.out.println ("this client doesn't exist");
				return client;
				}		
						
		}catch(SQLException sqle){
			System.out.println("error printing client"  + sqle.getMessage());
			try{
				statement.close();
				connection.close();
			}catch (SQLException sqle2){
				System.out.println ("error closing connection " + sqle.getMessage());
				}}
			
			return client;
	}
	
	public ArrayList<Client> reportClientList (Connection connection, String type)
	{
		Statement statement = getStatement(connection);
		ArrayList<Client> clients = new ArrayList<Client>();
		
		String search = "select * from Customers";
		
		try
		{
			ResultSet rs = statement.executeQuery(search);
			
			while ((rs != null) && (rs.next()))
			{
				int clientId = rs.getInt("id");
				
				if ( (type == "id"))
				{
					clients.add(report_client(clientId, connection));
				}
				else
				{
					String rank = rs.getString("rank");
					if (rank.equals(type))
						clients.add(report_client(clientId, connection));
				}
			}
			
			statement.close();
						
			return clients;
			
		}catch(SQLException sqle){
			System.out.println("error printing clients list"  + sqle.getMessage());
			try{
				statement.close();
				return clients;
			}catch (SQLException sqle2){
				System.out.println ("error closing connection " + sqle.getMessage());
				return clients;
				}}
			
			
	}
			
			
		
		
	//System Methods
		

	private boolean checkPassword (int password, Connection connection) throws SQLException
	{
		if (exist ("customers", "password", password, connection))
			return false;
		
		return true;
		
	}
	
	
	private int getLastCustomerId(Connection connection) 
	{
	
		int lastCustomerId = 0;
		
		Statement statement = getStatement(connection);
		
		try
		
		{
			ResultSet rs = statement.executeQuery("select Max(id) from customers;");
			rs.next();
			lastCustomerId = rs.getInt(1);
			lastCustomerId++;
		
		
		}catch (SQLException sqle){
			System.out.println (sqle.getMessage());
			
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}
		
		
		return lastCustomerId;                                                                                                         
			
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
	
	


