<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%
request.setAttribute("pageTitle", "Student Management");
request.setAttribute("customCss", "management.css");
request.setAttribute("customJs", "student-management.js");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid page-wrapper">

	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Student Management</h1>
		<div class="d-flex gap-2">
			<a href="${pageContext.request.contextPath}/students/export"
				class="btn btn-outline-success d-flex align-items-center gap-2">
				<i class="bi bi-file-earmark-excel"></i> Xuất Excel
			</a>
			<button type="button"
				class="btn btn-success d-flex align-items-center gap-2"
				data-bs-toggle="modal" data-bs-target="#studentImportModal">
				<i class="bi bi-file-earmark-arrow-up"></i> Nhập Excel
			</button>
		</div>
	</div>

	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<c:if test="${not empty successMessage}">
		<div class="alert alert-success">${successMessage}</div>
	</c:if>

	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger">${errorMessage}</div>
	</c:if>

	<div id="clientAlert" class="alert alert-danger d-none mb-3"></div>
	<div id="unsavedChangesAlert" class="alert alert-warning d-none mb-3">
		<i class="bi bi-exclamation-circle-fill me-2"></i>Bạn có <span
			id="unsavedCount" class="fw-bold">0</span> thay đổi chưa lưu xuống
		Database. Hãy nhấn nút <strong>Ghi</strong> để lưu thay đổi.
	</div>

	<div class="border rounded-3 bg-white p-4 mb-4 form-section">

		<form method="get"
			action="${pageContext.request.contextPath}/students"
			class="row g-3 mb-4">
			<div class="col-md-6">
				<label class="form-label small text-secondary"> Search
					keyword </label> <input type="text" name="keyword" class="form-control"
					value="${keyword}" placeholder="ID, last name, first name" />
			</div>
			<div class="col-md-4">
				<label class="form-label small text-secondary"> Class Filter
				</label> <select name="filterClassId" class="form-select">
					<option value="">All classes</option>
					<c:forEach var="classroom" items="${classrooms}">
						<option value="${classroom.classId}"
							${classroom.classId == classId ? 'selected' : ''}>
							${classroom.classId}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md-2 d-flex align-items-end">
				<button type="submit" class="btn btn-outline-secondary w-100">Search</button>
			</div>
		</form>

		<form:form id="studentForm" method="post"
			action="${pageContext.request.contextPath}/students/add"
			modelAttribute="studentDTO">
			<input type="hidden" name="page" value="${currentPage}" />
			<input type="hidden" name="keyword" value="${keyword}" />
			<input type="hidden" name="filterClassId" value="${classId}" />

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

					<form:errors path="email" cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-md-4">

					<label class="form-label small text-secondary"> Class ID </label> <select
						id="classId" name="classId" class="form-select">
						<option value="">Choose class</option>
						<c:forEach var="classroom" items="${classrooms}">
							<option value="${classroom.classId}"
								${classroom.classId == studentDTO.classId ? 'selected' : ''}>
								${classroom.classId}</option>
						</c:forEach>
					</select>

					<form:errors path="classId"
						cssClass="text-danger small mt-1 d-block" />

				</div>

			</div>

			<div class="d-flex gap-2 mt-4">
				<button type="button" class="btn btn-dark px-4" id="btnAdd">Thêm</button>

				<button type="button" class="btn btn-outline-secondary px-4"
					id="btnUpdate" disabled>Chỉnh sửa</button>

				<button type="button" class="btn btn-outline-danger px-4"
					id="btnDelete" disabled>Xóa</button>

				<button type="button" class="btn btn-outline-secondary" id="btnUndo"
					disabled>
					<i class="bi bi-arrow-counterclockwise me-1"></i> Undo
				</button>

				<button type="button" class="btn btn-primary px-4" id="btnSave"
					disabled>
					<i class="bi bi-save me-1"></i> Ghi
				</button>

				<button type="button" class="btn btn-outline-secondary px-4"
					id="btnReset">Xóa dữ liệu</button>
			</div>
		</form:form>
	</div>

	<form id="saveForm"
		action="${pageContext.request.contextPath}/students/save"
		method="post" class="d-none">
		<input type="hidden" name="page" value="${currentPage}" /> <input
			type="hidden" name="keyword" value="${keyword}" /> <input
			type="hidden" name="filterClassId" value="${classId}" /> <input
			type="hidden" name="actionsData" id="actionsDataInput" />
	</form>

	<div class="card border-0 shadow-sm management-card">

		<div class="table-responsive p-3 management-table-wrapper">

			<table class="table table-hover align-middle mb-0 management-table">

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

							<td><a href="#"
								class="student-quick-view-trigger text-decoration-none fw-bold"
								data-student-id="${student.studentId}"> ${student.studentId}
							</a></td>

							<td>${student.lastName}</td>

							<td>${student.firstName}</td>

							<td><fmt:formatDate value="${student.birthDate}"
									pattern="yyyy-MM-dd" /></td>

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
				<a class="pagination-item"
					href="students?page=1&keyword=${keyword}&filterClassId=${classId}">
					First </a>
				<a class="pagination-item"
					href="students?page=${currentPage - 1}&keyword=${keyword}&filterClassId=${classId}">
					&laquo; </a>
			</c:if>

			<c:if test="${currentPage > 3}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:forEach begin="${currentPage - 2 < 1 ? 1 : currentPage - 2}"
				end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}"
				var="i">
				<a
					href="students?page=${i}&keyword=${keyword}&filterClassId=${classId}"
					class="pagination-item ${currentPage == i ? 'active' : ''}">
					${i} </a>
			</c:forEach>

			<c:if test="${currentPage < totalPages - 2}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:if test="${currentPage < totalPages}">
				<a class="pagination-item"
					href="students?page=${currentPage + 1}&keyword=${keyword}&filterClassId=${classId}">
					&raquo; </a>
				<a class="pagination-item"
					href="students?page=${totalPages}&keyword=${keyword}&filterClassId=${classId}">
					Last </a>
			</c:if>
		</div>
	</div>

	<!-- Import Excel Modal -->
	<div class="modal fade" id="studentImportModal" tabindex="-1"
		aria-labelledby="studentImportModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow">
				<form action="${pageContext.request.contextPath}/students/import"
					method="post" enctype="multipart/form-data">
					<div class="modal-header bg-success text-white">
						<h5 class="modal-title fw-semibold" id="studentImportModalLabel">
							<i class="bi bi-file-earmark-excel me-2"></i> Nhập dữ liệu từ
							Excel
						</h5>
						<button type="button" class="btn-close btn-close-white"
							data-bs-dismiss="modal" aria-label="Đóng"></button>
					</div>
					<div class="modal-body">
						<div class="mb-3">
							<label for="excelFile"
								class="form-label fw-medium text-secondary">Chọn tệp
								Excel (.xlsx, .xls)</label> <input class="form-control" type="file"
								id="excelFile" name="file" accept=".xlsx, .xls" required />
						</div>
						<div class="alert alert-info py-2 px-3 mb-0 small">
							<i class="bi bi-info-circle-fill me-1"></i> Tệp Excel nên có tiêu
							đề ở dòng đầu tiên. <br />Cột 1: Mã sinh viên (8 ký tự) <br />Cột
							2: Họ sinh viên (tối đa 50 ký tự) <br />Cột 3: Tên sinh viên (tối
							đa 10 ký tự) <br />Cột 4: Ngày sinh (yyyy-MM-dd hoặc dd/MM/yyyy)
							<br />Cột 5: Địa chỉ (tối đa 100 ký tự) <br />Cột 6: Mã lớp (phải
							tồn tại trong hệ thống)
							<div class="mt-2">
								<a href="${pageContext.request.contextPath}/students/import/template" class="text-primary fw-medium text-decoration-none">
									<i class="bi bi-download me-1"></i>Tải về tệp mẫu cấu trúc
								</a>
							</div>
						</div>
					</div>
					<div class="modal-footer bg-light">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Hủy</button>
						<button type="submit" class="btn btn-success">Nhập dữ
							liệu</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Student Quick-View Modal -->
	<div class="modal fade" id="studentQuickViewModal" tabindex="-1"
		aria-labelledby="studentQuickViewModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content border-0 shadow">
				<div class="modal-header bg-primary text-white">
					<h5 class="modal-title fw-semibold" id="studentQuickViewModalLabel">
						<i class="bi bi-person-bounding-box me-2"></i> Hồ sơ chi tiết sinh
						viên
					</h5>
					<button type="button" class="btn-close btn-close-white"
						data-bs-dismiss="modal" aria-label="Đóng"></button>
				</div>
				<div class="modal-body p-4">
					<div class="row mb-4">
						<div class="col-md-3 text-center mb-3 mb-md-0">
							<div
								class="bg-light rounded-circle d-flex align-items-center justify-content-center mx-auto"
								style="width: 100px; height: 100px; border: 3px solid #dee2e6;">
								<i class="bi bi-person text-secondary" style="font-size: 3rem;"></i>
							</div>
							<div class="mt-2 fw-semibold text-secondary" id="qvStudentId">SV000000</div>
						</div>
						<div class="col-md-9">
							<div class="row g-3">
								<div class="col-sm-6">
									<div class="text-secondary small">Họ và tên</div>
									<div class="fw-bold fs-5" id="qvFullName">Nguyễn Văn A</div>
								</div>
								<div class="col-sm-6">
									<div class="text-secondary small">Lớp học</div>
									<div class="fw-bold fs-5" id="qvClassName">D18CQCN01
										(CNTT)</div>
								</div>
								<div class="col-sm-6">
									<div class="text-secondary small">Ngày sinh</div>
									<div class="fw-bold" id="qvBirthDate">1999-01-01</div>
								</div>
								<div class="col-sm-6">
									<div class="text-secondary small">Email</div>
									<div class="fw-bold" id="qvEmail">student@email.com</div>
								</div>
								<div class="col-sm-12">
									<div class="text-secondary small">Địa chỉ</div>
									<div class="fw-bold" id="qvAddress">123 Đường ABC, Quận
										XYZ</div>
								</div>
							</div>
						</div>
					</div>

					<hr />

					<h6 class="fw-semibold text-primary mb-3">
						<i class="bi bi-journal-check me-1"></i> Lịch sử làm bài thi
					</h6>
					<div class="table-responsive">
						<table class="table table-sm table-hover align-middle">
							<thead class="table-light">
								<tr>
									<th>Mã môn</th>
									<th>Tên môn học</th>
									<th>Lần thi</th>
									<th>Điểm số</th>
									<th>Ngày làm bài</th>
								</tr>
							</thead>
							<tbody id="qvExamHistoryBody">
								<tr>
									<td colspan="5" class="text-center text-muted">Đang tải dữ
										liệu...</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer bg-light">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Đóng</button>
				</div>
			</div>
		</div>
	</div>

</div>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
