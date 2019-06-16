package Swing_Files;
import javax.swing.JPanel;


public class PanelSwitcher 
{
	JPanel screen;
	PasswordPanel passwordPanel;
	MenuPanel menuPanel;
	BalancePanel balancePanel;
	WithdrawPanel withdrawPanel;
	TakeMoneyPanel takeMoneyPanel;
	HistoryPanel historyPanel;
	
	
	public PanelSwitcher(JPanel theScreen)
	{
		screen = theScreen;
		
//		passwordPanel = new PasswordPanel();
//		menuPanel = new MenuPanel("");
//		balancePanel = new BalancePanel();
//		withdrawPanel = new WithdrawPanel[2];
//		historyPanel = new HistoryPanel();
		
	}
	
	
	public void switchPanel (JPanel toPanel)
	{
		if (toPanel != null)
		{
			try{
				JPanel fromPanel = (JPanel)screen.getComponent(0);
				fromPanel.setVisible(false);
				screen.remove(fromPanel);
				}catch (ArrayIndexOutOfBoundsException exception){}
			
			screen.add(toPanel);
			screen.setName(toPanel.getName());
		}
	}

	
	

	public void switchPanel(WithdrawPanel[] withdrawPanel, int num) 
	{
		switchPanel(withdrawPanel[num]);		
	}


	public PasswordPanel getPasswordPanel() {
		return passwordPanel;
	}
	
	public PasswordPanel getNewPasswordPanel() {
		setPasswordPanel(new PasswordPanel());
		return (getPasswordPanel());
	}


	public void setPasswordPanel(PasswordPanel passwordPanel) {
		this.passwordPanel = passwordPanel;
	}


	public MenuPanel getMenuPanel() {
		return menuPanel;
	}
	
	public MenuPanel getNewMenuPanel(String name) {
		setMenuPanel(new MenuPanel(name));
		return (getMenuPanel());
	}


	public void setMenuPanel(MenuPanel menuPanel) {
		this.menuPanel = menuPanel;
	}


	public BalancePanel getBalancePanel() {
		return balancePanel;
	}
	
	public BalancePanel getNewBalancePanel() {
		setBalancePanel(new BalancePanel((MainPanel.getClientAction())));
		return (getBalancePanel());
	}


	public void setBalancePanel(BalancePanel balancePanel) {
		this.balancePanel = balancePanel;
	}


	public WithdrawPanel getWithdrawPanel() {
		return withdrawPanel;
	}
	
	public WithdrawPanel getNewWithdrawPanel() {
		setWithdrawPanel(new WithdrawPanel());
		return (getWithdrawPanel());
	}


	public void setWithdrawPanel(WithdrawPanel withdrawPanel) {
		this.withdrawPanel = withdrawPanel;
	}


	public HistoryPanel getHistoryPanel() {
		return historyPanel;
	}
	
	public HistoryPanel getNewHistoryPanel() {
		if (HistoryPanel.checkPanel())
		{
		setHistoryPanel(new HistoryPanel());
		return(getHistoryPanel());
		}
		return null;
		
	}


	public void setHistoryPanel(HistoryPanel historyPanel) {
		this.historyPanel = historyPanel;
	}


	public TakeMoneyPanel getTakeMoneyPanel() {
		return takeMoneyPanel;
	}
	
	public TakeMoneyPanel getNewTakeMoneyPanel() {
		setTakeMoneyPanel(new TakeMoneyPanel());
		return (getTakeMoneyPanel());
	}


	public void setTakeMoneyPanel(TakeMoneyPanel takeMoneyPanel) {
		this.takeMoneyPanel = takeMoneyPanel;
	}


}

	