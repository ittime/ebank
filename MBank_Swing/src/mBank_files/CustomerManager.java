package mBank_files;
import java.sql.Connection;
import java.util.ArrayList;


public abstract class CustomerManager extends Manager {
	
	public CustomerManager(){};

	
	//customer's methods
	public abstract Client checkExistence (int password, Connection connection);
	public abstract void UpdateDetails (Client details, Connection connection);
	public abstract Client reportClient (int clientId, Connection connection);
	public abstract ArrayList<Client> reportClientList (Connection connection, String type);
	
	//admin methods
	public abstract void addCustomer (Client client, Account account, Connection _connection, AccountManager manager);
	public abstract void changeDetails (Client client, Connection _connection);
	public abstract void removeCustomer
	(Client remvClient, Connection _connection,AccountManager_DB manager, DepositManager_DB manager2) ;







}
