<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Quản lý Lớp");
request.setAttribute("customCss", "management.css");
request.setAttribute("customJs", "classroom-analytics.js");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<!-- Chart.js CDN for data visualization -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<div class="container-fluid page-wrapper">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="h3 mb-0">Class Room Management</h1>
		<div class="d-flex gap-2">
			<a href="${pageContext.request.contextPath}/classrooms/export"
				class="btn btn-outline-success d-flex align-items-center gap-2">
				<i class="bi bi-file-earmark-excel"></i> Xuất Excel
			</a>
			<button type="button"
				class="btn btn-success d-flex align-items-center gap-2"
				data-bs-toggle="modal" data-bs-target="#classroomImportModal">
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

		<form:form id="classRoomForm"
			action="${pageContext.request.contextPath}/classrooms/add"
			method="post" modelAttribute="classroomDTO">

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

				<button type="button" class="btn btn-outline-dark px-4"
					id="btnReset">Xóa dữ liệu</button>
			</div>
		</form:form>
	</div>

	<form id="saveForm"
		action="${pageContext.request.contextPath}/classrooms/save"
		method="post" class="d-none">
		<input type="hidden" name="page" value="${currentPage}" /> <input
			type="hidden" name="actionsData" id="actionsDataInput" />
	</form>

	<div class="card border-0 shadow-sm management-card">
		<div class="table-responsive p-3 management-table-wrapper">
			<table class="table table-hover align-middle mb-0 management-table">
				<thead class="table-light">
					<tr>
						<th scope="col">Class ID</th>

						<th scope="col">Class Name</th>

						<th scope="col" class="text-end">Actions</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="cr" items="${classrooms}">

						<tr>

							<td>${cr.classId}</td>

							<td>${cr.className}</td>

							<td class="text-end pe-4">
								<button type="button"
									class="btn btn-sm btn-outline-primary me-2 btn-analytics"
									data-class-id="${cr.classId}" data-class-name="${cr.className}">
									<i class="bi bi-bar-chart-line"></i> Phân tích
								</button>
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
				<a class="pagination-item" href="classrooms?page=1"> First </a>

				<a class="pagination-item" href="classrooms?page=${currentPage - 1}">
					&laquo; </a>
			</c:if>

			<c:if test="${currentPage > 3}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:forEach begin="${currentPage - 2 < 1 ? 1 : currentPage - 2}"
				end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}"
				var="i">
				<a href="classrooms?page=${i}"
					class="pagination-item ${currentPage == i ? 'active' : ''}">
					${i} </a>
			</c:forEach>

			<c:if test="${currentPage < totalPages - 2}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:if test="${currentPage < totalPages}">
				<a class="pagination-item" href="classrooms?page=${currentPage + 1}">
					&raquo; </a>

				<a class="pagination-item" href="classrooms?page=${totalPages}">
					Last </a>
			</c:if>
		</div>
	</div>
	<!-- Import Excel Modal -->
	<div class="modal fade" id="classroomImportModal" tabindex="-1"
		aria-labelledby="classroomImportModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content border-0 shadow">
				<form action="${pageContext.request.contextPath}/classrooms/import"
					method="post" enctype="multipart/form-data">
					<div class="modal-header bg-success text-white">
						<h5 class="modal-title fw-semibold" id="classroomImportModalLabel">
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
							đề ở dòng đầu tiên. Cột 1: Mã lớp (tối đa 15 ký tự), Cột 2: Tên
							lớp.
							<div class="mt-2">
								<a href="${pageContext.request.contextPath}/classrooms/import/template" class="text-primary fw-medium text-decoration-none">
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

	<script>
document.addEventListener("DOMContentLoaded", function () {
  const btnAdd = document.getElementById("btnAdd");
  const btnUpdate = document.getElementById("btnUpdate");
  const btnDelete = document.getElementById("btnDelete");
  const btnUndo = document.getElementById("btnUndo");
  const btnSave = document.getElementById("btnSave");
  const btnReset = document.getElementById("btnReset");
  const tableBody = document.querySelector(".table-responsive table tbody");

  // Add click listeners to form buttons
  if (btnAdd) btnAdd.addEventListener("click", handleLocalAdd);
  if (btnUpdate) btnUpdate.addEventListener("click", handleLocalUpdate);
  if (btnDelete) btnDelete.addEventListener("click", handleLocalDelete);
  if (btnUndo) btnUndo.addEventListener("click", handleUndo);
  if (btnSave) btnSave.addEventListener("click", handleSave);
  if (btnReset) btnReset.addEventListener("click", resetForm);

  // Use event delegation on table body for Edit button
  if (tableBody) {
    tableBody.addEventListener("click", function (e) {
      const editBtn = e.target.closest(".btn-edit");
      if (editBtn) {
        const row = editBtn.closest("tr");
        fillFormFromRow(row);
        enableActionButtons();
        hideClientError();
      }
    });
  }

  // Warn about unsaved changes when navigating away
  window.addEventListener("beforeunload", function (e) {
    if (pendingActions.length > 0) {
      e.preventDefault();
      e.returnValue = "Bạn có thay đổi chưa được ghi xuống CSDL. Bạn có chắc chắn muốn rời đi?";
      return e.returnValue;
    }
  });
});

let pendingActions = [];
let activeRow = null;

function findRowById(classId) {
  const rows = document.querySelectorAll(".table-responsive table tbody tr");
  for (let row of rows) {
    const cells = row.querySelectorAll("td");
    if (cells.length > 0 && cells[0].innerText.trim() === classId) {
      return row;
    }
  }
  return null;
}

function fillFormFromRow(row) {
  const cells = row.querySelectorAll("td");
  const classId = cells[0].innerText.trim();
  const className = cells[1].innerText.trim();

  document.getElementById("classId").value = classId;
  document.getElementById("className").value = className;
  document.getElementById("classId").readOnly = true;
  setActiveRow(row);
}

function setActiveRow(row) {
  if (activeRow) {
    activeRow.classList.remove("table-primary");
  }
  activeRow = row;
  row.classList.add("table-primary");
}

function enableActionButtons() {
  const btnUpdate = document.getElementById("btnUpdate");
  const btnDelete = document.getElementById("btnDelete");
  if (btnUpdate) btnUpdate.disabled = false;
  if (btnDelete) btnDelete.disabled = false;
}

function resetForm() {
  const form = document.getElementById("classRoomForm");
  if (form) form.reset();

  const classIdInput = document.getElementById("classId");
  if (classIdInput) {
    classIdInput.readOnly = false;
  }

  if (activeRow) {
    activeRow.classList.remove("table-primary");
    activeRow = null;
  }

  const btnUpdate = document.getElementById("btnUpdate");
  const btnDelete = document.getElementById("btnDelete");
  if (btnUpdate) btnUpdate.disabled = true;
  if (btnDelete) btnDelete.disabled = true;

  hideClientError();
}

function showClientError(msg) {
  const alertDiv = document.getElementById("clientAlert");
  if (alertDiv) {
    alertDiv.innerText = msg;
    alertDiv.classList.remove("d-none");
    window.scrollTo({ top: 0, behavior: "smooth" });
  }
}

function hideClientError() {
  const alertDiv = document.getElementById("clientAlert");
  if (alertDiv) {
    alertDiv.classList.add("d-none");
  }
}

function updateUIState() {
  const unsavedChangesAlert = document.getElementById("unsavedChangesAlert");
  const unsavedCount = document.getElementById("unsavedCount");
  const btnUndo = document.getElementById("btnUndo");
  const btnSave = document.getElementById("btnSave");

  if (unsavedCount) unsavedCount.innerText = pendingActions.length;

  if (pendingActions.length > 0) {
    if (unsavedChangesAlert) unsavedChangesAlert.classList.remove("d-none");
    if (btnUndo) btnUndo.disabled = false;
    if (btnSave) btnSave.disabled = false;
  } else {
    if (unsavedChangesAlert) unsavedChangesAlert.classList.add("d-none");
    if (btnUndo) btnUndo.disabled = true;
    if (btnSave) btnSave.disabled = true;
  }
}

function handleLocalAdd() {
  const classIdInput = document.getElementById("classId");
  const classNameInput = document.getElementById("className");

  const classId = classIdInput.value.trim();
  const className = classNameInput.value.trim();

  // Client-side Validations
  if (!classId) {
    showClientError("Mã lớp không được để trống");
    return;
  }
  if (!className) {
    showClientError("Tên lớp không được để trống");
    return;
  }

  // Check duplicate ID in active rows
  const existingRow = findRowById(classId);
  if (existingRow && existingRow.style.display !== "none") {
    showClientError("Mã lớp đã tồn tại");
    return;
  }

  hideClientError();

  // Stage ADD action
  pendingActions.push({
    type: "ADD",
    classId: classId,
    className: className
  });

  // Update DOM: Insert new row at the top
  const tableBody = document.querySelector(".table-responsive table tbody");
  if (tableBody) {
    const tr = document.createElement("tr");
    tr.classList.add("table-success");
    tr.setAttribute("data-local-added", "true");
    tr.innerHTML = `
      <td>\${classId}</td>
      <td>\${className}</td>
      <td class="text-end pe-4">
        <button class="btn btn-sm btn-outline-secondary me-2 btn-edit">
          <i class="bi bi-pencil"></i>
        </button>
      </td>
    `;
    tableBody.insertBefore(tr, tableBody.firstChild);
  }

  resetForm();
  updateUIState();
}

function handleLocalUpdate() {
  const classId = document.getElementById("classId").value.trim();
  const className = document.getElementById("className").value.trim();

  if (!className) {
    showClientError("Tên lớp không được để trống");
    return;
  }

  const row = findRowById(classId);
  if (!row) {
    showClientError("Không tìm thấy dòng lớp tương ứng");
    return;
  }

  hideClientError();

  const cells = row.querySelectorAll("td");
  const oldClassName = cells[1].innerText.trim();

  if (className === oldClassName) {
    resetForm();
    return; // No change
  }

  // Stage UPDATE action
  pendingActions.push({
    type: "UPDATE",
    classId: classId,
    className: className,
    oldClassName: oldClassName
  });

  // Update DOM
  cells[1].innerText = className;
  if (!row.hasAttribute("data-local-added")) {
    row.classList.add("table-warning");
  }

  resetForm();
  updateUIState();
}

function handleLocalDelete() {
  const classId = document.getElementById("classId").value.trim();
  const row = findRowById(classId);
  if (!row) return;

  const cells = row.querySelectorAll("td");
  const className = cells[1].innerText.trim();

  if (!confirm("Bạn có chắc chắn muốn xóa lớp " + className + "?")) {
    return;
  }

  // Stage DELETE action
  pendingActions.push({
    type: "DELETE",
    classId: classId,
    className: className
  });

  // Update DOM
  row.style.display = "none";

  resetForm();
  updateUIState();
}

function handleUndo() {
  if (pendingActions.length === 0) return;

  const action = pendingActions.pop();
  const row = findRowById(action.classId);

  if (action.type === "ADD") {
    if (row && row.hasAttribute("data-local-added")) {
      row.remove();
    }
  } else if (action.type === "UPDATE") {
    if (row) {
      const cells = row.querySelectorAll("td");
      cells[1].innerText = action.oldClassName;

      let hasOtherUpdates = false;
      for (let act of pendingActions) {
        if (act.classId === action.classId && act.type === "UPDATE") {
          hasOtherUpdates = true;
          break;
        }
      }
      if (!hasOtherUpdates && !row.hasAttribute("data-local-added")) {
        row.classList.remove("table-warning");
      }
    }
  } else if (action.type === "DELETE") {
    if (row) {
      row.style.display = "";
    }
  }

  updateUIState();
  hideClientError();
}

function handleSave() {
  if (pendingActions.length === 0) return;

  // Serialize to simple text format: type:::classId:::className\n
  let dataStr = "";
  pendingActions.forEach(function (act) {
    dataStr += act.type + ":::" + act.classId + ":::" + act.className + "\n";
  });

  const actionsDataInput = document.getElementById("actionsDataInput");
  const saveForm = document.getElementById("saveForm");

  if (actionsDataInput && saveForm) {
    pendingActions = []; // Clear pending actions
    actionsDataInput.value = dataStr;
    saveForm.submit();
  }
}
</script>

	<!-- Classroom Analytics Modal -->
	<div class="modal fade" id="classroomAnalyticsModal" tabindex="-1"
		aria-labelledby="classroomAnalyticsModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered">
			<div class="modal-content border-0 shadow">
				<div class="modal-header bg-dark text-white">
					<h5 class="modal-title fw-semibold"
						id="classroomAnalyticsModalLabel">
						<i class="bi bi-bar-chart-line-fill me-2"></i> Phân tích kết quả
						học tập lớp: <span id="anClassNameHeader">Lớp</span>
					</h5>
					<button type="button" class="btn-close btn-close-white"
						data-bs-dismiss="modal" aria-label="Đóng"></button>
				</div>
				<div class="modal-body p-4">
					<div class="row g-3 mb-4">
						<div class="col-md-5">
							<label class="form-label small text-secondary fw-semibold">Chọn
								Môn Học</label> <select id="anSubjectSelect" class="form-select"></select>
						</div>
						<div class="col-md-3">
							<label class="form-label small text-secondary fw-semibold">Chọn
								Lần Thi</label> <select id="anTryNumberSelect" class="form-select">
								<option value="1">Lần 1</option>
								<option value="2">Lần 2</option>
							</select>
						</div>
						<div class="col-md-4 d-flex align-items-end">
							<button type="button" class="btn btn-primary w-100"
								id="btnQueryAnalytics">
								<i class="bi bi-search me-1"></i> Xem thống kê
							</button>
						</div>
					</div>

					<!-- Analytics Content Area -->
					<div id="analyticsContent" class="d-none">
						<!-- KPI cards -->
						<div class="row g-3 mb-4 text-center">
							<div class="col-6 col-lg-3">
								<div class="p-3 border rounded bg-light">
									<div class="text-secondary small">Sĩ số lớp</div>
									<div class="fs-3 fw-bold text-dark" id="anTotalStudents">0</div>
								</div>
							</div>
							<div class="col-6 col-lg-3">
								<div class="p-3 border rounded bg-light">
									<div class="text-secondary small">Đã thi / Chưa thi</div>
									<div class="fs-3 fw-bold text-primary">
										<span id="anAttempted">0</span> / <span id="anNotAttempted">0</span>
									</div>
								</div>
							</div>
							<div class="col-6 col-lg-3">
								<div class="p-3 border rounded bg-light">
									<div class="text-secondary small">Điểm trung bình</div>
									<div class="fs-3 fw-bold text-success" id="anAverageScore">0.0</div>
								</div>
							</div>
							<div class="col-6 col-lg-3">
								<div class="p-3 border rounded bg-light">
									<div class="text-secondary small">Tỷ lệ Đạt (>=4.0)</div>
									<div class="fs-3 fw-bold text-info" id="anPassRate">0%</div>
								</div>
							</div>
						</div>

						<div class="row g-4">
							<!-- Chart Column -->
							<div class="col-lg-7">
								<div class="card border rounded p-3 h-100 mb-0">
									<h6 class="fw-bold mb-3">
										<i class="bi bi-graph-up me-1"></i> Phân phối phổ điểm chữ
									</h6>
									<div style="position: relative; height: 300px;">
										<canvas id="gradeDistributionChart"></canvas>
									</div>
								</div>
							</div>

							<!-- Ranking Column -->
							<div class="col-lg-5">
								<div class="card border rounded p-3 h-100 mb-0">
									<h6 class="fw-bold mb-3 text-warning">
										<i class="bi bi-trophy me-1"></i> Top 5 sinh viên điểm cao
										nhất
									</h6>
									<div class="table-responsive">
										<table class="table table-sm table-hover align-middle">
											<thead class="table-light">
												<tr>
													<th>Hạng</th>
													<th>Mã SV</th>
													<th>Họ và tên</th>
													<th class="text-end">Điểm</th>
												</tr>
											</thead>
											<tbody id="anTopStudentsBody">
												<tr>
													<td colspan="4" class="text-center text-muted">Chưa có
														dữ liệu thi.</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Loading Spinner -->
					<div id="analyticsLoading" class="text-center py-5 d-none">
						<div class="spinner-border text-primary" role="status">
							<span class="visually-hidden">Loading...</span>
						</div>
						<div class="text-secondary mt-2">Đang xử lý dữ liệu học
							tập...</div>
					</div>

					<!-- Error Alert -->
					<div id="analyticsError" class="alert alert-danger mt-3 d-none"></div>
				</div>
				<div class="modal-footer bg-light">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Đóng</button>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../Shared/_LayoutEnd.jsp"%>