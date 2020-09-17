<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
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
<c:choose>
<c:when test="${not empty log}">
<h2>Already logged in!</h2>
</c:when>
<c:otherwise>
<div>
<h1>Already registered? Log in!</h1>
<form action="main" method="post">
<label>Nick</label>
<input type ="text" name = "nick" value ='<c:out value = "${form.getNick()}"/>' >
<label>Password</label>
<input type ="password" name = "password" >
<input type = "submit" name ="method" value = "Login">
</form>
<c:if test="${form.containsError('userPass')}">
	<c:out value = "${form.getErrorMsg('userPass')}"/>
</c:if>
<br><br>
<h2>
If you are new register <a href="${pageContext.request.contextPath}/servleti/register">here</a>
</h2>
</div>
</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${registeredAuthors.size()>0}">
<p>Registered authors:</p>
<c:forEach items = "${registeredAuthors}" var= "author" varStatus="status">
<h2><a href = "author/${author.getNick()}">${author.getNick()}</a><br></h2>
</c:forEach>
</c:when>
</c:choose>
</div>

</body>
</html>