package threads;
import java.sql.Connection;


import mBankProperties.MBankProperties;
import managers.DepositManager_DB;




public class DepositCheckThread_DB extends DepositCheckThread implements Runnable 
{
	
	DepositManager_DB DB_manager;
	Connection _connection;


	public DepositCheckThread_DB(Connection connection, DepositManager_DB manager)
	{
		super();
		
		{
			_connection = connection;
			DB_manager = manager;
			t = new Thread (this);
			t.start();
		}
	}

		public void run() 
		{
			while (true) {
				
				System.out.println ("daily check for dued deposits");
				DB_manager.depositCheck(_connection);
				
				Double foreignRateChange = MBankProperties.getDollar_foreign_rate();
				System.out.println ("daily update of the dollar rate change");
				DB_manager.foreignUpdate(_connection, foreignRateChange);
					
			try {
				Thread.sleep(1000*60*60*24); //sleep for a day
				
				
			} catch (InterruptedException e) {
				System.out.println ("the IndexUpdateThread had been interupted " + e);
			}
			}
			
			
		}

	}
