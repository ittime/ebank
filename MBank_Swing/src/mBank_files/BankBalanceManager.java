package mBank_files;
import java.sql.Connection;
import java.util.ArrayList;


public abstract class BankBalanceManager extends Manager {
	
	public BankBalanceManager() {}
	
	protected abstract void addRecord (Connection connection, BalanceAction log);
	
	public abstract ArrayList<BalanceAction> reportsActionsHistory (Connection connection, Client client);
	
	public abstract ArrayList<BalanceAction> reportBalanceChange (Connection connection);
	
	

}
