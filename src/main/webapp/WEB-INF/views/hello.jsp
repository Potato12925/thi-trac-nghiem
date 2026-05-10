<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                        <h4 class="mb-0">Welcome to Spring MVC</h4>
                        <c:choose>
                            <c:when test="${not empty sessionScope.LOGIN_USER}">
                                <a class="btn btn-sm btn-light" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-sm btn-light" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <h1 class="text-center text-success">${message}</h1>

                        <c:if test="${not empty sessionScope.LOGIN_USER}">
                            <div class="alert alert-info mt-3">
                                Xin chào <strong>${sessionScope.LOGIN_USER}</strong> - Quyền: <strong>${sessionScope.ROLE}</strong>
                            </div>
                        </c:if>

                        <div class="mt-4">
                            <h5>Kết quả kết nối database</h5>
                            <c:choose>
                                <c:when test="${dbConnected}">
                                    <div class="alert alert-success mb-0" role="alert">${dbStatus}</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="alert alert-danger mb-0" role="alert">${dbStatus}</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
