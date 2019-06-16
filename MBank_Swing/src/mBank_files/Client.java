package mBank_files;

public class Client extends ValueObject {
	//
	private int _clientId = 0,
			   _clientPassword = 0,
			   _clientPhoneNumber = 0;
	
	protected String 
			  _clientRank = "", 
			  _clientName = "",
			  _clientMail = "",
			  _clientAddress = "",
			  _clientComments = "";
	
	//those variables will be used for the pack on admin-action
	private Account _packAccount;
	private Client _packClient;
	
	public Client(int id, String name, int password, int phoneNumber,
			String mail, String address, String comments) 
	{
		super();
		
		_clientId = id;
		_clientPassword = password;
		_clientPhoneNumber = phoneNumber;
		_clientName = name;
		_clientMail = mail;
		_clientAddress = address;
		_clientComments = comments;
		
	}
	
	public Client(String name, int password, int phoneNumber,
			String mail, String address, String comments) 
	{
		super();
		
		_clientPassword = password;
		_clientPhoneNumber = phoneNumber;
		_clientName = name;
		_clientMail = mail;
		_clientAddress = address;
		_clientComments = comments;
		
	}
	
	
	public Client (Client client, Account account)
	{
		_packClient = client;
		_packAccount = account;
	}
	
	public Client(int phoneNumber, String mail, String address) {
		super();
		_clientPhoneNumber = phoneNumber;
		_clientMail = mail;
		_clientAddress = address;
	}
	
	public Client (String rank, String comments, int clientId)
	{
		_clientId = clientId;
		_clientRank = rank;
		_clientComments = comments;
	}

	public Client (int id)
	{
		super();
		_clientId = id;
	}

	public int get_clientId() {
		return _clientId;
	}

	public void set_clientId(int clientId) {
		_clientId = clientId;
	}

	public int get_clientPassword() {
		return _clientPassword;
	}

	public void set_clientPassword(int clientPassword) {
		_clientPassword = clientPassword;
	}

	public int get_clientPhoneNumber() {
		return _clientPhoneNumber;
	}

	public void set_clientPhoneNumber(int clientPhoneNumber) {
		_clientPhoneNumber = clientPhoneNumber;
	}

	public String get_clientRank() {
		return _clientRank;
	}

	public void set_clientRank(String clientRank) {
		_clientRank = clientRank;
	}

	public String get_clientName() {
		return _clientName;
	}

	public void set_clientName(String clientName) {
		_clientName = clientName;
	}

	public String get_clientMail() {
		return _clientMail;
	}

	public void set_clientMail(String clientMail) {
		_clientMail = clientMail;
	}

	public String get_clientAddress() {
		return _clientAddress;
	}

	public void set_clientAddress(String clientAddress) {
		_clientAddress = clientAddress;
	}

	public String get_clientComments() {
		return _clientComments;
	}

	public void set_clientComments(String clientComments) {
		_clientComments = clientComments;
	}

	public Account get_packAccount() {
		return _packAccount;
	}

	public Client get_packClient() {
		return _packClient;
	}
	
	public String toString()
	{
		return 
		("CID: "+ get_clientId() + " | " +
		 get_clientName()  + " | " +
		 "Rank: " + get_clientRank()  + " | " +
		 "Password: " + get_clientPassword()  + " | " +
		 "Phone number: " + get_clientPhoneNumber()  + " | " +
		 "Email address " + get_clientMail()  + " | " +
		 "Address: " + get_clientAddress()  + " | " +
		 "Comments: " + get_clientComments()
		 );
	}
	
	

}


