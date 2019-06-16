package managers;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import reports.Reports_General;



public abstract class Manager 
{
	static Reports_General _repManager;
	
	public Manager (){};
	
	public Statement getStatement(Connection connection)
	{
		@SuppressWarnings("unused")
		Statement statement = null;
		try
		{
			return (statement = connection.createStatement());
		}catch(SQLException sqle){
			System.out.println("could not create statement");
			return null;
		}
	}
		
	
	
	
}
