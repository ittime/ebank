package mBankProperties;


import java.util.Properties;


public class MBankProperties 
{
	static String Customer_r_rank;
	static String Customer_g_rank;
	static String Customer_p_rank;
	static int regular_credit;
	static int gold_credit;
	static int platinum_credit;
	static double regular_commission;
	static double gold_commission;
	static double platinum_commission;
	static double index;
	static double dollar_foreign_rate;
	static double bank_constant_interest;
	static double deposit_penalty;
	static int admin_password;
	static String admin_userName;
	static Properties _properties;

	public MBankProperties (Properties properties)
	{
		_properties = properties;
		Customer_r_rank = properties.getProperty("Customer_r_rank");
		Customer_g_rank = properties.getProperty("Customer_g_rank");
		Customer_p_rank = properties.getProperty("Customer_p_rank");
		regular_credit = (Integer.parseInt(properties.getProperty("regular_credit")));
		gold_credit = (Integer.parseInt(properties.getProperty("gold_credit")));
		platinum_credit = (Integer.parseInt(properties.getProperty("platinum_credit")));
		regular_commission = (Double.parseDouble(properties.getProperty("regular_commission")));
		gold_commission = (Double.parseDouble(properties.getProperty("gold_commission")));
		platinum_commission = (Double.parseDouble(properties.getProperty("platinum_commission")));
		bank_constant_interest = (Double.parseDouble(properties.getProperty("bank_constant_interest")));
		deposit_penalty = (Double.parseDouble(properties.getProperty("deposit_penalty")));
		admin_password = (Integer.parseInt(properties.getProperty("admin_password")));
		admin_userName = properties.getProperty("admin_userName");
		
//		_properties = properties;
//		Customer_r_rank = "Regular";
//		Customer_g_rank = "Gold";
//		Customer_p_rank = "Platinum";
//		regular_credit = 10000;
//		gold_credit = 100000;
//		platinum_credit = 200000;
//		regular_commission = 0.007;
//		gold_commission = 0.005;
//		platinum_commission = 0.003;
//		bank_constant_interest = 0.06;
//		deposit_penalty = 0.05;
//		admin_password = 123;
		
	}

	public static String getCustomer_r_rank() {
		return Customer_r_rank;
	}

	public static String getCustomer_g_rank() {
		return Customer_g_rank;
	}

	public static String getCustomer_p_rank() {
		return Customer_p_rank;
	}

	public static int getRegular_credit() {
		return regular_credit;
	}

	public static int getGold_credit() {
		return gold_credit;
	}

	public static int getPlatinum_credit() {
		return platinum_credit;
	}

	public static double getRegular_commission() {
		return regular_commission;
	}

	public static double getGold_commission() {
		return gold_commission;
	}

	public static double getPlatinum_commission() {
		return platinum_commission;
	}

	public static double getIndex() {
		index = 0.001;
		return index;
	}

	public static double getDollar_foreign_rate() {
		dollar_foreign_rate = 0.0004;
		return dollar_foreign_rate;
	}

	public static double getBank_constant_interest() {
		return bank_constant_interest;
	}

	public static double getDeposit_penalty() {
		return deposit_penalty;
	}

	public static int getAdmin_password() {
		return admin_password;
	}
	
	public static String getAdmin_userName() {
		return admin_userName;
	}
	
	public static double getCommission (String rank)
	{
		if (rank.equals("Regular"))
			return (getRegular_commission());
		if (rank.equals("Gold"))
			return (getGold_commission());
		else
			return (getPlatinum_commission());
	}

	
	
		
	}
    

