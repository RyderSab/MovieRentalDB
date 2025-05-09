<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 11:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Edit Movie</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h1>Edit Movie</h1>

  <form action="movies" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="movieId" value="${movie.movieID}">

    <div class="mb-3">
      <label class="form-label">Title</label>
      <input type="text" name="title" class="form-control"
             value="${movie.title}" required>
    </div>

    <div class="mb-3">
      <label class="form-label">Genre</label>
      <input type="text" name="genre" class="form-control"
             value="${movie.genre}" required>
    </div>

    <div class="mb-3">
      <label class="form-label">Release Date</label>
      <input type="date" name="releaseDate" class="form-control"
             value='<fmt:formatDate value="${movie.releaseDate}" pattern="yyyy-MM-dd"/>' required>
    </div>

    <div class="mb-3">
      <label class="form-label">Rating</label>
      <input type="number" step="0.1" name="rating" class="form-control" min="0" max="10"
             value="${movie.rating}" required>
    </div>

    <button type="submit" class="btn btn-primary">Update</button>
    <a href="movies" class="btn btn-secondary">Cancel</a>
  </form>
</div>
</body>
</html>
