<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 3:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Movies</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1>Movie List</h1>

    <!-- Add Movie Form -->
    <form action="movies" method="post" class="mb-4">
        <input type="hidden" name="action" value="add">
        <div class="row g-3">
            <div class="col-md-3">
                <input type="text" name="title" class="form-control" placeholder="Title" required>
            </div>
            <div class="col-md-2">
                <input type="text" name="genre" class="form-control" placeholder="Genre" required>
            </div>
            <div class="col-md-2">
                <input type="date" name="releaseDate" class="form-control" required>
            </div>
            <div class="col-md-2">
                <input type="number" step="0.1" name="rating" class="form-control" placeholder="Rating" min="0" max="10">
            </div>
            <div class="col-md-2">
                <div class="form-check">
                    <input type="checkbox" name="availability" class="form-check-input">
                    <label class="form-check-label">Available</label>
                </div>
            </div>
            <div class="col-md-1">
                <button type="submit" class="btn btn-primary">Add</button>
            </div>
        </div>
    </form>

    <!-- Movie Table -->
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Genre</th>
            <th>Release Date</th>
            <th>Rating</th>
            <th>Available</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${movies}" var="movie">
            <tr>
                <td>${movie.movieID}</td>
                <td>${movie.title}</td>
                <td>${movie.genre}</td>
                <td>${movie.releaseDate}</td>
                <td>${movie.rating}</td>
                <td>${movie.availabilityStatus ? 'Yes' : 'No'}</td>
                <td>
                    <a href="movies?action=edit&id=${movie.movieID}" class="btn btn-sm btn-warning">Edit</a>
                    <form action="movies" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="movieId" value="${movie.movieID}">
                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
