package mBank_files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;



public abstract class AccountManager extends Manager {
	
	public abstract void addAccount (Account account, int clientId, Connection connection) throws SQLException;
	
	public abstract void deposit (int amount, Connection connection, Client client, boolean needCommission);
	public abstract void deposit(double amount, Connection connection, Client client, boolean needCommission) ;
	
	public abstract boolean withdraw (int amount, Connection connection, Client client);
	
	public abstract void removeAccount(int clientId, Connection connection) throws SQLException;

	public abstract void accountDetails(Client client, Connection connection);
	
	public abstract ArrayList<Account> reportAccountList (Connection connection);
	
	public abstract Account reportAccount (Client client, Connection connection);

	


}



