<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 4:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Staff Management</title>
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
  <h1 class="mb-4">Staff Members</h1>

  <!-- Add New Staff Form -->
  <form action="staff" method="post" class="mb-4">
    <input type="hidden" name="action" value="add">
    <div class="row g-3">
      <div class="col-md-3">
        <input type="text" name="firstName" class="form-control" placeholder="First Name" required>
      </div>
      <div class="col-md-3">
        <input type="text" name="lastName" class="form-control" placeholder="Last Name" required>
      </div>
      <div class="col-md-3">
        <input type="email" name="email" class="form-control" placeholder="Email" required>
      </div>
      <div class="col-md-2">
        <select name="role" class="form-select" required>
          <option value="" selected disabled>Select Role</option>
          <option value="Manager">Manager</option>
          <option value="Clerk">Clerk</option>
        </select>
      </div>
      <div class="col-md-1">
        <button type="submit" class="btn btn-primary w-100">Add</button>
      </div>
    </div>
  </form>

  <!-- Staff Table -->
  <table class="table table-striped table-hover">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>Role</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${staffList}" var="staff">
      <tr>
        <td>${staff.staffID}</td>
        <td>${staff.firstName}</td>
        <td>${staff.lastName}</td>
        <td>${staff.email}</td>
        <td>${staff.role}</td>
        <td>
          <div class="btn-group">
            <a href="staff?action=edit&id=${staff.staffID}" class="btn btn-sm btn-warning">Edit</a>
            <form action="staff" method="post" onsubmit="return confirm('Delete this staff member?');">
              <input type="hidden" name="action" value="delete">
              <input type="hidden" name="staffId" value="${staff.staffID}">
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