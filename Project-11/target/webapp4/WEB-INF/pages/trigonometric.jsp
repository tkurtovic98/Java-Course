<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  session = "true" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<style>
body {
   background-color: <%=session.getAttribute("pickedBgCol")%>;
}
</style>
<meta charset="UTF-8">
<title>Trigonometric</title>
</head>
<body>
    <table cols="2" border ="1">
    <th>Sin(x)</th><th>Cos(x)</th>    
	<c:forEach items="${sin}" var="sinx" varStatus = "status">
       <tr><td><c:out value= "${sinx}"></c:out></td><td><c:out value = "${cos[status.index]}"></c:out></td></tr>
      </c:forEach>
    </table>
</body>
</html>