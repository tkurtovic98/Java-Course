<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<style>
body {
   background-color: <%=session.getAttribute("pickedBgCol")%>;
}
</style>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
<h1>Glasanje za omiljeni bend</h1>
<p>Koji je Vaš omiljeni bend? Kliknite na link kako biste glasali!</p>
<ol>
<c:forEach items ="${requestScope.bandMap}" var = "attrs">
  <li><a href ="glasanje-glasaj?id=${attrs.key}">${attrs.value}</a>
</c:forEach>
</ol>
</body>
</html>