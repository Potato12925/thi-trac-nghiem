<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Quên mật khẩu</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/assets/css/theme.css"
	rel="stylesheet" />

<style>
body {
	min-height: 100vh;
	background: linear-gradient(135deg, var(--oqs-primary) 0%,
		var(--oqs-secondary) 100%);
	display: flex;
	align-items: center;
}

.auth-page {
	display: flex;
	align-items: center;
	min-height: 100vh;
}

.auth-card {
	border-radius: 1rem;
	box-shadow: 0 10px 40px rgba(13, 110, 253, .2);
	border: 1px solid var(--oqs-light);
	background: var(--oqs-white);
}

.icon-circle {
	width: 80px;
	height: 80px;
	border-radius: 50%;
	background: var(--oqs-white);
	color: var(--oqs-primary);
	display: grid;
	place-items: center;
	font-size: 2rem;
}

.text-gradient {
	background: linear-gradient(135deg, var(--oqs-primary) 0%,
		var(--oqs-secondary) 100%);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
}

.input-group-premium {
	display: flex;
	align-items: center;
	position: relative;
	margin-bottom: 1rem;
}

.input-icon {
	position: absolute;
	left: 12px;
	color: var(--oqs-muted);
}

.form-control-premium {
	border: 1px solid #d9e4ff;
	border-radius: .5rem;
	padding: .75rem 1rem .75rem 2.5rem;
	width: 100%;
	color: var(--oqs-text);
	background: var(--oqs-white);
}

.form-control-premium:focus {
	border-color: var(--oqs-secondary);
	outline: none;
	box-shadow: 0 0 0 .2rem rgba(43, 140, 255, .15);
}

.btn-action {
	padding: .75rem 1.5rem;
	border-radius: .5rem;
	font-weight: 600;
	transition: all .3s;
}

.btn-action-primary {
	background: var(--oqs-primary);
	color: var(--oqs-white);
	border: none;
}

.btn-action-primary:hover {
	background: var(--oqs-secondary);
	color: var(--oqs-white);
	transform: translateY(-2px);
}

.auth-subtitle {
	color: var(--oqs-muted);
}

.max-w-md {
	max-width: 420px;
}
</style>
</head>
<body>
	<div class="auth-page">
		<div class="container">
			<div class="row align-items-center g-0">
				<div class="col-lg-7 d-none d-lg-block">
					<div class="pe-lg-5">
						<div class="icon-circle mb-4">
							<i class="bi bi-shield-lock-fill"></i>
						</div>
						<h2 class="display-5 fw-bold text-white mb-3">Khôi phục mật khẩu an toàn</h2>
						<p class="fs-5 text-white text-opacity-75 max-w-md">Nhập tên đăng nhập để hệ thống gửi mã OTP đến email đã đăng ký của bạn.</p>
					</div>
				</div>

				<div class="col-lg-5">
					<div class="auth-card">
						<div class="card-body p-4">
							<div class="text-center mb-4">
								<h1 class="h4 fw-bold text-gradient mb-2">Quên mật khẩu</h1>
								<p class="small auth-subtitle">Chúng tôi sẽ gửi mã OTP gồm 6 chữ số qua email</p>
							</div>

							<c:if test="${not empty error}">
								<div class="alert alert-danger border-0 mb-4 py-3 d-flex align-items-center gap-2">
									<i class="bi bi-exclamation-triangle-fill"></i>
									<span class="small fw-medium">${error}</span>
								</div>
							</c:if>

							<form:form method="post"
								action="${pageContext.request.contextPath}/auth/forgot-password"
								modelAttribute="forgotPasswordForm">
								<div class="mb-4">
									<label for="username" class="form-label small fw-bold">Tên đăng nhập</label>
									<div class="input-group-premium">
										<i class="bi bi-person input-icon"></i>
										<form:input path="username" id="username" cssClass="form-control-premium"
											placeholder="Nhập tên đăng nhập..." />
									</div>
									<form:errors path="username" cssClass="text-danger small" />
								</div>

								<button type="submit" class="btn btn-action btn-action-primary w-100 py-3">
									Gửi mã OTP <i class="bi bi-envelope-paper ms-2"></i>
								</button>
							</form:form>

							<div class="text-center mt-4">
								<a href="${pageContext.request.contextPath}/auth/login"
									class="small text-decoration-none">Quay lại đăng nhập</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
