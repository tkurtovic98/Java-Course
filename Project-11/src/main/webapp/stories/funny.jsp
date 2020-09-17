<%@page import="java.util.Random"%>
<%@page import="java.awt.Color"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
body {
   background-color: <%=session.getAttribute("pickedBgCol")%>;
}
</style>
<meta charset="UTF-8">
<title>Funny story</title>
<%
		String[] color = { "black", "pink", "yellow", "blue" };
		Random rand = new Random();
		String random = color[rand.nextInt(4)];
	%>
</head>
<body>
	<p style="color: <%=random%>">Funny story: The story of my life.<br>
		The end.</p>
</body>
</html>