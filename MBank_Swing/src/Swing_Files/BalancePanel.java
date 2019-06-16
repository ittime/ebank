package Swing_Files;
import java.awt.BorderLayout;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mBank_files.ClientAction;


public class BalancePanel extends panelsManager
{
	private JLabel message;
	private JLabel amount;
	private JLabel date;
		
	public BalancePanel(ClientAction action) 
	{
		createPanel();
	}
	
	private void createPanel ()
	{
		setPanel("BalancePanel", 20, 100);
		
		date = new JLabel(Calendar.getInstance().getTime().toString());
		formatMessageLabel(date);
		message = new JLabel ("Your current balance is");
		formatMessageLabel(message);
		amount = new JLabel ("                  "+_action.reportBalance());
		formatMessageLabel(amount);
		
		add (new JLabel());
		add (message, BorderLayout.NORTH);
		add (amount, BorderLayout.CENTER);
	}
	
	public void ContinueButton ()
	{
		String question = "Do you want to make another action?";
		
		boolean returnToMenu = (MessagesAndQuestions.showMessage(question));
		
		if (returnToMenu)
			switcher.switchPanel(switcher.getNewMenuPanel(MainPanel.getClientName()));
		else
			MainPanel.logOff();
	}

	
	public void CancelButton ()
	{
		ContinueButton();
	}	

}
