package mBank_files;
import java.sql.Connection;


public abstract class Action //every action hold these parameters
{
	protected Connection _connection; // the connection for the DB
	
	protected ValueObject _valueObject; // the value object that will be in use for transmitting information
	
	protected String _actionType; // is it an AdminAction or a ClientAction
	
	protected static int _actionId; // the id of the specific action that will be in use
	
	protected MBank _bank; // the bank object
	
	protected String [] _error = {"The system couldn't follow your request since data wasn't entered properly. Please try again."};
	
	protected Client _client; // the client in use of the customer or administrator



public Client get_client() {
		return _client;
	}


public void set_client(Client client) {
		this._client = client;
	}


public Action (Connection connection, MBank bank)
{
	_connection = connection;
	_valueObject = null;
	_actionType = null;
	_actionId = 0;
	_bank = bank;
	
	}


public Connection get_connection() {
	return _connection;
}

public void set_connection(Connection _connection) {
	this._connection = _connection;
}

public ValueObject get_valueObject() {
	return _valueObject;
}

public void set_valueObject(ValueObject object) {
	_valueObject = object;
}

public String get_actionType() {
	return _actionType;
}

public void set_actionType(String type) {
	_actionType = type;
}

public static int get_actionId() {
	return _actionId;
}

public static void set_actionId(int id) {
	_actionId = id;
}


public MBank get_bank() {
	return _bank;
}
}


