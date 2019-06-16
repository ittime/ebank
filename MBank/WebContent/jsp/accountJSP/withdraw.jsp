<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>withdraw</title>

<style type="text/css">
<!--
body,td,th {
	color: #CCCC99;
}
body {
	background-color: #993300;
}
.style3 {font-size: 24px
		 }
.style4 {
	color: #FFCC00
}
-->
</style>

<%
String phase = request.getParameter("withdrawPhase");
String fileName = "withdrawTextBlank.jsp";

if (phase.equals("b")) { //if the client is asked to confirm his deposit

	Double commission = (Double)session.getAttribute("commission");

	//if the user can still withdraw this amount
	if (commission>0) {
%>
	<script type="text/javascript">
	var check = confirm ("Withdraw amount: <%=request.getParameter("amount")%>\nFor this action you will be charged with a commission of <%=commission.doubleValue()%>");
	
	if (check == true){//the client agree to pay the commission
		top.location = "http://localhost:8080/MBank/WithdrawController?withdrawPhase=c";}
	else{ // the client doesn't want to pay rhe commission
		top.location = "http://localhost:8080/MBank/WithdrawController?withdrawPhase=a";}
	</script>
<%
	//if the user had reached his credit restriction
	}else{
%>
	<script type="text/javascript">
	var check = confirm ("Can't withdraw from the account: you have reached your credit restriction\nDo you want to try some other amount?");
	
	if (check == true){ //if the client wants to try some other amount
		top.location = "http://localhost:8080/MBank/WithdrawController?withdrawPhase=a";}
	else {
		top.location = "http://localhost:8080/MBank/WithdrawController?withdrawPhase=d";}
	</script>
<%
	}
	
	fileName = "withdrawText.jsp"; } //if the browser doesn't support JavaScript

%>
</head>
<body>

<div align="center">
<p><span class="style3">Please choose the amount of money to
withdraw:</span><br>
</p>

<form action="http://localhost:8080/MBank/WithdrawController" method="post">
	Amount: 
	<select	name = "amount">
	<option value="50" selected>50</option>
	<option value="100">100</option>
	<option value="200">200</option>
	<option value="500">500</option>
	<option value="1000">1000</option>
	</select>
	
	<input type="hidden" name="withdrawPhase" value="b">  <%-- continue to the next phase --%>

	<p><input type="submit" name="button" id="button" value="Submit"></p>
	
</form>
<br/>
<jsp:include page="<%=fileName%>" />

</div>
</body>
</html>