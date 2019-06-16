<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Insert title here</title>
<style type="text/css">
<!--
body,td,th {
	color: #CCCC99;
}
body {
	background-color: #993300;
}
.style3 {font-size: 24px}
.style4 {
	color: #FFCC00
}
-->
</style>
<%
Double commission = (Double)session.getAttribute("commission");
String commissionText = 
	
	"<table width='543' border='1' align='center' cellpadding='2' cellspacing='3'>"+
  		"<tr>"+
    		"<td width='513'><p>Deposit amount: "+request.getParameter("amount")+"<br/> For this action you will be charged with a commission of "+ commission.doubleValue() +"%</p>"+
      		"<p>&nbsp;</p>"+    
      		"</td>"+
  		"</tr>"+
  		"<tr>"+
    		"<td><div align='center'>"+
    		"<a href='http://localhost:8080/MBank/DepositController?depositPhase=c'>continue</a>"+
    		"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
    		"<a href='http://localhost:8080/MBank/DepositController?depositPhase=a'>cancel</a>"+
    		"</div></td>"+
  		"</tr>"+
	"</table>";
%>

</head>
<body>
<%=commissionText%>

</body>
</html>