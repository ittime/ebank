package mBank_files;

public class Account_Regular extends Account {
	
	 
	 public Account_Regular (int accountId, int clientId, int ammount, String comments)
	 {
		 super (accountId, clientId, ammount, comments);
		 _creditRestriction = MBankProperties.getRegular_credit();
	 }
	 
//	 public Account_Regular (Account account)
//	 {
//		 super (account.get_AccountId(), account.get_ClientId(), account.getAmmount(), account.get_accountComments());
//		 
//	
//	 }

	
}
