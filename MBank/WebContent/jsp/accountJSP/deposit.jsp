<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>Deposit</title>
<style type="text/css">
<!--
body,td,th {
	color: #CCCC99;
}

body {
	background-color: #993300;
}

.style3 {
	font-size: 24px
}

.style4 {
	color: #FFCC00
}
-->
</style>

<%
String phase = request.getParameter("depositPhase");
String fileName = "withdrawTextBlank.jsp";

if (phase.equals("b")){ //if we are on phase 'b', the client should see the commission value
	Double commission = (Double)session.getAttribute("commission");

	%>
	<script type="text/javascript">
	var check = confirm ("Deposit amount: <%=request.getParameter("amount")%>\nFor this action you will be charged with a commission of <%=commission.doubleValue()%>");
	
	if (check == true){
		top.location = "http://localhost:8080/MBank/DepositController?depositPhase=c";}
	else{
		top.location = "http://localhost:8080/MBank/DepositController?depositPhase=a";}
	</script>
<%
fileName = "depositText.jsp"; } //if the client doesn't support JavaScript 

%>
</head>
<body>

<div align="center">
<p><span class="style3">Please choose the amount of money to
deposit:</span><br>
</p>

<form action="http://localhost:8080/MBank/DepositController"
	method="post">Amount: <select name="amount">
	<option value="50" selected>50</option>
	<option value="100">100</option>
	<option value="200">200</option>
	<option value="500">500</option>
	<option value="1000">1000</option>
</select> <input type="hidden" name="depositPhase" value="b"> <%-- continue to the next phase --%>

<p><input type="submit" name="button" id="button" value="Submit"></p>

</form>
<br />
<jsp:include page="<%=fileName%>" /></div>
</body>
</html>