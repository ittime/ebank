package valueObject;
import java.util.Calendar;




public class Deposit_Index extends Deposit {

	public Deposit_Index(){super();}
	public Deposit_Index(int id, int amount, Calendar openDate, Calendar closureDate) {
		super(id, amount, openDate, closureDate);

		_interestType = "Index";

	}

	

}
