package controllers;


import java.io.IOException;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MainController responsible for managing responses
 */

public class MainController extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	try{
		ServletContext ctx = getServletContext();

		String dispatchAddress = "";
		String command = request.getParameter("command");
		
		if (command == null)
		{}	//throw exception

		if (command.equals("login"))
			dispatchAddress = "/LoginController";
		if (command.equals("logOut"))
			dispatchAddress = "/jsp/loginJSP/logOff.jsp";
		if (command.equals("details")){
			dispatchAddress = "/jsp/viewManager.jsp"; request.setAttribute("page", "accountJSP/accountDetails.jsp");}
		if (command.equals("withdraw"))
			dispatchAddress = "/WithdrawController";
		if (command.equals("deposit"))
			dispatchAddress = "/DepositController";
		if (command.equals("createDeposit"))
			dispatchAddress = "/CreateDepositController";
		if (command.equals("breakDeposit"))
			dispatchAddress = "/BreakDepositController";
		
		ctx.getRequestDispatcher(dispatchAddress).forward(request, response);
		
			
}catch (Exception e){
	e.printStackTrace();
	getServletContext().getRequestDispatcher("/errorPages/errorException.html").forward (request, response);}
}  	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}