package mBank_files;
import java.sql.Connection;
import java.util.ArrayList;


public class Reports_Admin extends Reports_General {
	
		Reports_Account _repAccount;
		Reports_Customer _repCustomer;
		Reports_Deposit _repDeposit;
		Reports_BalanceAction _repBalanceAction;
		
		Connection _connection;
		
		Client _client;
		
		
		public Reports_Admin (Connection connection)
		{
			_repAccount = get_repAccount();
			_repCustomer = get_repCustomer();
			_repDeposit = get_repDeposit();
			_repBalanceAction = get_repBalanceAction();
			
			_connection = connection;
					
		}
		
		public Client reportClient (Client client)
		{
			return (_repCustomer.reportClient(client, _connection));
		}
		
		public ArrayList<Client> reportClientList()
		{
			return (_repCustomer.reportClientList(_connection, "id"));
		}
		
		public ArrayList<Client> reportClientList(String type)
		{
			return (_repCustomer.reportClientList(_connection, type));
		}
		
		public ArrayList<Account> reportAccountList ()
		{
			return (_repAccount.reportAccountList(_connection));
		}
		
		public Deposit reportDeposit (Deposit deposit)
		{
			return (_repDeposit.reportDeposit(deposit, _connection));
		}
		
		public ArrayList<Deposit> reportDepositsList ()
		{
			return (_repDeposit.reportDepositList(_connection));
		}
		
		public ArrayList<Deposit> reportDepositsListByType (String type)
		{
			return (_repDeposit.reportDepositListbyType(type, _connection));
		}
		
		public ArrayList<ArrayList<Deposit>> reportDepositListbyType ()
		{
			return (_repDeposit.reportDepositListbyType(_connection));
		}
		
		public ArrayList<BalanceAction> reportBalanceChange ()
		{
			return (_repBalanceAction.reportBalanceChange(_connection));
		}
		
		public String calcBalance ()
		{
			return (_repBalanceAction.calcBalance(_connection));
		}
	
	

}
