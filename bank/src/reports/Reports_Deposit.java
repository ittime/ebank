package reports;
import java.sql.Connection;
import java.util.ArrayList;

import MBank.MBank;

import managers.DepositManager;

import valueObject.Deposit;






public class Reports_Deposit extends Reports_Manager 
{
	DepositManager _DB_Manager;
	
	public Reports_Deposit() 
	{
		_DB_Manager = MBank.get_bank().get_DepositManager();
	}
	
	public Deposit reportDeposit (Deposit deposit, Connection connection)
	{
		System.out.println ("Deposit details:");
		
		int depositId = deposit.get_depositId();
		
		return (_DB_Manager.reportDeposit(depositId, connection));
	}
	
	public ArrayList<Deposit> reportDepositList (Connection connection)
	{
		System.out.println ("Deposits list:");
		return (_DB_Manager.reportDepositsList(connection, "", ""));
	}
	
	public ArrayList<Deposit> reportDepositListbyClient (String clientId, Connection connection)
	{
		System.out.println ("Client's deposits list");
		
		return (_DB_Manager.reportDepositsList(connection, "customers", clientId ));
	}
	
	public int reportDepositsBalance (String clientId, Connection connection)
	{
		System.out.println("Sum the deposits");
		
		ArrayList<Deposit> deposits = _DB_Manager.reportDepositsList(connection, "customers", clientId);
		int sum = 0;
		
		for (Deposit deposit:deposits)
		{
			sum += deposit.get_amount();
		}
		
		return sum;
	}
	
	public ArrayList<Deposit> reportDepositListbyType (String type, Connection connection)
	{
		System.out.println ("Desposits list type");
		
		return (_DB_Manager.reportDepositsList(connection, "type", type));
	}
	
	public ArrayList<ArrayList<Deposit>> reportDepositListbyType (Connection connection)
	{
		ArrayList <Deposit> depositListConstant = _DB_Manager.reportDepositsList(connection, "type", "Constant");
		ArrayList <Deposit> depositListIndex = _DB_Manager.reportDepositsList(connection, "type", "Index");
		ArrayList <Deposit> depositListForeign = _DB_Manager.reportDepositsList(connection, "type", "Foreign");
		
		ArrayList<ArrayList<Deposit>> depositList = new ArrayList<ArrayList<Deposit>>();
		
		depositList.add(depositListConstant);
		depositList.add(depositListIndex);
		depositList.add(depositListForeign);
		
		return (depositList);	
	}


}
