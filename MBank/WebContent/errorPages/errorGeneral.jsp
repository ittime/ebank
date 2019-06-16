<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"
    isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>ERROR!</title>
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
	
.style1 {
	font-size: x-large;
	font-weight: bold;
}
.style2 {font-size: medium; fo nt-weight: bold; }
.style3 {font-size: small}
-->
</style>
</head>
<body>
<p align="center" class="style1">ERROR</p>
<p align="center" class="style2">Some error occurred.</p><br/><p align="center" class="style2">&nbsp;</p><p align="center" class="style2"><br/>
<span class="style3">If the error constantly re-happen, please contact our support.</span></p>
<%session.invalidate();%>
</body>
</html>