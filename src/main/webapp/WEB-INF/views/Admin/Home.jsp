<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<c:set var="managerName" value="${empty pgvProfile ? sessionScope.LOGIN_USER : pgvProfile.ho}" />
<c:set var="managerId" value="${empty pgvProfile.maGV ? sessionScope.LOGIN_USER : pgvProfile.maGV}" />

<c:set var="totalClassroomsValue" value="${empty totalClassrooms ? 0 : totalClassrooms}" />
<c:set var="totalStudentsValue" value="${empty totalStudents ? 0 : totalStudents}" />
<c:set var="totalTeachersValue" value="${empty totalTeachers ? 0 : totalTeachers}" />
<c:set var="totalSubjectsValue" value="${empty totalSubjects ? 0 : totalSubjects}" />

<style>
    body { background: #f4f7fb; }
    .pgv-dashboard { display: grid; gap: 1.5rem; }
    .profile-card {
        border-radius: 28px;
        overflow: hidden;
        border: 0;
        background: linear-gradient(135deg, #1d4ed8, #0284c7);
        box-shadow: 0 20px 45px rgba(2, 132, 199, 0.2);
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
    .manager-name { font-size: 2rem; font-weight: 700; margin-bottom: 0.4rem; }
    .manager-meta { font-size: 0.95rem; opacity: 0.92; margin-bottom: 0.25rem; }
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
    .summary-card { border: 0; border-radius: 28px; background: white; box-shadow: 0 12px 30px rgba(15, 23, 42, 0.06); }
    .summary-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem; }
    .summary-title { font-size: 1.25rem; font-weight: 700; color: #0f172a; }
    .table { margin-bottom: 0; }
    .table thead th { border: 0; background: #f8fafc; color: #64748b; font-size: 0.85rem; font-weight: 600; padding: 1rem; }
    .table tbody td { padding: 1rem; vertical-align: middle; border-color: #f1f5f9; }
    .badge-soft { background: #eff6ff; color: #1d4ed8; padding: 0.5rem 0.8rem; border-radius: 999px; font-size: 0.78rem; font-weight: 600; }
    .empty-state { padding: 2rem !important; color: #94a3b8; }
    @media (max-width: 992px) { .info-grid { grid-template-columns: repeat(2, minmax(0, 1fr)); } }
    @media (max-width: 576px) {
        .profile-top { flex-direction: column; text-align: center; }
        .manager-name { font-size: 1.6rem; }
        .info-grid { grid-template-columns: 1fr; }
        .summary-header { flex-direction: column; align-items: flex-start; gap: 0.75rem; }
    }
</style>

<div class="container-fluid pgv-dashboard">

    <section class="profile-card">
        <div class="profile-top">
            <div class="avatar-box">
                <c:choose>
                    <c:when test="${not empty pgvProfile.ten}">${fn:substring(pgvProfile.ten,0,1)}</c:when>
                    <c:otherwise>P</c:otherwise>
                </c:choose>
            </div>

            <div>
                <div class="manager-name">${managerName}</div>
                <div class="manager-meta"><i class="bi bi-person-badge"></i> Ma can bo: ${managerId}</div>
                <div class="manager-meta"><i class="bi bi-diagram-3"></i> Vai tro: PGV</div>
                <div class="manager-meta"><i class="bi bi-calendar-event"></i> Hom nay: <fmt:formatDate value="${today}" pattern="dd/MM/yyyy" /></div>
            </div>
        </div>

        <div class="info-grid">
            <div class="info-item">
                <div class="info-label">Tong lop hoc</div>
                <div class="info-value">${totalClassroomsValue}</div>
            </div>
            <div class="info-item">
                <div class="info-label">Tong sinh vien</div>
                <div class="info-value">${totalStudentsValue}</div>
            </div>
            <div class="info-item">
                <div class="info-label">Tong giang vien</div>
                <div class="info-value">${totalTeachersValue}</div>
            </div>
            <div class="info-item">
                <div class="info-label">Tong mon hoc</div>
                <div class="info-value">${totalSubjectsValue}</div>
            </div>
        </div>
    </section>

    <section class="card summary-card">
        <div class="card-body p-4">
            <div class="summary-header">
                <div class="summary-title">Tong hop xu ly gan day</div>
                <span class="badge text-bg-primary">Quan tri dao tao</span>
            </div>

            <div class="table-responsive">
                <table class="table align-middle">
                    <thead>
                        <tr>
                            <th>Nghiep vu</th>
                            <th>Doi tuong</th>
                            <th>So luong</th>
                            <th>Cap nhat luc</th>
                            <th>Trang thai</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty pgvActivities}">
                                <c:forEach var="item" items="${pgvActivities}">
                                    <tr>
                                        <td class="fw-semibold">${item.operationName}</td>
                                        <td>${item.targetGroup}</td>
                                        <td>${item.quantity}</td>
                                        <td>${item.updatedAt}</td>
                                        <td><span class="badge-soft">${empty item.status ? 'On dinh' : item.status}</span></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5" class="text-center empty-state">Chua co du lieu tong hop gan day.</td>
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
