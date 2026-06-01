<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% request.setAttribute("pageTitle", "Lịch Sử Thi"); %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<div class="container-fluid">
    <!-- Header -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1 class="h3 mb-1 text-dark fw-bold">Lịch Sử Thi</h1>
            <p class="text-muted mb-0">Xem lại danh sách các môn thi trắc nghiệm bạn đã tham gia</p>
        </div>
    </div>

    <!-- Notifications -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i> ${successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- History Table Card -->
    <div class="card border-0 shadow-sm rounded-3 overflow-hidden">
        <div class="card-header bg-white py-3 border-bottom border-light">
            <h5 class="card-title mb-0 text-dark fw-semibold">
                <i class="bi bi-clock-history me-2 text-primary"></i>Danh Sách Lượt Thi
            </h5>
        </div>
        <div class="table-responsive p-3">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light text-secondary">
                    <tr>
                        <th scope="col" class="ps-3" style="width: 5%;">STT</th>
                        <th scope="col" class="text-center" style="width: 25%;">Môn Học</th>
                        <th scope="col" class="text-center" style="width: 10%;">Mã Lớp</th>
                        <th scope="col" class="text-center" style="width: 10%;">Lần Thi</th>
                        <th scope="col" class="text-center" style="width: 15%;">Ngày Thi</th>
                        <th scope="col" class="text-center" style="width: 15%;">Thời Gian Làm Bài</th>
                        <th scope="col" class="text-center" style="width: 10%;">Điểm Số</th>
                        <th scope="col" class="text-end pe-3" style="width: 15%;">Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="exam" items="${exams}" varStatus="status">
                        <tr>
                            <td class="ps-3 fw-medium text-secondary">${status.index + 1}</td>
                            <td>
                                <div class="fw-semibold text-dark">${exam.subject.subjectName}</div>
                                <div class="small text-muted">Mã MH: ${exam.subject.subjectId}</div>
                            </td>
                            <td><span class="badge bg-light text-center text-dark border">${exam.classId}</span></td>
                            <td class="text-center">
                                <span class="badge bg-primary-subtle text-primary border border-primary-subtle">
                                    Lần ${exam.attempt}
                                </span>
                            </td>
                            <td>
                                <div class="small fw-semibold text-dark text-center">
                                    <fmt:formatDate value="${exam.examDate}" pattern="dd-MM-yyyy" />
                                </div>
                            </td>
                            <td>
                                <div class="small text-dark text-center">
                                    <i class="bi bi-clock me-1 text-muted"></i>
                                    <fmt:formatDate value="${exam.startTime}" pattern="HH:mm:ss" /> - 
                                    <fmt:formatDate value="${exam.endTime}" pattern="HH:mm:ss" />
                                </div>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${exam.score >= 5.0}">
                                        <span class="fs-6 fw-bold text-success">${exam.score}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="fs-6 fw-bold text-danger">${exam.score}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-end pe-3">
                                <a href="${pageContext.request.contextPath}/history/detail?id=${exam.id}" 
                                   class="btn btn-sm btn-dark px-3 py-1.5 rounded-2 d-inline-flex align-items-center gap-1">
                                    <i class="bi bi-eye"></i> Chi tiết
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty exams}">
                        <tr>
                            <td colspan="8" class="text-center text-muted py-5">
                                <i class="bi bi-emoji-neutral fs-1 d-block mb-3 text-secondary"></i>
                                Bạn chưa tham gia bài thi trắc nghiệm nào.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
