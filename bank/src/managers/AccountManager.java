package managers;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import valueObject.Account;
import valueObject.Client;





public abstract class AccountManager extends Manager {
	
	public abstract void addAccount (Account account, int clientId, Connection connection) throws SQLException;
	public abstract void removeAccount(int clientId, Connection connection) throws SQLException;

	public abstract void deposit (int amount, Connection connection, Client client, boolean needCommission);
	public abstract void deposit(double amount, Connection connection, Client client, boolean needCommission) ;
	public abstract void withdraw (int amount, Connection connection, Client client);
	public abstract double getCommission (Client client, int amount, Connection connection);
	
	
	public abstract Account reportAccount (Client client, Connection connection);
	public abstract ArrayList<Account> reportAccountList (Connection connection);

	


}



