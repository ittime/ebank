package valueObject;

import mBankProperties.MBankProperties;


public class Account_Regular extends Account {
	
	 
	 public Account_Regular (int accountId, int clientId, int ammount, String comments)
	 {
		 super (accountId, clientId, ammount, comments);
		 _creditRestriction = MBankProperties.getRegular_credit();
	 }

	 

	
}
