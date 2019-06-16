<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<%@page import="actions.ClientAction"%>
<%@page import="valueObject.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page import="valueObject.BalanceAction"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>Account's Log</title>
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

.linkage {
	font-size: small;
	font-weight: bold;
}
-->
</style>
<%
ClientAction action = (ClientAction)session.getAttribute("action");
String prmtr = (String)request.getParameter("par"); //is it the account log or the deposit log
ArrayList <BalanceAction> actions = action.reportActionsHistory(prmtr);


%>
</head>
<body>
<%
if (actions.size() !=0){
%>
<div align="center">
<p class="style1">Deposit Details</p>
<table width="200" border="3" cellspacing="3" cellpadding="2">
	<tr>
		<th scope="col">Action ID</th>
		<th scope="col">Amount</th>
		<th scope="col">Description</th>
		<th scope="col">Date</th>
	</tr>
	<%
	
	for (BalanceAction ba:actions){
		
		ba = ba.formatActionText();
	%>
	<tr>
		<td><%=ba.get_actionId()%></td>
		<td><%=ba.get_actionAmmount()%></td>
		<td><%=ba.get_action()%></td>
		<td><%=ba.get_date()%></td>
	</tr>
	<%
	};
	%>
</table>
<%
}
else {
%>
	out.print("<script type='text/javascript'>\nalert ('You dont have any deposits!');\n</script>");
<%
}
%>
<p class='linkage' align="center"><a
	href='/MBank/MainController?command=details'>~Go Back~</a></p>
</div>
</body>
</html>