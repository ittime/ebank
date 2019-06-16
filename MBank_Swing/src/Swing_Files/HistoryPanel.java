package Swing_Files;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;


import mBank_files.BalanceAction;
import mBank_files.ClientAction;


public class HistoryPanel extends panelsManager 
{
	
	private String [] headers = {"Action ID", "Action", "Amount", "Date"};

	private JTable table;
	private JLabel clientID;
	
	private double amount;
	private String action;
	
	private static ClientAction staticAction = MainPanel.getClientAction();;
	
	JPanel panel;
	
	
	
	
	public HistoryPanel()
	{
		if (checkPanel())
			createPanel();
			
	}
	
	public static boolean checkPanel()
	{
		
		ArrayList<BalanceAction> actions = staticAction.reportsActionsHistory();
		
		try{
			
		int clientId = actions.get(0).get_clientId();
		
		}catch (Exception ex){
			MessagesAndQuestions.showWarning("There are no actions in the history");
			return false;}
		
		return true;
		
	}
	
	private void createPanel ()
	{
		setPanel();
		
		table = createTable();
		
		table.removeEditor();
	
		JScrollPane scroll = new JScrollPane (table);
		scroll.setForeground(Color.black);
					
		formatMessageLabel(clientID);
				
		add (clientID);
		add(Box.createRigidArea(new Dimension (0,5)));
		add (scroll);
		
		
	}

	private void setPanel() {
		setBackground(Color.black);
		_action = MainPanel.getClientAction();
		switcher = MainPanel.getSwitcher();
		setName("HistoryPanel");
		setLayout(new BoxLayout (this, BoxLayout.Y_AXIS));
	}
	
	
	private JTable createTable()
	{
		ArrayList<BalanceAction> actions = _action.reportsActionsHistory();
		
		int clientId = actions.get(0).get_clientId();
		clientID = new JLabel ("Actions history for Client ID:" + clientId);
		
		DefaultTableModel model = new DefaultTableModel (headers, 0);
				
		for (BalanceAction ba:actions)
			{
			
			Vector row = new Vector();
			
			row.add(ba.get_actionId());
			
			String newAction = ba.get_action();
			
			if (formatActionString(newAction))
			{
				row.add(action);
				row.add (amount);
			}
			else 
			{
				row.add(ba.get_action());
				row.add(ba.get_actionAmmount());
			}
				
			row.add(ba.get_date());
			
			model.addRow(row);
			}
		
		return new JTable(model);
		}
		

	
	
	private boolean formatActionString(String actionFromBalance) {
		
		String deposit = actionFromBalance.substring(0, 7);
		String withdraw = actionFromBalance.substring(0, 8);
		
		if ((deposit.equals("deposit")||(withdraw.equals("withdraw"))))
		{
			int firstPar = (actionFromBalance.indexOf("(")+1);
			int lastPar = (actionFromBalance.lastIndexOf(")"));
			
			//a
			amount = (Double.parseDouble(actionFromBalance.substring(firstPar, lastPar)));
			
			//b
			action = (actionFromBalance.substring(0, firstPar-1));
			
			return true;
		}
		
		return false;
		
		
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





