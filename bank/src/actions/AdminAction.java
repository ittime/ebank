package actions;
import java.sql.Connection;
import java.util.ArrayList;

import reports.Reports_Admin;

import MBank.MBank;

import mBankProperties.MBankProperties;
import managers.AccountManager;
import managers.AccountManager_DB;
import managers.CustomerManager;
import managers.DepositManager;
import managers.DepositManager_DB;

import valueObject.Account;
import valueObject.Account_Gold;
import valueObject.Account_Platinum;
import valueObject.Account_Regular;
import valueObject.BalanceAction;
import valueObject.Client;
import valueObject.Deposit;
import valueObject.ValueObject;






public class AdminAction extends Action 
{
	CustomerManager _customerManager;
	DepositManager _depositManager;
	AccountManager _accountManager;
	
	Reports_Admin _repAdmin;
	
	// the constructor
	public AdminAction(Connection connection, MBank bank) {
		super(connection, bank);
		
		_customerManager = bank.get_CustomerManager();
		_depositManager = get_bank().get_DepositManager();
		_accountManager = get_bank().get_AccountManager();
		
		_repAdmin = new Reports_Admin (connection);
}
	
	
	//
	//methods
	//
	
	public void addCustomer(ValueObject clientObject, ValueObject accountObject)
	{
		if ((clientObject instanceof Client) && (accountObject instanceof Account)
			&&
			(((Account)accountObject).get_amount() > 0)) //check it the amount isn't negative
		{
			
			Account account = (Account) accountObject;
			
			Client client = (Client)clientObject; //casting it as a Client object
			
			Client pack = new Client (client, account);
			
			pack = decideType(pack);
			
			_customerManager.addCustomer(pack.get_packClient(), pack.get_packAccount(), _connection, _accountManager);
		}
		else 
			System.out.println(_error[0]);
	}
	
	private Client decideType (Client pack)
	{
		int amount = pack.get_packAccount().getAmmount();
		Account account = pack.get_packAccount();
		Client client = pack.get_packClient();
		
		if (amount < 10000)
		{
			account = new Account_Regular (account.get_AccountId(), account.get_ClientId(), account.getAmmount(), account.get_accountComments());
			client.set_clientRank(MBankProperties.getCustomer_r_rank());
		}
		else if (amount < 100000)
		{
			account = new Account_Gold (account.get_AccountId(), account.get_ClientId(), account.getAmmount(), account.get_accountComments());
			client.set_clientRank(MBankProperties.getCustomer_g_rank());
		}
		else
		{
			account = new Account_Platinum (account.get_AccountId(), account.get_ClientId(), account.getAmmount(), account.get_accountComments());
			client.set_clientRank(MBankProperties.getCustomer_p_rank());
		}
		
		return (new Client (client, account));
		
	}


	public void removeCustomer (ValueObject clientObject)
	{
		if (clientObject instanceof Client)
		{
			Client remvClient = (Client)clientObject; //casting it as a Client object
			
			_customerManager.removeCustomer(remvClient, _connection, (AccountManager_DB)_accountManager, (DepositManager_DB) _depositManager);
		}
		else 
			System.out.println(_error[0]);
		
	}
	
	public void changeDetails (ValueObject clientObject)
	{
		CustomerManager manager = get_bank().get_CustomerManager();
		
		if (clientObject instanceof Client)
		{
			Client details = (Client)clientObject; //casting it as a Client object
			
			manager.changeDetails(details, _connection);
		}
		else 
			System.out.println(_error[0]);
		
	}
	
	public Client reportClient (Client client)
	{
		return (_repAdmin.reportClient(client));
	}
	
	public ArrayList<Client> reportClientsList ()
	{
		return (_repAdmin.reportClientList());
	}
	
	public ArrayList<Client> reportClientsList (String type)
	{
		return (_repAdmin.reportClientList(type));
	}
	
	public ArrayList<Account> reportAccountsList ()
	{
		return (_repAdmin.reportAccountList());
	}
	
	public Deposit reportDeposit (Deposit deposit)
	{
		return (_repAdmin.reportDeposit(deposit));
	}


	public ArrayList<Deposit> reportDepositsList ()
	{
		return (_repAdmin.reportDepositsList());
	}
	
	public ArrayList<Deposit> reportDepositsList (String type)
	{
		return (_repAdmin.reportDepositsListByType(type));
	}
	
	public ArrayList<ArrayList<Deposit>> reportDepositListbyType ()
	{
		return (_repAdmin.reportDepositListbyType());
	}
	
	public ArrayList<BalanceAction> reportBalanceChange ()
	{
		return (_repAdmin.reportBalanceChange());
	}
	
	public String repCalcBalance ()
	{
		return (_repAdmin.calcBalance());
	}
}
