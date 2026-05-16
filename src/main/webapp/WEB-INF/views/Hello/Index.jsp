<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Online Quiz System" scope="request" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet" />
<style>
    :root {
        --oqs-primary: #0d6efd;
        --oqs-secondary: #2b8cff;
        --oqs-light: #f4f8ff;
        --oqs-text: #1b2a4a;
        --oqs-muted: #5f6b84;
        --oqs-white: #ffffff;
    }

    #main-content.app-content {
        padding: 0;
    }

    .landing-page {
        color: var(--oqs-text);
        background: #f7faff;
        overflow-x: hidden;
    }

    .landing-navbar {
        background: rgba(255, 255, 255, 0.92);
        backdrop-filter: blur(8px);
        box-shadow: 0 12px 30px rgba(13, 110, 253, 0.08);
    }

    .brand-dot {
        width: 36px;
        height: 36px;
        border-radius: 12px;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        background: linear-gradient(135deg, var(--oqs-primary), #36b4ff);
        color: #fff;
        font-size: 1rem;
    }

    .nav-link {
        color: #2c3f66;
        font-weight: 500;
    }

    .nav-link:hover {
        color: var(--oqs-primary);
    }

    .btn-login {
        border-radius: 999px;
        padding: 0.62rem 1.35rem;
        box-shadow: 0 10px 25px rgba(13, 110, 253, 0.28);
    }

    .hero {
        min-height: 100vh;
        background: radial-gradient(circle at top right, rgba(54, 180, 255, 0.35), transparent 35%),
            linear-gradient(120deg, #eaf3ff 0%, #ffffff 45%, #edf5ff 100%);
        display: flex;
        align-items: center;
        position: relative;
    }

    .hero-title {
        font-size: clamp(2rem, 5vw, 3.4rem);
        font-weight: 800;
        line-height: 1.2;
    }

    .hero-text {
        color: var(--oqs-muted);
        font-size: 1.08rem;
        max-width: 620px;
    }

    .hero-card {
        background: var(--oqs-white);
        border: 0;
        border-radius: 1.25rem;
        box-shadow: 0 24px 45px rgba(12, 81, 172, 0.14);
    }

    .hero-visual {
        border-radius: 1.25rem;
        overflow: hidden;
        box-shadow: 0 22px 40px rgba(13, 110, 253, 0.2);
        animation: floaty 5s ease-in-out infinite;
    }

    .hero-visual img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

    .feature-card {
        border: 0;
        border-radius: 1rem;
        box-shadow: 0 12px 28px rgba(14, 74, 152, 0.1);
        transition: transform 0.25s ease, box-shadow 0.25s ease;
        height: 100%;
    }

    .feature-card:hover {
        transform: translateY(-8px);
        box-shadow: 0 20px 35px rgba(14, 74, 152, 0.16);
    }

    .feature-icon {
        width: 56px;
        height: 56px;
        border-radius: 16px;
        background: linear-gradient(135deg, #e4f0ff, #f3f8ff);
        color: var(--oqs-primary);
        display: inline-flex;
        align-items: center;
        justify-content: center;
        font-size: 1.25rem;
    }

    .section-title {
        font-weight: 800;
        letter-spacing: -0.02em;
    }

    .about-wrap {
        background: linear-gradient(140deg, #f2f7ff 0%, #ffffff 100%);
        border-radius: 1.25rem;
        box-shadow: 0 14px 30px rgba(9, 65, 139, 0.1);
    }

    .about-point i {
        color: #0e85ff;
    }

    .cta-box {
        background: linear-gradient(130deg, #0d6efd, #36b4ff);
        border-radius: 1.2rem;
        color: #fff;
        box-shadow: 0 22px 42px rgba(13, 110, 253, 0.3);
    }

    .footer {
        background: #0f1f40;
        color: rgba(255, 255, 255, 0.9);
    }

    .footer a {
        color: rgba(255, 255, 255, 0.86);
        transition: color .2s ease;
    }

    .footer a:hover {
        color: #fff;
    }

    .social-link {
        width: 38px;
        height: 38px;
        border: 1px solid rgba(255, 255, 255, 0.35);
        border-radius: 50%;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        text-decoration: none;
    }

    .reveal {
        opacity: 0;
        transform: translateY(16px);
        transition: opacity 0.55s ease, transform 0.55s ease;
    }

    .reveal.show {
        opacity: 1;
        transform: translateY(0);
    }

    @keyframes floaty {
        0% { transform: translateY(0px); }
        50% { transform: translateY(-10px); }
        100% { transform: translateY(0px); }
    }

    @media (max-width: 991.98px) {
        .hero {
            padding-top: 92px;
            padding-bottom: 56px;
            min-height: auto;
        }
    }
</style>

<div class="landing-page">
    <nav class="navbar navbar-expand-lg landing-navbar fixed-top">
        <div class="container">
            <a class="navbar-brand d-flex align-items-center gap-2 fw-bold" href="#home">
                <span class="brand-dot"><i class="fa-solid fa-graduation-cap"></i></span>
                <span>Online Quiz System</span>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#landingNav" aria-controls="landingNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="landingNav">
                <ul class="navbar-nav ms-auto align-items-lg-center gap-lg-2">
                    <li class="nav-item"><a class="nav-link" href="#home">Trang chủ</a></li>
                    <li class="nav-item"><a class="nav-link" href="#about">Giới thiệu</a></li>
                    <li class="nav-item"><a class="nav-link" href="#features">Tính năng</a></li>
                    <li class="nav-item"><a class="nav-link" href="#contact">Liên hệ</a></li>
                    <li class="nav-item ms-lg-2 mt-2 mt-lg-0">
                        <a class="btn btn-primary btn-login" href="${pageContext.request.contextPath}/auth/login">Đăng nhập</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <section id="home" class="hero">
        <div class="container">
            <div class="row align-items-center g-4 g-lg-5">
                <div class="col-lg-7 reveal">
                    <div class="hero-card p-4 p-md-5">
                        <h1 class="hero-title mb-3">Nền tảng thi trắc nghiệm trực tuyến</h1>
                        <p class="hero-text mb-4">Hệ thống hỗ trợ tổ chức thi online nhanh chóng, tiện lợi và hiện đại dành cho sinh viên và giảng viên.</p>
                        <div class="d-flex flex-wrap gap-3">
                            <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-primary btn-lg px-4">Đăng nhập</a>
                            <a href="#features" class="btn btn-outline-primary btn-lg px-4">Tìm hiểu thêm</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-5 reveal">
                    <div class="hero-visual">
                        <img src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f?auto=format&fit=crop&w=900&q=80" alt="Education technology" />
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section id="features" class="py-5">
        <div class="container py-4">
            <div class="text-center mb-5 reveal">
                <h2 class="section-title mb-3">Tính năng nổi bật</h2>
                <p class="text-muted mb-0">Thiết kế cho môi trường giáo dục hiện đại, tối ưu trải nghiệm cho mọi vai trò sử dụng.</p>
            </div>
            <div class="row g-4">
                <div class="col-sm-6 col-lg-4 reveal">
                    <div class="card feature-card p-4">
                        <span class="feature-icon mb-3"><i class="fa-solid fa-laptop"></i></span>
                        <h3 class="h5 fw-bold">Thi online</h3>
                        <p class="text-muted mb-0">Tham gia kỳ thi trực tuyến linh hoạt trên nhiều thiết bị.</p>
                    </div>
                </div>
                <div class="col-sm-6 col-lg-4 reveal">
                    <div class="card feature-card p-4">
                        <span class="feature-icon mb-3"><i class="fa-solid fa-file-circle-plus"></i></span>
                        <h3 class="h5 fw-bold">Tạo đề thi</h3>
                        <p class="text-muted mb-0">Giảng viên xây dựng đề nhanh từ ngân hàng câu hỏi thông minh.</p>
                    </div>
                </div>
                <div class="col-sm-6 col-lg-4 reveal">
                    <div class="card feature-card p-4">
                        <span class="feature-icon mb-3"><i class="fa-solid fa-check-double"></i></span>
                        <h3 class="h5 fw-bold">Chấm điểm tự động</h3>
                        <p class="text-muted mb-0">Kết quả chính xác, giảm tải xử lý thủ công và tiết kiệm thời gian.</p>
                    </div>
                </div>
                <div class="col-sm-6 col-lg-4 reveal">
                    <div class="card feature-card p-4">
                        <span class="feature-icon mb-3"><i class="fa-solid fa-book-open-reader"></i></span>
                        <h3 class="h5 fw-bold">Quản lý môn học</h3>
                        <p class="text-muted mb-0">Tổ chức môn học, lớp học và nội dung kiểm tra tập trung.</p>
                    </div>
                </div>
                <div class="col-sm-6 col-lg-4 reveal">
                    <div class="card feature-card p-4">
                        <span class="feature-icon mb-3"><i class="fa-solid fa-chart-line"></i></span>
                        <h3 class="h5 fw-bold">Xem kết quả thi</h3>
                        <p class="text-muted mb-0">Sinh viên và giảng viên theo dõi điểm số, lịch sử thi rõ ràng.</p>
                    </div>
                </div>
                <div class="col-sm-6 col-lg-4 reveal">
                    <div class="card feature-card p-4">
                        <span class="feature-icon mb-3"><i class="fa-solid fa-user-shield"></i></span>
                        <h3 class="h5 fw-bold">Phân quyền người dùng</h3>
                        <p class="text-muted mb-0">Phù hợp cho Sinh viên, Giảng viên và Phòng giáo vụ.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section id="about" class="py-5">
        <div class="container py-4">
            <div class="about-wrap p-4 p-lg-5 reveal">
                <div class="row g-4 align-items-center">
                    <div class="col-lg-6">
                        <h2 class="section-title mb-3">Giải pháp thi trực tuyến toàn diện</h2>
                        <p class="text-muted mb-0">Online Quiz System giúp tổ chức thi trực tuyến dễ dàng, tối ưu quy trình từ tạo đề, tổ chức thi đến chấm điểm và tổng hợp kết quả cho nhà trường.</p>
                    </div>
                    <div class="col-lg-6">
                        <div class="row g-3">
                            <div class="col-12 about-point d-flex align-items-start gap-3">
                                <i class="fa-solid fa-circle-check mt-1"></i>
                                <span>Tiết kiệm thời gian cho giảng viên và phòng giáo vụ.</span>
                            </div>
                            <div class="col-12 about-point d-flex align-items-start gap-3">
                                <i class="fa-solid fa-circle-check mt-1"></i>
                                <span>Chấm điểm tự động, giảm sai sót trong đánh giá.</span>
                            </div>
                            <div class="col-12 about-point d-flex align-items-start gap-3">
                                <i class="fa-solid fa-circle-check mt-1"></i>
                                <span>Hỗ trợ học tập và kiểm tra hiệu quả cho sinh viên.</span>
                            </div>
                            <div class="col-12 about-point d-flex align-items-start gap-3">
                                <i class="fa-solid fa-circle-check mt-1"></i>
                                <span>Giao diện thân thiện, dễ sử dụng trên mọi thiết bị.</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="py-5">
        <div class="container py-2">
            <div class="cta-box text-center p-4 p-md-5 reveal">
                <h2 class="fw-bold mb-3">Sẵn sàng bắt đầu?</h2>
                <p class="mb-4 opacity-75">Đăng nhập để truy cập hệ thống thi trắc nghiệm trực tuyến của bạn ngay hôm nay.</p>
                <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-light btn-lg px-4">Đăng nhập hệ thống</a>
            </div>
        </div>
    </section>

    <footer id="contact" class="footer pt-5 pb-4">
        <div class="container">
            <div class="row g-4 align-items-start">
                <div class="col-lg-5">
                    <h5 class="fw-bold mb-3">Online Quiz System</h5>
                    <p class="mb-0">Nền tảng công nghệ giáo dục dành cho Sinh viên, Giảng viên và Phòng giáo vụ.</p>
                </div>
                <div class="col-sm-6 col-lg-3">
                    <h6 class="fw-semibold mb-3">Liên hệ</h6>
                    <p class="mb-2"><i class="fa-solid fa-envelope me-2"></i>support@onlinequiz.edu.vn</p>
                    <p class="mb-0"><i class="fa-solid fa-phone me-2"></i>Hotline: 1900 1234</p>
                </div>
                <div class="col-sm-6 col-lg-4">
                    <h6 class="fw-semibold mb-3">Kết nối</h6>
                    <div class="d-flex gap-2">
                        <a href="#" class="social-link" aria-label="Facebook"><i class="fa-brands fa-facebook-f"></i></a>
                        <a href="#" class="social-link" aria-label="LinkedIn"><i class="fa-brands fa-linkedin-in"></i></a>
                        <a href="#" class="social-link" aria-label="YouTube"><i class="fa-brands fa-youtube"></i></a>
                    </div>
                </div>
            </div>
            <hr class="my-4 border-light-subtle" />
            <p class="mb-0 text-center">&copy; 2026 Online Quiz System. All rights reserved.</p>
        </div>
    </footer>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var reveals = document.querySelectorAll('.reveal');

        var observer = new IntersectionObserver(function(entries) {
            entries.forEach(function(entry) {
                if (entry.isIntersecting) {
                    entry.target.classList.add('show');
                }
            });
        }, { threshold: 0.15 });

        reveals.forEach(function(item) {
            observer.observe(item);
        });
    });
</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
