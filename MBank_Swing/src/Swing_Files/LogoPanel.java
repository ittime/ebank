package Swing_Files;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LogoPanel extends panelsManager

{
	
	Icon logo = new ImageIcon ("logo.jpg");
	
	public LogoPanel() 
	{
		setPanel("LogoPanel", 0, 0);
		JLabel image = new JLabel (logo);
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		add (image, BorderLayout.WEST);
		setSize(2, 2);
		
	}

}
