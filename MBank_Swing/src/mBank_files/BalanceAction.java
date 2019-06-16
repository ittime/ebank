package mBank_files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BalanceAction extends ValueObject {
	private int 
			_actionId, 
			_clientId;
	
	private double _actionAmmount;
	
	private String _action;
	
	private String _date;
	
	public BalanceAction(int actionId, int clientId, double ammount, String action,
			String date) {
		
		super();
		
		_actionId = actionId;
		_clientId = clientId;
		_actionAmmount = ammount;
		_action = action;
		_date = date;
	}

	public BalanceAction (String action, double commission, int clientId)
	{
		_action = action;
		_actionAmmount = commission;
		_clientId = clientId;
		_date = formatDate(Calendar.getInstance());
		
		_actionId=0;
		
	}
	
	public int get_actionId() {
		return _actionId;
	}

	public double get_actionAmmount() {
		return _actionAmmount;
	}
	
	private void set_actionAmount (double amount)
	{
		_actionAmmount = amount;
	}

	public int get_clientId() {
		return _clientId;
	}

	public String get_action() {
		return _action;
	}
	
	private void set_action (String action)
	{
		_action = action;
	}

	public String get_date() {
		return _date;
	}
	
	private String formatDate (Calendar date)
	{
		String format = "dd/MM/yyyy hh:mm:ss";
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String formattedDate = (sdf.format(date.getTime()));
		
		return (formattedDate);
		
	}
	
	public String toString()
	{
		
		bbDepositToSting(get_action());
		
		
		return 
		(get_actionId()+"): "+get_date()+"  "+ get_action()+ " | amount: " + get_actionAmmount());
	}

	private void bbDepositToSting(String action) {
		
		String deposit = action.substring(0, 7);
		String withdraw = action.substring(0, 8);
		
		if ((deposit.equals("deposit")||(withdraw.equals("withdraw"))))
		{
			int firstPar = (action.indexOf("(")+1);
			int lastPar = (action.lastIndexOf(")"));
			
			//a
			set_actionAmount(Double.parseDouble(action.substring(firstPar, lastPar)));
			
			//b
			set_action(action.substring(0, firstPar-1));
			
		}
		
		
	}
		
		
//		String format = "dd/MM/yyyy hh:mm:ss";
//		
//		SimpleDateFormat sdf = new SimpleDateFormat(format);
//		
//		String formattedDate = (sdf.format(date.getTime()));
//		
//		
//		Date temp = new Date();
//		
//		try {
//			temp = sdf.parse(formattedDate);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			System.out.println("problem with date formatting " + e);
//		}
//		date.setTime(temp);
//		
//		
//		return (date);
}
	
	
	

