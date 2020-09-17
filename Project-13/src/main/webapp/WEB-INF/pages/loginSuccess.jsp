<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Success!</title>
</head>
<body>
<h2>
<%
        if (session.getAttribute("current.user.fn") != null) {
    %>
       <c:out value='<%=session.getAttribute("current.user.fn")%>'></c:out>
       <c:out value='<%=session.getAttribute("current.user.ln")%>'></c:out>
       <a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>
    <%
        } 
        else {
    %>
        not logged in
    <%
        }
    %>
</h2>
<br>
<h1>Welcome <c:out value= "${nick}"/></h1>

<div><c:if test="${availableEntries.size()>0}">
<c:forEach items ="${availableEntries}" var = "entry">
<a href ="author/${nick}/${entry.getId()}">${entry.getTitle()}</a><br>
</c:forEach>
</c:if>
</div>

<a href = 'author/${nick}/new'>Create new blog post!</a>
<p> <a href ="${pageContext.request.contextPath}">Return to main page</a><p>
</body>
</html>