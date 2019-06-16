<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" import="valueObject.Account" import="valueObject.Client" import="actions.ClientAction"%>
    
    <%@page errorPage="/errorPages/errorGeneral.jsp"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Account Details</title>
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

<%

ClientAction action = (ClientAction)session.getAttribute("action");
Account account = action.reportAccount();

String comments = account.get_accountComments();

comments = (comments == null)? "none":comments; //fix the comments entry it was wrongly written

%>
</head>

<body>
<div align="center">
  <p class="head">Account Details</p>
  <p>&nbsp;</p>
  <table width="456" border="3" cellspacing="3" cellpadding="2">
    <tr>
      <th width="56" scope="col">Client ID</th>
      <th width="74" scope="col">Account ID</th>
      <th width="86" scope="col">Amount</th>
      <th width="97" scope="col">Credit Restriction</th>
      <th width="89" scope="col">Comments</th>
    </tr>
    <tr>
      <td><%=account.get_ClientId()%></td>
      <td><%=account.get_AccountId()%></td>
      <td><%=ClientAction.formatResult(account.get_amount())%></td>
      <td><%=ClientAction.formatResult(account.get_creditRestriction())%></td>
      <td><%=comments%></td>
    </tr>
  </table>
  <p class="linkage"><a href = "/MBank/MainController?command=details">~Go Back~</a></p>
  <p>&nbsp;</p>
  </div>
</body>
</html>