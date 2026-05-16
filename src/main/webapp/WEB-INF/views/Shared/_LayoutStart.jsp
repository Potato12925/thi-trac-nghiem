<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String pageTitle = (String) request.getAttribute("pageTitle");
    if (pageTitle == null) pageTitle = "QLDSV_HTC";
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><%= pageTitle %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css" />
    <style>
        body { min-height: 100vh; background: #f4f6fb; }
        .app-layout { display: flex; min-height: 100vh; }
        .is-unauthenticated .app-sidebar { display: none; }
        .app-sidebar { width: 250px; background: #0b5ed7; color: #fff; flex-shrink: 0; display: flex; flex-direction: column; }
        .app-main { flex: 1; display: flex; flex-direction: column; }
        .app-header { display: flex; justify-content: space-between; align-items: center; padding: 1rem 1.5rem; background: #fff; border-bottom: 1px solid #e9ecef; }
        .app-content { padding: 1.5rem; }
        .sidebar-brand { padding: 1rem; border-bottom: 1px solid rgba(255,255,255,.15); }
        .sidebar-brand h2 { margin: 0; font-size: 1.25rem; letter-spacing: .03em; }
        .sidebar-brand p { margin: .25rem 0 0; font-size: .95rem; color: rgba(255,255,255,.85); }
        .sidebar-nav { padding: 1rem; overflow-y: auto; }
        .sidebar-nav ul { list-style: none; padding: 0; margin: 0; }
        .sidebar-nav li { margin-bottom: .35rem; }
        .sidebar-nav .nav-link { color: #dee2e6; display: flex; align-items: center; gap: .75rem; padding: .65rem 1rem; border-radius: .65rem; text-decoration: none; }
        .sidebar-nav .nav-link:hover, .sidebar-nav .nav-link.active { color: #fff; background: rgba(255,255,255,.14); }
        .header-user { display: flex; align-items: center; gap: .75rem; cursor: pointer; }
        .header-user-info .name { font-weight: 600; }
        .header-user-info .role { font-size: .9rem; color: #6c757d; }
        .header-avatar { width: 42px; height: 42px; border-radius: 50%; background: #0d6efd; color: #fff; display: grid; place-items: center; font-weight: 700; }
        .sidebar-footer { margin-top: auto; padding: 1rem; border-top: 1px solid rgba(255,255,255,.15); }
        .sidebar-footer .sidebar-roles { display: flex; flex-wrap: wrap; gap: .35rem; margin-bottom: 1rem; }
        .role-badge { padding: .35rem .6rem; border-radius: 999px; font-size: .75rem; background: rgba(255,255,255,.16); color: #fff; }
        .sidebar-logout { width: 100%; color: #fff; background: transparent; border: 1px solid rgba(255,255,255,.35); }
        .toast-container { z-index: 1055; }
        .skip-to-main-content { position: absolute; left: -999px; top: auto; width: 1px; height: 1px; overflow: hidden; }
        .skip-to-main-content:focus { position: static; width: auto; height: auto; padding: .5rem 1rem; background: #0b5ed7; color: #fff; z-index: 1100; }
        .card .card-header { background: #fff !important; }
    </style>
</head>
<body>
    <a href="#main-content" class="skip-to-main-content">Bỏ qua để đến nội dung chính</a>
    <div class="app-layout <c:if test="${empty sessionScope.LOGIN_USER}">is-unauthenticated</c:if>">
        <c:if test="${not empty sessionScope.LOGIN_USER}">
            <jsp:include page="/WEB-INF/views/Shared/_Sidebar.jsp" />
        </c:if>
        <div class="app-main">
            <c:if test="${not empty sessionScope.LOGIN_USER}">
                <jsp:include page="/WEB-INF/views/Shared/_Header.jsp" />
            </c:if>
            <main id="main-content" class="app-content" tabindex="-1">
