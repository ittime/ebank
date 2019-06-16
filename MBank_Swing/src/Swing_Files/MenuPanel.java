package Swing_Files;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.Position;


public class MenuPanel extends panelsManager {
	
	JButton withdrawScreen, balanceScreen, historyScreen, logOff;
		
	public MenuPanel(String name) 
	{
		setPanel("MenuPanel", 10, 20);
		
		String message = "";
		if (!(name.equals("")))
			message = "             Welcome back " + name;
		JLabel welcome = new JLabel (message);
		formatMessageLabel(welcome);
		
		withdrawScreen = new JButton("Withdraw Money      ");
		withdrawScreen.setName("WithdrawPanel");
		withdrawScreen.addMouseListener(new ButtonsListener());
		
		balanceScreen = new JButton ("Watch your balance");
		balanceScreen.setName("BalancePanel");
		balanceScreen.addMouseListener(new ButtonsListener());
		
		historyScreen = new JButton ("Watch your bank statement");
		historyScreen.setName("HistoryPanel");
		historyScreen.addMouseListener(new ButtonsListener());
		
		logOff = new JButton ("Logoff from the system         ");
		logOff.setName("logoff");
		logOff.addMouseListener(new ButtonsListener());
			
		Dimension dim1 = new Dimension(0, 50);
		Dimension dim2 = new Dimension(0, 70);
		
		JPanel left = new JPanel();
		left.setBackground(Color.black);
		left.setLayout(new BoxLayout (left, BoxLayout.Y_AXIS));
		left.add(Box.createRigidArea(dim1));
		left.add(withdrawScreen);
		left.add(Box.createRigidArea(dim2));
		left.add(balanceScreen);
		
		JPanel right = new JPanel();
		right.setBackground(Color.black);
		right.add(Box.createRigidArea(dim1));
		right.setLayout(new BoxLayout (right, BoxLayout.Y_AXIS));
		right.add(historyScreen);
		right.add(Box.createRigidArea(dim2));
		right.add(logOff);
		
		add (welcome, BorderLayout.NORTH);
		
		add (left, BorderLayout.WEST);
		add (right, BorderLayout.EAST);
		
	}
	
	private class ButtonsListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent event) 
		{
			JButton button = (JButton)event.getComponent();
			
			PanelSwitcher switcher = MainPanel.getSwitcher();
			
			if (button.getName().equals("WithdrawPanel"))
				switcher.switchPanel(switcher.getNewWithdrawPanel()); 
			
			if (button.getName().equals("BalancePanel"))
				switcher.switchPanel(switcher.getNewBalancePanel()); 
			
			if (button.getName().equals("HistoryPanel"))
			{
				switcher.switchPanel(switcher.getNewHistoryPanel());
			}
			
			if (button.getName().equals("logoff"))
			{
				logOff();
			}
		}
	}
	
	public void CancelButton ()
	{
		logOff();	
	}
	
	private void logOff()
	{
	int question = JOptionPane.showConfirmDialog(null, "Are you sure that you want to logoff?");
		
		if (question == JOptionPane.YES_OPTION)
			MainPanel.logOff();
		else
			switcher.switchPanel(switcher.getNewMenuPanel(""));
	}



}
