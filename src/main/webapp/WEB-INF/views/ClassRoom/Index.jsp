<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Quản lý Lớp");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Class Room Management</h1>
	</div>

	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<div class="border rounded-3 bg-white p-4 mb-4">

		<form:form id="classRoomForm"
			action="${pageContext.request.contextPath}/classRoom/add"
			method="post" modelAttribute="classRoomDTO">

			<input type="hidden" id="_method" name="_method" value="POST" />

			<div class="row g-3">

				<div class="col-md-3">

					<label class="form-label small text-secondary"> Class ID </label>

					<form:input path="classId" cssClass="form-control" />
					<form:errors path="classId"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-5">

					<label class="form-label small text-secondary"> Class Name
					</label>

					<form:input path="className" cssClass="form-control" />
					<form:errors path="className"
						cssClass="text-danger small mt-1 d-block" />
				</div>
			</div>

			<div class="d-flex gap-2 mt-4">

				<button type="submit" class="btn btn-dark px-4"
					onclick="submitForm(
                        'add',
                    )">

					Add</button>

				<button type="submit" class="btn btn-outline-secondary px-4"
					onclick="submitForm(
                        'update',
                    )">

					Update</button>

				<button type="submit" class="btn btn-outline-danger px-4"
					onclick="submitForm(
                        'delete',
                    )">

					Xóa</button>

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
						<th scope="col">Class ID</th>

						<th scope="col">Class Name</th>

						<th scope="col" class="text-end">Actions</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="cr" items="${classRoomList}">

						<tr>

							<td>${cr.classId}</td>

							<td>${cr.className}</td>

							<td class="text-end pe-4">

								<button class="btn btn-sm btn-outline-secondary me-2 btn-edit">
									<i class="bi bi-pencil"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script>
	function fillFormFromRow(row) {
		const cells = row.querySelectorAll("td");
		
		const maLop = cells[0].innerText;
		const tenLop = cells[1].innerText;
		
		document.getElementById("classId").value = maLop;
        document.getElementById("className").value = tenLop;
 
        document.getElementById("classId").readOnly = true;
	}

	const editButtons = document.querySelectorAll(".btn-edit");
	
	editButtons.forEach(button => {
		button.addEventListener("click", function () {
			const row = this.closest("tr");
			
			fillFormFromRow(row);
		});
	});

	function submitForm(action) {

        const form = document.getElementById("classRoomForm");

        form.action =
            "${pageContext.request.contextPath}/classRoom/"
            + action;
    }
	
	function resetForm() {
	    document.getElementById("classRoomForm").reset();

	    document.getElementById("classId").readOnly = false;
	}
</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
