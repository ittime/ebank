<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="actions.ClientAction"%>
<%@page import="valueObject.Deposit"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<html>
<head>
<style type="text/css">
<!--
body,td,th {
	color: #CCCC99;
}

body {
	background-color: #993300;
}

a:link {
	color: #FFFF66;
}
a:visited {
	color: #FFFF66;
}
.head {
	font-size: large;
	font-weight: bold;
}
.linkage {font-size: small; font-weight: bold; }
-->
</style>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Deposit Details</title>
<%
ClientAction action = (ClientAction)session.getAttribute("action");
int depositId = (Integer.parseInt(request.getParameter("depositId")));

ArrayList<Deposit> deposits = action.reportDepositsList(); //get all of the client's deposits

String DepositsTable = "";

if (depositId !=0){
	
	Deposit deposit = action.reportDeposit(new Deposit (depositId));
	
	Calendar openDate = deposit.get_openDate();
	Calendar closeDate = deposit.get_closureDate();
	
	String format = "dd/MM/yyyy";	
	SimpleDateFormat sdf = new SimpleDateFormat(format);	
	String formatOpenDate = (sdf.format(openDate.getTime()));
	String formatClosureDate = (sdf.format(closeDate.getTime()));
		
	DepositsTable =
	"<div align='center'>"+
	  "<p>Deposit Details</p>"+
	  "<table width='200' border='3' cellspacing='3' cellpadding='2'>"+
	    "<tr>"+
	      "<th scope='col'>Client ID</th>"+
	      "<th scope='col'>Deposit ID</th>"+
	      "<th scope='col'>Amount</th>"+
	      "<th scope='col'>Interest Type</th>"+
	      "<th scope='col'>From</th>"+
	      "<th scope='col'>Until</th>"+
	      "<th scope='col'>Estimated Amount</th>"+
	      "<th scope='col'>Final Amount</th>"+
	    "</tr>"+
	    "<tr>"+
	      "<td>"+deposit.get_clientId()+"</td>"+
	      "<td>"+deposit.get_depositId()+"</td>"+
	      "<td>"+ClientAction.formatResult(deposit.get_amount())+"</td>"+
	      "<td>"+deposit.get_interestType()+"</td>"+
	      "<td>"+formatOpenDate+"</td>"+
	      "<td>"+formatClosureDate+"</td>"+
	      "<td>"+ClientAction.formatResult(deposit.get_estimatedAmount())+"</td>"+
	      "<td>"+ClientAction.formatResult(deposit.get_finalAmount())+"</td>"+
	   "</tr>"+
	  "</table>"+
	  "</div>";
}
%>
<script type="text/javascript">
function subForm(element)
{
	element.checked = true;
	var depositsForm = document.getElementById('depositsForm');
	depositsForm.action = location.href; 
	depositsForm.submit(); 
} 
</script>
</head>
<body>
<%
if (deposits.size() != 0){

	out.print("<form id='depositsForm' action='reportsJSP/depositsDetails.jsp' method='get'><p align='center'>");

	for (Deposit deposit : deposits) {
	int getDepositId = deposit.get_depositId();
	out.print("<input type='radio' name='depositId' id='radio' value='"+ getDepositId + "' onclick='subForm(this)'></input>Deposit ID: " + getDepositId + "<br/>");
	}
	out.print ("</p></form>");
}
else
		out.print("<script type='text/javascript'>\nalert ('You dont have any deposits!');\n</script>");
%>
<p align="center"><%=DepositsTable%></p>
<p class='linkage' align="center"><a href = '/MBank/MainController?command=details'>~Go Back~</a></p>
</body>
</html>