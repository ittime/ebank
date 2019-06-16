package Swing_Files;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class testMainFrame extends JFrame {
	
	private JPanel jp1, jp2, activePanel = new JPanel(), p5;
	private JLabel label1, label2, label3, label4, label5;
	JLabel [] lbls;
	String code = "";
	
	public testMainFrame() {


		JFrame mainFrame = new JFrame ();
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		
//		
//		lbls = new JLabel [5];
//		
//		
//		
//		
//		for (Integer i=0; i < lbls.length; i++)
//		{
//			JLabel lbl = new JLabel ("_");
//			
//			lbl.setVisible(true);
//			
//			jp1.add(lbl);
//			
//			lbls[i] = lbl;
//		}
//		
		
		
		
		//jp2.add(label2);
		
		jp1.setVisible(true);
		jp2.setVisible(false);
		
		JButton bt = new JButton ("switch!");
		
		
		bt.setName("passwordPanel");
		
		System.out.println (bt.getName());
		
		bt.addMouseListener(new switchButton());;
		
		System.out.println (bt.getText());
		
		JPanel p3 = new JPanel();
		p3.setName("1");
		p3.add(new JLabel ("*first panel*"));
		
		p5 = new JPanel();
		p5.setName("2");
		p5.add(new JLabel ("*second panel*"));
		
		p3.setVisible(true);
		p5.setVisible(false);
		
		activePanel.add(p3);
		
		System.out.println (activePanel.getComponentCount());
		//activePanel.setName(p3.getName());
		
		activePanel.add(p5);
		System.out.println (activePanel.getComponentCount());
		
		JPanel jp22 = new JPanel();
		jp22.setName("22");
		jp22.add(new JLabel ("*22 22 22 22*"));
		jp22.setVisible(false);
		
		activePanel.add (jp22);
		
		System.out.println (activePanel.getComponentCount());
		
		Component [] josh = activePanel.getComponents();
		
		for (int i = 0; i < josh.length; i++)
		{
			System.out.println (josh[i].getName());
		}
	
		
		JPanel p4 = new JPanel();
		
		p4.add (activePanel);
		
		
		
		
		p4.add(bt);
		
		
		Container cp = mainFrame.getContentPane();
		
		//JButton [] btns = new JButton[10];
		
		
//		for (Integer i=0; i < btns.length; i++)
//		{
//			JButton b = new JButton (i.toString());
//			b.addMouseListener(new buttonListener());
//			p4.add(b);
//			
//			btns[i] = b;
//		}
//		
		
		
		
		
		
		cp.setLayout(new BorderLayout());
			
	//	cp.add(p3);
		
	//	p3.add(jp1);
		
		cp.add(p4, BorderLayout.NORTH);
		
		
		p3.setPreferredSize(new Dimension (400, 100));
		
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
		
	}
	
	private class switchButton implements MouseListener
	{
		public void mouseClicked(MouseEvent arg0) 
		{
			
			Component [] josh = activePanel.getComponents();
			
			for (int i = 0; i < josh.length; i++)
			{
				josh [i].setVisible(false);
				
				if ((i+1) != josh.length)
				josh [i+1].setVisible(true);
				
				try {
					Thread.sleep (2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			
		

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private class buttonListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event) {
			JButton a = (JButton)event.getComponent();
			
			
			
			//label1.setText(event.getButton());
			
//			
//			label1.setText(
			
			
			
			//JButton a = (JButton)event.getComponent();
			
			//Container b = a;
			
			int i =0;
			
					
			while (i<4 && lbls[i].getText() != "_")
			{
				i++;
			}
		
			
			lbls[i].setText("*");
			
			if (code.length()<5)
				code += a.getText();
			
			System.out.println (i + ": " + code);
			
//				
			
			
			
			
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
