package Swing_Files;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mBank_files.ClientAction;

public class OtherAmountPanel extends WithdrawPanel

{
	JPanel otherAmountPanel;
	JPanel newAmountPanel;
	JLabel newAmount;
	String amountEntered;

	public OtherAmountPanel(ClientAction action) 
	{
		super();
		
		otherAmountPanel = this;
		
		setPanel("OtherAmountPanel", 0, 0);
		
		amountEntered = "";
		
		JLabel message = new JLabel ("Please enter the amount of money required");
		formatMessageLabel(message);
		
		newAmount = new JLabel (amountEntered);
		
		newAmountPanel = new JPanel();
		newAmountPanel.add (new JLabel("      "));
		newAmountPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 15));
		newAmountPanel.add(newAmount);
		
		add (message, BorderLayout.NORTH);
		add (newAmountPanel, BorderLayout.CENTER);
	}
	
	public void addNumberToAmount(JButton button)
	{
		amountEntered  += button.getText();	
		
		newAmount.setText(amountEntered);
		
	}
	
	public void ContinueButton ()
	{
		try{
			boolean greatSuccess = false;
			
			int amount = Integer.parseInt(newAmount.getText());
			
			double check = amount%10;
			
			if ((check == 0)&&(amount >= 20))
			{
				greatSuccess = (_action.withdraw(amount));
				switchTo(greatSuccess);
			}
			else
			{
				MessagesAndQuestions.showWarning("Please choose amounts such as 20, 50, 100, 150, etc.");
				amountEntered = "";
				newAmount.setText(amountEntered);
			}
			
						
		}catch (NumberFormatException nfe){
			MessagesAndQuestions.showWarning("Exceeding the allowed amount");
			amountEntered = "";
			newAmount.setText(amountEntered);
		}
		
	}

	
	public void CancelButton ()
	{
		amountEntered = "";
		switchTo (false);
	}
	
	private void switchTo(boolean greatSuccess) {
		
		if (greatSuccess)
		{
			switcher.switchPanel(switcher.getNewTakeMoneyPanel());
		}

		String question = "Do you want to make another action?";
		boolean returnToMenu = (MessagesAndQuestions.showMessage(question));
			
		if (returnToMenu)
			switcher.switchPanel(switcher.getNewMenuPanel(MainPanel.getClientName()));
		else
			MainPanel.logOff();
	}
		
	

}
