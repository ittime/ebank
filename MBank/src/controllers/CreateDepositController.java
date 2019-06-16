package controllers;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import valueObject.Deposit;
import valueObject.Deposit_Constant;
import valueObject.Deposit_Foreign;
import valueObject.Deposit_Index;

import actions.ClientAction;

/**
 * This servelt controls the creating deposit cycle
 *
 */
public class CreateDepositController extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** 
		 * The create-deposit sequence is made of 3 phases:
		 * A: ask the client to create the deposit
		 * B: create the deposit and show to the client  
		 * C: exit the create-deposit cycle
		 */

		try{
			
		HttpSession session = request.getSession(false); 
		ClientAction action = (ClientAction)session.getAttribute("action"); 

		String dispatchAddress = "/jsp/viewManager.jsp";
		request.setAttribute("page", "/jsp/depositJSP/createDeposit.jsp");

		String phase = request.getParameter("createDepositPhase");

		if (phase.equals("a")) //phase A
		{} //nothing to do, just load the page

		if (phase.equals("b")) // phase B
		{
			//load the values
			int id = action.get_client().get_clientId(); 
			int amount = (Integer.parseInt(request.getParameter("amount"))); //the amount to deposit
			Calendar calendar = Calendar.getInstance(); //create the "from-date"
			int day = (Integer.parseInt(request.getParameter("day"))); 
			int month = (Integer.parseInt(request.getParameter("month"))); 
			int year = (Integer.parseInt(request.getParameter("year")));
			Calendar calendar2 = Calendar.getInstance(); //create the "until-date"
			calendar2.set(year, month, day); 
			int type = (Integer.parseInt(request.getParameter("type"))); //'0' for constant, '1' for index and '2' for foreign

			//decide the type of the deposit object and put it into the session
			if (type==0){
				Deposit_Constant deposit = new Deposit_Constant (id, amount, calendar, calendar2);
				session.setAttribute("deposit", deposit);
				}
			if (type==1){
				Deposit_Index deposit = new Deposit_Index (id, amount, calendar, calendar2);
				session.setAttribute("deposit", deposit);
				}
			if (type==2){
				Deposit_Foreign deposit = new Deposit_Foreign (id, amount, calendar, calendar2);
				session.setAttribute("deposit", deposit);
				}
			
			Deposit deposit = (Deposit)(session.getAttribute("deposit")); //the deposit

			int depositId = action.addDeposit(deposit); // create the deposit

			session.setAttribute("depositId", depositId); //get the deposit's id into the session
			
			request.setAttribute("page", "/jsp/depositJSP/createDepositSuccess.jsp");
			
			}
		

			if (phase.equals("c")) // phase C
			{
				session.removeAttribute("createDepositPhase");
				session.removeAttribute("deposit");
				session.removeAttribute("depositId");
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