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
<c:forEach  items="${requestScope.polls}" var = "poll" >
	<h1>${poll.getTitle()}</h1>
	<p><a href="${pageContext.request.contextPath}/servleti/glasanje?pollID=${poll.getPollID()}">${poll.getMessage()}</a>
</c:forEach>
</body>
</html>