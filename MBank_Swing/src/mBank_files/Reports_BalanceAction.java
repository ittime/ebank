package mBank_files;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class Reports_BalanceAction extends Reports_Manager 
{
	BankBalanceManager _DB_Manager;
	
	public Reports_BalanceAction()
	{
			_DB_Manager = MBank.get_bank().get_BankBalanceManager();
	}
	
	public ArrayList<BalanceAction> reportsActionsHistory (Connection connection, Client client)
	{
		//System.out.println ("Client's actions history");
		
		return (_DB_Manager.reportsActionsHistory(connection, client));
	}
	
	public ArrayList<BalanceAction> reportBalanceChange (Connection connection)
	{
		System.out.println ("Actions that changed the bank balance:");
		
		return (_DB_Manager.reportBalanceChange(connection));
	}
	
	public String calcBalance (Connection connection)
	{
		System.out.println ("Calculating the bank's balance");
		ArrayList<BalanceAction> balanceArray = _DB_Manager.reportBalanceChange(connection);
		double balance =0;
		
		if (balanceArray != null)
			for (BalanceAction bAction:balanceArray)
			{
				balance += bAction.get_actionAmmount();
			}
		else
			System.out.println("problem with retrieving calculating the balance");
		
		return (formatFloatPoint (balance));
			
	}

	private String formatFloatPoint(double balance) {
		
		String format = "0.000";
		
		DecimalFormat df = new DecimalFormat (format);
		
		return (df.format(balance));
		
	}


}
