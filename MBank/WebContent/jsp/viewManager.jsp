<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">

<title>MBank</title>

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
<% 
// if the user is logged it should show the "logoff" command, else it should show the login screen
HttpSession session = request.getSession(false);

String logLine = "";
String logOut = "<font color='#FFCC00'><a href ='/MBank/MainController?command=logOut'>log out from the system</a></font>";

if (session != null && request.getAttribute("page")!= "loginJSP/login.jsp")
	logLine = logOut;
else
	request.setAttribute("page", "loginJSP/login.jsp");


	

%>
</head>

<body>

<p><div align="center" class="style1">
  <p>~&nbsp; M - B A N K  &nbsp; <em>online</em> &nbsp;~</p>
  </div>

<table width="973" height="508" border="1" cellpadding="2" cellspacing="3">
  <tr>
    <td width="152" height="34"><%@include file="/menu.html"%></td>
    <td width="798" rowspan="2"><jsp:include page="<%=request.getAttribute("page").toString()%>" /></td>
  </tr>
  <tr>
    <td height="110"><%=logLine%></td>
  </tr>
</table>

<%
if (request.getParameter("sessionError") != null)	
	out.print("<script type='text/javascript'>alert ('You must login before you enter the MBank system');</script>");
%>

</body>
</html>