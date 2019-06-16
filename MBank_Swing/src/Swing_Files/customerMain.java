package Swing_Files;
import java.awt.Dimension;

import javax.swing.JFrame;


public class customerMain {

	public static void main(String[] args) {
		
		MainFrame main = new MainFrame("MBank ATM");
		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setPreferredSize(new Dimension (500, 730));
		main.pack();
		main.setVisible(true);
		
		
		
		

	}

}
