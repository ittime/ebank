package actions;
import java.sql.Connection;

import MBank.MBank;

import valueObject.Client;
import valueObject.ValueObject;





public abstract class Action //every action hold these parameters
{
	protected Connection _connection; // the connection for the DB
	
	protected ValueObject _valueObject; // the value object that will be in use for transmitting information
	
	protected MBank _bank; // the bank object
	
	protected String [] _error = 
	{"The system couldn't follow your request since data wasn't entered properly. Please try again."};
	
	protected Client _client; // the client in use of the customer or administrator



public Action (Connection connection, MBank bank)
	{
		_connection = connection;
		_valueObject = null;
		_bank = bank;
		
	}


public Client get_client() {
		return _client;
	}


public void set_client(Client client) {
		this._client = client;
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

public MBank get_bank() {
	return _bank;
}
}


