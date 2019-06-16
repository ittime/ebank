package reports;
import java.sql.Connection;
import java.util.ArrayList;

import valueObject.Account;
import valueObject.BalanceAction;
import valueObject.Client;
import valueObject.Deposit;




public class Reports_Client extends Reports_General {
	
	Reports_Account _repAccount;
	Reports_Customer _repCustomer;
	Reports_Deposit _repDeposit;
	Reports_BalanceAction _repBalanceAction;
	
	Connection _connection;
	
	Client _client;
	
	
	public Reports_Client (Connection connection, Client client)
	{
		_repAccount = get_repAccount();
		_repCustomer = get_repCustomer();
		_repDeposit = get_repDeposit();
		_repBalanceAction = get_repBalanceAction();
		
		_connection = connection;
		_client = client;
		
	}
	
	public Client reportClient ()
	{
		return (_repCustomer.reportClient(_client, _connection));
	}
	
	public ArrayList<BalanceAction> reportsActionsHistory ()
	{
		return (_repBalanceAction.reportsActionsHistory(_connection, _client));
	}
	
	public ArrayList<BalanceAction> reportsActionsHistoryByParmtr (String parameter){
		return (_repBalanceAction.reportsActionsHistoryByParmtr(_connection, _client, parameter));
	}
	
	public Account reportAccount ()
	{
		return (_repAccount.reportAccount(_client, _connection));
		
	}
	
	public Deposit reportDeposit (Deposit deposit)
	{
		return(_repDeposit.reportDeposit(deposit, _connection));
	}
	
	public ArrayList<Deposit> reportDepositsList ()
	{
		String clientId = "" + _client.get_clientId();
		
		return (_repDeposit.reportDepositListbyClient (clientId, _connection));
	}
	
	public int reportDepositsBalance (){
		String clientId = "" + _client.get_clientId();
		
		return (_repDeposit.reportDepositsBalance(clientId, _connection));
	}

}


