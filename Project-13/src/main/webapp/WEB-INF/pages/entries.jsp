<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User entries</title>
<style><%@include file="/WEB-INF/css/style.css"%></style>
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
<div>
<h1>Entries of author: <c:out value="${nick}"/></h1>
<h2>
<c:if test="${availableEntries.size()>0}">
<c:forEach items ="${availableEntries}" var = "entry">
<a href ="${nick}/${entry.getId()}">${entry.getTitle()}</a><br>
</c:forEach>
</c:if>
</h2>
</div>

<div>
<c:if test="${not empty id}">
<p><a href="${nick}/new">Create new blog post</a><p>
</c:if>

<p> <a href ="${pageContext.request.contextPath}">Return to main page</a><p>
</div>

</body>
</html>