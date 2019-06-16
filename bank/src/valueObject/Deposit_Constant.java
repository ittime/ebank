package valueObject;
import java.util.Calendar;




public class Deposit_Constant extends Deposit {

	public Deposit_Constant(){super();}
	public Deposit_Constant(int id, int amount, Calendar calendar, Calendar calendar2) {
		super(id, amount, calendar, calendar2);
		
		_interestType = "Constant";
	}

	
}
