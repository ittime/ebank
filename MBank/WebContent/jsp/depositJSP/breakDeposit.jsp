<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255" import="mBankProperties.MBankProperties"
	import="valueObject.*"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>Break Deposit</title>
<style type="text/css">
<!--
body,td,th {
	color: #CCCC99;
}

body {
	background-color: #993300;
}

.style3 {
	font-size: 36px;
	font-weight: bold;
	color: #CCCC00;
}

.style4 {
	font-size: 16px
}

.style7 {
	font-size: 14px
}
-->
</style>
<%
	String phase = request.getParameter("breakDepositPhase");
	Deposit deposit = (Deposit) session.getAttribute("deposit");

	String fileName = "/jsp/accountJSP/withdrawTextBlank.jsp";

	if (phase.equals("b")) //if we are on phase 'b', the client should confirm the deposit to be broken
	{
%>
<script type="text/javascript">
	
	var check = confirm ("Are you sure that you want to break this deposit? (ID:<%=deposit.get_depositId()%>)  \n\nYour estimated amount is: <%=deposit.get_estimatedAmount()%>, and the penalty is: <%=MBankProperties.getDeposit_penalty()%>");
	
	if (check == true){
		top.location = "http://localhost:8080/MBank/BreakDepositController?breakDepositPhase=c";
	}else {
		top.location = "http://localhost:8080/MBank/BreakDepositController?breakDepositPhase=a";}
	</script>
<%
	fileName = "breakDepositConfirm.jsp"; // if the client doesn't support JavaScript

	} 

%>
</head>
<body>

Choose the deposit you want to break:

<form action="/MBank/BreakDepositController" method="get">

<jsp:include page="displayDeposits.jsp" /> 

<input name="breakDepositPhase" type="hidden" value="b" />

<p align="center"><input type="submit" name="button" id="button" value="Submit" /></p>

</form>

<jsp:include page="<%=fileName%>" />

</body>
</html>