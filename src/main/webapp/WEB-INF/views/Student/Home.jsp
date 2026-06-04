<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<c:set var="studentIdValue" value="${empty studentProfile.studentId ? sessionScope.LOGIN_USER : studentProfile.studentId}" />
<c:set var="fullNameValue" value="${fn:trim(studentProfile.lastName)} ${fn:trim(studentProfile.firstName)}" />
<c:set var="classNameValue" value="${empty studentProfile.classRoom.className ? studentProfile.classRoom.classId : studentProfile.classRoom.className}" />
<c:set var="completedSubjectCountValue" value="${empty dashboard ? 0 : dashboard.completedSubjectCount}" />
<c:set var="pendingExamCountValue" value="${empty dashboard ? 0 : dashboard.pendingExamCount}" />
<c:set var="averageScoreValue" value="${empty dashboard or empty dashboard.averageScore ? 0 : dashboard.averageScore}" />
<c:set var="latestExamSubjectValue" value="${empty dashboard.latestExamSubjectName ? 'Chưa có dữ liệu' : dashboard.latestExamSubjectName}" />

<style>
.student-dashboard .profile-card {
    border: 0;
    border-radius: 1.5rem;
    color: #fff;
    background: linear-gradient(135deg, #0d6efd 0%, #3b82f6 55%, #60a5fa 100%);
    box-shadow: 0 1rem 2.5rem rgba(13, 110, 253, 0.18);
}

.student-dashboard .stats-card,
.student-dashboard .schedule-card,
.student-dashboard .info-card {
    border: 0;
    border-radius: 1.25rem;
    box-shadow: 0 0.75rem 2rem rgba(15, 23, 42, 0.08);
}

.student-dashboard .avatar-box {
    width: 5.5rem;
    height: 5.5rem;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    background: rgba(255, 255, 255, 0.18);
    border: 2px solid rgba(255, 255, 255, 0.25);
    font-size: 2rem;
    font-weight: 700;
}

.student-dashboard .meta-item {
    color: rgba(255, 255, 255, 0.92);
}

.student-dashboard .stat-value {
    font-size: 1.85rem;
    font-weight: 700;
    color: #0f172a;
}

.student-dashboard .stat-label {
    color: #64748b;
    font-size: 0.92rem;
}

.student-dashboard .detail-label {
    color: #64748b;
    font-size: 0.88rem;
}

.student-dashboard .detail-value {
    color: #0f172a;
    font-weight: 600;
}

.student-dashboard .empty-state {
    color: #64748b;
}
</style>

<div class="container-fluid student-dashboard">
    <div class="card profile-card mb-4">
        <div class="card-body p-4 p-lg-5">
            <div class="d-flex flex-column flex-lg-row align-items-lg-center gap-4">
                <div class="avatar-box">
                    <c:choose>
                        <c:when test="${not empty studentProfile.firstName}">
                            ${fn:substring(fn:trim(studentProfile.firstName), 0, 1)}
                        </c:when>
                        <c:otherwise>S</c:otherwise>
                    </c:choose>
                </div>

                <div class="flex-grow-1">
                    <h1 class="h2 mb-2">
                        <c:choose>
                            <c:when test="${not empty studentProfile}">
                                ${empty fullNameValue ? studentIdValue : fullNameValue}
                            </c:when>
                            <c:otherwise>${studentIdValue}</c:otherwise>
                        </c:choose>
                    </h1>

                    <div class="row g-2">
                        <div class="col-md-6 meta-item">
                            <i class="bi bi-person-badge me-2"></i>
                            MSSV: <span class="fw-semibold">${empty studentIdValue ? 'Chưa cập nhật' : studentIdValue}</span>
                        </div>
                        <div class="col-md-6 meta-item">
                            <i class="bi bi-mortarboard me-2"></i>
                            Lớp: <span class="fw-semibold">${empty classNameValue ? 'Chưa cập nhật' : classNameValue}</span>
                        </div>
                        <div class="col-md-6 meta-item">
                            <i class="bi bi-calendar-event me-2"></i>
                            Ngày sinh:
                            <span class="fw-semibold">
                                <c:choose>
                                    <c:when test="${not empty studentProfile.birthDate}">
                                        <fmt:formatDate value="${studentProfile.birthDate}" pattern="dd/MM/yyyy" />
                                    </c:when>
                                    <c:otherwise>Chưa cập nhật</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="col-md-6 meta-item">
                            <i class="bi bi-clock-history me-2"></i>
                            Hôm nay:
                            <span class="fw-semibold">
                                <fmt:formatDate value="${today}" pattern="dd/MM/yyyy" />
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4 mb-4">
        <div class="col-12 col-md-6 col-xl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="stat-label mb-2">Tổng môn đã thi</div>
                    <div class="stat-value">${completedSubjectCountValue}</div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6 col-xl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="stat-label mb-2">Bài thi sắp tới</div>
                    <div class="stat-value">${pendingExamCountValue}</div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6 col-xl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="stat-label mb-2">Điểm trung bình</div>
                    <div class="stat-value">
                        <fmt:formatNumber value="${averageScoreValue}" minFractionDigits="0" maxFractionDigits="2" />
                    </div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6 col-xl-3">
            <div class="card stats-card h-100">
                <div class="card-body">
                    <div class="stat-label mb-2">Môn thi gần nhất</div>
                    <div class="detail-value">${latestExamSubjectValue}</div>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <div class="col-12 col-xl-4">
            <div class="card info-card h-100">
                <div class="card-body p-4">
                    <h2 class="h5 mb-4">Thông tin liên hệ</h2>

                    <div class="mb-3">
                        <div class="detail-label mb-1">Email</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${not empty studentProfile.email}">
                                    ${studentProfile.email}
                                </c:when>
                                <c:otherwise>Chưa cập nhật</c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div>
                        <div class="detail-label mb-1">Địa chỉ</div>
                        <div class="detail-value">
                            <c:choose>
                                <c:when test="${not empty studentProfile.address}">
                                    ${studentProfile.address}
                                </c:when>
                                <c:otherwise>Chưa cập nhật</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-12 col-xl-8">
            <div class="card schedule-card h-100">
                <div class="card-body p-4">
                    <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2 mb-4">
                        <h2 class="h5 mb-0">Lịch thi sắp tới</h2>
                        <span class="badge text-bg-primary">Theo lớp hiện tại</span>
                    </div>

                    <div class="table-responsive">
                        <table class="table align-middle mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>Môn thi</th>
                                    <th>Ngày thi</th>
                                    <th>Lần thi</th>
                                    <th>Trình độ</th>
                                    <th>Thời gian thi</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty upcomingExams}">
                                        <c:forEach var="exam" items="${upcomingExams}">
                                            <tr>
                                                <td>
                                                    <div class="fw-semibold">${empty exam.subjectName ? exam.subjectId : exam.subjectName}</div>
                                                    <c:if test="${not empty exam.subjectId}">
                                                        <div class="text-muted small">${exam.subjectId}</div>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <fmt:formatDate value="${exam.examDate}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td>Lần ${exam.tryNumber}</td>
                                                <td>
                                                    <span class="badge rounded-pill text-bg-light border">${empty exam.level ? 'Chưa cập nhật' : exam.level}</span>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty exam.duration}">
                                                            ${exam.duration} phút
                                                        </c:when>
                                                        <c:otherwise>Chưa cập nhật</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="5" class="text-center py-4 empty-state">
                                                Chưa có lịch thi sắp tới.
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
    </div>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
