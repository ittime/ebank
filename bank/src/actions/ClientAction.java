package actions;
import java.sql.Connection;
import java.util.ArrayList;

import reports.Reports_Client;

import MBank.MBank;

import managers.AccountManager;
import managers.CustomerManager;
import managers.DepositManager;

import valueObject.Account;
import valueObject.BalanceAction;
import valueObject.Client;
import valueObject.Deposit;
import valueObject.ValueObject;






public class ClientAction extends Action 
{
	CustomerManager _customerManager;
	DepositManager _depositManager;
	AccountManager _accountManager;
	
	Reports_Client _repClient;
	
	//constructor
	public ClientAction(Connection connection, MBank bank, Client client) {
		
		super(connection, bank);
		
		_client = client;
		_customerManager = bank.get_CustomerManager();
		_depositManager = bank.get_DepositManager();
		_accountManager = bank.get_AccountManager();
		
		_repClient = new Reports_Client (connection, _client);
		
	}
	

	//
	//methods
	//
	
	//customerManager methods
	
	public void updateDetails (ValueObject clientObject)
	{
		if (clientObject instanceof Client)
		{
			Client client = (Client)clientObject; //casting it as a Client object
			
			client.set_clientId(_client.get_clientId());
			
			_customerManager.UpdateDetails(client, _connection);
		}
		else 
			System.out.println(_error[0]);
	
	}
	
	//_account-object methods
	
	public void deposit (int amount)
	{
		if (amount > 0)
		{
			_accountManager.deposit(amount, _connection, _client, true);
		}
		else 
			System.out.println(_error[0]);
				
	}
	
	public double getCommission (int amount){
		
		return (_accountManager.getCommission(_client, amount, _connection));		
	}
	
	public void withdraw (int amount)
	{
		if (amount > 0)
		{
			_accountManager.withdraw(amount, _connection, _client);
		}
		else 
			System.out.println(_error[0]);
				
	}
	
		
	//deposit-object methods
	
	public int addDeposit (ValueObject depositObject)
	{
		if (depositObject instanceof Deposit)
		{
			Deposit deposit = (Deposit)depositObject; //casting it as a Client object
			return (_depositManager.addDeposit(deposit, _connection, _bank));
		}
		else {
			System.out.println(_error[0]);
			return -1;}
				
	}
	
	public void breakDeposit (ValueObject depositObject)
	{
		if (depositObject instanceof Deposit)
		{
			Deposit deposit = (Deposit)depositObject; //casting it as a Client object
			_depositManager.breakDeposit(deposit, _connection);
		}
		else 
			System.out.println(_error[0]);
				
	}
	
	//reports methods
	
	public Client reportClient ()
	{
		return (_repClient.reportClient());
	}
	
	public ArrayList<BalanceAction> reportsActionsHistory ()
	{
		return (_repClient.reportsActionsHistory());
	}
	
	public Account reportAccount()
	{
		return (_repClient.reportAccount());
	}
	
	public Deposit reportDeposit(Deposit deposit)
	{
		return (_repClient.reportDeposit(deposit));
	}
	
	public ArrayList<Deposit> reportDepositsList ()
	{
		return (_repClient.reportDepositsList());
	}
	
	public double getInterest(String type){
		return (_depositManager.decideInterest(type));
	}
	
	public ArrayList<BalanceAction> reportActionsHistory (String parameter){
		return (_repClient.reportsActionsHistoryByParmtr(parameter));
	}
	
	public int reportDepositsBalance () {
		return (_repClient.reportDepositsBalance());
	}
	
	public static String formatResult (int origNumber){
		
		String number = ""+origNumber;
		int count = 0;
		String formatNumber = "";
		
		for (int i=number.length(); i > 0; i--){
			count++;
			formatNumber = number.charAt(i-1)+formatNumber;
			
			if (count == 3 && i!= 1){
				formatNumber = ","+formatNumber;
				count=0;
			}
		}
		
		return(formatNumber);
	}
	
	

	
	
	
	
	
	
	
		
	
	
	
	

}
