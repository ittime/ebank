package mBank_files;

public class Account_Gold extends Account {
	
	 
	 public Account_Gold (int accountId, int clientId, int ammount, String comments)
	 {
		 super (accountId, clientId, ammount, comments);
		 _creditRestriction = MBankProperties.getGold_credit();
	 }
	 
//	 public Account_Gold (Account account)
//	 {
//		 super (account.get_AccountId(), account.get_ClientId(), account.getAmmount(), account.get_accountComments());
//	
//	 }
	 
	
	 
	
	

}
