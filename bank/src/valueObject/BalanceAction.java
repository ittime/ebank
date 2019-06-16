package valueObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;




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
	
	public void set_action (String action)
	{
		_action = action;
	}

	public String get_date() {
		return _date;
	}
	
	public String toString()
	{
		
		bbDepositToSting(get_action());
		
		
		return 
		(get_actionId()+"): "+get_date()+"  "+ get_action()+ " | amount: " + get_actionAmmount());
	}

	private String formatDate (Calendar date)
	{
		String format = "dd/MM/yyyy hh:mm:ss";
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String formattedDate = (sdf.format(date.getTime()));
		
		return (formattedDate);
		
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
	
	public BalanceAction formatActionText (){
		
		String action = this.get_action();
		
		String deposit = action.substring(0, 7);
		String withdraw = action.substring(0, 8);
		
		if ((deposit.equals("deposit")||(withdraw.equals("withdraw"))))
		{
			int firstPar = (action.indexOf("(")+1);
			int lastPar = (action.lastIndexOf(")"));
			
			//a
			this.set_actionAmount(Double.parseDouble(action.substring(firstPar, lastPar)));
			
			//b
			this.set_action(action.substring(0, firstPar-1));
			
		}
		
		return this;		
	}

	public void set_actionId(int id) {
		_actionId = id;
	}

	public void set_clientId(int id) {
		_clientId = id;
	}

	public void set_actionAmmount(double ammount) {
		_actionAmmount = ammount;
	}

	public void set_date(String _date) {
		this._date = _date;
	}
		
		

}
	
	
	

