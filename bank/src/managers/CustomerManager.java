package managers;
import java.sql.Connection;
import java.util.ArrayList;

import valueObject.Account;
import valueObject.Client;




public abstract class CustomerManager extends Manager {
	
	public CustomerManager(){};

		
	public abstract Client checkExistence (String userName, int password, Connection connection);
	
	public abstract void addCustomer (Client client, Account account, Connection _connection, AccountManager manager);
    public abstract void removeCustomer
	(Client remvClient, Connection _connection,AccountManager_DB manager, DepositManager_DB manager2) ;

    public abstract void changeDetails (Client client, Connection _connection);
	public abstract void UpdateDetails (Client details, Connection connection);
	
	public abstract Client reportClient (int clientId, Connection connection);
	public abstract ArrayList<Client> reportClientList (Connection connection, String type);







}
