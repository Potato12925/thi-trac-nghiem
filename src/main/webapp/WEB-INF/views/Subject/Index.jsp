<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Quản lý Môn học");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container py-4">

	<div class="d-flex justify-content-between align-items-center mb-4">
		<div>
			<h3 class="fw-semibold mb-1">Quản lý môn học</h3>
			<p class="text-muted mb-0 small">Quản lý danh sách môn học trong
				hệ thống</p>
		</div>
	</div>

	<div class="border rounded-3 bg-white p-4 mb-4">

		<form:form id="subjectForm"
			action="${pageContext.request.contextPath}/subject/add" method="post"
			modelAttribute="monHoc">

			<input type="hidden" id="_method" name="_method" value="POST" />

			<div class="row g-3">

				<div class="col-md-3">

					<label class="form-label small text-secondary"> Mã môn học
					</label>

					<form:input path="maMH" cssClass="form-control" />
					<form:errors path="maMH" cssClass="text-danger small mt-1 d-block"/>
				</div>

				<div class="col-md-5">

					<label class="form-label small text-secondary"> Tên môn học
					</label>

					<form:input path="tenMH" cssClass="form-control" />
					<form:errors path="tenMH" cssClass="text-danger small mt-1 d-block"/>
				</div>

				<div class="col-md-2">

					<label class="form-label small text-secondary"> Số tiết LT
					</label>

					<form:input path="soTiet_LT" cssClass="form-control" />
					<form:errors path="soTiet_LT" cssClass="text-danger small mt-1 d-block"/>
				</div>

				<div class="col-md-2">

					<label class="form-label small text-secondary"> Số tiết TH
					</label>

					<form:input path="soTiet_TH" cssClass="form-control" />
					<form:errors path="soTiet_TH" cssClass="text-danger small mt-1 d-block"/>
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
	<div class="border rounded-3 bg-white overflow-hidden">

		<div class="px-4 py-3 border-bottom">
			<h6 class="mb-0 fw-semibold">Danh sách môn học</h6>
		</div>

		<form id="searchForm"
			action="${pageContext.request.contextPath}/subject" method="get"
			class="row g-3 align-items-end mt-3">
			<div class="col-md-3">
				<label class="form-label text-sm fw-medium">Tìm kiếm</label>
				<div class="input-group input-group-sm">
					<input type="text" name="search" class="form-control"
						placeholder="Mã MH hoặc Tên MH" value="${search}" />
					<button class="btn btn-outline-secondary" type="submit">
						<i class="bi bi-search"></i>
					</button>
				</div>
			</div>
		</form>

		<div class="table-responsive">

			<table class="table align-middle mb-0">

				<thead class="bg-light">
					<tr>
						<th class="px-4 py-3 text-secondary fw-semibold">Mã MH</th>

						<th class="py-3 text-secondary fw-semibold">Tên môn học</th>

						<th class="py-3 text-secondary fw-semibold">Số tiết LT</th>

						<th class="py-3 text-secondary fw-semibold">Số tiết TH</th>

						<th class="py-3 text-end text-secondary fw-semibold pe-4">
							Hành động</th>
					</tr>
				</thead>

				<tbody>

					<c:forEach var="mh" items="${danhSachMonHoc}">

						<tr>

							<td class="px-4 fw-medium">${mh.maMH}</td>

							<td>${mh.tenMH}</td>

							<td>${mh.soTiet_LT}</td>

							<td>${mh.soTiet_TH}</td>

							<td class="text-end pe-4">

								<button class="btn btn-sm btn-outline-secondary me-2 btn-edit">
									<i class="bi bi-pencil"></i>
								</button>

								<button class="btn btn-sm btn-outline-danger">
									<i class="bi bi-trash"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<style>
body {
	background-color: #f8f9fa;
}

.form-control {
	height: 42px;
	border-radius: 10px;
	border: 1px solid #dee2e6;
	font-size: 14px;
}

.form-control:focus {
	box-shadow: none;
	border-color: #212529;
}

.btn {
	height: 40px;
	border-radius: 10px;
	font-size: 14px;
	font-weight: 500;
	box-shadow: none !important;
}

.table th {
	font-size: 13px;
	letter-spacing: 0.3px;
	border-bottom: 1px solid #e9ecef;
}

.table td {
	padding-top: 16px;
	padding-bottom: 16px;
	border-color: #f1f3f5;
	font-size: 14px;
}

.table tbody tr:hover {
	background-color: #fafafa;
}
</style>

<script>
	function fillFormFromRow(row) {
		const cells = row.querySelectorAll("td");
		
		const maMH = cells[0].innerText;
		const tenMH = cells[1].innerText;
		const soTietLT = cells[2].innerText;
		const soTietTH = cells[3].innerText;
		        
		document.getElementById("maMH").value = maMH;
        document.getElementById("tenMH").value = tenMH;
        document.getElementById("soTiet_LT").value = soTietLT;
        document.getElementById("soTiet_TH").value = soTietTH;
        
        document.getElementById("maMH").readOnly = true;
	}

	const editButtons = document.querySelectorAll(".btn-edit");
	
	editButtons.forEach(button => {
		button.addEventListener("click", function () {
			const row = this.closest("tr");
			
			fillFormFromRow(row);
		});
	});

	function submitForm(action, method) {

        const form = document.getElementById("subjectForm");

        form.action =
            "${pageContext.request.contextPath}/subject/"
            + action;

        document.getElementById("_method").value =
            method;
    }
	
	function resetForm() {
	    document.getElementById("subjectForm").reset();

	    document.getElementById("maMH").readOnly = false;
	}
</script>


<%@ include file="../Shared/_LayoutEnd.jsp"%>