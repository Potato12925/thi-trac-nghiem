<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Lecturer Management");
request.setAttribute("customCss", "management.css");
request.setAttribute("customJs", "lecturer-management.js");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid page-wrapper">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Lecturer Management</h1>
	</div>

	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger">${errorMessage}</div>
	</c:if>

	<div class="border rounded-3 bg-white p-4 mb-4 form-section">
		<form method="get" action="${pageContext.request.contextPath}/lecturers" class="row g-3 mb-4">
			<div class="col-md-8">
				<label class="form-label small text-secondary"> Search keyword </label>
				<input type="text" name="keyword" class="form-control" value="${keyword}" placeholder="ID, last name, first name" />
			</div>
			<div class="col-md-4 d-flex align-items-end">
				<button type="submit" class="btn btn-outline-secondary w-100">Search</button>
			</div>
		</form>

		<form:form id="lecturerForm" method="post"
			action="${pageContext.request.contextPath}/lecturers/add"
			modelAttribute="lecturerDTO">
			<input type="hidden" name="page" value="${currentPage}" />
			<input type="hidden" name="keyword" value="${keyword}" />

			<div class="row g-3">
				<div class="col-md-2">
					<label class="form-label small text-secondary"> Lecturer ID
					</label>

					<form:input path="lecturerId" id="lecturerId"
						cssClass="form-control" />

					<form:errors path="lecturerId"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-3">
					<label class="form-label small text-secondary"> Last Name </label>

					<form:input path="lastName" id="lastName" cssClass="form-control" />

					<form:errors path="lastName"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-2">
					<label class="form-label small text-secondary"> First Name
					</label>

					<form:input path="firstName" id="firstName" cssClass="form-control" />

					<form:errors path="firstName"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-2">
					<label class="form-label small text-secondary"> Phone
						Number </label>

					<form:input path="phoneNumber" id="phoneNumber"
						cssClass="form-control" />

					<form:errors path="phoneNumber"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-3">
					<label class="form-label small text-secondary"> Address </label>

					<form:input path="address" id="address" cssClass="form-control" />

					<form:errors path="address"
						cssClass="text-danger small mt-1 d-block" />
				</div>
			</div>

			<div class="d-flex gap-2 mt-4">
				<button type="submit"
					formaction="${pageContext.request.contextPath}/lecturers/add"
					class="btn btn-dark px-4">Add</button>

				<button type="submit" disabled id="btnUpdate"
					formaction="${pageContext.request.contextPath}/lecturers/update"
					class="btn btn-outline-secondary px-4">Update</button>

				<button type="submit"
					formaction="${pageContext.request.contextPath}/lecturers/delete"
					disabled id="btnDelete" class="btn btn-outline-danger px-4"
					onclick="return confirm('Delete this lecturer?')">Delete</button>

			<button type="button" class="btn btn-outline-secondary" id="btnUndo" disabled>Undo</button>

			<button type="button" class="btn btn-outline-dark"
				onclick="resetForm()">Reset</button>
			</div>

		</form:form>

	</div>

	<div class="card border-0 shadow-sm management-card">
		<div class="table-responsive p-3 management-table-wrapper">
			<table class="table table-hover align-middle mb-0 management-table">
				<thead class="table-light">
					<tr>
						<th>Lecturer ID</th>

						<th>Last Name</th>

						<th>First Name</th>

						<th>Phone Number</th>

						<th>Address</th>

						<th class="text-end">Actions</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${lecturers}" var="lecturer">
						<tr data-id="${lecturer.lecturerId}"
							data-lastname="${lecturer.lastName}"
							data-firstname="${lecturer.firstName}"
							data-phone="${lecturer.phoneNumber}"
							data-address="${lecturer.address}">

							<td class="fw-medium text-success">${lecturer.lecturerId}</td>

							<td>${lecturer.lastName}</td>

							<td>${lecturer.firstName}</td>

							<td>${lecturer.phoneNumber}</td>

							<td>${lecturer.address}</td>

							<td class="text-end">
								<button type="button"
									class="btn btn-sm btn-outline-secondary me-2 btn-edit">

									<i class="bi bi-pencil"></i>
								</button>
							</td>
						</tr>

						<c:if test="${empty lecturers}">
							<tr>
								<td colspan="6" class="text-center text-muted py-4">No
									lecturers found</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="pagination-wrapper">
			<c:if test="${currentPage > 1}">
				<a class="pagination-item" href="lecturers?page=1&keyword=${keyword}"> First </a>
				<a class="pagination-item" href="lecturers?page=${currentPage - 1}&keyword=${keyword}">
					&laquo; </a>
			</c:if>

			<c:if test="${currentPage > 3}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:forEach begin="${currentPage - 2 < 1 ? 1 : currentPage - 2}"
				end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}"
				var="i">
				<a href="lecturers?page=${i}&keyword=${keyword}"
					class="pagination-item ${currentPage == i ? 'active' : ''}">
					${i} </a>
			</c:forEach>

			<c:if test="${currentPage < totalPages - 2}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:if test="${currentPage < totalPages}">
				<a class="pagination-item" href="lecturers?page=${currentPage + 1}&keyword=${keyword}">
					&raquo; </a>
				<a class="pagination-item" href="lecturers?page=${totalPages}&keyword=${keyword}">
					Last </a>
			</c:if>
		</div>
	</div>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp"%>