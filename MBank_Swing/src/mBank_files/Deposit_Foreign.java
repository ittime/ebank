package mBank_files;
import java.util.Calendar;

public class Deposit_Foreign extends Deposit {

	public Deposit_Foreign(){super();}
	public Deposit_Foreign(int id, int amount, Calendar openDate, Calendar closureDate) {
		super(id, amount, openDate, closureDate);

		_interestType = "Foreign";

	}

	
}
