<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Lecturer Management");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Lecturer Management</h1>
	</div>

	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger">${errorMessage}</div>
	</c:if>

	<div class="border rounded-3 bg-white p-4 mb-4">
		<form:form id="lecturerForm" method="post"
			action="${pageContext.request.contextPath}/lecturer/add"
			modelAttribute="lecturerDTO">
			<input type="hidden" name="page" value="${currentPage}" />

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
					formaction="${pageContext.request.contextPath}/lecturer/add"
					class="btn btn-dark px-4">Add</button>

				<button type="submit" disabled id="btnUpdate"
					formaction="${pageContext.request.contextPath}/lecturer/update"
					class="btn btn-outline-secondary px-4">Update</button>

				<button type="submit"
					formaction="${pageContext.request.contextPath}/lecturer/delete"
					disabled id="btnDelete" class="btn btn-outline-danger px-4"
					onclick="return confirm('Delete this lecturer?')">Delete</button>

				<button type="button" class="btn btn-outline-dark"
					onclick="resetForm()">Reset</button>
			</div>

		</form:form>

	</div>

	<div class="card border-0 shadow-sm">
		<div class="table-responsive p-3">
			<table class="table table-hover align-middle mb-0">
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
				<a class="pagination-item" href="lecturer?page=1"> First </a>
				<a class="pagination-item" href="lecturer?page=${currentPage - 1}">
					&laquo; </a>
			</c:if>

			<c:if test="${currentPage > 3}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:forEach begin="${currentPage - 2 < 1 ? 1 : currentPage - 2}"
				end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}"
				var="i">
				<a href="lecturer?page=${i}"
					class="pagination-item ${currentPage == i ? 'active' : ''}">
					${i} </a>
			</c:forEach>

			<c:if test="${currentPage < totalPages - 2}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:if test="${currentPage < totalPages}">
				<a class="pagination-item" href="lecturer?page=${currentPage + 1}">
					&raquo; </a>
				<a class="pagination-item" href="lecturer?page=${totalPages}">
					Last </a>
			</c:if>
		</div>
	</div>
</div>

<script>

function fillFormFromRow(row) {

	document.getElementById("lecturerId").value =
		row.dataset.id.trim();

	document.getElementById("lastName").value =
		row.dataset.lastname;

	document.getElementById("firstName").value =
		row.dataset.firstname;

	document.getElementById("phoneNumber").value =
		row.dataset.phone;

	document.getElementById("address").value =
		row.dataset.address;

	document.getElementById("lecturerId").readOnly = true;
}

	document
		.querySelectorAll(".btn-edit")
		.forEach(button => {

			button.addEventListener("click", function () {
				const row =
					this.closest("tr");

				fillFormFromRow(row);

				document.getElementById("btnUpdate").disabled = false;
				document.getElementById("btnDelete").disabled = false;
			});

		});

	function resetForm() {

		document
			.getElementById("lecturerForm")
			.reset();

		document
			.getElementById("lecturerId")
			.readOnly = false;
		
		document
		.getElementById("btnUpdate")
		.disabled = true;

	document
		.getElementById("btnDelete")
		.disabled = true;
	}

</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>