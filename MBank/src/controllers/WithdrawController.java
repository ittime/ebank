package controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.Request;

import actions.ClientAction;


/**
 * WithdrawController
 *
 */
public class WithdrawController extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** 
		 * The withdraw sequence is made of 4 phases:
		 * A: the client didn't choose yet the amount of money to withdraw
		 * B: the client chose the money but need to know about the commission and to validate his credit
		 * C: the client agreed to withdraw the money and his credit is OK 
		 * D: the money was withdrawn
		 */

		try{
			
		HttpSession session = request.getSession(false);
		String dispatchAddress = "/jsp/viewManager.jsp";
		request.setAttribute("page", "/jsp/accountJSP/withdraw.jsp");

		String phase = request.getParameter("withdrawPhase");

		if (phase.equals("a")) //phase A
		{} //nothing to do, just load the page
		
		if (phase.equals("b")) // phase B
		{
			int amount = (Integer.parseInt(request.getParameter("amount"))); //the amount to withdraw
			ClientAction action = (ClientAction)session.getAttribute("action"); 
			
			double commission = action.getCommission(amount); //what is the client's commission (if it's negative then the credit is not enough)
			
			session.setAttribute("commission", commission); //so we could use it later
			session.setAttribute("amount", amount);
				
		}
		
		if (phase.equals("c")) // phase C
		{
			int amount = (Integer)(session.getAttribute("amount")); //the amount to withdraw
			ClientAction action = (ClientAction)session.getAttribute("action"); 
			
			action.withdraw(amount);
			
			dispatchAddress="/withdrawSuccess.html";
		}
		
		
		if (phase.equals("d")) // phase D
		{
			session.removeAttribute("amount");
			dispatchAddress = "/MainController?command=details";			
		}
		
		ServletContext ctx = getServletContext();
		ctx.getRequestDispatcher(dispatchAddress).forward(request, response);
		
		}catch (Exception e){
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPages/errorException.jsp").forward (request, response);}
	
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}   	  	    
}