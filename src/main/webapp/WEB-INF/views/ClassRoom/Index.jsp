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
		<h1 class="h3 mb-0">Quản lý Lớp</h1>
	</div>

	<h3>${error}</h3>

	<div class="border rounded-3 bg-white p-4 mb-4">

		<form:form id="classRoomForm"
			action="${pageContext.request.contextPath}/classRoom/add"
			method="post" modelAttribute="classRoomDTO">

			<input type="hidden" id="_method" name="_method" value="POST" />

			<div class="row g-3">

				<div class="col-md-3">

					<label class="form-label small text-secondary"> Mã lớp </label>

					<form:input path="maLop" cssClass="form-control" />
					<form:errors path="maLop" cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-5">

					<label class="form-label small text-secondary"> Tên lớp </label>

					<form:input path="tenLop" cssClass="form-control" />
					<form:errors path="tenLop"
						cssClass="text-danger small mt-1 d-block" />
				</div>
			</div>

			<div class="d-flex gap-2 mt-4">

				<button type="submit" class="btn btn-dark px-4"
					onclick="submitForm(
                        'add',
                        'POST'
                    )">

					Thêm</button>

				<button type="submit" class="btn btn-outline-secondary px-4"
					onclick="submitForm(
                        'update',
                        'PUT'
                    )">

					Chỉnh sửa</button>

				<button type="submit" class="btn btn-outline-danger px-4"
					onclick="submitForm(
                        'delete',
                        'DELETE'
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
						<th scope="col">Mã lớp</th>

						<th scope="col">Tên lớp</th>

						<th scope="col" class="text-end">Hành động</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="cr" items="${classRoomList}">

						<tr>

							<td>${cr.maLop}</td>

							<td>${cr.tenLop}</td>

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
		
		document.getElementById("maLop").value = maLop;
        document.getElementById("tenLop").value = tenLop;
 
        document.getElementById("maLop").readOnly = true;
	}

	const editButtons = document.querySelectorAll(".btn-edit");
	
	editButtons.forEach(button => {
		button.addEventListener("click", function () {
			const row = this.closest("tr");
			
			fillFormFromRow(row);
		});
	});

	function submitForm(action, method) {

        const form = document.getElementById("classRoomForm");

        form.action =
            "${pageContext.request.contextPath}/classRoom/"
            + action;

        document.getElementById("_method").value =
            method;
    }
	
	function resetForm() {
	    document.getElementById("classRoomForm").reset();

	    document.getElementById("maLop").readOnly = false;
	}
</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
