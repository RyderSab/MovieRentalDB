<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 1:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Rentals</title>
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <a href="movies" class="btn btn-primary me-2">View Movies</a>
                <a href="staff" class="btn btn-primary me-2">View Staff</a>
                <a href="members" class="btn btn-success">View Members</a>
            </div>
        </div>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container mt-4">
    <h1 class="mb-4">Movie Rentals</h1>

    <!-- Add New Rental Form -->
    <form action="rentals" method="post" class="mb-4">
        <input type="hidden" name="action" value="add">
        <div class="row g-3">
            <div class="col-md-4">
                <select name="movieId" class="form-select" required>
                    <option value="" selected disabled>Select Movie</option>
                    <c:forEach items="${movies}" var="movie">
                        <option value="${movie.movieID}">${movie.title} (${movie.genre})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4">
                <select name="memberId" class="form-select" required>
                    <option value="" selected disabled>Select Member</option>
                    <c:forEach items="${members}" var="member">
                        <option value="${member.memberID}">
                                ${member.firstName} ${member.lastName} (${member.email})
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <input type="date" name="rentalDate" class="form-control" value="${currentDate}" required>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary w-100">Rent Movie</button>
            </div>
        </div>
    </form>

    <!-- Rentals Table -->
    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>Rental ID</th>
            <th>MemberID</th>
            <th>MovieID</th>
            <th>Rental Date</th>
            <th>Return Date</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${rentals}" var="rental">
            <tr>
                <td>${rental.rentalID}</td>
                <td>${rental.memberID}</td>
                <td>${rental.movieID}</td>
                <td>${rental.rentalDate}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty rental.returnDate}">
                            ${rental.returnDate}
                        </c:when>
                        <c:otherwise>
                            <span class="text-danger">Not Returned</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${rental.status}</td>
                <td>
                    <div class="btn-group">
                        <c:if test="${rental.status == 'Rented'}">
                            <form action="rentals" method="post" class="d-inline">
                                <input type="hidden" name="action" value="return">
                                <input type="hidden" name="rentalId" value="${rental.rentalID}">
                                <button type="submit" class="btn btn-sm btn-success">Return</button>
                            </form>
                        </c:if>
                        <form action="rentals" method="post" onsubmit="return confirm('Delete this rental record?');">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="rentalId" value="${rental.rentalID}">
                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>