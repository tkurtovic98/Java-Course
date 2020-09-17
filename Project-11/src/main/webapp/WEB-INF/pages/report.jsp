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
<title>Report</title>
</head>
<body>

	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>

	<img alt="PieChart"
		src="${pageContext.request.contextPath}/reportImage">
</body>
</html>