<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Quản lý Môn học");
request.setAttribute("customCss", "management.css");
request.setAttribute("customJs", "subject-management.js");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid page-wrapper">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Danh mục Môn học</h1>
		<div class="d-flex gap-2">
			<a href="${pageContext.request.contextPath}/subjects/export"
				class="btn btn-outline-success d-flex align-items-center gap-2">
				<i class="bi bi-file-earmark-excel"></i> Xuất Excel
			</a>
			<button type="button"
				class="btn btn-success d-flex align-items-center gap-2"
				data-bs-toggle="modal" data-bs-target="#subjectImportModal">
				<i class="bi bi-file-earmark-arrow-up"></i> Nhập Excel
			</button>
		</div>
	</div>

	<div class="row mb-4">
		<div class="col-md-8">
			<form action="${pageContext.request.contextPath}/subjects"
				method="get" class="d-flex">
				<input type="hidden" name="page" value="1" /> <input type="search"
					name="search" value="${search}" class="form-control me-2"
					placeholder="Tìm mã hoặc tên môn học" />
				<button type="submit" class="btn btn-primary px-4">Tìm kiếm</button>
				<a href="${pageContext.request.contextPath}/subjects"
					class="btn btn-outline-secondary ms-2 px-4">Xóa</a>
			</form>
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

		<form:form id="subjectForm"
			action="${pageContext.request.contextPath}/subjects/add"
			method="post" modelAttribute="subjectDTO">
			<input type="hidden" name="page" value="${currentPage}" />
			<input type="hidden" name="search" value="${search}" />

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
				<button type="button" class="btn btn-dark px-4" id="btnAdd">Thêm</button>

				<button type="button" class="btn btn-outline-secondary px-4" id="btnUpdate" disabled>Chỉnh sửa</button>

				<button type="button" class="btn btn-outline-danger px-4" id="btnDelete" disabled>Xóa</button>
				
				<button type="button" class="btn btn-outline-secondary" id="btnUndo" disabled>
					<i class="bi bi-arrow-counterclockwise me-1"></i> Undo
				</button>

				<button type="button" class="btn btn-primary px-4" id="btnSave" disabled>
					<i class="bi bi-save me-1"></i> Ghi
				</button>

				<button type="button" class="btn btn-outline-secondary px-4" id="btnReset">Xóa dữ liệu</button>
			</div>
		</form:form>
	</div>

	<form id="saveForm" action="${pageContext.request.contextPath}/subjects/save" method="post" class="d-none">
		<input type="hidden" name="page" value="${currentPage}" />
		<input type="hidden" name="search" value="${search}" />
		<input type="hidden" name="actionsData" id="actionsDataInput" />
	</form>

	<div class="card border-0 shadow-sm management-card">
		<div class="table-responsive p-3 management-table-wrapper">
			<table class="table table-hover align-middle mb-0 management-table">
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
								<button type="button"
									class="btn btn-sm btn-outline-secondary me-2 btn-edit">
									<i class="bi bi-pencil p-2"></i> Chỉnh sửa
								</button>
								<button type="button"
									class="btn btn-sm btn-outline-danger btn-delete">
									<i class="bi bi-trash p-2"></i> Xóa
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="pagination-wrapper">
			<c:if test="${currentPage > 1}">
				<a class="pagination-item" href="subjects?page=1&search=${search}">
					First </a>
				<a class="pagination-item"
					href="subjects?page=${currentPage - 1}&search=${search}">
					&laquo; </a>
			</c:if>

			<c:if test="${currentPage > 3}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:forEach begin="${currentPage - 2 < 1 ? 1 : currentPage - 2}"
				end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}"
				var="i">
				<a href="subjects?page=${i}&search=${search}"
					class="pagination-item ${currentPage == i ? 'active' : ''}">
					${i} </a>
			</c:forEach>

			<c:if test="${currentPage < totalPages - 2}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:if test="${currentPage < totalPages}">
				<a class="pagination-item"
					href="subjects?page=${currentPage + 1}&search=${search}">
					&raquo; </a>
				<a class="pagination-item"
					href="subjects?page=${totalPages}&search=${search}"> Last </a>
			</c:if>
		</div>
	</div>

	<div class="modal fade" id="subjectDeleteModal" tabindex="-1"
		aria-labelledby="subjectDeleteModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow">
				<div class="modal-header bg-danger text-white">
					<h5 class="modal-title fw-semibold" id="subjectDeleteModalLabel">
						<i class="bi bi-exclamation-triangle-fill me-2"></i> Xác nhận xóa
					</h5>
					<button type="button" class="btn-close btn-close-white"
						data-bs-dismiss="modal" aria-label="Đóng"></button>
				</div>
				<div class="modal-body" id="subjectDeleteModalBody">Bạn có
					chắc muốn xóa môn học này?</div>
				<div class="modal-footer bg-light">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Hủy</button>
					<button type="button" class="btn btn-danger"
						id="confirmDeleteButton">Xóa</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Import Excel Modal -->
	<div class="modal fade" id="subjectImportModal" tabindex="-1"
		aria-labelledby="subjectImportModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow">
				<form action="${pageContext.request.contextPath}/subjects/import"
					method="post" enctype="multipart/form-data">
					<div class="modal-header bg-success text-white">
						<h5 class="modal-title fw-semibold" id="subjectImportModalLabel">
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
							đề ở dòng đầu tiên. Cột 1: Mã môn học (tối đa 5 ký tự), Cột 2:
							Tên môn học.
							<div class="mt-2">
								<a href="${pageContext.request.contextPath}/subjects/import/template" class="text-primary fw-medium text-decoration-none">
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

	<%@ include file="../Shared/_LayoutEnd.jsp"%>