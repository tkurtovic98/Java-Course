<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
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
<h1>Register by entering your information below!</h1>
<form action="${pageContext.request.contextPath}/servleti/register" method="post">
<div>First Name<input type ="text" name = "firstName" size = "30"><br>
 <c:if test="${form.containsError('firstName')}">
	<c:out value="${form.getErrorMsg('firstName')}"/>
 </c:if>
 </div>
<div>Last Name<input type ="text" name = "lastName" size = "30"><br>
 <c:if test="${form.containsError('lastName')}">
	<c:out value="${form.getErrorMsg('lastName')}"/>
 </c:if>
 </div>
<div>E-mail<input type ="text" name = "eMail" size = "30"><br>
 <c:if test="${form.containsError('eMail')}">
	<c:out value="${form.getErrorMsg('eMail')}"/>
 </c:if>
 </div>
<div>Nick<input type ="text" name = "nick" size = "30"><br>
 <c:if test="${form.containsError('nick')}">
	<c:out value="${form.getErrorMsg('nick')}"/>
 </c:if>
 </div>
<div>Password<input type ="password" name = "password" size = "30"><br>
 <c:if test="${form.containsError('password')}">
	<c:out value="${form.getErrorMsg('password')}"/>
 </c:if>
 </div>
<div>
<input type = "submit" name ="registration" value = "Register">
<br>
<p> <a href ="${pageContext.request.contextPath}">Return to main page</a><p>
</div>
</form>
</body>
</html>