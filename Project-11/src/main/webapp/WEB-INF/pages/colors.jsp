<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session = "true" %>
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
<title>Colors</title>
</head>
<body>
<a href = "${pageContext.request.contextPath}/setcolor?pickedBgCol=white">WHITE</a><br>
<a href = "${pageContext.request.contextPath}/setcolor?pickedBgCol=red">RED</a><br>
<a href = "${pageContext.request.contextPath}/setcolor?pickedBgCol=green">GREEN</a><br>
<a href = "${pageContext.request.contextPath}/setcolor?pickedBgCol=cyan">CYAN</a><br>
</body>
</html>