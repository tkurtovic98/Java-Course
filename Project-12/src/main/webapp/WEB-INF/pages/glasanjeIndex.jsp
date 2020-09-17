<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
<h1>${requestScope.title}</h1>
<p>${requestScope.message}</p>
<ol>
<c:forEach items ="${requestScope.pollMap}" var = "attrs">
  <li><a href ="${pageContext.request.contextPath}/servleti/glasanje-glasaj?id=${attrs.key}">${attrs.value}</a>
</c:forEach>
</ol>
</body>
</html>