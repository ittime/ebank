package MBank;
import java.util.ArrayList;
import java.util.Calendar;

import actions.ClientAction;

import valueObject.BalanceAction;
import valueObject.Client;
import valueObject.Deposit;
import valueObject.Deposit_Constant;
import valueObject.Deposit_Foreign;
import valueObject.Deposit_Index;





public class CustomerMain {

	public static void main(String[] args) {
		
		
		MBank bank = MBank.createBank();
		
		int password = 33002 ;
		String userName = "ringa";
		ClientAction action = (ClientAction) bank.accessManager (userName, password);
		
		if (action != null)
		// an action was created 
			{
				//update client details
				Client details = new Client (547433321, "ringa@gmail.com", "Rabinov 14");
				System.out.println ("Updating client <<"+ action.get_client().get_clientName() + ">> details");
				action.updateDetails 
				(details);
				
//				add deposit method
				System.out.println ("check the wrong data-type sequence:");
				action.addDeposit(new Client(2)); // to check the wrong data-type sequence
				
				//add deposits
				System.out.println ("adding a deposit");
				action.addDeposit
				(new Deposit_Constant (action.get_client().get_clientId(), 30000, Calendar.getInstance(), createDate(14, Calendar.JULY, 2018)));

				System.out.println ("adding a deposit");
				action.addDeposit
				(new Deposit_Index (action.get_client().get_clientId(), 10000, Calendar.getInstance(), createDate(10, Calendar.AUGUST, 2008)));
				
				System.out.println ("adding a deposit");
				action.addDeposit
				(new Deposit_Foreign (action.get_client().get_clientId(), 400, Calendar.getInstance(), createDate(21, Calendar.JULY, 2018)));

				System.out.println ("adding a deposit");
				action.addDeposit
				(new Deposit_Constant (action.get_client().get_clientId(), 2000, Calendar.getInstance(), createDate(20, Calendar.JULY, 2018)));

				System.out.println ("adding a deposit");
				action.addDeposit
				(new Deposit_Index (action.get_client().get_clientId(), 4150, Calendar.getInstance(), createDate(21, Calendar.JULY, 2018)));
				
				System.out.println ("adding a deposit");
				action.addDeposit
				(new Deposit_Foreign (action.get_client().get_clientId(), 10000, Calendar.getInstance(), createDate(15, Calendar.JULY, 2018)));

				
				////				

				
				//***
				//deposit money method
				
				System.out.println ("depositing to the account");
				action.deposit(6088);
				
				//
				//***
				
				
				//***
				//withdraw money method
				System.out.println ("withdrawing from the account");
				action.withdraw (2300);
				//
				//***
				
				//***
				//
				action.breakDeposit
				(new Deposit(1));
				//
				//***
				
				//print the client's details
				System.out.println (action.reportClient());
				
				//print the actions history
				ArrayList<BalanceAction> actions = action.reportsActionsHistory();
			
				int i =1;
				for (BalanceAction ba:actions)
				{
					System.out.println (i+" (" + ba);
					i++;
				}
				//
				
				
				//print the client's account details
				System.out.println (action.reportAccount());
				//
				
				//print a deposit details
				System.out.println (action.reportDeposit(new Deposit (2)));
				//

				//print the details of a non-existent deposit
				System.out.println ("print the details of a non-existent deposit");
				Deposit dd = action.reportDeposit(new Deposit (999));
				if (dd != null)
				System.out.println (dd);
				//
			
				//print the details of all of the deposits
				ArrayList<Deposit> deposits = action.reportDepositsList();
				if (deposits != null)
					for (Deposit depositt:deposits)
						System.out.println (depositt);
				else
					System.out.println("problem with retrieving the deposits list");
			}
	}
				
			
	
	private static Calendar createDate(int day, int month, int year) //return a calendar with the day-month-year data 
	{
		Calendar cal = Calendar.getInstance();
		
		cal.set(year, month, day);
		
		return cal;
	}

}


	
