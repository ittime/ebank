package Swing_Files;
import javax.swing.JOptionPane;


public class MessagesAndQuestions
{
	public static void showWarning (String warning)
	{
		JOptionPane.showMessageDialog(null, warning);
	}
	
	public static boolean showMessage (String message)
	{
		int question = JOptionPane.showConfirmDialog(null, message);
		
		if (question == JOptionPane.YES_OPTION)
			return true;
		else return false;
		
	}
	

	

}
