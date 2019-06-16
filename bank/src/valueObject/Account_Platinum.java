package valueObject;

import mBankProperties.MBankProperties;


public class Account_Platinum extends Account {
	

	 public Account_Platinum (int accountId, int clientId, int ammount, String comments)
	 {
		 super (accountId, clientId, ammount, comments);
		 _creditRestriction = MBankProperties.getPlatinum_credit();
	 }
	 

}

	
