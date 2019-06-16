package mBank_files;

public class Account_Platinum extends Account {
	

	 public Account_Platinum (int accountId, int clientId, int ammount, String comments)
	 {
		 super (accountId, clientId, ammount, comments);
		 _creditRestriction = MBankProperties.getPlatinum_credit();
	 }
	 
//	 public Account_Platinum (Account account)
//	 {
//		 super (account.get_AccountId(), account.get_ClientId(), account.getAmmount(), account.get_accountComments());
//	
//	 }
}

	
