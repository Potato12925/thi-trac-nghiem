<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<c:set var="showEmailPanel" value="${not empty changeEmailDTO.newEmail or not empty confirmEmailChangeDTO.newEmail or not empty confirmEmailChangeDTO.otpCode}" />
<c:set var="showPasswordPanel" value="${not empty changePasswordDTO.currentPassword or not empty changePasswordDTO.newPassword or not empty changePasswordDTO.confirmPassword}" />

<style>
    .settings-shell {
        display: grid;
        gap: 1.5rem;
    }

    .profile-card {
        width: min(100%, 760px);
        border: 0;
        border-radius: 26px;
        background: linear-gradient(135deg, #0f766e, #0ea5e9 50%, #38bdf8);
        color: #fff;
        box-shadow: 0 18px 36px rgba(14, 165, 233, 0.2);
    }

    .profile-card .card-body {
        padding: 1.75rem 1.75rem 1.5rem;
    }

    .profile-title {
        font-size: 1.75rem;
        font-weight: 700;
        margin-bottom: 0.35rem;
    }

    .profile-subtitle {
        opacity: 0.82;
        margin-bottom: 1.25rem;
    }

    .profile-grid {
        display: grid;
        grid-template-columns: repeat(2, minmax(0, 1fr));
        gap: 0.85rem;
    }

    .profile-item {
        background: rgba(255, 255, 255, 0.14);
        border-radius: 18px;
        padding: 0.95rem 1rem;
    }

    .profile-label {
        font-size: 0.8rem;
        opacity: 0.82;
        margin-bottom: 0.25rem;
    }

    .profile-value {
        font-weight: 600;
    }

    .settings-panel {
        border: 0;
        border-radius: 24px;
        box-shadow: 0 14px 30px rgba(15, 23, 42, 0.08);
        overflow: hidden;
    }

    .settings-toggle {
        width: 100%;
        border: 0;
        background: #fff;
        padding: 1.15rem 1.35rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
        text-align: left;
    }

    .settings-toggle:hover {
        background: #f8fafc;
    }

    .settings-toggle-title {
        font-size: 1.05rem;
        font-weight: 700;
        color: #0f172a;
    }

    .settings-toggle-subtitle {
        color: #64748b;
        font-size: 0.92rem;
        margin-top: 0.2rem;
    }

    .settings-toggle i.bi-chevron-down {
        transition: transform 0.2s ease;
    }

    .settings-toggle[aria-expanded="true"] i.bi-chevron-down {
        transform: rotate(180deg);
    }

    .settings-body {
        padding: 1.35rem;
        border-top: 1px solid #eef2f7;
        background: #fff;
    }

    .otp-box {
        border: 1px dashed #a5f3fc;
        background: #f4feff;
        border-radius: 18px;
        padding: 1rem;
        margin-top: 1rem;
    }

    @media (max-width: 768px) {
        .profile-grid {
            grid-template-columns: 1fr;
        }

        .profile-card {
            width: 100%;
        }
    }
</style>

<div class="container-fluid settings-shell">
    <div class="d-flex align-items-center justify-content-between flex-wrap gap-2">
        <div>
            <h1 class="h3 fw-bold mb-1">Cài đặt tài khoản</h1>
            <p class="text-muted mb-0">Xem thông tin cá nhân và cập nhật email, mật khẩu của bạn.</p>
        </div>
        <span class="badge text-bg-info px-3 py-2">Giảng viên</span>
    </div>

    <section class="card profile-card">
        <div class="card-body">
            <div class="profile-title">${lecturerProfile.lastName} ${lecturerProfile.firstName}</div>
            <div class="profile-subtitle">Thông tin cá nhân của tài khoản đang đăng nhập.</div>

            <div class="profile-grid">
                <div class="profile-item">
                    <div class="profile-label">Mã giảng viên</div>
                    <div class="profile-value">${lecturerProfile.lecturerId}</div>
                </div>
                <div class="profile-item">
                    <div class="profile-label">Số điện thoại</div>
                    <div class="profile-value">${empty lecturerProfile.phoneNumber ? 'Chưa cập nhật' : lecturerProfile.phoneNumber}</div>
                </div>
                <div class="profile-item">
                    <div class="profile-label">Địa chỉ</div>
                    <div class="profile-value">${empty lecturerProfile.address ? 'Chưa cập nhật' : lecturerProfile.address}</div>
                </div>
                <div class="profile-item">
                    <div class="profile-label">Email hiện tại</div>
                    <div class="profile-value">${empty lecturerProfile.email ? 'Chưa cập nhật' : lecturerProfile.email}</div>
                </div>
            </div>
        </div>
    </section>

    <section class="card settings-panel">
        <button class="settings-toggle"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#lecturerEmailSettings"
                aria-expanded="${showEmailPanel ? 'true' : 'false'}"
                aria-controls="lecturerEmailSettings">
            <span>
                <span class="settings-toggle-title">Đổi email</span>
                <span class="d-block settings-toggle-subtitle">Bấm để nhập email mới và xác nhận OTP.</span>
            </span>
            <i class="bi bi-chevron-down"></i>
        </button>

        <div id="lecturerEmailSettings" class="collapse ${showEmailPanel ? 'show' : ''}">
            <div class="settings-body">
                <form:form modelAttribute="changeEmailDTO"
                           method="post"
                           action="${pageContext.request.contextPath}/lecturers/settings/email/send-otp">
                    <div class="mb-3">
                        <label class="form-label">Email mới</label>
                        <form:input path="newEmail" cssClass="form-control" placeholder="nhap-email-moi@example.com" />
                        <form:errors path="newEmail" cssClass="text-danger small mt-1 d-block" />
                    </div>
                    <button type="submit" class="btn btn-info text-white">
                        <i class="bi bi-envelope-paper me-2"></i>Gửi OTP
                    </button>
                </form:form>

                <div class="otp-box">
                    <div class="fw-semibold mb-3">Xác nhận OTP</div>
                    <form:form modelAttribute="confirmEmailChangeDTO"
                               method="post"
                               action="${pageContext.request.contextPath}/lecturers/settings/email/confirm">
                        <div class="mb-3">
                            <label class="form-label">Email mới</label>
                            <form:input path="newEmail" cssClass="form-control" placeholder="nhap-email-moi@example.com" />
                            <form:errors path="newEmail" cssClass="text-danger small mt-1 d-block" />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mã OTP</label>
                            <form:input path="otpCode" cssClass="form-control" maxlength="6" placeholder="Nhập 6 chữ số OTP" />
                            <form:errors path="otpCode" cssClass="text-danger small mt-1 d-block" />
                        </div>
                        <button type="submit" class="btn btn-success">
                            <i class="bi bi-shield-check me-2"></i>Xác nhận đổi email
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </section>

    <section class="card settings-panel">
        <button class="settings-toggle"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#lecturerPasswordSettings"
                aria-expanded="${showPasswordPanel ? 'true' : 'false'}"
                aria-controls="lecturerPasswordSettings">
            <span>
                <span class="settings-toggle-title">Đổi mật khẩu</span>
                <span class="d-block settings-toggle-subtitle">Bấm để mở form đổi mật khẩu.</span>
            </span>
            <i class="bi bi-chevron-down"></i>
        </button>

        <div id="lecturerPasswordSettings" class="collapse ${showPasswordPanel ? 'show' : ''}">
            <div class="settings-body">
                <form:form modelAttribute="changePasswordDTO"
                           method="post"
                           action="${pageContext.request.contextPath}/auth/change-password">
                    <div class="mb-3">
                        <label class="form-label">Mật khẩu hiện tại</label>
                        <form:password path="currentPassword" cssClass="form-control" />
                        <form:errors path="currentPassword" cssClass="text-danger small mt-1 d-block" />
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mật khẩu mới</label>
                        <form:password path="newPassword" cssClass="form-control" />
                        <form:errors path="newPassword" cssClass="text-danger small mt-1 d-block" />
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Xác nhận mật khẩu mới</label>
                        <form:password path="confirmPassword" cssClass="form-control" />
                        <form:errors path="confirmPassword" cssClass="text-danger small mt-1 d-block" />
                    </div>
                    <button type="submit" class="btn btn-dark">
                        <i class="bi bi-key me-2"></i>Đổi mật khẩu
                    </button>
                </form:form>
            </div>
        </div>
    </section>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
