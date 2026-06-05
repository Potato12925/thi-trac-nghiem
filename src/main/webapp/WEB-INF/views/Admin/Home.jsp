<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<c:set var="accountIdValue" value="${empty accountId ? sessionScope.LOGIN_USER : accountId}" />
<c:set var="accountRoleValue" value="${empty accountRole ? sessionScope.ROLE : accountRole}" />
<c:set var="displayNameValue" value="${empty accountIdValue ? 'Quản lý đào tạo' : accountIdValue}" />
<c:set var="roleLabelValue" value="${empty accountRoleValue ? 'PGV' : accountRoleValue}" />

<c:set var="totalClassroomsValue" value="${empty totalClassrooms ? 0 : totalClassrooms}" />
<c:set var="totalStudentsValue" value="${empty totalStudents ? 0 : totalStudents}" />
<c:set var="totalLecturersValue" value="${empty totalLecturers ? 0 : totalLecturers}" />
<c:set var="totalSubjectsValue" value="${empty totalSubjects ? 0 : totalSubjects}" />
<c:set var="totalQuestionsValue" value="${empty totalQuestions ? 0 : totalQuestions}" />
<c:set var="totalExamsValue" value="${empty totalExams ? 0 : totalExams}" />
<c:set var="totalAccountsValue" value="${empty totalAccounts ? 0 : totalAccounts}" />

<style>
.admin-dashboard .hero-card {
    border: 0;
    border-radius: 1.5rem;
    color: #fff;
    background: linear-gradient(135deg, #0b5ed7 0%, #1d4ed8 55%, #0ea5e9 100%);
    box-shadow: 0 1rem 2.5rem rgba(13, 110, 253, 0.18);
}

.admin-dashboard .stats-card,
.admin-dashboard .table-card {
    border: 0;
    border-radius: 1.25rem;
    box-shadow: 0 0.75rem 2rem rgba(15, 23, 42, 0.08);
}

.admin-dashboard .avatar-box {
    width: 5.5rem;
    height: 5.5rem;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    background: rgba(255, 255, 255, 0.16);
    border: 2px solid rgba(255, 255, 255, 0.24);
    font-size: 2rem;
    font-weight: 700;
}

.admin-dashboard .stat-value {
    font-size: 1.8rem;
    font-weight: 700;
    color: #0f172a;
}

.admin-dashboard .stat-label {
    color: #64748b;
    font-size: 0.92rem;
}

.admin-dashboard .metric-icon {
    width: 2.75rem;
    height: 2.75rem;
    border-radius: 0.9rem;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    font-size: 1.15rem;
    background: #e7f1ff;
    color: #0b5ed7;
}
</style>

<div class="container-fluid admin-dashboard">
    <div class="card hero-card mb-4">
        <div class="card-body p-4 p-lg-5">
            <div class="d-flex flex-column flex-lg-row align-items-lg-center gap-4">
                <div class="avatar-box">
                    <c:choose>
                        <c:when test="${not empty accountIdValue}">
                            ${fn:substring(fn:toUpperCase(accountIdValue), 0, 1)}
                        </c:when>
                        <c:otherwise>P</c:otherwise>
                    </c:choose>
                </div>

                <div class="flex-grow-1">
                    <h1 class="h2 mb-2">${displayNameValue}</h1>

                    <div class="row g-2 text-white-50">
                        <div class="col-md-6">
                            <i class="bi bi-person-badge me-2"></i> Tài khoản:
                            <span class="fw-semibold text-white">
                                <c:choose>
                                    <c:when test="${not empty accountIdValue}">${accountIdValue}</c:when>
                                    <c:otherwise>Chưa có dữ liệu</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="col-md-6">
                            <i class="bi bi-shield-check me-2"></i> Vai trò:
                            <span class="fw-semibold text-white">${roleLabelValue}</span>
                        </div>
                        <div class="col-md-6">
                            <i class="bi bi-window-sidebar me-2"></i> Khu vực:
                            <span class="fw-semibold text-white">Bảng điều khiển quản lý đào tạo</span>
                        </div>
                        <div class="col-md-6">
                            <i class="bi bi-calendar-event me-2"></i> Hôm nay:
                            <span class="fw-semibold text-white">
                                <fmt:formatDate value="${today}" pattern="dd/MM/yyyy" />
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4 mb-4">
        <div class="col-12 col-md-6 col-xl-4 col-xxl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div class="stat-label">Tổng lớp học</div>
                        <span class="metric-icon"><i class="bi bi-mortarboard"></i></span>
                    </div>
                    <div class="stat-value">${totalClassroomsValue}</div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6 col-xl-4 col-xxl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div class="stat-label">Tổng sinh viên</div>
                        <span class="metric-icon"><i class="bi bi-people"></i></span>
                    </div>
                    <div class="stat-value">${totalStudentsValue}</div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6 col-xl-4 col-xxl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div class="stat-label">Tổng giảng viên</div>
                        <span class="metric-icon"><i class="bi bi-person-badge"></i></span>
                    </div>
                    <div class="stat-value">${totalLecturersValue}</div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6 col-xl-4 col-xxl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div class="stat-label">Tổng môn học</div>
                        <span class="metric-icon"><i class="bi bi-book"></i></span>
                    </div>
                    <div class="stat-value">${totalSubjectsValue}</div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6 col-xl-4 col-xxl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div class="stat-label">Tổng câu hỏi</div>
                        <span class="metric-icon"><i class="bi bi-patch-question"></i></span>
                    </div>
                    <div class="stat-value">${totalQuestionsValue}</div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6 col-xl-4 col-xxl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div class="stat-label">Tổng bài thi</div>
                        <span class="metric-icon"><i class="bi bi-journal-check"></i></span>
                    </div>
                    <div class="stat-value">${totalExamsValue}</div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6 col-xl-4 col-xxl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div class="stat-label">Tổng tài khoản</div>
                        <span class="metric-icon"><i class="bi bi-person-lock"></i></span>
                    </div>
                    <div class="stat-value">${totalAccountsValue}</div>
                </div>
            </div>
        </div>
    </div>

    <div class="card table-card">
        <div class="card-body p-4">
            <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2 mb-4">
                <div>
                    <h2 class="h5 mb-1">Bài kiểm tra được giáo viên đăng ký gần nhất</h2>
                    <p class="text-muted mb-0">Danh sách 7 đợt đăng ký thi mới nhất từ dữ liệu thực trong hệ thống.</p>
                </div>
                <span class="badge text-bg-primary">Dữ liệu từ GIAOVIEN_DANGKY</span>
            </div>

            <div class="table-responsive">
                <table class="table align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>Môn học</th>
                            <th>Lớp</th>
                            <th>Giáo viên đăng ký</th>
                            <th>Trình độ</th>
                            <th>Lần thi</th>
                            <th>Số câu hỏi</th>
                            <th>Thời gian làm bài</th>
                            <th>Ngày thi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty recentExamRegistrations}">
                                <c:forEach var="registration" items="${recentExamRegistrations}">
                                    <tr>
                                        <td>
                                            <div class="fw-semibold">
                                                <c:choose>
                                                    <c:when test="${not empty registration.subjectName}">
                                                        ${registration.subjectName}
                                                    </c:when>
                                                    <c:otherwise>Chưa có dữ liệu</c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="text-muted small">
                                                <c:choose>
                                                    <c:when test="${not empty registration.subjectId}">
                                                        ${registration.subjectId}
                                                    </c:when>
                                                    <c:otherwise>N/A</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="fw-semibold">
                                                <c:choose>
                                                    <c:when test="${not empty registration.className}">
                                                        ${registration.className}
                                                    </c:when>
                                                    <c:otherwise>Chưa có dữ liệu</c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="text-muted small">
                                                <c:choose>
                                                    <c:when test="${not empty registration.classId}">
                                                        ${registration.classId}
                                                    </c:when>
                                                    <c:otherwise>N/A</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="fw-semibold">
                                                <c:choose>
                                                    <c:when test="${not empty registration.lecturerName}">
                                                        ${registration.lecturerName}
                                                    </c:when>
                                                    <c:otherwise>Chưa có dữ liệu</c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="text-muted small">
                                                <c:choose>
                                                    <c:when test="${not empty registration.lecturerId}">
                                                        ${registration.lecturerId}
                                                    </c:when>
                                                    <c:otherwise>N/A</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </td>
                                        <td>
                                            <span class="badge rounded-pill text-bg-light border">
                                                <c:choose>
                                                    <c:when test="${not empty registration.level}">
                                                        ${registration.level}
                                                    </c:when>
                                                    <c:otherwise>Chưa có dữ liệu</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty registration.attempt}">
                                                    Lần ${registration.attempt}
                                                </c:when>
                                                <c:otherwise>Chưa có dữ liệu</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty registration.questionCount}">
                                                    ${registration.questionCount} câu
                                                </c:when>
                                                <c:otherwise>Chưa có dữ liệu</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty registration.duration}">
                                                    ${registration.duration} phút
                                                </c:when>
                                                <c:otherwise>Chưa có dữ liệu</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty registration.examDate}">
                                                    <fmt:formatDate value="${registration.examDate}" pattern="dd/MM/yyyy" />
                                                </c:when>
                                                <c:otherwise>Chưa có dữ liệu</c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="8" class="text-center py-4 text-muted">
                                        Chưa có bài kiểm tra nào được giáo viên đăng ký.
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
