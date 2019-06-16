package mBank_files;
import java.sql.Connection;
import java.util.ArrayList;


public class Reports_Customer extends Reports_Manager {
	
	CustomerManager _DB_Manager;
	
	public Reports_Customer() 
	{
		_DB_Manager = (CustomerManager_DB)MBank.get_bank().get_CustomerManager();
		
	}
	
	public Client reportClient (Client client, Connection connection)
	{
		int clientId = client.get_clientId();
		
		System.out.println ("client's details:");
		
		return 
		(_DB_Manager.reportClient(clientId, connection));
	}
	
	public ArrayList<Client> reportClientList(Connection connection, String type)
	{
		System.out.println ("clients list:");
		return (_DB_Manager.reportClientList(connection, type));		

	}
	
	
	
	




}
