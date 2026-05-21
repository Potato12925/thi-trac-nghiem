<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<c:set var="fullName" value="${studentProfile.lastName} ${studentProfile.firstName}" />
<c:set var="studentId" value="${empty studentProfile.studentId ? sessionScope.LOGIN_USER : studentProfile.studentId}" />
<c:set var="className" value="${empty studentProfile.classRoom.className ? studentProfile.classRoom.classId : studentProfile.classRoom.className}" />
<c:set var="totalSubjectsTakenValue" value="${empty totalSubjectsTaken ? 0 : totalSubjectsTaken}" />
<c:set var="totalPendingExamsValue" value="${empty totalPendingExams ? 0 : totalPendingExams}" />
<c:set var="averageScoreValue" value="${empty averageScore ? 0 : averageScore}" />
<c:set var="recentSubjectValue" value="${empty recentSubject ? 'Chưa có dữ liệu' : recentSubject}" />

<style>
    .student-dashboard { display: grid; gap: 1.25rem; }
    .welcome-card { border: 0; border-radius: 1rem; background: linear-gradient(135deg, #0b5ed7, #4b8ff9); color: #fff; box-shadow: 0 12px 24px rgba(11, 94, 215, 0.2); }
    .welcome-grid { display: grid; grid-template-columns: repeat(3, minmax(0, 1fr)); gap: 0.9rem; }
    .welcome-item { background: rgba(255, 255, 255, 0.15); border-radius: 0.8rem; padding: 0.75rem 0.85rem; }
    .welcome-item .label { font-size: 0.8rem; opacity: 0.9; }
    .welcome-item .value { font-weight: 700; }

    .stat-grid { display: grid; grid-template-columns: repeat(4, minmax(0, 1fr)); gap: 1rem; }
    .stat-card { border: 0; border-radius: 1rem; box-shadow: 0 10px 18px rgba(23, 44, 82, 0.08); }
    .stat-icon { width: 44px; height: 44px; border-radius: 0.8rem; display: grid; place-items: center; font-size: 1.2rem; }

    .icon-subject { background: #e8f1ff; color: #0d6efd; }
    .icon-pending { background: #fff3e3; color: #ff8a00; }
    .icon-score { background: #eafaf0; color: #16a34a; }
    .icon-recent { background: #f1ecff; color: #6f42c1; }

    .schedule-card { border: 0; border-radius: 1rem; box-shadow: 0 10px 18px rgba(23, 44, 82, 0.08); }

    @media (max-width: 992px) {
        .welcome-grid { grid-template-columns: repeat(2, minmax(0, 1fr)); }
        .stat-grid { grid-template-columns: repeat(2, minmax(0, 1fr)); }
    }

    @media (max-width: 576px) {
        .welcome-grid { grid-template-columns: 1fr; }
        .stat-grid { grid-template-columns: 1fr; }
    }
</style>

<div class="container-fluid student-dashboard">
    <section class="card welcome-card">
        <div class="card-body p-4">
            <p class="mb-2 fw-semibold">Xin chào, ${empty studentProfile ? sessionScope.LOGIN_USER : fullName}.</p>
            <h2 class="h4 mb-4">Chúc bạn có một buổi học hiệu quả và thi thật tốt.</h2>

            <div class="welcome-grid">
                <div class="welcome-item">
                    <div class="label">Họ tên sinh viên</div>
                    <div class="value">${empty studentProfile ? 'Chưa cập nhật' : fullName}</div>
                </div>
                <div class="welcome-item">
                    <div class="label">Mã sinh viên</div>
                    <div class="value">${studentId}</div>
                </div>
                <div class="welcome-item">
                    <div class="label">Ngày sinh</div>
                    <div class="value">
                        <c:choose>
                            <c:when test="${not empty studentProfile.birthDate}">
                                <fmt:formatDate value="${studentProfile.birthDate}" pattern="dd/MM/yyyy" />
                            </c:when>
                            <c:otherwise>Chưa cập nhật</c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="welcome-item">
                    <div class="label">Lớp</div>
                    <div class="value">${empty className ? 'Chưa cập nhật' : className}</div>
                </div>
                <div class="welcome-item">
                    <div class="label">Ngày hiện tại</div>
                    <div class="value"><fmt:formatDate value="${today}" pattern="dd/MM/yyyy" /></div>
                </div>
            </div>
        </div>
    </section>

    <section class="stat-grid">
        <article class="card stat-card">
            <div class="card-body d-flex align-items-center justify-content-between">
                <div>
                    <div class="text-secondary small">Tổng số môn đã thi</div>
                    <div class="h4 mb-0">${totalSubjectsTakenValue}</div>
                </div>
                <div class="stat-icon icon-subject"><i class="bi bi-journal-check"></i></div>
            </div>
        </article>

        <article class="card stat-card">
            <div class="card-body d-flex align-items-center justify-content-between">
                <div>
                    <div class="text-secondary small">Tổng số bài thi chưa thi</div>
                    <div class="h4 mb-0">${totalPendingExamsValue}</div>
                </div>
                <div class="stat-icon icon-pending"><i class="bi bi-hourglass-split"></i></div>
            </div>
        </article>

        <article class="card stat-card">
            <div class="card-body d-flex align-items-center justify-content-between">
                <div>
                    <div class="text-secondary small">Điểm trung bình</div>
                    <div class="h4 mb-0"><fmt:formatNumber value="${averageScoreValue}" maxFractionDigits="2" minFractionDigits="1" /></div>
                </div>
                <div class="stat-icon icon-score"><i class="bi bi-graph-up-arrow"></i></div>
            </div>
        </article>

        <article class="card stat-card">
            <div class="card-body d-flex align-items-center justify-content-between">
                <div>
                    <div class="text-secondary small">Môn thi gần nhất</div>
                    <div class="h6 mb-0">${recentSubjectValue}</div>
                </div>
                <div class="stat-icon icon-recent"><i class="bi bi-clock-history"></i></div>
            </div>
        </article>
    </section>

    <section class="card schedule-card">
        <div class="card-body p-4">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3 class="h5 mb-0">Lịch thi / ca thi sắp tới</h3>
                <span class="badge text-bg-primary">Sắp diễn ra</span>
            </div>

            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>Môn thi</th>
                            <th>Ngày thi</th>
                            <th>Ca thi</th>
                            <th>Phòng thi</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty upcomingExams}">
                                <c:forEach var="exam" items="${upcomingExams}">
                                    <tr>
                                        <td>${exam.subjectName}</td>
                                        <td>${exam.examDate}</td>
                                        <td>${exam.shift}</td>
                                        <td>${exam.room}</td>
                                        <td><span class="badge text-bg-warning">Chưa thi</span></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5" class="text-center text-secondary py-4">Chưa có lịch thi sắp tới.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
