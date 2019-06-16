package mBank_files;
import java.util.ArrayList;


public class Account extends ValueObject {
	
	protected int 
			  _AccountId
			 ,_ClientId
			 ,_amount
			 ,_creditRestriction;
	
	protected String 
			  _accountComments;
	
	protected ArrayList <Deposit> 
			  _deposits;
	
	


public Deposit getDeposit (int depositIndex)
{
	Deposit deposit = _deposits.get(depositIndex);
	if (deposit !=null)
		return deposit;
	
	else 
		return null;
}




public int get_AccountId() {
	return _AccountId;
}




public int get_ClientId() {
	return _ClientId;
}




public int getAmmount() {
	return _amount;
}




public String get_accountComments() {
	return _accountComments;
}




public ArrayList<Deposit> get_deposits() {
	return _deposits;
}




public Account(int accountId, int clientId, int amount, String comments) {
	
	super();
	
	_AccountId = accountId;
	_ClientId = clientId;
	_amount = amount;
	_accountComments = comments;
}

public Account(int amount, String comments) {
	
	super();
	
	_amount = amount;
	_accountComments = comments;
}




public int get_creditRestriction() {
	return _creditRestriction;
}


public void set_creditRestriction(int restriction) {
	_creditRestriction = restriction;
}




public int get_amount() {
	return _amount;
}

public String toString()
{
	return ("Account ID: " + get_AccountId() +
			" | Client ID: " + get_ClientId() +
			" | Amount: " + get_amount() +
			" | Credit restriction: " + get_creditRestriction() +
			" | Comments: " + get_accountComments()
			);
			
			
}







}
