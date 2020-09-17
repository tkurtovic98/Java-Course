<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Blog post</title>
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
<h1>
Title: <c:out value="${entry.getTitle()}"/></h1><br>
<p>Text<br><c:out value="${entry.getText()}"/></p>

<c:forEach items ="${comments}" var = "comment" varStatus ="index">
<c:out value="${index.index}"></c:out>
	 <c:out value="${comment.getPostedOn()}"/> <br> 
	 <c:out value="${comment.getMessage()}"/><br>
</c:forEach>

<form action="${pageContext.request.contextPath}/servleti/addComment" method="post">
<textarea  name = "commentToSubmit" placeholder="Add comment here..."></textarea>
<br>
<input type="submit" name ="submitComment" value ="comment">
</form>
</div>

<p>
<c:if test="${not empty loggedIn}">
<c:out value="${loggedIn}"/>, you can edit your blog entry <a href="${pageContext.request.contextPath}/servleti/author/${loggedIn}/edit">here</a>
</c:if>
</p>
<br>
<c:out value="${commentAdded}"></c:out>
<p> <a href ="${pageContext.request.contextPath}">Return to main page</a><p>
</body>
</html>