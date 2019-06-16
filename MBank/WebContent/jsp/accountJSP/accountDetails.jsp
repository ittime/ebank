<%@ page language="java" import="valueObject.Client" import="actions.ClientAction" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
ClientAction action = (ClientAction) session.getAttribute("action");
Client client = action.get_client();
%>



<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Insert title here</title>
<style type="text/css">
<!--
body,td,th {
	color: #33FF66;
}
body {
	background-color: #993300;
}
.style3 {font-size: 24px}
.style4 {
	color: #FFCC00
}
.style8 {
	font-size: large;
	color: #FFCC99;
	font-weight: bold;
}
.style9 {
	font-size: small
}
.style10 {color: #CCCC99}
.style11 {
	color: #FFCC00;
	font-size: small;
}
.style13 {font-size: 18px}
.style14 {font-size: 16px}
.style15 {color: #FFCC00; font-size: 18px; }
-->
</style>
<%
String acount = ClientAction.formatResult(action.reportAccount().get_amount());
String deposit = ClientAction.formatResult(action.reportDepositsBalance());
String total = ClientAction.formatResult(action.reportAccount().get_amount()+action.reportDepositsBalance());
%>
</head>
<body>
<Center>
<H1 class="style4">
<span class="style14">Welcome</span> <span class="style3"><%=client.get_clientName()%> <span class="style14">!</span></span><br/>
</H1>
<H1 class="style4"><span class="style13">How are you today? </span></H1>
<table width="267" border="1" cellspacing="3" cellpadding="2">
  <tr>
    <td width="253" height="60" class="style15">
      <p>Your current <strong>account</strong> balance is: <%=acount%></p>
      <p>Your current <strong>deposits</strong> balance is: <%=deposit%></p>
      <p>Your total balance is: <%=total%> </p>
      </td>
  </tr>
</table>
<p>&nbsp;</p>
<p><span class="style8">Reports</span></p>
<p class="style9">
<span class="style4"><a href="/MBank/jsp/reportsJSP/accountDetails.jsp"> Your Account Details</a>
<span class="style10">||</span> <a href="/MBank/jsp/reportsJSP/depositsDetails.jsp?depositId=0">Watch Your Deposits</a></span> 
<span class="style10">||</span><a href="/MBank/jsp/reportsJSP/accountAndDepositLog.jsp?par=currentAccount">Watch Your Account's Log of Actions</a></p>
<p align="center"><span class="style11"><a href="/MBank/jsp/reportsJSP/accountAndDepositLog.jsp?par=deposit">Watch Your Deposits Log of Actions</a></span></p>
</Center>
</body>
</html>