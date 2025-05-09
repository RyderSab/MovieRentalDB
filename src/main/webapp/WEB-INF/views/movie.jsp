<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 3:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Movies</title>
</head>
<body>
<h1>Movie List</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Title</th>
    </tr>
    <c:forEach items="${movies}" var="movie">
        <tr>
            <td>${movie.movieID}</td>
            <td>${movie.title}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
