<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%
request.setAttribute("pageTitle", "Quản lý Sinh viên");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Hồ sơ Sinh viên</h1>
	</div>

	<h3>${error}</h3>

	<div class="border rounded-3 bg-white p-4 mb-4">

		<form:form id="studentForm"
			action="${pageContext.request.contextPath}/student/add" method="post"
			modelAttribute="sinhVienDTO">

			<input type="hidden" id="_method" name="_method" value="POST" />

			<div class="row g-3">

				<div class="col-md-3">

					<label class="form-label small text-secondary"> Mã sinh
						viên </label>

					<form:input path="maSV" cssClass="form-control" />
					<form:errors path="maSV" cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-5">

					<label class="form-label small text-secondary"> Họ </label>

					<form:input path="ho" cssClass="form-control" />
					<form:errors path="ho" cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-2">

					<label class="form-label small text-secondary"> Tên </label>

					<form:input path="ten" cssClass="form-control" />
					<form:errors path="ten" cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-2">

					<label class="form-label small text-secondary"> Ngày sinh </label>

					<form:input path="ngaySinh" cssClass="form-control"
						placeholder="yyyy-MM-dd" />
					<form:errors path="ngaySinh"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-2">

					<label class="form-label small text-secondary"> Địa chỉ </label>

					<form:input path="diaChi" cssClass="form-control" />
					<form:errors path="diaChi"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-2">

					<label class="form-label small text-secondary">Mã lớp</label>

					<form:input path="maLop" cssClass="form-control" />
					<form:errors path="maLop"
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
						<th scope="col">Mã sinh viên</th>
						<th scope="col">Họ</th>
						<th scope="col">Tên</th>
						<th scope="col">Ngày sinh</th>
						<th scope="col">Địa chỉ</th>
						<th scope="col">Lớp</th>
						<th scope="col" class="text-end">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="sv" items="${danhSachSinhVien}">

						<tr>

							<td class="px-4 fw-medium">${sv.maSV}</td>

							<td>${sv.ho}</td>

							<td>${sv.ten}</td>

							<td>${sv.ngaySinh}</td>

							<td>${sv.diaChi}</td>

							<td>${sv.lop.maLop}</td>

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
		
		const maSV = cells[0].innerText;
		const ho = cells[1].innerText;
		const ten = cells[2].innerText;
		const ngaySinh = cells[3].innerText;
		const diaChi = cells[4].innerText;
		const maLop = cells[5].innerText;
		
		document.getElementById("maSV").value = maSV;
        document.getElementById("ho").value = ho;
        document.getElementById("ten").value = ten;
        document.getElementById("ngaySinh").value = ngaySinh;
        document.getElementById("diaChi").value = diaChi;
        document.getElementById("maLop").value = maLop;
        
        document.getElementById("maSV").readOnly = true;
	}

	const editButtons = document.querySelectorAll(".btn-edit");
	
	editButtons.forEach(button => {
		button.addEventListener("click", function () {
			const row = this.closest("tr");
			
			fillFormFromRow(row);
		});
	});

	function submitForm(action, method) {

        const form = document.getElementById("studentForm");

        form.action =
            "${pageContext.request.contextPath}/student/"
            + action;

        document.getElementById("_method").value =
            method;
    }
	
	function resetForm() {
	    document.getElementById("studentForm").reset();

	    document.getElementById("maSV").readOnly = false;
	}
</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
