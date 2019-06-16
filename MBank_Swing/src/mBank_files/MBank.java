package mBank_files;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class MBank {
	
	private static MBank _bank = null;
	
	MBankProperties _MBankProperties;
	private BankBalanceManager _BankBalanceManager;
	private AccountManager _AccountManager;
	private CustomerManager _CustomerManager;
	private DepositManager _DepositManager;
	
	private Reports_General _repGeneralManager;
	
	
	//bank creation
	
	public static MBank createBank()
	{
		if (_bank == null)
			_bank = new MBank();
		
		return _bank;
	}
	
	private MBank ()
	{
		//_repGeneralManager = new Reports_General();
		
		_BankBalanceManager = new BankBalanceManager_DB();
		_AccountManager = new AccountManager_DB((BankBalanceManager_DB)_BankBalanceManager);
		_CustomerManager = new CustomerManager_DB((BankBalanceManager_DB)_BankBalanceManager);
		_DepositManager = new DepositManager_DB((BankBalanceManager_DB)_BankBalanceManager, (AccountManager_DB) _AccountManager);
		
		System.setProperty("jdbc.drivers", "sun.jdbc.odbc.JdbcOdbcDriver"); //loads DB driver
		
		_MBankProperties = new MBankProperties (loadFile("MBank.properties"));
		
		
	}
	
	//end bank creation
	
	
	
	//the AccessManager is responsible for validating clients&passwords	
	public Action accessManager (int password)
	{
		if ( checkAdmin (password)) // if it's the administrator
			{
					System.out.println ("Welcome, Administrator\nWhat do you want to do today?");
					Connection connection = getConnection();
					AdminAction action = (AdminAction) getAction(0, connection, new Client(0));
					return action; // return a new AdminAction
			}
		else // if it's a customer
			{
				Client client = _CustomerManager.checkExistence (password, getConnection()); //check up the user -- returns the client found or null if it wasn't found
				if (client == null) //if the customer wasn't found
				{
					//System.out.println ("It seems that your password is incorrect, please rewrite it");
					return null;
				}
				else // if the customer was found on the DB
					{
					//System.out.println ("Welcome " + client.get_clientName() + ".\nWhat do you want to do today?");
					Connection connection = getConnection();
					ClientAction action = (ClientAction) getAction(1, connection, client);
					return action; // return a new ClientAction
					}
			}					
	}
	
	private boolean checkAdmin(int password) {
		
		if (password == MBankProperties.getAdmin_password())
			return true;
		
		return false;
	}

	//i should delete this method
	public void logOff ()
	{
//		setClient (null);
//		setConnection (null);
//		setAction (null);
//		
	}
	

	public Connection getConnection()
	{
		Connection connection = null; //a new connection 
		try // try to load a connection for the DB
			{
				connection = DriverManager.getConnection("jdbc:odbc:MBank");
			}
		catch(SQLException sqle) // if it didn't work
			{
				System.out.println(sqle.getMessage()); //print an error massage
			}
				
		return connection; // return the new connection
	}
	
	public Action getAction(int type, Connection connection, Client client) 
	{
		switch (type)
		{
		case 0: return ( new AdminAction (connection, _bank) );
		case 1: return ( new ClientAction(connection, _bank, client) );
		default: return null;
		}
	}

	public AccountManager get_AccountManager() {
		return _AccountManager;
	}

	public CustomerManager get_CustomerManager() {
		return _CustomerManager;
	}

	public DepositManager get_DepositManager() {
		return _DepositManager;
	}

	public BankBalanceManager get_BankBalanceManager() {
		return _BankBalanceManager;
	}

	public static MBank get_bank() {
		return _bank;
	}

	public static void set_bank(MBank _bank) {
		MBank._bank = _bank;
	}

	public Reports_General get_repGeneralManager() {
		return _repGeneralManager;
	}
	
	 public Properties loadFile (String fileName)
	    {
	    try {
	    	Properties properties = new Properties();
	        properties.load(new FileInputStream(fileName));
	        return properties;
	    
	    } catch (IOException ioe) {
	    	System.out.println ("error loading properties file: " + ioe);
	    	return null;
	    }}

	public MBankProperties get_MBankProperties() {
		return _MBankProperties;
	}
}