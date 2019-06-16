<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255" session="false" import="valueObject.*"
	import="actions.ClientAction" import="java.text.SimpleDateFormat"
	import="java.util.Calendar"%>

<%@page errorPage="/errorPages/errorGeneral.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>Create a new Deposit</title>
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

<script language="JavaScript" type="text/javascript">

<!--form validation from here (JS):

function validateForm()
{
	var validDay = validateDay();
	var validMonth = validateMonth();
	var validYear = validateYear();
	
	totalValid = validDay && validMonth && validYear;
	
	if (!totalValid)
		alert ("The date must be after the current date!");
		
	return (totalValid);
	
}

function validateDay()
{
	var day = document.depositForm.day.value;
	var date = new Date();
	
	var year = document.depositForm.year.value;
	var month = document.depositForm.month.value;
	
	if (year == date.getFullYear() && month == (date.getMonth()+1)) {
	if (day < date.getDate())
	{
		return false;
	}
	}
	return true; 
}
function validateMonth()
{
	var month = document.depositForm.month.value;
	var date = new Date();
	var year = document.depositForm.year.value;
	
	if (year == date.getFullYear()) {
	
	if (month < (date.getMonth()+1))
	{
		return false;
	}
	}
	
	return true; 
}
function validateYear()
{
	var year = document.depositForm.year.value;
	var date = new Date();
	var error = "";
	
	var illegalChars = /\W/; // allow only letters, numbers, and underscores
	
	if (year == "")
	{
		alert("You must provide a year!");
		return false;
	}
	else 
		{
	
			if (year.length != 4) 
			{
    			error += "A year is only 4 characters.\n";
			}
			if (year.search(/[A-Z]+/) > -1 || year.search(/[a-z]+/) > -1)
			{
				error += "The year must contain numbers only.\n";
  			}
  			if (illegalChars.test(year))
  			 {
       			error += "The year contains illegal characters. ";
       		}
       		
       		if (error!="")
       		{
       			alert (error);
       			return false;
       		}
	 	}
	if (year < date.getFullYear())
	{
		return false;
	}
	return true; 
}

</script>

</head>

<body>
<form name="depositForm" 
	action="http://localhost:8080/MBank/CreateDepositController?createDepositPhase=b"
	method="post" onsubmit="return validateForm()">
<p align="center" class="style3">Deposit-Creation Wizard</p>
<p align="center">1. Choose the amount of money to deposit</p>
<p align="center">Amount: <select name="amount">
	<option value="50" selected="selected">50</option>
	<option value="100">100</option>
	<option value="200">200</option>
	<option value="500">500</option>
	<option value="1000">1000</option>
</select></p>
<p align="center">2. Choose the date to end the deposit</p>
<p align="center">Ending Date (day, month, year) : <label> <select
	name="day" id="select">
	<option value="1" selected="selected">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
	<option value="6">6</option>
	<option value="7">7</option>
	<option value="8">8</option>
	<option value="9">9</option>
	<option value="10">10</option>
	<option value="11">11</option>
	<option value="12">12</option>
	<option value="13">13</option>
	<option value="14">14</option>
	<option value="15">15</option>
	<option value="16">16</option>
	<option value="17">17</option>
	<option value="18">18</option>
	<option value="19">19</option>
	<option value="20">20</option>
	<option value="21">21</option>
	<option value="22">22</option>
	<option value="23">23</option>
	<option value="24">24</option>
	<option value="25">25</option>
	<option value="26">26</option>
	<option value="27">27</option>
	<option value="28">28</option>
	<option value="29">29</option>
	<option value="30">30</option>
	<option value="31">31</option>
</select> </label> // 
<select name="month" id="select2">
	<option value="01" selected="selected">1</option>
	<option value="02">2</option>
	<option value="03">3</option>
	<option value="04">4</option>
	<option value="05">5</option>
	<option value="06">6</option>
	<option value="07">7</option>
	<option value="08">8</option>
	<option value="09">9</option>
	<option value="10">10</option>
	<option value="11">11</option>
	<option value="12">12</option>
</select> // 
<input name="year" type="text" id="year" value="2008" size="7"
	maxlength="4" /></p>
<p align="center">3. Choose the type of the deposit</p>
<p align="center">Deposit Type : <select name="type" id="type">
	<option value="0" selected="selected">Constant interest</option>
	<option value="1">Index-linked interest</option>
	<option value="2">Foreign-linked interest</option>
</select></p>
<p align="center">&nbsp;</p>
<p align="center"><label> <input type="submit" name="button"
	id="button" value="Submit" /> </label></p>
</form>
</body>
</html>