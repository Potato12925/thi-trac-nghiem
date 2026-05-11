<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("pageTitle", "Trang chủ"); %>
<%@ include file="Shared/_LayoutStart.jsp" %>
<div class="container-fluid mt-3">
    <div class="row justify-content-center">
        <div class="col-12 col-xl-10">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                    <div>
                        <h4 class="mb-0">Bảng điều khiển</h4>
                        <small>Giao diện tương tự QLDSV_HTC</small>
                    </div>
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
<%@ include file="Shared/_LayoutEnd.jsp" %>
