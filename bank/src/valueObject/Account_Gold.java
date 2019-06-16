package valueObject;

import mBankProperties.MBankProperties;


public class Account_Gold extends Account {
	
	 
	 public Account_Gold (int accountId, int clientId, int ammount, String comments)
	 {
		 super (accountId, clientId, ammount, comments);
		 _creditRestriction = MBankProperties.getGold_credit();
	 }

	 
	 
	
	 
	
	

}
