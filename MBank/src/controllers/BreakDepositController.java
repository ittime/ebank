package controllers;

import java.io.IOException;
import java.util.ArrayList;
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

import mBankProperties.MBankProperties;
import actions.ClientAction;

/**
 * Servlet implementation class for Servlet: CreateDepositController
 *
 */
public class BreakDepositController extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
		HttpSession session = request.getSession(false);
		ClientAction action = (ClientAction)session.getAttribute("action");

				
		/** 
		 * The break-deposit sequence is made of 3 phases:
		 * A: ask the client to create the deposit
		 * B: ask the client to confirm
		 * C: create the deposit and show to the client  
		 * D: exit the create-deposit cycle
		 */

		
		String dispatchAddress = "/jsp/viewManager.jsp";
		request.setAttribute("page", "/jsp/depositJSP/breakDeposit.jsp");

		String phase = request.getParameter("breakDepositPhase");
		
		if (phase.equals("a")) //phase A
		{//check if there are any deposits at all
			ArrayList<Deposit>deposits = action.reportDepositsList();

			if (deposits.size() == 0){
				dispatchAddress = "/errorPages/noDepositsError.html";
			}
		} 
		if (phase.equals("b")) // phase B
		{
			Deposit deposit = action.reportDeposit(new Deposit((Integer.parseInt(request.getParameter("depositID"))))); //get the deposit based on the id taken from the user
			session.setAttribute("deposit", deposit);
		}
		
		if (phase.equals("c")) // phase C
		{
			Deposit deposit = (Deposit)session.getAttribute("deposit");
			int id = deposit.get_depositId();
			
			action.breakDeposit(new Deposit(id));
			
			dispatchAddress = ("/jsp/depositJSP/breakDepositSuccess.jsp");
		}
		
		if (phase.equals("d")) //phase D
		{
			session.removeAttribute("deposit");
			
			dispatchAddress = "/MainController?command=details";			
		}
		
		ServletContext ctx = getServletContext();
		ctx.getRequestDispatcher(dispatchAddress).forward(request, response);
		
		}catch (Exception e){
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPages/errorGeneral.jsp").forward (request, response);}
		}
	}
