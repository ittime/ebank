package Swing_Files;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import mBank_files.ClientAction;
import mBank_files.MBank;


public class PasswordPanel extends panelsManager 
{
	JLabel [] _lbls;
	JLabel please;
	String secretCode;
	MBank bank;
	
	public PasswordPanel() 
	{
		bank = MainFrame.getBank();
		
		_lbls = new JLabel [5];
		
		secretCode = "";
		
		setPanel("PasswordPanel", 40, 0);
		
		please = new JLabel ("Hello. Please enter your secret code");
		
		formatMessageLabel(please);
		
		JPanel lblsPanel = new JPanel();
		
		for (Integer i=0; i < _lbls.length; i++)
		{
			JLabel lbl = new JLabel ("_");
			
			lblsPanel.add(lbl, BorderLayout.CENTER);
			
			_lbls[i] = lbl;
		}
		
		lblsPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		add (please, BorderLayout.NORTH);
		add (lblsPanel, BorderLayout.CENTER);
	
		
	}	
	
	public void addNumberToCode(JButton button)
	{
		int i =0;
		
		while (i<4 && _lbls[i].getText() != "_")
		{
			i++;
		}
		
		_lbls[i].setText("*");
	
		
		if (secretCode.length()<5)
		secretCode += button.getText();		
	}
	
	public ClientAction ContinueButton ()
	{
		if (secretCode.length()==5)
		{
			int password = Integer.parseInt(secretCode);
			ClientAction action = (ClientAction) bank.accessManager (password);
				
		if (action != null)
		{
			return action;
		}else
			return null;
		}
		else return null;
		
	}
	
	public void CancelButton ()
	{
		secretCode = "";
	}
	
	
	
	

}
