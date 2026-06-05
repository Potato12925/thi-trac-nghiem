<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Quản lý tài khoản");
request.setAttribute("customCss", "management.css");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<style>
.account-page .summary-card,
.account-page .creation-card {
	border-radius: 20px;
	border: 1px solid #e8edf5;
	box-shadow: 0 12px 30px rgba(15, 23, 42, 0.06);
}

.account-page .summary-card {
	background: linear-gradient(135deg, #0b5ed7 0%, #2563eb 55%, #38bdf8 100%);
	color: #fff;
}

.account-page .summary-card .muted {
	color: rgba(255, 255, 255, 0.8);
}

.account-page .badge-soft {
	display: inline-flex;
	align-items: center;
	gap: 0.35rem;
	padding: 0.45rem 0.7rem;
	border-radius: 999px;
	font-size: 0.78rem;
	font-weight: 600;
	background: rgba(255, 255, 255, 0.14);
}

.account-page .role-badge {
	min-width: 102px;
}

.account-page .table-note {
	font-size: 0.82rem;
	color: #64748b;
}

.account-page .empty-state {
	padding: 2.5rem 1rem;
	text-align: center;
	color: #64748b;
}

.account-page .modal .form-control,
.account-page .modal .form-select {
	height: 46px;
}
</style>

<div class="container-fluid page-wrapper account-page">
	<div class="summary-card card border-0 mb-4">
		<div class="card-body p-4 p-lg-5">
			<div class="d-flex flex-column flex-lg-row justify-content-between gap-4">
				<div>
					<h2 class="h3 mb-2">Quản lý tài khoản hệ thống</h2>
					<p class="mb-0 muted">
						Theo dõi và quản lý tài khoản
					</p>
				</div>
				<div class="text-lg-end">
					<div class="small muted">Tài khoản đăng nhập</div>
					<div class="fs-5 fw-semibold">${sessionScope.LOGIN_USER}</div>
					<div class="small muted mt-2">Tổng số tài khoản hiển thị</div>
					<div class="display-6 fw-bold">${accounts.size()}</div>
				</div>
			</div>
		</div>
	</div>

	<c:if test="${not empty successMessage}">
		<div class="alert alert-success">${successMessage}</div>
	</c:if>

	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger">${errorMessage}</div>
	</c:if>

	<div class="card creation-card border-0 mb-4">
		<div class="card-body p-4">
			<form method="get" action="${pageContext.request.contextPath}/admin/accounts"
				class="row g-3 align-items-end">
				<div class="col-md-4">
					<label class="form-label small text-secondary">Lọc theo loại tài khoản</label>
					<select name="role" class="form-select">
						<option value="">Tất cả role</option>
						<c:forEach var="roleOption" items="${roleOptions}">
							<option value="${roleOption}"
								${accountFilter.role eq roleOption ? 'selected' : ''}>${roleOption}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-6">
					<label class="form-label small text-secondary">Tìm theo mã tài khoản</label>
					<input type="text" name="keyword" class="form-control"
						value="${accountFilter.keyword}" placeholder="Ví dụ: PGV00001, TH101, 001" />
				</div>
				<div class="col-md-2">
					<button type="submit" class="btn btn-dark w-100">
						<i class="bi bi-search me-2"></i>Lọc
					</button>
				</div>
			</form>
		</div>
	</div>

	<div class="row g-4 mb-4">
		<div class="col-12 col-xl-5">
			<div class="card creation-card border-0 h-100">
				<div class="card-body p-4">
					<div class="d-flex align-items-center gap-3 mb-3">
						<div class="bg-dark text-white rounded-circle d-inline-flex align-items-center justify-content-center"
							style="width: 48px; height: 48px;">
							<i class="bi bi-person-plus"></i>
						</div>
						<div>
							<h3 class="h5 mb-1">Tạo tài khoản phòng giáo vụ</h3>
						</div>
					</div>

					<form:form method="post"
						action="${pageContext.request.contextPath}/admin/accounts/create-pgv"
						modelAttribute="createPgvAccountDTO" class="row g-3">
						<input type="hidden" name="filterRole" value="${accountFilter.role}" />
						<input type="hidden" name="keyword" value="${accountFilter.keyword}" />

						<div class="col-12">
							<label class="form-label small text-secondary">Mã tài khoản phòng giáo vụ</label>
							<form:input path="username" cssClass="form-control" placeholder="Ví dụ: PGV00002" />
							<form:errors path="username" cssClass="text-danger small mt-1 d-block" />
						</div>

						<div class="col-12">
							<label class="form-label small text-secondary">Mật khẩu</label>
							<form:password path="password" cssClass="form-control" placeholder="Nhập mật khẩu khởi tạo" />
							<form:errors path="password" cssClass="text-danger small mt-1 d-block" />
						</div>

						<div class="col-12">
							<button type="submit" class="btn btn-dark w-100">
								<i class="bi bi-shield-plus me-2"></i>Tạo tài khoản phòng giáo vụ
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>

		<div class="col-12 col-xl-7">
			<div class="card creation-card border-0 h-100">
				<div class="card-body p-4">
					<div class="d-flex align-items-center gap-3 mb-3">
						<div class="bg-primary text-white rounded-circle d-inline-flex align-items-center justify-content-center"
							style="width: 48px; height: 48px;">
							<i class="bi bi-person-badge"></i>
						</div>
						<div>
							<h3 class="h5 mb-1">Tạo tài khoản giảng viên</h3>
							<p class="text-muted mb-0">Chỉ hiển thị giảng viên tồn tại thật và chưa có tài khoản trong hệ thống.</p>
						</div>
					</div>

					<form:form method="post"
						action="${pageContext.request.contextPath}/admin/accounts/create-lecturer"
						modelAttribute="createLecturerAccountDTO" class="row g-3">
						<input type="hidden" name="filterRole" value="${accountFilter.role}" />
						<input type="hidden" name="keyword" value="${accountFilter.keyword}" />

						<div class="col-md-8">
							<label class="form-label small text-secondary">Giảng viên chưa có tài khoản</label>
							<form:select path="lecturerId" cssClass="form-select">
								<form:option value="" label="Chọn giảng viên" />
								<c:forEach var="lecturer" items="${availableLecturers}">
									<form:option value="${lecturer.lecturerId}">
										${lecturer.lecturerId} - ${lecturer.lastName} ${lecturer.firstName}
										<c:if test="${not empty lecturer.email}">
											(${lecturer.email})
										</c:if>
									</form:option>
								</c:forEach>
							</form:select>
							<form:errors path="lecturerId" cssClass="text-danger small mt-1 d-block" />
							<c:if test="${empty availableLecturers}">
								<div class="table-note mt-2">Tất cả giảng viên đã có tài khoản.</div>
							</c:if>
						</div>

						<div class="col-md-4">
							<label class="form-label small text-secondary">Mật khẩu khởi tạo</label>
							<form:password path="password" cssClass="form-control" placeholder="Tối thiểu 6 ký tự" />
							<form:errors path="password" cssClass="text-danger small mt-1 d-block" />
						</div>

						<div class="col-12">
							<button type="submit" class="btn btn-primary w-100" ${empty availableLecturers ? 'disabled' : ''}>
								<i class="bi bi-person-workspace me-2"></i>Tạo tài khoản giảng viên
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

	<div class="card border-0 shadow-sm management-card">
		<div class="card-body p-4 border-bottom">
			<div class="d-flex flex-column flex-md-row justify-content-between gap-2">
				<div>
					<h3 class="h5 mb-1">Danh sách tài khoản trong hệ thống</h3>
				</div>
				<div class="table-note">
					<i class="bi bi-info-circle me-1"></i> Tài khoản sinh viên chỉ hỗ trợ reset mật khẩu và gửi email tại màn hình này.
				</div>
			</div>
		</div>

		<div class="table-responsive management-table-wrapper">
			<table class="table table-hover align-middle mb-0 management-table">
				<thead class="table-light">
					<tr>
						<th>Mã tài khoản</th>
						<th>Role</th>
						<th>Loại tài khoản</th>
						<th>Họ tên hiển thị</th>
						<th>Email</th>
						<th class="text-end">Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty accounts}">
							<c:forEach var="account" items="${accounts}">
								<tr>
									<td class="fw-semibold">${account.username}</td>
									<td>
										<span class="badge role-badge
											${account.role eq 'PGV' ? 'text-bg-primary' : ''}
											${account.role eq 'GIAOVIEN' ? 'text-bg-success' : ''}
											${account.role eq 'SINHVIEN' ? 'text-bg-secondary' : ''}">
											${account.role}
										</span>
									</td>
									<td>
										<div class="fw-medium">${account.accountType}</div>
									</td>
									<td>${account.displayName}</td>
									<td>
										<c:choose>
											<c:when test="${not empty account.email}">${account.email}</c:when>
											<c:otherwise><span class="text-muted">Chưa có email</span></c:otherwise>
										</c:choose>
									</td>
									<td class="text-end">
										<div class="d-inline-flex gap-2 flex-wrap justify-content-end">
											<c:if test="${account.canResetPassword}">
												<button type="button" class="btn btn-sm btn-outline-warning"
													data-bs-toggle="modal" data-bs-target="#resetPasswordModal"
													data-username="${account.username}">
													<i class="bi bi-key"></i>
												</button>
											</c:if>

											<c:if test="${account.canUpdateRole}">
												<button type="button" class="btn btn-sm btn-outline-primary"
													data-bs-toggle="modal" data-bs-target="#updateRoleModal"
													data-username="${account.username}" data-role="${account.role}">
													<i class="bi bi-arrow-repeat"></i>
												</button>
											</c:if>

											<c:if test="${account.canDelete}">
												<button type="button" class="btn btn-sm btn-outline-danger"
													data-bs-toggle="modal" data-bs-target="#deleteAccountModal"
													data-username="${account.username}" data-role="${account.role}">
													<i class="bi bi-trash"></i>
												</button>
											</c:if>
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6" class="empty-state">
									<i class="bi bi-inboxes fs-2 d-block mb-2"></i>
									Không tìm thấy tài khoản nào phù hợp với bộ lọc hiện tại.
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</div>

<div class="modal fade" id="resetPasswordModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content border-0 shadow">
			<form:form method="post"
				action="${pageContext.request.contextPath}/admin/accounts/reset-password"
				modelAttribute="adminResetPasswordDTO">
				<input type="hidden" name="filterRole" value="${accountFilter.role}" />
				<input type="hidden" name="keyword" value="${accountFilter.keyword}" />
				<div class="modal-header bg-warning-subtle">
					<h5 class="modal-title">
						<i class="bi bi-key me-2"></i>Đặt lại mật khẩu
					</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
				</div>
				<div class="modal-body">
					<p class="text-muted mb-3">
						Hệ thống sẽ băm mật khẩu mới theo cơ chế hiện tại và gửi email chứa mã tài khoản, mật khẩu mới cho giảng viên hoặc sinh viên.
					</p>

					<div class="mb-3">
						<label class="form-label small text-secondary">Mã tài khoản</label>
						<form:input path="username" cssClass="form-control" id="resetPasswordUsername" readonly="true" />
						<form:errors path="username" cssClass="text-danger small mt-1 d-block" />
					</div>

					<div class="mb-3">
						<label class="form-label small text-secondary">Mật khẩu mới</label>
						<form:password path="newPassword" cssClass="form-control" placeholder="Tối thiểu 6 ký tự" />
						<form:errors path="newPassword" cssClass="text-danger small mt-1 d-block" />
					</div>

					<div>
						<label class="form-label small text-secondary">Xác nhận mật khẩu</label>
						<form:password path="confirmPassword" cssClass="form-control" placeholder="Nhập lại mật khẩu mới" />
						<form:errors path="confirmPassword" cssClass="text-danger small mt-1 d-block" />
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Hủy</button>
					<button type="submit" class="btn btn-warning">Reset và gửi email</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<div class="modal fade" id="updateRoleModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content border-0 shadow">
			<form:form method="post"
				action="${pageContext.request.contextPath}/admin/accounts/update-role"
				modelAttribute="updateAccountRoleDTO">
				<input type="hidden" name="filterRole" value="${accountFilter.role}" />
				<input type="hidden" name="keyword" value="${accountFilter.keyword}" />
				<div class="modal-header bg-primary-subtle">
					<h5 class="modal-title">
						<i class="bi bi-arrow-repeat me-2"></i>Cập nhật role
					</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
				</div>
				<div class="modal-body">
					<p class="text-muted mb-3">
						Chỉ tài khoản đang có role `PGV` và có dữ liệu nền hợp lệ mới đổi được sang role khác.
					</p>

					<div class="mb-3">
						<label class="form-label small text-secondary">Mã tài khoản</label>
						<form:input path="username" cssClass="form-control" id="updateRoleUsername" readonly="true" />
						<form:errors path="username" cssClass="text-danger small mt-1 d-block" />
					</div>

					<div>
						<label class="form-label small text-secondary">Role mới</label>
						<form:select path="role" cssClass="form-select" id="updateRoleValue">
							<c:forEach var="roleOption" items="${roleOptions}">
								<form:option value="${roleOption}">${roleOption}</form:option>
							</c:forEach>
						</form:select>
						<form:errors path="role" cssClass="text-danger small mt-1 d-block" />
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Hủy</button>
					<button type="submit" class="btn btn-primary">Cập nhật role</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<div class="modal fade" id="deleteAccountModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content border-0 shadow">
			<form method="post" action="${pageContext.request.contextPath}/admin/accounts/delete">
				<input type="hidden" name="filterRole" value="${accountFilter.role}" />
				<input type="hidden" name="keyword" value="${accountFilter.keyword}" />
				<input type="hidden" name="deleteUsername" id="deleteUsernameInput" />
				<div class="modal-header bg-danger-subtle">
					<h5 class="modal-title">
						<i class="bi bi-trash me-2"></i>Xác nhận xóa tài khoản
					</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
				</div>
				<div class="modal-body">
					<p class="mb-2">Bạn có chắc muốn xóa tài khoản <strong id="deleteUsernameLabel"></strong> không?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Hủy</button>
					<button type="submit" class="btn btn-danger">Xóa tài khoản</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	document.addEventListener('DOMContentLoaded', function() {
		var resetModal = document.getElementById('resetPasswordModal');
		if (resetModal) {
			resetModal.addEventListener('show.bs.modal', function(event) {
				var button = event.relatedTarget;
				if (!button) {
					return;
				}

				var username = button.getAttribute('data-username') || '';
				var input = document.getElementById('resetPasswordUsername');
				if (input) {
					input.value = username;
				}
			});
		}

		var roleModal = document.getElementById('updateRoleModal');
		if (roleModal) {
			roleModal.addEventListener('show.bs.modal', function(event) {
				var button = event.relatedTarget;
				if (!button) {
					return;
				}

				var username = button.getAttribute('data-username') || '';
				var role = button.getAttribute('data-role') || 'PGV';

				var usernameInput = document.getElementById('updateRoleUsername');
				var roleInput = document.getElementById('updateRoleValue');

				if (usernameInput) {
					usernameInput.value = username;
				}

				if (roleInput) {
					roleInput.value = role;
				}
			});
		}

		var deleteModal = document.getElementById('deleteAccountModal');
		if (deleteModal) {
			deleteModal.addEventListener('show.bs.modal', function(event) {
				var button = event.relatedTarget;
				if (!button) {
					return;
				}

				var username = button.getAttribute('data-username') || '';
				var input = document.getElementById('deleteUsernameInput');
				var label = document.getElementById('deleteUsernameLabel');

				if (input) {
					input.value = username;
				}

				if (label) {
					label.textContent = username;
				}
			});
		}

		var openModalId = '${openModal}';
		if (openModalId) {
			var openModalElement = document.getElementById(openModalId);
			if (openModalElement) {
				var modal = new bootstrap.Modal(openModalElement);
				modal.show();
			}
		}
	});
</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
