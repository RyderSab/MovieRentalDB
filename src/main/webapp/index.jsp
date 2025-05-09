<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 4:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Movie Rental System</title>
    <!-- Optional: Add Bootstrap for styling -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1>Welcome to the Movie Rental System!</h1>

    <!-- Link to your MovieServlet -->
    <a href="movies" class="btn btn-primary">View All Movies</a>

    <!-- Optional: Add more links -->
    <div class="mt-3">
        <a href="WEB-INF/views/login.jsp" class="btn btn-secondary">Staff Login</a>
    </div>
</div>
</body>
</html>