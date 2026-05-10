<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow-sm">
                    <div class="card-header bg-success text-white">
                        <h5 class="mb-0">Đăng ký tài khoản</h5>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <form method="post" action="${pageContext.request.contextPath}/register">
                            <div class="mb-3">
                                <label class="form-label" for="username">Tên đăng nhập</label>
                                <input id="username" name="username" class="form-control" value="${username}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label" for="password">Mật khẩu</label>
                                <input id="password" type="password" name="password" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label" for="confirmPassword">Xác nhận mật khẩu</label>
                                <input id="confirmPassword" type="password" name="confirmPassword" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label" for="role">Vai trò</label>
                                <select id="role" name="role" class="form-select" required>
                                    <option value="">Chọn vai trò</option>
                                    <option value="PGV" ${role == 'PGV' ? 'selected' : ''}>PGV</option>
                                    <option value="GIANGVIEN" ${role == 'GIANGVIEN' ? 'selected' : ''}>GIANGVIEN</option>
                                    <option value="SINHVIEN" ${role == 'SINHVIEN' ? 'selected' : ''}>SINHVIEN</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label" for="maGV">Mã giáo viên (nếu có)</label>
                                <input id="maGV" name="maGV" class="form-control" value="${maGV}">
                            </div>

                            <button type="submit" class="btn btn-success w-100">Đăng ký</button>
                        </form>

                        <div class="text-center mt-3">
                            <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
