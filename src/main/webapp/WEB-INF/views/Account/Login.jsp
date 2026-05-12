<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "Đăng nhập"); %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Đăng nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet" />
    <style>
        body { min-height: 100vh; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); display: flex; align-items: center; }
        .login-page { display: flex; align-items: center; min-height: 100vh; }
        .login-illustration { padding: 2rem; }
        .icon-circle { width: 80px; height: 80px; border-radius: 50%; background: #667eea; color: #fff; display: grid; place-items: center; font-size: 2rem; }
        .login-card { border-radius: 1rem; box-shadow: 0 10px 40px rgba(0, 0, 0, .2); }
        .text-gradient { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }
        .input-group-premium { display: flex; align-items: center; position: relative; margin-bottom: 1rem; }
        .input-icon { position: absolute; left: 12px; color: #999; }
        .form-control-premium { border: 1px solid #ddd; border-radius: .5rem; padding: .75rem 1rem .75rem 2.5rem; }
        .role-switch-group { display: flex; gap: .5rem; margin-bottom: 2rem; }
        .role-switch-btn { padding: .75rem .75rem; border: 2px solid #ddd; border-radius: .5rem; cursor: pointer; transition: all .3s; text-align: center; }
        .btn-check:checked + .role-switch-btn { background: #667eea; color: #fff; border-color: #667eea; }
        .btn-action { padding: .75rem 1.5rem; border-radius: .5rem; font-weight: 600; transition: all .3s; }
        .btn-action-primary { background: #667eea; color: #fff; border: none; }
        .btn-action-primary:hover { background: #5568d3; transform: translateY(-2px); }
        .max-w-md { max-width: 400px; }
    </style>
</head>
<body>
    <div class="login-page">
        <div class="container">
            <div class="row align-items-center g-0">
                <div class="col-lg-7 d-none d-lg-block">
                    <div class="login-illustration pe-lg-5">
                        <div class="icon-circle mb-4">
                            <i class="bi bi-mortarboard-fill"></i>
                        </div>
                        <h2 class="display-4 fw-bold text-white mb-3">Hệ Thống Quản Lý Đào Tạo</h2>
                        <p class="fs-5 text-white text-opacity-75 max-w-md">
                            Cổng thông tin tích hợp dành cho sinh viên và giảng viên. Quản lý điểm, thời khóa biểu và đăng ký tín chỉ một cách thông minh.
                        </p>
                    </div>
                </div>

                <div class="col-lg-5">
                    <div class="login-card">
                        <div class="card-body p-4">
                            <div class="text-center mb-4">
                                <h1 class="h4 fw-bold text-gradient mb-2">Xin chào trở lại!</h1>
                                <p class="text-muted small">Vui lòng đăng nhập để tiếp tục</p>
                            </div>

                            <c:if test="${not empty error}">
                                <div class="alert alert-danger border-0 mb-4 py-3 d-flex align-items-center gap-2" role="alert">
                                    <i class="bi bi-exclamation-triangle-fill"></i>
                                    <span class="small fw-medium">${error}</span>
                                </div>
                            </c:if>

                            <form method="post" action="${pageContext.request.contextPath}/login">
                                <div class="role-switch-group mb-4">
                                    <div class="flex-grow-1">
                                        <input type="radio" class="btn-check" name="role" id="rolePGV" value="PGV" checked>
                                        <label class="role-switch-btn d-block" for="rolePGV">PGV</label>
                                    </div>
                                    <div class="flex-grow-1">
                                        <input type="radio" class="btn-check" name="role" id="roleGV" value="GIANGVIEN">
                                        <label class="role-switch-btn d-block" for="roleGV">Giáo viên</label>
                                    </div>
                                    <div class="flex-grow-1">
                                        <input type="radio" class="btn-check" name="role" id="roleSV" value="SINHVIEN">
                                        <label class="role-switch-btn d-block" for="roleSV">Sinh viên</label>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label for="ma" class="form-label small fw-bold text-muted">Mã đăng nhập</label>
                                    <div class="input-group-premium">
                                        <i class="bi bi-person input-icon"></i>
                                        <input type="text" name="ma" id="ma" class="form-control-premium" placeholder="Nhập mã đăng nhập..." required />
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <label for="password" class="form-label small fw-bold text-muted">Mật khẩu</label>
                                    <div class="input-group-premium">
                                        <i class="bi bi-lock input-icon"></i>
                                        <input type="password" name="password" id="password" class="form-control-premium pe-5" placeholder="••••••••" required />
                                        <button class="btn btn-link position-absolute end-0 top-50 translate-middle-y text-muted p-2 text-decoration-none" type="button" id="togglePassword">
                                            <i class="bi bi-eye"></i>
                                        </button>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-action btn-action-primary w-100 py-3">
                                    Đăng nhập ngay <i class="bi bi-box-arrow-in-right ms-2"></i>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('togglePassword').addEventListener('click', function() {
            var input = document.getElementById('password');
            var type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            this.innerHTML = '<i class="bi bi-' + (type === 'password' ? 'eye' : 'eye-slash') + '"></i>';
        });
    </script>
</body>
</html>
