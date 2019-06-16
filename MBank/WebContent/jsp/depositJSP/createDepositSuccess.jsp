<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" session="false"
     import="valueObject.*"
	import="actions.ClientAction" import="java.text.SimpleDateFormat"
	import="java.util.Calendar"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Success</title>
<style type="text/css">
<!--
body,td,th {
	color: #33FF66;
}
body {
	background-color: #993300;
}
.style1 {
	color: #CCCC99;
	font-weight: bold;
}
a:link {
	color: #FFFF66;
}
a:visited {
	color: #FFFF66;
}
-->
</style>
<%
HttpSession session = request.getSession(false);
Deposit deposit = (Deposit)session.getAttribute("deposit");
ClientAction action = (ClientAction)session.getAttribute("action");

int depositId = (Integer)session.getAttribute("depositId");
double interest = action.getInterest(deposit.get_interestType());

%>
</head>
<body>
<script type="text/ecmascript">
	alert ("Your deposit (ID:<%=depositId%>) was succesfuly added. Your interest rate is <%=interest%>");
</script>
<p align="center">
<a href="http://localhost:8080/MBank/CreateDepositController?createDepositPhase=c">Go back</a>
</p>
</body>
</html>