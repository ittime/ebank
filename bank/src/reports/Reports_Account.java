package reports;
import java.sql.Connection;
import java.util.ArrayList;

import MBank.MBank;

import managers.AccountManager;
import managers.AccountManager_DB;

import valueObject.Account;
import valueObject.Client;






public class Reports_Account extends Reports_Manager 
{
	AccountManager _DB_Manager;
	
	public Reports_Account ()
	{
		_DB_Manager = (AccountManager_DB) MBank.get_bank().get_AccountManager();
	}
	
	public Account reportAccount (Client client, Connection connection)
	{
		System.out.println ("Account details:");
		return (_DB_Manager.reportAccount(client , connection));
	}
	
	public ArrayList<Account> reportAccountList (Connection connection)
	{
		System.out.println ("Accounts list:");
		return (_DB_Manager.reportAccountList(connection));
	}
	
	
}
