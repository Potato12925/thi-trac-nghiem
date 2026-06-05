<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Quản lý Giảng viên");
request.setAttribute("customCss", "management.css");
request.setAttribute("customJs", "lecturer-management.js");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid page-wrapper">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Quản lý Giảng viên</h1>
		<div class="d-flex gap-2">
			<a href="${pageContext.request.contextPath}/lecturers/export"
				class="btn btn-outline-success d-flex align-items-center gap-2">
				<i class="bi bi-file-earmark-excel"></i> Xuất Excel
			</a>
			<button type="button"
				class="btn btn-success d-flex align-items-center gap-2"
				data-bs-toggle="modal" data-bs-target="#lecturerImportModal">
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
		<i class="bi bi-exclamation-circle-fill me-2"></i>Bạn có <span id="unsavedCount" class="fw-bold">0</span> thay đổi chưa lưu xuống Database. Hãy nhấn nút <strong>Ghi</strong> để lưu thay đổi.
	</div>

	<div class="border rounded-3 bg-white p-4 mb-4 form-section">
		<form method="get" action="${pageContext.request.contextPath}/lecturers" class="row g-3 mb-4">
			<div class="col-md-8">
				<label class="form-label small text-secondary"> Từ khóa tìm kiếm </label>
				<input type="text" name="keyword" class="form-control" value="${keyword}" placeholder="Mã giảng viên, họ, tên" />
			</div>
			<div class="col-md-4 d-flex align-items-end">
				<button type="submit" class="btn btn-outline-secondary w-100">Tìm kiếm</button>
			</div>
		</form>

		<form:form id="lecturerForm" method="post"
			action="${pageContext.request.contextPath}/lecturers/add"
			modelAttribute="lecturerDTO">
			<input type="hidden" name="page" value="${currentPage}" />
			<input type="hidden" name="keyword" value="${keyword}" />

			<div class="row g-3">
				<div class="col-md-2">
					<label class="form-label small text-secondary"> Mã giảng viên
					</label>

					<form:input path="lecturerId" id="lecturerId"
						cssClass="form-control" />

					<form:errors path="lecturerId"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-3">
					<label class="form-label small text-secondary"> Họ </label>

					<form:input path="lastName" id="lastName" cssClass="form-control" />

					<form:errors path="lastName"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-2">
					<label class="form-label small text-secondary"> Tên
					</label>

					<form:input path="firstName" id="firstName" cssClass="form-control" />

					<form:errors path="firstName"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-2">
					<label class="form-label small text-secondary"> Số điện thoại </label>

					<form:input path="phoneNumber" id="phoneNumber"
						cssClass="form-control" />

					<form:errors path="phoneNumber"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-3">
					<label class="form-label small text-secondary"> Địa chỉ </label>

					<form:input path="address" id="address" cssClass="form-control" />

					<form:errors path="address"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-md-3">
					<label class="form-label small text-secondary"> Email </label>

					<form:input path="email" id="email" cssClass="form-control" />

					<form:errors path="email"
						cssClass="text-danger small mt-1 d-block" />
				</div>
			</div>

			<div class="d-flex gap-2 mt-4">
				<button type="button" class="btn btn-dark px-4" id="btnAdd">Thêm</button>

				<button type="button" class="btn btn-outline-secondary px-4" id="btnUpdate" disabled>Chỉnh sửa</button>

				<button type="button" class="btn btn-outline-danger px-4" id="btnDelete" disabled>Xóa</button>
				
				<button type="button" class="btn btn-outline-secondary" id="btnUndo" disabled>
					<i class="bi bi-arrow-counterclockwise me-1"></i> Hoàn tác
				</button>

				<button type="button" class="btn btn-primary px-4" id="btnSave" disabled>
					<i class="bi bi-save me-1"></i> Ghi
				</button>

				<button type="button" class="btn btn-outline-secondary px-4" id="btnReset">Xóa dữ liệu</button>
			</div>
		</form:form>
	</div>

	<form id="saveForm" action="${pageContext.request.contextPath}/lecturers/save" method="post" class="d-none">
		<input type="hidden" name="page" value="${currentPage}" />
		<input type="hidden" name="keyword" value="${keyword}" />
		<input type="hidden" name="actionsData" id="actionsDataInput" />
	</form>

	<div class="card border-0 shadow-sm management-card">
		<div class="table-responsive p-3 management-table-wrapper">
			<table class="table table-hover align-middle mb-0 management-table">
				<thead class="table-light">
					<tr>
						<th>Mã giảng viên</th>

						<th>Họ</th>

						<th>Tên</th>

						<th>Số điện thoại</th>

						<th>Địa chỉ</th>

						<th>Email</th>

						<th class="text-end">Hành động</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${lecturers}" var="lecturer">
						<tr data-id="${lecturer.lecturerId}"
							data-lastname="${lecturer.lastName}"
							data-firstname="${lecturer.firstName}"
							data-phone="${lecturer.phoneNumber}"
							data-address="${lecturer.address}"
							data-email="${lecturer.email}">

							<td class="fw-medium text-success">${lecturer.lecturerId}</td>

							<td>${lecturer.lastName}</td>

							<td>${lecturer.firstName}</td>

							<td>${lecturer.phoneNumber}</td>

							<td>${lecturer.address}</td>

							<td>${lecturer.email}</td>

							<td class="text-end">
								<button type="button"
									class="btn btn-sm btn-outline-secondary me-2 btn-edit">

									<i class="bi bi-pencil"></i>
								</button>
							</td>
						</tr>

						<c:if test="${empty lecturers}">
							<tr>
								<td colspan="6" class="text-center text-muted py-4">Không tìm thấy giảng viên</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="pagination-wrapper">
			<c:if test="${currentPage > 1}">
				<a class="pagination-item" href="lecturers?page=1&keyword=${keyword}"> Đầu </a>
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
					Cuối </a>
			</c:if>
		</div>
	</div>
	<!-- Import Excel Modal -->
	<div class="modal fade" id="lecturerImportModal" tabindex="-1"
		aria-labelledby="lecturerImportModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow">
				<form action="${pageContext.request.contextPath}/lecturers/import"
					method="post" enctype="multipart/form-data">
					<div class="modal-header bg-success text-white">
						<h5 class="modal-title fw-semibold" id="lecturerImportModalLabel">
							<i class="bi bi-file-earmark-excel me-2"></i> Nhập dữ liệu từ Excel
						</h5>
						<button type="button" class="btn-close btn-close-white"
							data-bs-dismiss="modal" aria-label="Đóng"></button>
					</div>
					<div class="modal-body">
						<div class="mb-3">
							<label for="excelFile"
								class="form-label fw-medium text-secondary">Chọn tệp Excel (.xlsx, .xls)</label>
							<input class="form-control" type="file"
								id="excelFile" name="file" accept=".xlsx, .xls" required />
						</div>
						<div class="alert alert-info py-2 px-3 mb-0 small">
							<i class="bi bi-info-circle-fill me-1"></i> Tệp Excel nên có tiêu đề ở dòng đầu tiên.
							<br/>Cột 1: Mã giảng viên (tối đa 8 ký tự)
							<br/>Cột 2: Họ giảng viên (tối đa 40 ký tự)
							<br/>Cột 3: Tên giảng viên (tối đa 10 ký tự)
							<br/>Cột 4: Số điện thoại (tối đa 15 ký tự)
							<br/>Cột 5: Địa chỉ (tối đa 50 ký tự)
							<div class="mt-2">
								<a href="${pageContext.request.contextPath}/lecturers/import/template" class="text-primary fw-medium text-decoration-none">
									<i class="bi bi-download me-1"></i>Tải về tệp mẫu cấu trúc
								</a>
							</div>
						</div>
					</div>
					<div class="modal-footer bg-light">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Hủy</button>
						<button type="submit" class="btn btn-success">Nhập dữ liệu</button>
					</div>
				</form>
			</div>
		</div>
	</div>

</div>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
