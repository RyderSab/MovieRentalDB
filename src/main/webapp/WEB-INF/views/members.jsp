<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Member Management</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h1>Member List</h1>
    <div>
      <a href="movies" class="btn btn-primary me-2">View Movies</a>
      <a href="members?action=new" class="btn btn-success">Add New Member</a>
    </div>
  </div>

  <table class="table table-striped">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Email</th>
      <th>Phone</th>
      <th>Member Since</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${members}" var="member">
      <tr>
        <td>${member.memberID}</td>
        <td>${member.firstName} ${member.lastName}</td>
        <td>${member.email}</td>
        <td>${member.phone}</td>
        <td><fmt:formatDate value="${member.membershipDate}" pattern="yyyy-MM-dd"/></td>
        <td>
          <div class="btn-group">
            <a href="members?action=edit&id=${member.memberID}"
               class="btn btn-sm btn-warning">Edit</a>
            <form action="members" method="post" onsubmit="return confirm('Delete this member?');">
              <input type="hidden" name="action" value="delete">
              <input type="hidden" name="memberId" value="${member.memberID}">
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