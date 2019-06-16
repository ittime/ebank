package mBank_files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BankBalanceManager_DB extends BankBalanceManager {
	
	public synchronized void addRecord (Connection connection, BalanceAction log)
	{
		Statement statement = getStatement(connection);
		
		int lastId;
		
		synchronized (statement) {
			lastId = getLastBBId(connection);
		}
			
		try {
			String insertValues = 
				"INSERT INTO balance " +
				"VALUES (" + 
				lastId + ", '" +
				log.get_action() + "', " +
				log.get_actionAmmount() + ", '" +
				log.get_date() + "', " +
				log.get_clientId() + ")";
			
			statement.executeUpdate(insertValues);
			
			statement.executeQuery("SELECT * FROM balance");
			
			}catch (SQLException sqle)
			{
				System.out.println ("unable to add a report: " + sqle.getMessage());
			}finally {
				try{
					statement.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
				}
			}
		}

	private BalanceAction getBalanceAction (Connection connection, int id) throws SQLException
	{
		Statement statement = getStatement(connection);
		
		ResultSet rs = statement.executeQuery("select * from balance where id = " + id);
		
		rs.next();
		
		BalanceAction ba = new BalanceAction 
		(
				id,
				rs.getInt("client_id"),
				rs.getDouble("amount"),
				rs.getString("action"),
				rs.getString("date")
		);
		
		return ba;
		
	}
	
	public ArrayList<BalanceAction> reportsActionsHistory (Connection connection, Client client)
	{
		Statement statement = getStatement(connection);
		int id = client.get_clientId();
		ArrayList<BalanceAction> actions = new ArrayList<BalanceAction>();
		
		String find = "select * from balance where client_id =" + id;
		
		try 
		{
			ResultSet rs = statement.executeQuery(find);
			
			while ((rs != null) && (rs.next()))
			{
				int bbId = rs.getInt("id");
				actions.add(getBalanceAction(connection, bbId));
			}
			
			statement.close();
			
			return actions;
			
		}catch(SQLException sqle){
			System.out.println("error printing actions list"  + sqle.getMessage());
			try{
				statement.close();
				return actions;
			}catch (SQLException sqle2){
				System.out.println ("error closing connection " + sqle.getMessage());
				return actions;
				}}
			
		
		
	}
	
	public ArrayList<BalanceAction> reportBalanceChange (Connection connection)
	{
		Statement statement = getStatement(connection);
		
		ArrayList<BalanceAction> actions = new ArrayList<BalanceAction>();
		
		try{
			
			String find = "select * from balance";
			
			ResultSet rsCheck = statement.executeQuery(find);
			
			if (rsCheck.next())
			{
				ResultSet rs = statement.executeQuery(find);
				
				while ((rs != null) && (rs.next()))
				{
					double change = rs.getDouble("amount");
					if (change != 0)
					{
						int id = rs.getInt("id");
						actions.add(getBalanceAction(connection, id));
					}
						
				}
			}else
				System.out.println ("no acitons");
			
		}catch (SQLException sqle)
		{
			System.out.println ("error reporting on action: " + sqle);
		}finally{
			try{
				statement.close();
				}catch (SQLException sqle){
					System.out.println (sqle.getMessage());
				}
				 }
		
		return actions;
		
	}

	private synchronized int getLastBBId(Connection connection)
	{
		int lastBBId = 11111;
		
		Statement statement = getStatement(connection);
		
		try{
		ResultSet rs = statement.executeQuery("select Max(id) from balance;");
		
		rs.next();
		
		lastBBId = rs.getInt(1);
		
		lastBBId++;
		
		
		}catch (SQLException sqle){
			System.out.println (sqle.getMessage());
			
		}finally {
			try{
				statement.close();
			}catch (SQLException sqle){
				System.out.println (sqle.getMessage());
			}
		}
		
		
		return lastBBId;                                                                                                         
			
		}
	
	
}
