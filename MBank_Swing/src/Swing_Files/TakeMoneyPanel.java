package Swing_Files;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TakeMoneyPanel extends panelsManager
{
	JLabel take = new JLabel ("Please Take Your Money");
	
	public TakeMoneyPanel() 
	{
		formatMessageLabel(take);
		setPanel("TakeMoneyPanel", 0, 0);
		add (take, BorderLayout.CENTER);
	}
	
	

}
