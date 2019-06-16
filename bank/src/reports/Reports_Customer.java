package reports;
import java.sql.Connection;
import java.util.ArrayList;

import MBank.MBank;

import managers.CustomerManager;
import managers.CustomerManager_DB;

import valueObject.Client;





public class Reports_Customer extends Reports_Manager {
	
	CustomerManager _DB_Manager;
	
	public Reports_Customer() 
	{
		_DB_Manager = (CustomerManager_DB)MBank.get_bank().get_CustomerManager();
		
	}
	
	public Client reportClient (Client client, Connection connection)
	{
		int clientId = client.get_clientId();
		
		System.out.println ("Client's details:");
		
		return 
		(_DB_Manager.reportClient(clientId, connection));
	}
	
	public ArrayList<Client> reportClientList(Connection connection, String type)
	{
		System.out.println ("Clients list:");
		return (_DB_Manager.reportClientList(connection, type));	
	}
	
	
	
	




}
