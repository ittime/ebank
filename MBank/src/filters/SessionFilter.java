package filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class for Servlet: SessionFilter
 *
 */
public class SessionFilter extends javax.servlet.http.HttpServlet implements Filter{
	static final long serialVersionUID = 1L;

	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {


		try{
			
		
		HttpServletRequest request = (HttpServletRequest) req;

		boolean checkLogin = false;
		
		HttpSession session = request.getSession(false); // get the session (if exist)
		String command = req.getParameter("command");
		if (command != null){
			checkLogin = command.equals("login"); //check if the user is currently trying to connect
		}
		if (session != null || (checkLogin)) // if there is a session or there isn't a session but the client is currently trying to login
			chain.doFilter(req, res); //continue normally
		else{ // otherwise --- it should 'throw' the client to the login screen
			res.getWriter().print("<script type='text/javascript'>top.location = 'http://localhost:8080/MBank/jsp/viewManager.jsp?sessionError=true';</script>"); // return to the mainscreen and mention that the client tried to enter without logging in first
		}
		
		}catch (Exception e){
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/errorPages/errorException.jsp").forward (req, res);}
		}

	

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}



}