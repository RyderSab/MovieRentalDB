<%--
  Created by IntelliJ IDEA.
  User: ryder
  Date: 5/9/2025
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<table class="table">
  <thead>
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
        <a href="members?action=edit&id=${member.memberID}" class="btn btn-warning">Edit</a>
        <!-- Delete button would go here -->
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>