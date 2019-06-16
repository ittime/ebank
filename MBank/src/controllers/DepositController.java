package controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mBankProperties.MBankProperties;
import actions.ClientAction;

/**
 * Depositing money controller
 *
 */
 public class DepositController extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** 
		 * The deposit sequence is made of 4 phases:
		 * A: the client didn't choose yet the amount of money to deposit
		 * B: the client chose the money but need to know about the commission
		 * C: the client agreed to deposit the money  
		 * D: the money was deposited
		 */

	   try{
		HttpSession session = request.getSession(false);
		String dispatchAddress = "/jsp/viewManager.jsp";
		request.setAttribute("page", "/jsp/accountJSP/deposit.jsp");

		String phase = request.getParameter("depositPhase");

		if (phase.equals("a")) //phase A
		{} //nothing to do, just load the page
		
		if (phase.equals("b")) // phase B
		{
			int amount = (Integer.parseInt(request.getParameter("amount"))); //the amount to deposit
			ClientAction action = (ClientAction)session.getAttribute("action"); 
			
			double commission = MBankProperties.getCommission(action.get_client().get_clientRank()); //what is the client's commission 
			
			session.setAttribute("commission", commission); //so we could use it later
			session.setAttribute("amount", amount);
				
		}
		
		if (phase.equals("c")) // phase C
		{
			int amount = (Integer)(session.getAttribute("amount")); //the amount to deposit
			ClientAction action = (ClientAction)session.getAttribute("action"); 
			
			action.deposit(amount);
			
			dispatchAddress = ("/depositSuccess.html");
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