<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 4:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reviews</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Review List</h1>
        <div>
            <a href="movies" class="btn btn-primary me-2">View Movies</a>
        </div>
    </div>

    <table class="table table-striped">
        <thead class="table-dark">
        <tr>
            <th>ReviewID</th>
            <th>MemberID</th>
            <th>MovieID</th>
            <th>ReviewDate</th>
            <th>Rating</th>
            <th>ReviewText</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reviews}" var="review">
            <tr>
                <td>${review.reviewID}</td>
                <td>${review.memberID}</td>
                <td>${review.movieID}</td>
                <td>${review.rating}</td>
                <td>${review.reviewText}</td>
                <td><fmt:formatDate value="${review.reviewDate}" pattern="yyyy-MM-dd"/></td>
                <td>
                    <div class="btn-group">
                        <a href="reviews?action=edit&id=${review.reviewID}"
                           class="btn btn-sm btn-warning">Edit</a>
                        <form action="members" method="post" onsubmit="return confirm('Delete this member?');">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="memberId" value="${review.reviewID}">
                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
