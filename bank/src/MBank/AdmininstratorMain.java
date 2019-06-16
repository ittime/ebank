package MBank;
import java.util.ArrayList;

import actions.AdminAction;

import valueObject.Account;
import valueObject.BalanceAction;
import valueObject.Client;
import valueObject.Deposit;





public class AdmininstratorMain {

	public static void main(String[] args) 
	{
		//create/get the bank
		MBank bank = MBank.createBank();
		
		//login
		int password = 123;
		String userName = "admn";
		AdminAction action = (AdminAction) bank.accessManager (userName, password);
		
		if (action == null) //wrong password
				System.out.println ("Some problem occured with the login procedure. Please try again!");
			
		else // an action was created 
			{
				
				//add a client method
				Client client = new Client ("Bjork", "jorka", 55555, 5413392,
						"bjork@bjork.com", "Iceland/London", "magnificent");
				Account account = new Account (200000, "music industry");
				action.addCustomer(client, account);
				//

				//add another client
				Client client2 = new Client ("Temp Tempy", "tempist", 68541, 5034421,
						"temp@temp.temp", "Mean while", "none");
				Account account2 = new Account (10, "temp");
				action.addCustomer(client2, account2);
				//
				
				//remove a customer
				action.removeCustomer(new Client (7));
			
				//update a client's details method
				Client details = new Client ("Gold", "The Hatch", 2);
				action.changeDetails(details);

				//print a report on a specific client
				System.out.println (action.reportClient(new Client (3)));
				
				
				//print a list of the clients from a specific type
				ArrayList<Client> clients = action.reportClientsList("Regular");
				
				for (Client clientt:clients)
				{
					System.out.println (clientt);
				}
				
				ArrayList<Client> clientsGold = action.reportClientsList("Gold");
				
				for (Client clientt:clientsGold)
				{
					System.out.println (clientt);
				}
				//
				
				//print a list of all of the accounts
				ArrayList<Account> accounts = action.reportAccountsList();
				
				if (accounts != null)
					for (Account accountt:accounts)
					{
						System.out.println (accountt);
					}
				else
					System.out.println("problem with retrieving the account list");
				//
				
				//print a list of all of the deposits				
				ArrayList<Deposit> deposits = action.reportDepositsList();
				
				if (deposits != null)
					for (Deposit depositt:deposits)
					{
						System.out.println (depositt);
					}
				else
					System.out.println("problem with retrieving the deposits list");
				//

				//print a report on a specific deposit
				System.out.println (action.reportDeposit(new Deposit (3)));
				
				//print a list of the deposits organized by type
				ArrayList<ArrayList<Deposit>> depositsList = action.reportDepositListbyType();
								
				if (depositsList != null)
				{
					for (ArrayList<Deposit> depositsList2:depositsList)
					{
						boolean possible = true;
						try{
							depositsList2.get(0);
						}catch (IndexOutOfBoundsException iboe){possible=false;}
						if (possible)
						{
							System.out.println ((depositsList2.get(0).get_interestType()) + " :");
							for (Deposit depositt:depositsList2)
								{
								System.out.println (depositt);
								}
						}
					}
				}else
				System.out.println("problem with retrieving the deposits list");
				
			
				//print a list of the actions that changed the bank's balance
				ArrayList<BalanceAction> actions = action.reportBalanceChange();
				
				if (actions != null)
					for (BalanceAction bAction:actions)
					{
						System.out.println (bAction);
					}
				else
					System.out.println("problem with retrieving the actions list");
					
				
				//print the bank's current balance
				System.out.println ("the bank balance is: " + action.repCalcBalance());
				
			}
	}
}

