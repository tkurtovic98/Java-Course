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
table.rez td {text-align: center;}
</style>
<meta charset="UTF-8">
<title>Rezultati</title>
</head>
<body>
<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja</p>
<table border = "1" cellspacing = "0" class = "rez">
<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
<tbody>
<c:forEach items ="${requestScope.voteMap}" var ="votes">
<tr><td>${votes.key}</td><td>${votes.value}</td></tr>
</c:forEach>
</tbody>
</table>

<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-Chart" src="${pageContext.request.contextPath}/glasanje-grafika" width = "400" height = 400 />

<h2>Rezultati u XLS formatu</h2>
<p>Rezultati dostuptni <a href = "${pageContext.request.contextPath}/glasanje-xls">ovdje</a></p>

<h2>Razno</h2>
<h3>Primjeri pobjedničkih pjesama: </h3>
<c:forEach items="${requestScope.winnersMap}" var ="link">
<p><a href = "${link.value}">${link.key}</a></p>
</c:forEach>
</body>
</html>