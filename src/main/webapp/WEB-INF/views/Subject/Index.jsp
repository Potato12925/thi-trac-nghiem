<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Quản lý Môn học");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Danh mục Môn học</h1>
	</div>

	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger">${errorMessage}</div>
	</c:if>

	<div class="border rounded-3 bg-white p-4 mb-4">

		<form:form id="subjectForm"
			action="${pageContext.request.contextPath}/subjects/add"
			method="post" modelAttribute="subjectDTO">
			<input type="hidden" name="page" value="${currentPage}" />

			<div class="row g-3">
				<div class="col-md-3">
					<label class="form-label small text-secondary"> Mã môn học
					</label>

					<form:input path="subjectId" cssClass="form-control" />

					<form:errors path="subjectId"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-5">
					<label class="form-label small text-secondary"> Tên môn học
					</label>

					<form:input path="subjectName" cssClass="form-control" />

					<form:errors path="subjectName"
						cssClass="text-danger small mt-1 d-block" />
				</div>
			</div>

			<div class="d-flex gap-2 mt-4">
				<button type="submit" class="btn btn-dark px-4"
					onclick="submitForm('add')">Thêm</button>

				<button type="submit" class="btn btn-outline-secondary px-4"
					onclick="submitForm('update')">Chỉnh sửa</button>

				<button type="submit" class="btn btn-outline-danger px-4"
					onclick="submitForm('delete')">Xóa</button>
			</div>
		</form:form>
	</div>

	<div class="card border-0 shadow-sm">
		<div class="table-responsive p-3">
			<table class="table table-hover align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th scope="col">Mã MH</th>

						<th scope="col">Tên Môn học</th>

						<th scope="col" class="text-end">Hành động</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${subjects}" var="item">
						<tr>
							<td class="px-4 fw-medium">${item.subjectId}</td>

							<td>${item.subjectName}</td>

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

		<div class="pagination-wrapper">
			<c:if test="${currentPage > 1}">
				<a class="pagination-item" href="subjects?page=1"> First </a>
				<a class="pagination-item" href="subjects?page=${currentPage - 1}">
					&laquo; </a>
			</c:if>

			<c:if test="${currentPage > 3}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:forEach begin="${currentPage - 2 < 1 ? 1 : currentPage - 2}"
				end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}"
				var="i">
				<a href="subjects?page=${i}"
					class="pagination-item ${currentPage == i ? 'active' : ''}">
					${i} </a>
			</c:forEach>

			<c:if test="${currentPage < totalPages - 2}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:if test="${currentPage < totalPages}">
				<a class="pagination-item" href="subjects?page=${currentPage + 1}">
					&raquo; </a>
				<a class="pagination-item" href="subjects?page=${totalPages}">
					Last </a>
			</c:if>
		</div>
	</div>
</div>

<script>

    function fillFormFromRow(row) {

        const cells = row.querySelectorAll("td");

        const subjectId = cells[0].innerText.trim();
        const subjectName = cells[1].innerText.trim();

        document.getElementById("subjectId").value = subjectId;
        document.getElementById("subjectName").value = subjectName;

        document.getElementById("subjectId").readOnly = true;
    }

    const editButtons = document.querySelectorAll(".btn-edit");

    editButtons.forEach(button => {

        button.addEventListener("click", function () {

            const row = this.closest("tr");

            fillFormFromRow(row);
        });
    });

    function submitForm(action) {

        const form = document.getElementById("subjectForm");

        form.action =
            "${pageContext.request.contextPath}/subjects/" + action;
    }

    function resetForm() {

        document.getElementById("subjectForm").reset();

        document.getElementById("subjectId").readOnly = false;
    }

</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
