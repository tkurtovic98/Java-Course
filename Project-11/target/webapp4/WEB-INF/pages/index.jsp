<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
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
<title>Index</title>
</head>
<body>
<a href = "${pageContext.request.contextPath}/index.jsp?name=colors.jsp">Colors.jsp</a><br>
<a href = "${pageContext.request.contextPath}/trigonometric?a=0&b=90">Trigonometric</a><br>
<a href = "${pageContext.request.contextPath}/funny.jsp">Funny Story</a><br>
<a href = "${pageContext.request.contextPath}/index.jsp?name=report.jsp">PieChart</a><br>
<a href = "${pageContext.request.contextPath}/powers?a=1&b=100&n=3">Excel file</a><br>
<a href = "${pageContext.request.contextPath}/index.jsp?name=appinfo.jsp">Time running</a><br>
<br>
<form action="trigonometric" method = "GET">
Početni kut:<br><input type="number" name="a" min ="0" max="360" step="1" value="0"><br>
Završni kut:<br><input type="number" name="b" min ="0" max="360" step="1" value="360"><br>
<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
</form>
</body>
</html>