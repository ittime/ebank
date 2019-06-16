package mBank_files;
import java.sql.Connection;
import java.util.ArrayList;


public class AdmininstratorMain {

	public static void main(String[] args) 
	{
		//create/get the bank
		MBank bank = MBank.createBank();
		
		//login
		int password = 123;
		AdminAction action = (AdminAction) bank.accessManager (password);
		
		if (action == null)
				System.out.println ("Some problem occured with the login procedure. Please try again!");
			
			else // an action was created 
			{
				
				//add a client method
				Client client = new Client ("Rinat Gamliel", 33002, 547411147,
						"rinatgamliel@gmail.com", "at his parents house", "a cute person!");
				
				Account account = new Account (2000, "none");
				
				action.addCustomer(client, account);
				//
				Client client2 = new Client ("King Khan", 68541, 547488127,
						"kkbbq@012.net.il", "Berlin", "a musician");
				
				Account account2 = new Account (300000, "richy rich");
				
				action.addCustomer(client2, account2);
				
//
//				action.removeCustomer(new Client (6));
//				
//				//update a client's details method
//				Client details = new Client ("Gold", "The Hatch", 2);
//				
//				action.changeDetails(details);
//				
				
//				action.removeDeposit
//				(new Deposit (5));
				
//				
				System.out.println (action.reportClient(new Client (3)));
				
				
//				ArrayList<Client> clients = action.reportClientsList("Regular");
//				
//				for (Client clientt:clients)
//				{
//					System.out.println (clientt);
//				}
//				
//				
//				ArrayList<Account> accounts = action.reportAccountsList();
//				
//				if (accounts != null)
//					for (Account accountt:accounts)
//					{
//						System.out.println (accountt);
//					}
//				else
//					System.out.println("problem with retrieving the account list");
//					
//				ArrayList<Deposit> deposits = action.reportDepositsList();
//				
//				if (deposits != null)
//					for (Deposit depositt:deposits)
//					{
//						System.out.println (depositt);
//					}
//				else
//					System.out.println("problem with retrieving the deposits list");
//			
				
//				System.out.println (action.reportDeposit(new Deposit (3)));
//				
//				ArrayList<ArrayList<Deposit>> deposits = action.reportDepositListbyType();
//								
//				if (deposits != null)
//				{
//					for (ArrayList<Deposit> deposits2:deposits)
//					{
//						System.out.println ((deposits2.get(0).get_interestType()) + " :");
//						for (Deposit depositt:deposits2)
//						{
//							System.out.println (depositt);
//						}
//					}
//				}else
//				System.out.println("problem with retrieving the deposits list");
//				
			
//				ArrayList<BalanceAction> actions = action.reportBalanceChange();
				
//				if (actions != null)
//					for (BalanceAction bAction:actions)
//					{
//						System.out.println (bAction);
//					}
//				else
//					System.out.println("problem with retrieving the actions list");
//					
				
				System.out.println ("the bank balance is: " + action.repCalcBalance());
				
				
			}
		
		
		
		
	}

	}

