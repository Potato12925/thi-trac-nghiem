<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<c:set var="teacherName" value="${empty teacherProfile ? sessionScope.LOGIN_USER : teacherProfile.ho} ${empty teacherProfile ? '' : teacherProfile.ten}" />
<c:set var="teacherId" value="${empty teacherProfile.maGV ? sessionScope.LOGIN_USER : teacherProfile.maGV}" />

<c:set var="totalQuestionsValue" value="${empty totalQuestions ? 0 : totalQuestions}" />
<c:set var="totalExamRegistrationsValue" value="${empty totalExamRegistrations ? 0 : totalExamRegistrations}" />
<c:set var="totalScoresPublishedValue" value="${empty totalScoresPublished ? 0 : totalScoresPublished}" />
<c:set var="lastActiveSubjectValue" value="${empty lastActiveSubject ? 'Chua co du lieu' : lastActiveSubject}" />

<style>
    body { background: #f4f7fb; }
    .teacher-dashboard { display: grid; gap: 1.5rem; }
    .profile-card {
        border-radius: 28px;
        overflow: hidden;
        border: 0;
        background: linear-gradient(135deg, #0f766e, #0ea5e9);
        box-shadow: 0 20px 45px rgba(14, 165, 233, 0.2);
        color: white;
    }
    .profile-top { display: flex; align-items: center; gap: 1.5rem; padding: 2rem; }
    .avatar-box {
        width: 95px; height: 95px; border-radius: 50%;
        background: rgba(255,255,255,0.18);
        display: flex; align-items: center; justify-content: center;
        font-size: 2.2rem; font-weight: 700; flex-shrink: 0;
        backdrop-filter: blur(12px); border: 2px solid rgba(255,255,255,0.2);
    }
    .teacher-name { font-size: 2rem; font-weight: 700; margin-bottom: 0.4rem; }
    .teacher-meta { font-size: 0.95rem; opacity: 0.92; margin-bottom: 0.25rem; }
    .info-grid { display: grid; grid-template-columns: repeat(4, minmax(0, 1fr)); gap: 1rem; padding: 0 2rem 2rem; }
    .info-item {
        background: rgba(255,255,255,0.12);
        border-radius: 22px;
        padding: 1rem;
        backdrop-filter: blur(14px);
        transition: 0.25s ease;
    }
    .info-item:hover { transform: translateY(-3px); background: rgba(255,255,255,0.18); }
    .info-label { font-size: 0.82rem; opacity: 0.85; margin-bottom: 0.45rem; }
    .info-value { font-size: 1.5rem; font-weight: 700; }
    .recent-subject { font-size: 1rem; line-height: 1.4; }
    .task-card { border: 0; border-radius: 28px; background: white; box-shadow: 0 12px 30px rgba(15, 23, 42, 0.06); }
    .task-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem; }
    .task-title { font-size: 1.25rem; font-weight: 700; color: #0f172a; }
    .table { margin-bottom: 0; }
    .table thead th { border: 0; background: #f8fafc; color: #64748b; font-size: 0.85rem; font-weight: 600; padding: 1rem; }
    .table tbody td { padding: 1rem; vertical-align: middle; border-color: #f1f5f9; }
    .badge-soft { background: #ecfeff; color: #0f766e; padding: 0.5rem 0.8rem; border-radius: 999px; font-size: 0.78rem; font-weight: 600; }
    .empty-state { padding: 2rem !important; color: #94a3b8; }
    @media (max-width: 992px) { .info-grid { grid-template-columns: repeat(2, minmax(0, 1fr)); } }
    @media (max-width: 576px) {
        .profile-top { flex-direction: column; text-align: center; }
        .teacher-name { font-size: 1.6rem; }
        .info-grid { grid-template-columns: 1fr; }
        .task-header { flex-direction: column; align-items: flex-start; gap: 0.75rem; }
    }
</style>

<div class="container-fluid teacher-dashboard">

    <section class="profile-card">
        <div class="profile-top">
            <div class="avatar-box">
                <c:choose>
                    <c:when test="${not empty teacherProfile.ten}">${fn:substring(teacherProfile.ten,0,1)}</c:when>
                    <c:otherwise>G</c:otherwise>
                </c:choose>
            </div>

            <div>
                <div class="teacher-name">${teacherName}</div>
                <div class="teacher-meta"><i class="bi bi-person-badge"></i> Ma giang vien: ${teacherId}</div>
                <div class="teacher-meta"><i class="bi bi-briefcase"></i> Vai tro: Giang vien</div>
                <div class="teacher-meta"><i class="bi bi-calendar-event"></i> Hom nay: <fmt:formatDate value="${today}" pattern="dd/MM/yyyy" /></div>
            </div>
        </div>

        <div class="info-grid">
            <div class="info-item">
                <div class="info-label">Tong cau hoi da tao</div>
                <div class="info-value">${totalQuestionsValue}</div>
            </div>
            <div class="info-item">
                <div class="info-label">Dot dang ky thi</div>
                <div class="info-value">${totalExamRegistrationsValue}</div>
            </div>
            <div class="info-item">
                <div class="info-label">Bang diem da cong bo</div>
                <div class="info-value">${totalScoresPublishedValue}</div>
            </div>
            <div class="info-item">
                <div class="info-label">Mon lam viec gan nhat</div>
                <div class="recent-subject">${lastActiveSubjectValue}</div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
