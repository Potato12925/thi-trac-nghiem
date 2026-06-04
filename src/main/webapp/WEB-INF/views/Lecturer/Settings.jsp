<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<style>
    .settings-grid {
        display: grid;
        grid-template-columns: 1.1fr 1fr;
        gap: 1.5rem;
    }

    .settings-card {
        border: 0;
        border-radius: 24px;
        box-shadow: 0 16px 34px rgba(15, 23, 42, 0.08);
        overflow: hidden;
    }

    .settings-hero {
        background: linear-gradient(135deg, #0f766e, #0ea5e9 50%, #38bdf8);
        color: #fff;
    }

    .settings-hero .card-body {
        padding: 2rem;
    }

    .settings-title {
        font-size: 1.75rem;
        font-weight: 700;
        margin-bottom: 0.5rem;
    }

    .profile-list {
        display: grid;
        gap: 0.85rem;
        margin-top: 1.5rem;
    }

    .profile-item {
        background: rgba(255, 255, 255, 0.14);
        border-radius: 18px;
        padding: 1rem 1.1rem;
    }

    .profile-label {
        font-size: 0.82rem;
        opacity: 0.85;
        margin-bottom: 0.25rem;
    }

    .profile-value {
        font-size: 1rem;
        font-weight: 600;
    }

    .form-section + .form-section {
        margin-top: 1.25rem;
    }

    .section-title {
        font-size: 1.1rem;
        font-weight: 700;
        color: #0f172a;
        margin-bottom: 0.25rem;
    }

    .section-subtitle {
        color: #64748b;
        font-size: 0.92rem;
        margin-bottom: 1rem;
    }

    .otp-box {
        border: 1px dashed #a5f3fc;
        background: #f4feff;
        border-radius: 18px;
        padding: 1rem;
    }

    @media (max-width: 992px) {
        .settings-grid {
            grid-template-columns: 1fr;
        }
    }
</style>

<div class="container-fluid">
    <div class="d-flex align-items-center justify-content-between flex-wrap gap-2 mb-4">
        <div>
            <h1 class="h3 fw-bold mb-1">Cài đặt tài khoản</h1>
            <p class="text-muted mb-0">Xem thông tin cá nhân và cập nhật email, mật khẩu của bạn.</p>
        </div>
        <span class="badge text-bg-info px-3 py-2">Giảng viên</span>
    </div>

    <div class="settings-grid">
        <section class="card settings-card settings-hero">
            <div class="card-body">
                <div class="settings-title">${lecturerProfile.lastName} ${lecturerProfile.firstName}</div>
                <div class="opacity-75">Chỉ tài khoản hiện tại mới được truy cập và cập nhật thông tin này.</div>

                <div class="profile-list">
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

        <section class="d-grid gap-4">
            <div class="card settings-card">
                <div class="card-body p-4">
                    <div class="form-section">
                        <div class="section-title">Đổi email</div>
                        <div class="section-subtitle">Hệ thống sẽ gửi OTP 6 số tới email mới trước khi lưu thay đổi.</div>

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
                    </div>

                    <div class="form-section otp-box">
                        <div class="section-title">Xác nhận OTP</div>
                        <div class="section-subtitle mb-3">Email chỉ được cập nhật khi OTP đúng và chưa hết hạn.</div>

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

            <div class="card settings-card">
                <div class="card-body p-4">
                    <div class="section-title">Đổi mật khẩu</div>
                    <div class="section-subtitle">Mật khẩu hiện tại sẽ được kiểm tra với dữ liệu đã hash trong bảng tài khoản.</div>

                    <form:form modelAttribute="changePasswordDTO"
                               method="post"
                               action="${pageContext.request.contextPath}/lecturers/settings/password">
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
</div>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
