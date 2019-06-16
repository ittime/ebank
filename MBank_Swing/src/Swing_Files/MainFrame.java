package Swing_Files;
import javax.swing.JFrame;

import mBank_files.Action;
import mBank_files.ClientAction;
import mBank_files.MBank;


public class MainFrame extends JFrame 
{
	private static MBank bank;
	
	
	MainPanel main;
	
	
	public MainFrame(String name) {
		
		super (name);
		
		bank = MBank.createBank();
		
		main = new MainPanel(bank);
		
		this.add(main);
	}


	public static MBank getBank() {
		return bank;
	}
	
	
}

