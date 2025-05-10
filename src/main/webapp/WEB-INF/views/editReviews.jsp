<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 4:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Review</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h1 class="mb-4">Edit Review</h1>

    <form action="reviews" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="reviewId" value="${review.reviewID}">

        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Member ID:</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext"
                       value="${review.memberID}">
            </div>
        </div>

        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Movie ID:</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext"
                       value="${review.movieID}">
            </div>
        </div>

        <div class="mb-3">
            <label for="rating" class="form-label">Rating (0-5)</label>
            <input type="number" step="0.1" min="0" max="5" class="form-control" id="rating"
                   name="rating" value="${review.rating}" required>
        </div>

        <div class="mb-3">
            <label for="reviewText" class="form-label">Review</label>
            <textarea class="form-control" id="reviewText" name="reviewText"
                      rows="3" required>${review.reviewText}</textarea>
        </div>

        <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Date:</label>
            <div class="col-sm-10">
                <fmt:formatDate value="${review.reviewDate}" pattern="yyyy-MM-dd" var="formattedDate"/>
                <input type="date" class="form-control" name="reviewDate"
                       value="${formattedDate}" required>
            </div>
        </div>

        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <a href="reviews" class="btn btn-secondary me-md-2">Cancel</a>
            <button type="submit" class="btn btn-primary">Update Review</button>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>