<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    <%@page import="mBankProperties.MBankProperties"%>
   	<%@page import="valueObject.Deposit"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Confirm Deposit</title>
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

<%@page session="false"%>
</head>
<body>
<%
HttpSession session = request.getSession(false);
Deposit deposit = (Deposit)session.getAttribute("deposit");
%>
 <table width="543" border="1" align="center" cellpadding="2" cellspacing="3">  
  		 <tr>  
    		 <td width="513"><p align="center">Are you sure that you want to break this deposit? (ID:<%=deposit.get_depositId()%><br/>
    		 Your estimated amount is: <%=deposit.get_estimatedAmount()%>, and the penalty is: <%=MBankProperties.getDeposit_penalty()%></p>  
      		 <p>&nbsp;</p>      
      		 </td>  
  		 </tr>  
  		 <tr>  
    		 <td><div align="center">  
    		 <a href="http://localhost:8080/MBank/BreakDepositController?breakDepositPhase=c">continue</a>  
    		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
    		 <a href="http://localhost:8080/MBank/BreakDepositController?breakDepositPhase=a">cancel</a>  
    		 </div></td>  
  		 </tr>  
	 </table> 
</body>
</html>