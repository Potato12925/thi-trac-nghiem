<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setAttribute("pageTitle", "Quản lý Môn học");
%>
<%@ include file="../Shared/_LayoutStart.jsp"%>
<div class="container-fluid">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Danh mục Môn học</h1>
	</div>

	<div class="card border-0 shadow-sm mb-4 p-3">
		<c:if test="${not empty successMessage}">
			<div class="alert alert-success alert-dismissible fade show"
				role="alert">
				${successMessage}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				${errorMessage}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
		</c:if>
		<c:if test="${not empty infoMessage}">
			<div class="alert alert-info alert-dismissible fade show"
				role="alert">
				${infoMessage}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
		</c:if>

		<form id="subjectForm"
			action="${pageContext.request.contextPath}/subject/save"
			method="post" class="row g-3 align-items-end">
			<div class="col-md-2">
				<label class="form-label text-sm fw-medium">Mã MH</label> <input
					type="text" name="maMH" class="form-control form-control-sm"
					placeholder="MAMH" value="${subject.maMH}" />
			</div>
			<div class="col-md-4">
				<label class="form-label text-sm fw-medium">Tên Môn học</label> <input
					type="text" name="tenMH" class="form-control form-control-sm"
					placeholder="Nhập tên môn học" value="${subject.tenMH}" />
			</div>
			<div class="col-md-6">
				<div class="d-flex flex-wrap gap-2 justify-content-md-end">
					<button class="btn btn-primary" type="button"
						onclick="addPendingSubject()">Thêm</button>
					<button class="btn btn-outline-primary" type="button"
						onclick="document.getElementById('subjectForm').submit()">Hiệu
						chỉnh</button>
					<button class="btn btn-outline-secondary" type="reset">Phục
						hồi</button>
					<button class="btn btn-outline-danger" type="button"
						onclick="submitDelete()">Xóa</button>
					<c:if test="${canUndo}">
						<button class="btn btn-warning" type="submit" form="undoForm">Phục
							hồi thao tác</button>
						<button class="btn btn-success" type="button"
							onclick="submitSave()">Ghi</button>
					</c:if>
				</div>
			</div>
		</form>

		<form id="batchSaveForm"
			action="${pageContext.request.contextPath}/subject/save-batch"
			method="post"></form>

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

		<form id="undoForm"
			action="${pageContext.request.contextPath}/subject/undo"
			method="post" class="d-none"></form>
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
				<tbody id="subjectTableBody">
					<c:forEach items="${subjects}" var="item">
						<tr>
							<td class="fw-medium text-primary">${item.maMH}</td>
							<td>${item.tenMH}</td>
							<td class="text-end"><a
								class="btn btn-sm btn-outline-primary me-1"
								href="${pageContext.request.contextPath}/subject?maMH=${item.maMH}&search=${search}">
									<i class="bi bi-pencil"></i>
							</a>
								<form action="${pageContext.request.contextPath}/subject/delete"
									method="post" class="d-inline">
									<input type="hidden" name="maMH" value="${item.maMH}" />
									<button class="btn btn-sm btn-outline-danger" type="submit">
										<i class="bi bi-trash"></i>
									</button>
								</form></td>
						</tr>
					</c:forEach>
					<c:if test="${empty subjects}">
						<tr id="emptyRow">
							<td colspan="3" class="text-center text-muted">Không có môn
								học nào.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
</div>

<form id="deleteForm"
	action="${pageContext.request.contextPath}/subject/delete"
	method="post">
	<input type="hidden" name="maMH" id="deleteMaMH" />
</form>

<script>
	var pendingSubjects = [];

	function addPendingSubject() {
		var form = document.getElementById('subjectForm');
		var maMH = form.elements['maMH'].value.trim();
		var tenMH = form.elements['tenMH'].value.trim();

		if (!maMH || !tenMH) {
			alert('Cần nhập đầy đủ Mã MH và Tên Môn học trước khi thêm.');
			return;
		}

		if (pendingSubjects.some(function(item) {
			return item.maMH === maMH;
		})) {
			alert('Mã môn học này đã được thêm tạm.');
			return;
		}

		pendingSubjects.push({
			maMH : maMH,
			tenMH : tenMH
		});
		renderPendingSubjects();
		                clearSubjectForm();
	}

	function renderPendingSubjects() {
		var tbody = document.getElementById('subjectTableBody');
		var emptyRow = document.getElementById('emptyRow');

		// Remove existing pending rows
		var existingPending = tbody.querySelectorAll('.pending-row');
		existingPending.forEach(function(row) {
			row.remove();
		});

		// Hide empty message when there are pending rows
		if (emptyRow) {
			emptyRow.style.display = pendingSubjects.length > 0 ? 'none' : '';
		}

		// Append pending rows after server rows
		pendingSubjects
				.forEach(function(item, index) {
					var row = document.createElement('tr');
					row.className = 'table-warning pending-row';
					row.innerHTML = ''
							+ '<td class="fw-medium text-primary">'
							+ item.maMH
							+ '</td>'
							+ '<td>'
							+ item.tenMH
							+ '</td>'
							+ '<td class="text-end">'
							+ '<span class="badge bg-warning text-dark me-2">Chưa lưu</span>'
							+ '<button type="button" class="btn btn-sm btn-outline-danger" onclick="removePendingSubject('
							+ index + ')">' + '<i class="bi bi-x-lg"></i>'
							+ '</button>' + '</td>';
					tbody.appendChild(row);
				});

		updateBatchFormInputs();
	}

	function updateBatchFormInputs() {
		var batchForm = document.getElementById('batchSaveForm');
		batchForm.innerHTML = '';

		pendingSubjects.forEach(function(item) {
			var inputCode = document.createElement('input');
			inputCode.type = 'hidden';
			inputCode.name = 'pendingMaMH';
			inputCode.value = item.maMH;
			batchForm.appendChild(inputCode);

			var inputName = document.createElement('input');
			inputName.type = 'hidden';
			inputName.name = 'pendingTenMH';
			inputName.value = item.tenMH;
			batchForm.appendChild(inputName);
		});
	}

	function removePendingSubject(index) {
		pendingSubjects.splice(index, 1);
		renderPendingSubjects();
	}

	function clearSubjectForm() {
		var form = document.getElementById('subjectForm');
		form.reset();
		form.elements['maMH'].value = '';
		form.elements['tenMH'].value = '';
	}

	function submitSave() {
		if (pendingSubjects.length > 0) {
			document.getElementById('batchSaveForm').submit();
			return;
		}
		document.getElementById('subjectForm').submit();
	}

	function submitDelete() {
		var value = document.getElementById('subjectForm').elements['maMH'].value;
		if (!value) {
			return;
		}
		document.getElementById('deleteMaMH').value = value;
		document.getElementById('deleteForm').submit();
	}
</script>
<%@ include file="../Shared/_LayoutEnd.jsp"%>
