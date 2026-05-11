<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "Đăng nhập"); %>
<%@ include file="Shared/_LayoutStart.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">Đăng nhập hệ thống</h5>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <form method="post" action="${pageContext.request.contextPath}/login">
                        <div class="mb-3">
                            <label for="username" class="form-label">Tài khoản</label>
                            <input id="username" name="username" class="form-control" value="${username}" required />
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Mật khẩu</label>
                            <input id="password" type="password" name="password" class="form-control" required />
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
                    </form>
                    <div class="text-center mt-3">
                        <a href="${pageContext.request.contextPath}/register">Chưa có tài khoản? Đăng ký</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="Shared/_LayoutEnd.jsp" %>
