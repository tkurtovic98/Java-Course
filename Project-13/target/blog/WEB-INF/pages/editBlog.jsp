<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Blog</title>
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
<div >
<h1><c:out value = "${formName}"/></h1>
<form action="${pageContext.request.contextPath}/${action}" method="post">
<c:choose>
<c:when test="${empty requestScope.entry}">
Title<br><input type ="text" name ="title" size ="50"><br>
<textarea name = "entry" > </textarea>
<br>
<input type="submit" name ="submit" value="Submit new entry">
</c:when>
<c:otherwise>
<textarea name = "entry" rows= 10 cols=30>${entry.getText()}</textarea>
<br>
<input type ="submit" name ="edit" value = "Edit entry">
</c:otherwise>
</c:choose>
</form>
<h2>
<c:if test="${not empty insert}">
	<c:out value="${insert}"></c:out>
</c:if>
</h2>
<p> <a href ="${pageContext.request.contextPath}">Return to main page</a><p>
</div>
</body>
</html>