<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setAttribute("pageTitle", "Trang chủ");
%>
<%@ include file="../Shared/_LayoutStart.jsp"%>
<div class="container-fluid">
	<div class="card border-0 shadow-lg mb-5 p-5 text-center"
		style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: #fff;">
		<h1 class="display-5 fw-bold mb-4">Khám phá cổng thông tin
			QLDSV_HTC</h1>
		<p class="fs-5 mb-5">Trải nghiệm hệ thống quản lý điểm chuyên
			nghiệp, nhanh chóng và mượt mà dành riêng cho sinh viên và giảng
			viên.</p>
		<div class="d-flex flex-wrap justify-content-center gap-3">
			<a class="btn btn-light btn-lg px-4"
				href="${pageContext.request.contextPath}/auth/login"> <i
				class="bi bi-person-circle me-2"></i> Đăng nhập hệ thống
			</a> <a class="btn btn-outline-light btn-lg px-4" href="#quick-reports">
				<i class="bi bi-file-earmark-bar-graph me-2"></i> Xem báo cáo mẫu
			</a>
		</div>
	</div>

	<div id="quick-reports" class="mt-5 pt-3">
		<h2 class="h4 fw-bold mb-4 text-center">Tiện ích nội bộ</h2>
		<div class="row g-4 justify-content-center">
			<div class="col-md-6 col-lg-5">
				<div class="card h-100 border-0 shadow-sm">
					<div class="card-body">
						<div class="d-flex align-items-center gap-3 mb-3">
							<div
								style="width: 50px; height: 50px; border-radius: 50%; background: #0b5ed7; color: #fff; display: grid; place-items: center;">
								<i class="bi bi-file-earmark-pdf"></i>
							</div>
							<div>
								<h3 class="h6 fw-bold mb-1">Phiếu Điểm Cá Nhân</h3>
								<p class="text-muted small mb-0">Xuất PDF phiếu điểm của
									sinh viên</p>
							</div>
						</div>
						<p class="text-muted small mb-4">Hỗ trợ xuất phiếu điểm cho
							sinh viên để đánh giá quá trình học tập chi tiết và đầy đủ thông
							tin.</p>
						<a class="btn btn-outline-secondary w-100" href="#">Xuất phiếu
							điểm N15DCCN001</a>
					</div>
				</div>
			</div>

			<div class="col-md-6 col-lg-5">
				<div class="card h-100 border-0 shadow-sm">
					<div class="card-body">
						<div class="d-flex align-items-center gap-3 mb-3">
							<div
								style="width: 50px; height: 50px; border-radius: 50%; background: #198754; color: #fff; display: grid; place-items: center;">
								<i class="bi bi-file-earmark-spreadsheet"></i>
							</div>
							<div>
								<h3 class="h6 fw-bold mb-1">Danh Sách Lớp Tín Chỉ</h3>
								<p class="text-muted small mb-0">Xuất báo cáo dưới dạng PDF</p>
							</div>
						</div>
						<p class="text-muted small mb-4">Hiển thị danh sách lớp tín
							chỉ của khoa theo niên khóa, học kỳ tương ứng.</p>
						<a class="btn btn-outline-success w-100" href="#">Xuất danh
							sách mẫu (CNTT)</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="../Shared/_LayoutEnd.jsp"%>
