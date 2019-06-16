package reports;

import MBank.MBank;



public class Reports_General {
	
	MBank _bank;
	Reports_Account _repAccount;
	Reports_BalanceAction _repBalanceAction;
	Reports_Customer _repCustomer;
	Reports_Deposit _repDeposit;
	
	
	public Reports_General ()
	{
		_repAccount = new Reports_Account();
		_repBalanceAction = new Reports_BalanceAction();
		_repCustomer = new Reports_Customer();
		_repDeposit = new Reports_Deposit();
	}


	public Reports_Account get_repAccount() {
		return _repAccount;
	}


	public Reports_BalanceAction get_repBalanceAction() {
		return _repBalanceAction;
	}


	public Reports_Customer get_repCustomer() {
		return _repCustomer;
	}


	public Reports_Deposit get_repDeposit() {
		return _repDeposit;
	}

}
