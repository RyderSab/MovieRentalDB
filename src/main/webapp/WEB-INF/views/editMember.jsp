<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 1:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Member</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>${empty member.memberID ? 'Add New' : 'Edit'} Member</h1>
        <a href="members" class="btn btn-secondary">Back to List</a>
    </div>

    <form action="members" method="post">
        <input type="hidden" name="action" value="${empty member.memberID ? 'add' : 'update'}">
        <c:if test="${not empty member.memberID}">
            <input type="hidden" name="memberId" value="${member.memberID}">
        </c:if>

        <div class="mb-3">
            <label class="form-label">First Name</label>
            <input type="text" name="firstName" class="form-control"
                   value="${member.firstName}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Last Name</label>
            <input type="text" name="lastName" class="form-control"
                   value="${member.lastName}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control"
                   value="${member.email}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Phone</label>
            <input type="tel" name="phone" class="form-control"
                   value="${member.phone}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Membership Date</label>
            <input type="date" name="membershipDate" class="form-control"
                   value="<fmt:formatDate value="${member.membershipDate}" pattern="yyyy-MM-dd"/>" required>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
        <a href="members" class="btn btn-outline-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
