package Swing_Files;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


import mBank_files.ClientAction;


public class WithdrawPanel extends panelsManager {
	
	private JButton fifty, hundred, twoHundredFifty, fiveHundreds, other;
	
	private OtherAmountPanel otherPanel;
	
	public WithdrawPanel() {
		
		createPanel();
		
	}

	private void createPanel() {
		
		setPanel("WithdrawPanel", 20, 0);
		
		fifty = new JButton ("50");
		fifty.setName("50");
		fifty.addMouseListener(new ButtonsListener());
		
		hundred = new JButton ("100");
		hundred.setName("100");
		hundred.addMouseListener(new ButtonsListener());
		
		twoHundredFifty = new JButton ("250");
		twoHundredFifty.setName("250");
		twoHundredFifty.addMouseListener(new ButtonsListener());
		
		fiveHundreds = new JButton ("500");
		fiveHundreds.setName("500");
		fiveHundreds.addMouseListener(new ButtonsListener());
		
		other = new JButton ("Other amount");
		other.setName("other");
		other.addMouseListener(new ButtonsListener());
		
		Dimension dim1 = new Dimension(0, 50);
		Dimension dim2 = new Dimension(0, 40);
		Dimension dim3 = new Dimension(0, 100);
		
		JPanel left = new JPanel();
		left.setBackground(Color.black);
		left.setLayout(new BoxLayout (left, BoxLayout.Y_AXIS));
		left.add(Box.createRigidArea(dim1));
		left.add(fifty);
		left.add(Box.createRigidArea(dim2));
		left.add(hundred);
		left.add(Box.createRigidArea(dim1));
		
		JPanel right = new JPanel();
		right.setBackground(Color.black);
		right.setLayout(new BoxLayout (right, BoxLayout.Y_AXIS));
		right.add(Box.createRigidArea(dim1));
		right.add(twoHundredFifty);
		right.add(Box.createRigidArea(dim2));
		right.add(fiveHundreds);
		right.add(Box.createRigidArea(dim1));
		
		JPanel otherPanel = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setVgap(80);
		otherPanel.setLayout(layout);
		otherPanel.setBackground(Color.black);
		otherPanel.add(other);
		
		JLabel message = new JLabel ("Choose the amount of money to withdraw");
		formatMessageLabel(message);
		
		add (message, BorderLayout.NORTH);
		
		add (left, BorderLayout.WEST);
		add (right, BorderLayout.EAST);
		add (otherPanel, BorderLayout.CENTER);
		
	}
	
	private class ButtonsListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event) 
		{
			JButton button = (JButton)event.getComponent();
			
			String question = "Do you want to make another action?";
			
			if (!(button.getName().equals("other")))
			{
				int amount = Integer.parseInt(button.getName());
				boolean greatSuccess = (_action.withdraw(amount));
				
				if (greatSuccess)
				{
					switcher.switchPanel(switcher.getNewTakeMoneyPanel());
				}
				
				boolean returnToMenu = (MessagesAndQuestions.showMessage(question));
				
				if (returnToMenu)
					switcher.switchPanel(switcher.getNewMenuPanel(MainPanel.getClientName()));
				else
					MainPanel.logOff();
			}
			else{
				otherPanel = new OtherAmountPanel(_action);
				switcher.switchPanel(otherPanel);
			}
		}
	}

	public OtherAmountPanel getOtherPanel() {
		return otherPanel;
	}
	
	public void ContinueButton ()
	{
		CancelButton();
	}

	
	public void CancelButton ()
	{
		String question = "Do you want to make another action?";
		
		boolean returnToMenu = (MessagesAndQuestions.showMessage(question));
		
		if (returnToMenu)
			switcher.switchPanel(switcher.getNewMenuPanel(MainPanel.getClientName()));
		else
			MainPanel.logOff();
	}
			
	
	

}
