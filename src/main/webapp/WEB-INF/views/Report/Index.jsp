<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <% request.setAttribute("pageTitle",
"Báo cáo - In ấn"); %> <%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="h3 mb-0">Báo cáo & In ấn</h1>
  </div>

  <div class="alert alert-info d-flex align-items-center mb-4" role="alert">
    <i class="bi bi-info-circle-fill me-2 fs-4"></i>
    <div>
      Màn hình này hiển thị các chức năng in báo cáo theo yêu cầu của hệ thống.
    </div>
  </div>

  <div class="row g-4">
    <div class="col-md-4">
      <div class="card h-100 border-0 shadow-sm p-4 text-center">
        <div class="mb-3 text-primary">
          <i class="bi bi-list-columns-reverse fs-1"></i>
        </div>
        <h5 class="fw-bold mb-2">Danh Sách Lớp Tín Chỉ</h5>
        <p class="text-muted small mb-4">
          In danh sách các lớp tín chỉ được mở trong một học kỳ của một niên
          khóa.
        </p>
        <button type="button" class="btn btn-outline-primary">
          <i class="bi bi-printer me-1"></i> In Báo Cáo
        </button>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card h-100 border-0 shadow-sm p-4 text-center">
        <div class="mb-3 text-info">
          <i class="bi bi-people fs-1"></i>
        </div>
        <h5 class="fw-bold mb-2">Danh Sách SV Đăng Ký LTC</h5>
        <p class="text-muted small mb-4">
          In danh sách chi tiết sinh viên đã đăng ký vào một lớp tín chỉ cụ thể.
        </p>
        <button type="button" class="btn btn-outline-info">
          <i class="bi bi-printer me-1"></i> In Báo Cáo
        </button>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card h-100 border-0 shadow-sm p-4 text-center">
        <div class="mb-3 text-success">
          <i class="bi bi-journal-check fs-1"></i>
        </div>
        <h5 class="fw-bold mb-2">Bảng Điểm Môn Học</h5>
        <p class="text-muted small mb-4">
          In bảng điểm tổng kết môn học của một lớp tín chỉ (đã nhập xong điểm).
        </p>
        <button type="button" class="btn btn-outline-success">
          <i class="bi bi-printer me-1"></i> In Báo Cáo
        </button>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card h-100 border-0 shadow-sm p-4 text-center">
        <div class="mb-3 text-warning">
          <i class="bi bi-person-lines-fill fs-1"></i>
        </div>
        <h5 class="fw-bold mb-2">Phiếu Điểm Của Sinh Viên</h5>
        <p class="text-muted small mb-4">
          In phiếu điểm tất cả các môn học mà một sinh viên (Mã SV) đã thi và có
          điểm.
        </p>
        <button type="button" class="btn btn-outline-warning">
          <i class="bi bi-printer me-1"></i> In Báo Cáo
        </button>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card h-100 border-0 shadow-sm p-4 text-center">
        <div class="mb-3 text-danger">
          <i class="bi bi-award fs-1"></i>
        </div>
        <h5 class="fw-bold mb-2">Bảng Điểm Tổng Kết Lớp</h5>
        <p class="text-muted small mb-4">
          In bảng điểm tổng kết toàn bộ môn học cho toàn sinh viên trong một
          Lớp.
        </p>
        <button type="button" class="btn btn-outline-danger">
          <i class="bi bi-printer me-1"></i> In Báo Cáo
        </button>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card h-100 border-0 shadow-sm p-4 text-center">
        <div class="mb-3 text-secondary">
          <i class="bi bi-cash-coin fs-1"></i>
        </div>
        <h5 class="fw-bold mb-2">Danh Sách Học Phí</h5>
        <p class="text-muted small mb-4">
          In danh sách đóng học phí của một Lớp trong một Học kỳ và Niên khóa
          xác định.
        </p>
        <button type="button" class="btn btn-outline-secondary">
          <i class="bi bi-printer me-1"></i> In Báo Cáo
        </button>
      </div>
    </div>
  </div>
</div>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
