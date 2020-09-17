<%@page import="java.io.IOException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%!private void writeTime(JspWriter out, long time) {
		long currTime = System.currentTimeMillis();
		long diff = currTime - time;

		long seconds = 1000;
		long minutes = seconds * 60;
		long hours = minutes * 60;
		long days = hours * 24;

		long daysPassed = diff / days;
		diff = diff % days;

		long hoursPassed = diff / hours;
		diff = diff % hours;

		long minutesPassed = diff / minutes;
		diff = diff % minutes;

		long secondsPassed = diff / seconds;

		try {
			out.print(daysPassed + " days " + hoursPassed + " hours " + minutesPassed + " minutes " + secondsPassed
					+ " seconds ");
		} catch (IOException e) {

		}
	}%>

<!DOCTYPE html>
<html>
<head>
<style>
body {
	background-color: <%=session.getAttribute("pickedBgCol")%>;
}
</style>
<meta charset="UTF-8">
<title>AppInfo</title>
</head>
<body>
	<h1>App has been active for:</h1>
	<p>
		<%
			long time = (Long) application.getAttribute("startTime");
			writeTime(out, time);
		%>
	</p>
</body>
</html>