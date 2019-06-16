<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>

<%@page import="java.util.ArrayList"%>
<%@page import="actions.ClientAction"%>
<%@page import="valueObject.Deposit"%>
   
<%@page errorPage="/errorPages/errorGeneral.jsp"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Insert title here</title>
<%
ClientAction action = (ClientAction)session.getAttribute("action");
ArrayList<Deposit> deposits = action.reportDepositsList(); //get all of the client's deposits
%>

</head>
<body>
<%
for (Deposit deposit : deposits) {
	int depositId = deposit.get_depositId();
	out.print("<input type='radio' name='depositID' id='radio' value='"+ depositId + "'</input>Deposit ID: " + depositId + "<br/>");
}
%>
</body>
</html>