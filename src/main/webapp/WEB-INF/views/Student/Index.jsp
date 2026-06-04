<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Student Management");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid">

	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Student Management</h1>
	</div>

	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<div class="border rounded-3 bg-white p-4 mb-4">

		<form:form id="studentForm" method="post"
			action="${pageContext.request.contextPath}/students/add"
			modelAttribute="studentDTO">
			<input type="hidden" name="page" value="${currentPage}" />

			<div class="row g-3">

				<div class="col-md-3">

					<label class="form-label small text-secondary"> Student ID
					</label>

					<form:input path="studentId" id="studentId" cssClass="form-control" />

					<form:errors path="studentId"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-md-4">

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

				<div class="col-md-3">

					<label class="form-label small text-secondary"> Birth Date
					</label>

					<form:input type="date" path="birthDate" id="birthDate"
						cssClass="form-control" />

					<form:errors path="birthDate"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-md-8">

					<label class="form-label small text-secondary"> Address </label>

					<form:input path="address" id="address" cssClass="form-control" />

					<form:errors path="address"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-md-4">

					<label class="form-label small text-secondary"> Email </label>

					<form:input path="email" id="email" cssClass="form-control" />

					<form:errors path="email"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-md-4">

					<label class="form-label small text-secondary"> Class ID </label>

					<form:input path="classId" id="classId" cssClass="form-control" />

					<form:errors path="classId"
						cssClass="text-danger small mt-1 d-block" />

				</div>

			</div>

			<div class="d-flex gap-2 mt-4">

				<button type="submit" class="btn btn-dark px-4"
					onclick="submitForm('/students/add')">Add</button>

				<button type="submit" class="btn btn-outline-secondary px-4"
					onclick="submitForm('/students/update')">Update</button>

				<button type="submit" class="btn btn-outline-danger px-4"
					onclick="submitForm('/students/delete')">Delete</button>

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
						<th>Student ID</th>

						<th>Last Name</th>

						<th>First Name</th>

						<th>Birth Date</th>

						<th>Address</th>

						<th>Email</th>

						<th>Class ID</th>

						<th class="text-end">Actions</th>
					</tr>

				</thead>

				<tbody>

					<c:forEach var="student" items="${students}">

						<tr>

							<td>${student.studentId}</td>

							<td>${student.lastName}</td>

							<td>${student.firstName}</td>

							<td>${student.birthDate}</td>

							<td>${student.address}</td>

							<td>${student.email}</td>

							<td>${student.classRoom.classId}</td>

							<td class="text-end">

								<button type="button"
									class="btn btn-sm btn-outline-secondary btn-edit">

									<i class="bi bi-pencil"></i>

								</button>

							</td>

						</tr>

					</c:forEach>

				</tbody>

			</table>

		</div>

		<div class="pagination-wrapper">
			<c:if test="${currentPage > 1}">
				<a class="pagination-item" href="students?page=1"> First </a>
				<a class="pagination-item" href="students?page=${currentPage - 1}">
					&laquo; </a>
			</c:if>

			<c:if test="${currentPage > 3}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:forEach begin="${currentPage - 2 < 1 ? 1 : currentPage - 2}"
				end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}"
				var="i">
				<a href="students?page=${i}"
					class="pagination-item ${currentPage == i ? 'active' : ''}">
					${i} </a>
			</c:forEach>

			<c:if test="${currentPage < totalPages - 2}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:if test="${currentPage < totalPages}">
				<a class="pagination-item" href="students?page=${currentPage + 1}">
					&raquo; </a>
				<a class="pagination-item" href="students?page=${totalPages}">
					Last </a>
			</c:if>
		</div>
	</div>

</div>

<script>

	function fillFormFromRow(row) {

		const cells = row.querySelectorAll("td");

		document.getElementById("studentId").value =
			cells[0].innerText.trim();

		document.getElementById("lastName").value =
			cells[1].innerText.trim();

		document.getElementById("firstName").value =
			cells[2].innerText.trim();

		document.getElementById("birthDate").value =
			cells[3].innerText.trim();

		document.getElementById("address").value =
			cells[4].innerText.trim();

		document.getElementById("email").value =
			cells[5].innerText.trim();

		document.getElementById("classId").value =
			cells[6].innerText.trim();

		document.getElementById("studentId").readOnly = true;
	}

	document
		.querySelectorAll(".btn-edit")
		.forEach(button => {

			button.addEventListener("click", function () {

				const row = this.closest("tr");

				fillFormFromRow(row);

			});

		});

    function submitForm(action) {

        const form = document.getElementById("studentForm");

        form.action =
            "${pageContext.request.contextPath}" + action;
    }

    function resetForm() {

        document.getElementById("studentForm").reset();

        document.getElementById("studentId").readOnly = false;
    }


</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
