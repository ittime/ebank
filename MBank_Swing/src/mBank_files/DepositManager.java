package mBank_files;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public abstract class DepositManager extends Manager {
	
	public DepositManager(){};
	
	public abstract void addDeposit (Deposit deposit, Connection connection, MBank bank);
	
	public abstract void breakDeposit (Deposit deposit, Connection connection);

	public abstract void removeDeposits(int clientId, Connection connection);
	
	public abstract void closeOrRemove_Deposit (int id, Connection connection, String message);
	
	public abstract Deposit reportDeposit (int DepositId, Connection connection);
	
	public abstract ArrayList<Deposit> reportDepositsList (Connection connection, String type, String condition);
	
	
	protected long calc_ammountOfDays(Calendar openDate, Calendar closureDate) 
	{
		
		long millis1 = openDate.getTimeInMillis();
		long millis2 = closureDate.getTimeInMillis();
		
		long differenceInMillis = millis2-millis1;
		
		long ammountOfDays = differenceInMillis / (24*60*60*1000);
		
		return (ammountOfDays);
		
	}
	
	protected double[] calc_estimatedAmmount(String type, int amount, double penalty, long amountOfDays) 
	{
		double interest = MBankProperties.getBank_constant_interest();
		double [] estim = new double [2];
		double dailyInterest = calc_dailyInterest(interest);
		double dailyDollarRate = MBankProperties.getDollar_foreign_rate();
		
		double estimatedAmount, finalEstimatedAmount;
		
		finalEstimatedAmount = ( amount + (dailyInterest*amountOfDays*amount) );
		
		
		if (type == "Index")
		{
			dailyInterest = calc_dailyInterest(interest + (MBankProperties.getIndex()/30));
			finalEstimatedAmount = ( amount + (dailyInterest*amountOfDays*amount) ); 		
		
		}
		else if (type == "Foreign")
		{
			dailyInterest = dailyDollarRate;			
			finalEstimatedAmount = (amount + (dailyDollarRate*amountOfDays*amount) );			
		}
		
		estimatedAmount = (amount + (dailyInterest*amount) - (penalty*amount) );
		
		
		estim [0] = estimatedAmount;
		estim [1] = finalEstimatedAmount;
		
		return (estim);
	}
	
	protected double calc_dailyInterest (double interest)
	{
		Calendar cal = Calendar.getInstance();
		
		double amountOfDaysInYear = (double)cal.getActualMaximum(Calendar.DAY_OF_YEAR);
		
		return (interest/amountOfDaysInYear);
	}
	
	protected String formatDate (Calendar date)
	{
		String format = "dd/MM/yyyy";
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String formattedDate = (sdf.format(date.getTime()));
		
		return (formattedDate);
	}
	
	protected double decideInterest(String type) {

		double interest = MBankProperties.getBank_constant_interest();
		
		if (type == "Foreign")
			interest = MBankProperties.getDollar_foreign_rate();
		else if (type == "Index")
			interest = interest + MBankProperties.getIndex();
		
		return interest;
	}
	
	
	
	
}
