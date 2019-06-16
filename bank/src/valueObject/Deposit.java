package valueObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;




public class Deposit extends ValueObject{
	
	protected int _depositId,
			    _clientId,
			    _amount,
			    _estimatedAmount,
			    _finalAmount;
	
	protected String _interestType;
	
	protected Calendar _openDate,
				 _closureDate;

	
	public Deposit(){}
	
	public Deposit(int depositId, int clientId, int amount, int estimatedAmount, int finalAmount,
			String interestType, Calendar openingDate, Calendar closingDate) 
	{
		super();
		
		_depositId = depositId;
		_clientId = clientId;
		_amount = amount;
		_estimatedAmount = estimatedAmount;
		_finalAmount = finalAmount;
		_interestType = interestType;
		_openDate = openingDate;
		_closureDate = closingDate;
	}
	
	
	public Deposit(int id, int amount, Calendar openDate, Calendar closureDate) 
	{
		super();
		
		_clientId = id;
		_amount = amount;
		_openDate = openDate;
		_closureDate = closureDate;
	}

	public Deposit(int depositId, int amount) 
	{
		super();
		
		_depositId = depositId;
		_amount = amount;
	}
	
	public Deposit (int depositId)
	{
		_depositId = depositId;
	}

	public int get_depositId() {
		return _depositId;
	}

	public void set_depositId(int id) {
		_depositId = id;
	}

	public int get_clientId() {
		return _clientId;
	}

	public void set_clientId(int id) {
		_clientId = id;
	}

	public int get_amount() {
		return _amount;
	}

	public void set_amount(int _amount) {
		this._amount = _amount;
	}

	public int get_estimatedAmount() {
		return _estimatedAmount;
	}

	public void set_estimatedAmount(int amount) {
		_estimatedAmount = amount;
	}

	public int get_finalAmount() {
		return _finalAmount;
	}

	public void set_finalAmount(int amount) {
		_finalAmount = amount;
	}

	public String get_interestType() {
		return _interestType;
	}

	public void set_interestType(String type) {
		_interestType = type;
	}

	public Calendar get_openDate() {
		return _openDate;
	}

	public void set_openDate(Calendar date) {
		_openDate = date;
	}

	public Calendar get_closureDate() {
		return _closureDate;
	}

	public void set_closureDate(Calendar date) {
		_closureDate = date;
	}
	
	public String toString()
	{
		return ("Deposit ID: "
				+ _depositId 
				+ " / Client ID: " + _clientId + " | " 
				+ _interestType 
				+ " | Amount: " + _amount 
				+ "(estimated amount: "	+ (double)(_estimatedAmount) 
				+", final estimated amount: "+ (double) _finalAmount + ")"	
				+ " from " + formatDate(_openDate) 
				+ " to "+ formatDate(_closureDate)
			);
	}
	
	private String formatDate (Calendar date)
	{
		
		if (date != null)
		
		{
			
		String format = "dd/MM/yyyy";
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String formattedDate = (sdf.format(date.getTime()));
		
		return (formattedDate); 
		
		}
		
		else return null;
		
	}
}

