package Swing_Files;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mBank_files.ClientAction;

public abstract class panelsManager extends JPanel
{
	protected ClientAction _action;
	protected PanelSwitcher switcher;

	protected JLabel formatMessageLabel(JLabel message) 
	{		
		message.setForeground(Color.white);
		message.setFont(new Font ("Arial", 1, 18));
			
		return message;
	}
	
	protected void setPanel(String name, int VGap, int HGap) 
	{		
		_action = MainPanel.getClientAction();
		switcher = MainPanel.getSwitcher();
		
		setName (name);
		setBackground(Color.black);
		BorderLayout layout = new BorderLayout();
		if (VGap != 0)
			layout.setVgap(VGap);
		if (HGap !=0)
			layout.setHgap(HGap);
		setLayout(layout);
	}
	

}
