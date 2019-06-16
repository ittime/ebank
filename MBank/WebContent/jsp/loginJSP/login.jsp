<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Login</title>
<style type="text/css">
<!--
.style1 {
	font-size: large;
	font-weight: bold;
}

body {
	background-color: #993300;
}

-->
</style>
<%@page session="false"%>
<% 
//did the user accessed this page before
	String error = "";
	HttpSession session = request.getSession(false);
	if (request.getAttribute("errorID")!=null){
		error = "Invalid user name or password, please try again";
		session.invalidate();} // get rid of the old session
%>

<script language="JavaScript" type="text/javascript">
<!--form validation from here (JS):

function validateForm()
{
	var validUser = validateUser();
	var validPass = validatePass();
	return (validUser && validPass);
}

function validateUser () //validate the userName
{
	var txt = document.loginForm.userName.value;

	var insertError = document.getElementById("userError");
	var error = "";
	insertError.innerHTML = "";

	var illegalChars = /\W/; // allow only letters, numbers, and underscores
	
	if (txt == "")
	{
		error += "You must provide a user name ";
	}
	else 
		{
	
			if (txt.length != 5) 
			{
    			error += "The username must be 5 characters only ";
			}
			if (txt.search(/[A-Z]+/) > -1 || txt.search(/[0-9]+/) > -1)
			{
				error += "The user name must not contain uppercase letters and/or numbers. ";
  			}
  			if (illegalChars.test(txt))
  			 {
       			error += "The username contains illegal characters. ";
			}
	 	}
    
    insertError.innerHTML = error;	
		
	if (error != "")
	{
		return false;
	}
	return true;
}
function validatePass ()
{
	var txt = document.loginForm.password.value;

	var insertError = document.getElementById("passwordError");
	var error = "";
	insertError.innerHTML = "";

	var illegalChars = /\W/; // allow only letters, numbers, and underscores
	
	if (txt == "")
	{
		error += "You must provide a passwoed. ";
	}
	else 
	{
	
		if (txt.length != 5) 
		{
	    	error += "The password must be 5 characters only. ";
		}
		if (txt.search(/[A-Z]+/) > -1 || txt.search(/[a-z]+/) > -1)
		{
			error += "The password must not contain letters. ";
  		}
  		if (illegalChars.test(txt)) {
       		error +="The password contains illegal characters. ";
		} 
    }
    
    insertError.innerHTML = error;	
		
	if (error != "")
	{
		return false;
	}
	return true;
}
//-->
</script>

</head>

<body>

<p align="center" class="style1">Login to MBank</p>

<form action="http://localhost:8080/MBank/MainController" name ="loginForm" method="post" onsubmit="return validateForm()">

<br/><center>
<p>User Name:&nbsp;<input name="userName" type="text" id="userName" /></p>
<font color="#00FFFF"><span id="userError"></span></font>
<p>Password:&nbsp;&nbsp;&nbsp;<input name="password" type="password" id="password"/><br/>
<br/><font color="#00FFFF"><span id="passwordError"></span></font>
<font color="#FFFFCC"><%=error %></font></p>

<input name = "command" type= "hidden" value = "login" />

<p><input name="login" type="submit" value="Login" /></p>
</center>
</form>

</body>
</html>