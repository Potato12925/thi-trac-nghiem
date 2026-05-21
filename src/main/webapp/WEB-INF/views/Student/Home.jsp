<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<c:set var="fullName" value="${studentProfile.lastName} ${studentProfile.firstName}" />
<c:set var="studentId" value="${empty studentProfile.studentId ? sessionScope.LOGIN_USER : studentProfile.studentId}" />
<c:set var="className" value="${empty studentProfile.classRoom.className ? studentProfile.classRoom.classId : studentProfile.classRoom.className}" />

<c:set var="totalSubjectsTakenValue" value="${empty totalSubjectsTaken ? 0 : totalSubjectsTaken}" />
<c:set var="totalPendingExamsValue" value="${empty totalPendingExams ? 0 : totalPendingExams}" />
<c:set var="averageScoreValue" value="${empty averageScore ? 0 : averageScore}" />
<c:set var="recentSubjectValue" value="${empty recentSubject ? 'Chưa có dữ liệu' : recentSubject}" />

<style>

    body {
        background: #f4f7fb;
    }

    .student-dashboard {
        display: grid;
        gap: 1.5rem;
    }

    .profile-card {
        border-radius: 28px;
        overflow: hidden;
        border: 0;
        background:
            linear-gradient(135deg, #2563eb, #4f46e5);
        box-shadow: 0 20px 45px rgba(37, 99, 235, 0.18);
        color: white;
    }

    .profile-top {
        display: flex;
        align-items: center;
        gap: 1.5rem;
        padding: 2rem;
    }

    .avatar-box {
        width: 95px;
        height: 95px;
        border-radius: 50%;
        background: rgba(255,255,255,0.18);
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 2.2rem;
        font-weight: 700;
        flex-shrink: 0;
        backdrop-filter: blur(12px);
        border: 2px solid rgba(255,255,255,0.2);
    }

    .student-name {
        font-size: 2rem;
        font-weight: 700;
        margin-bottom: 0.4rem;
    }

    .student-meta {
        font-size: 0.95rem;
        opacity: 0.92;
        margin-bottom: 0.25rem;
    }

    .info-grid {
        display: grid;
        grid-template-columns: repeat(4, minmax(0, 1fr));
        gap: 1rem;
        padding: 0 2rem 2rem;
    }

    .info-item {
        background: rgba(255,255,255,0.12);
        border-radius: 22px;
        padding: 1rem;
        backdrop-filter: blur(14px);
        transition: 0.25s ease;
    }

    .info-item:hover {
        transform: translateY(-3px);
        background: rgba(255,255,255,0.18);
    }

    .info-label {
        font-size: 0.82rem;
        opacity: 0.85;
        margin-bottom: 0.45rem;
    }

    .info-value {
        font-size: 1.5rem;
        font-weight: 700;
    }

    .recent-subject {
        font-size: 1rem;
        line-height: 1.4;
    }

    .schedule-card {
        border: 0;
        border-radius: 28px;
        background: white;
        box-shadow: 0 12px 30px rgba(15, 23, 42, 0.06);
    }

    .schedule-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1rem;
    }

    .schedule-title {
        font-size: 1.25rem;
        font-weight: 700;
        color: #0f172a;
    }

    .table {
        margin-bottom: 0;
    }

    .table thead th {
        border: 0;
        background: #f8fafc;
        color: #64748b;
        font-size: 0.85rem;
        font-weight: 600;
        padding: 1rem;
    }

    .table tbody td {
        padding: 1rem;
        vertical-align: middle;
        border-color: #f1f5f9;
    }

    .badge-soft {
        background: #fff7ed;
        color: #ea580c;
        padding: 0.5rem 0.8rem;
        border-radius: 999px;
        font-size: 0.78rem;
        font-weight: 600;
    }

    .empty-state {
        padding: 2rem !important;
        color: #94a3b8;
    }

    @media (max-width: 992px) {

        .info-grid {
            grid-template-columns: repeat(2, minmax(0, 1fr));
        }
    }

    @media (max-width: 576px) {

        .profile-top {
            flex-direction: column;
            text-align: center;
        }

        .student-name {
            font-size: 1.6rem;
        }

        .info-grid {
            grid-template-columns: 1fr;
        }

        .schedule-header {
            flex-direction: column;
            align-items: flex-start;
            gap: 0.75rem;
        }
    }

</style>

<div class="container-fluid student-dashboard">

    <!-- PROFILE -->

    <section class="profile-card">

        <div class="profile-top">

            <div class="avatar-box">
                <c:choose>
                    <c:when test="${not empty studentProfile.firstName}">
                        ${fn:substring(studentProfile.firstName,0,1)}
                    </c:when>
                    <c:otherwise>
                        S
                    </c:otherwise>
                </c:choose>
            </div>

            <div>

                <div class="student-name">
                    ${empty studentProfile ? sessionScope.LOGIN_USER : fullName}
                </div>

                <div class="student-meta">
                    <i class="bi bi-person-badge"></i>
                    MSSV: ${studentId}
                </div>

                <div class="student-meta">
                    <i class="bi bi-mortarboard"></i>
                    Lớp:
                    ${empty className ? 'Chưa cập nhật' : className}
                </div>

                <div class="student-meta">

                    <i class="bi bi-calendar-event"></i>

                    Ngày sinh:

                    <c:choose>

                        <c:when test="${not empty studentProfile.birthDate}">
                            <fmt:formatDate
                                    value="${studentProfile.birthDate}"
                                    pattern="dd/MM/yyyy" />
                        </c:when>

                        <c:otherwise>
                            Chưa cập nhật
                        </c:otherwise>

                    </c:choose>

                </div>

            </div>

        </div>

        <!-- STATS -->

        <div class="info-grid">

            <div class="info-item">

                <div class="info-label">
                    Tổng môn đã thi
                </div>

                <div class="info-value">
                    ${totalSubjectsTakenValue}
                </div>

            </div>

            <div class="info-item">

                <div class="info-label">
                    Bài thi chưa thi
                </div>

                <div class="info-value">
                    ${totalPendingExamsValue}
                </div>

            </div>

            <div class="info-item">

                <div class="info-label">
                    Điểm trung bình
                </div>

                <div class="info-value">

                    <fmt:formatNumber
                            value="${averageScoreValue}"
                            maxFractionDigits="2"
                            minFractionDigits="1" />

                </div>

            </div>

            <div class="info-item">

                <div class="info-label">
                    Môn thi gần nhất
                </div>

                <div class="recent-subject">
                    ${recentSubjectValue}
                </div>

            </div>

        </div>

    </section>

    <!-- UPCOMING EXAMS -->

    <section class="card schedule-card">

        <div class="card-body p-4">

            <div class="schedule-header">

                <div class="schedule-title">
                    Lịch thi sắp tới
                </div>

                <span class="badge text-bg-primary">
                    Sắp diễn ra
                </span>

            </div>

            <div class="table-responsive">

                <table class="table align-middle">

                    <thead>

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

                                    <td class="fw-semibold">
                                        ${exam.subjectName}
                                    </td>

                                    <td>
                                        ${exam.examDate}
                                    </td>

                                    <td>
                                        ${exam.shift}
                                    </td>

                                    <td>
                                        ${exam.room}
                                    </td>

                                    <td>
                                        <span class="badge-soft">
                                            Chưa thi
                                        </span>
                                    </td>

                                </tr>

                            </c:forEach>

                        </c:when>

                        <c:otherwise>

                            <tr>

                                <td colspan="5"
                                    class="text-center empty-state">

                                    Chưa có lịch thi sắp tới.

                                </td>

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