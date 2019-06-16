package threads;
import java.sql.Connection;
import java.util.Calendar;


import mBankProperties.MBankProperties;
import managers.DepositManager_DB;



public class IndexUpdateThread_DB extends indexUpdateThread implements Runnable
{
	DepositManager_DB DB_manager;
	Connection _connection;
	DepositCheckThread_DB other;
	
	public IndexUpdateThread_DB (Connection connection, DepositManager_DB manager, DepositCheckThread_DB otherthread )
	{
		_connection = connection;
		other = otherthread;
		DB_manager = manager;
		t = new Thread (this);
		t.start();
	}

	public void run() 
	{
		while (true) {
			
			System.out.println ("updating the monthly index rate");
			Double indexChange = MBankProperties.getIndex();
			DB_manager.indexUpdate(_connection, indexChange);
				
		try {
			
			int maxDayInMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
			
			Thread.sleep(Math.abs(1000*60*60*24*maxDayInMonth));  //sleep for a month
			
			
		} catch (InterruptedException e) {
			System.out.println ("the IndexUpdateThread had been interupted " + e);
		}
		}
		
		
	}

}
