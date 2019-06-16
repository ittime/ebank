package mBank_files;
import java.sql.Connection;
import java.util.ArrayList;

import Swing_Files.MessagesAndQuestions;


public class ClientAction extends Action 
{
	CustomerManager _customerManager;
	DepositManager _depositManager;
	AccountManager _accountManager;
	
	Reports_Client _repClient;
	
	//constructor
	public ClientAction(Connection connection, MBank bank, Client client) {
		
		super(connection, bank);
		
		_actionType = "Customer";
		_client = client;
		_customerManager = bank.get_CustomerManager();
		_depositManager = get_bank().get_DepositManager();
		_accountManager = get_bank().get_AccountManager();
		
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
		AccountManager manager = get_bank().get_AccountManager();
		
		if (amount > 0)
		{
			manager.deposit(amount, _connection, _client, true);
		}
		else 
			System.out.println(_error[0]);
				
	}
	
	public boolean withdraw (int amount)
	{
		AccountManager manager = get_bank().get_AccountManager();
		
		if (amount > 0)
		{
			return (manager.withdraw(amount, _connection, _client));
		}
		else  
			{MessagesAndQuestions.showMessage(_error[0]); return false;}
	}
	
	public void accountDetails ()
	{
				
		AccountManager manager = get_bank().get_AccountManager();
		
		manager.accountDetails(_client, _connection);

		
		
	}
	
	//deposit-object methods
	
	public void addDeposit (ValueObject depositObject)
	{
		if (depositObject instanceof Deposit)
		{
			Deposit deposit = (Deposit)depositObject; //casting it as a Client object
			_depositManager.addDeposit(deposit, _connection, _bank);
		}
		else 
			System.out.println(_error[0]);
				
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
	
	public int reportBalance()
	{
		Account account = _repClient.reportBalance();
		
		return account.get_amount();
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
	
	

	
	
	
	
	
	
	
		
	
	
	
	

}
