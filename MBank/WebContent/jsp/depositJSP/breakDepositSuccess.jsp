<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" session="false"
     import="valueObject.*"
	%>

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
int depositId = deposit.get_depositId();

%>
</head>
<body>
<script type="text/ecmascript">
	alert ("The deposit(ID:<%=depositId%>) was succesfuly broken.");
</script>
<p align="center">
<a href="http://localhost:8080/MBank/BreakDepositController?breakDepositPhase=d">Go back</a>
</p>
</body>
</html>