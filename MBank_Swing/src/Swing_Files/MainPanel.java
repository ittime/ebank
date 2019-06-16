package Swing_Files;
import javax.swing.*;
import javax.swing.border.Border;

import mBank_files.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class MainPanel extends JPanel
{
	private static MBank _bank;
	
	private static ClientAction clientAction;
	
	private static String name;
	
	private static PanelSwitcher switcher;
	
	private LogoPanel logoPanel;
	private JPanel screen;
//	JPanel blankPanel;
	private JPanel buttonsPanel;
	
		
	public MainPanel(MBank bank) 
	{
		_bank = bank;
		clientAction =  null;
		
		setLayout(new BorderLayout());
		Border border = BorderFactory.createEtchedBorder();
		
		screen = new JPanel();
		screen.setBackground(Color.black);
		screen.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		switcher = new PanelSwitcher(screen);
		
		switcher.switchPanel(switcher.getNewPasswordPanel());
		
		logoPanel = new LogoPanel();
		logoPanel.setBorder(border);
		
		buttonsPanel = createButtons();
		buttonsPanel.setBorder(border);
		
		
		add (logoPanel, BorderLayout.NORTH);
		add (buttonsPanel, BorderLayout.SOUTH);
		add (screen, BorderLayout.CENTER);
			
	}

	private JPanel createButtons() 
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension (30, 140));
		panel.setBackground(Color.BLACK);
		
		JPanel blank = new JPanel();
		blank.setLayout(new BorderLayout());
		blank.setBackground(Color.BLACK);
		
		JPanel numberButtons = createNumberButtons();
		
		Border border = BorderFactory.createEtchedBorder();
		
		JButton continueButton = new JButton ("Continue");
		continueButton.addMouseListener(new ContinueButtonListener());
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.addMouseListener(new CancelButtonListener());

		panel.add(numberButtons, BorderLayout.CENTER);
		blank.add(continueButton, BorderLayout.WEST);
		blank.add(cancelButton, BorderLayout.EAST);
		
		JLabel lbl1 = new JLabel ("");
//		blank.setBackground(Color.DARK_GRAY);
//		blank.setForeground(Color.DARK_GRAY);
		
		blank.add(lbl1, BorderLayout.NORTH);
		blank.add(panel, BorderLayout.SOUTH);
		
		blank.setBorder(border);
		
	
		
		return blank;
	}

	private JPanel createNumberButtons() 
	{
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(Color.black);
		
				
		panel.setLayout(new GridLayout(5 , 2 ));
		emptyPanel.setLayout(new BorderLayout());
		
		
		
		JButton [] btns = new JButton[9];
		for (Integer i = 1; i < btns.length; i++ )
		{
			JButton b = new JButton (i.toString());
			b.addMouseListener(new numbersButtonsListener());
			panel.add(b);
			
			btns[i] = b;
		}
		
		
		JButton num9 = new JButton ("9");
		JButton num0 = new JButton ("0");
		
		num9.addMouseListener(new numbersButtonsListener());
		num0.addMouseListener(new numbersButtonsListener());
		
		panel.add(num9);
		panel.add(num0);
				
		JLabel logo2 = new JLabel ("MBank");
		
		emptyPanel.add(logo2, BorderLayout.CENTER);	
		emptyPanel.add(panel, BorderLayout.SOUTH);
		
		Border border = BorderFactory.createEtchedBorder();
		panel.setBorder(border);
		
		return panel;
		
		
	}
	
	private JLabel formatMessageLabel(JLabel message) {
		
		message.setForeground(Color.white);
		message.setFont(new Font ("Arial", 1, 18));
			
		return message;
	}
	
	public static PanelSwitcher getSwitcher()
	{
		return switcher;
	}
	
	public static void logOff()
	{
		clientAction = null;
		
		MessagesAndQuestions.showWarning("Thank you for using the MBank ATM. Please come back again");
		
		switcher.switchPanel(switcher.getNewPasswordPanel());
		
	}
	
		
		
	private class numbersButtonsListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event) 
		{
			
			JButton button = (JButton)event.getComponent();
			
			if (screen.getName().equals ("PasswordPanel"))
			{
				switcher.getPasswordPanel().addNumberToCode(button);
			}
			
			if (screen.getName().equals("OtherAmountPanel"))
			{
				switcher.getWithdrawPanel().getOtherPanel().addNumberToAmount(button);
			}
		}
	}
	
	private class ContinueButtonListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event) 
			{
				if (screen.getName().equals ("PasswordPanel") )
				{
					ClientAction action = switcher.getPasswordPanel().ContinueButton();
					clientAction = action;
					if (action !=null){
						name = action.get_client().get_clientName();
						switcher.switchPanel(switcher.getNewMenuPanel(name));}
					else {
						MessagesAndQuestions.showWarning("Wrong Password. Please try again");
						switcher.switchPanel(switcher.getNewPasswordPanel());
						}
				}
				
				if (screen.getName().equals("OtherAmountPanel"))
				{
					switcher.getWithdrawPanel().getOtherPanel().ContinueButton();
				}
				
				if (screen.getName().equals("BalancePanel"))
				{
					switcher.getBalancePanel().ContinueButton();
				}
				
				if (screen.getName().equals("HistoryPanel"))
				{
					switcher.getHistoryPanel().ContinueButton();
				}
				
				if (screen.getName().equals("WithdrawPanel"))
				{
					switcher.getWithdrawPanel().ContinueButton();
					return;
				}
			}
		}
		

		private class CancelButtonListener extends MouseAdapter
		{
			public void mouseClicked(MouseEvent event) 
			{
				if (screen.getName().equals ("PasswordPanel"))
				{
					switcher.getPasswordPanel().CancelButton();
					
					logOff();
				}
				
				if (screen.getName().equals("OtherAmountPanel"))
				{
					switcher.getWithdrawPanel().getOtherPanel().CancelButton();
					return;
				}
				
				if (screen.getName().equals("WithdrawPanel"))
				{
					switcher.getWithdrawPanel().CancelButton();
					return;
				}
				
				if (screen.getName().equals("BalancePanel"))
				{
					switcher.getBalancePanel().CancelButton();
					return;
				}
				
				if (screen.getName().equals("MenuPanel"))
				{
					switcher.getMenuPanel().CancelButton();
					return;
				}
				
				if (screen.getName().equals("HistoryPanel"))
				{
					switcher.getHistoryPanel().CancelButton();
					return;
				}
					
					
				
			}
			
		}


		public static MBank get_bank() {
			return _bank;
		}

		public static ClientAction getClientAction() {
			return clientAction;
		}

		public static String getClientName() {
			return name;
		}

		
		
	}	
	
	


