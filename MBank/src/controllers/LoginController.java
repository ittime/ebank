package controllers;


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import MBank.MBank;
import actions.ClientAction;

/**
 * loginController
 * responsible for validating the user's identity
 *
 */
public class LoginController extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	public void init (ServletConfig config) throws ServletException{

		super.init(config);

		MBank bank = MBank.createBank();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			
		ServletContext ctx = getServletContext();
		String toPage = "";

		String userName = request.getParameter("userName");
		int password = Integer.parseInt(request.getParameter("password"));

		HttpSession session = request.getSession(true);

		MBank bank = MBank.get_bank();

		ClientAction action = (ClientAction) bank.accessManager (userName, password);

		if (action != null){
			toPage = "accountJSP/accountDetails.jsp";
			session.setAttribute("loggedUser", "true");
			session.setAttribute("action", action);
			request.setAttribute("page", toPage);
		}
		else {
			toPage = "loginJSP/login.jsp";
			session.setAttribute("loggedUser", "false");
			request.setAttribute("errorID", "true");
			request.setAttribute("page", toPage);
		}


		ctx.getRequestDispatcher("/jsp/viewManager.jsp").forward(request, response);		

		}catch (Exception e){
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPages/errorGeneral.jsp").forward (request, response);}
	
	}
	
	  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}