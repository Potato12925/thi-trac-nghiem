<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <% request.setAttribute("pageTitle",
"Kết quả học tập"); %> <%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <div>
      <h2 class="h3 fw-bold mb-1">Kết quả học tập</h2>
      <p class="text-muted small mb-0">
        <i class="bi bi-info-circle me-1"></i> Xem điểm các môn học và kết quả
        tích lũy
      </p>
    </div>
    <button class="btn btn-primary d-inline-flex align-items-center gap-2 px-4">
      <i class="bi bi-file-earmark-pdf"></i> Xuất bảng điểm (PDF)
    </button>
  </div>

  <div class="row g-3 mb-4">
    <div class="col-12 col-sm-6 col-lg-3">
      <div class="card border-0 shadow-sm h-100">
        <div class="card-body">
          <div class="d-flex align-items-center gap-3">
            <div
              style="
                width: 50px;
                height: 50px;
                border-radius: 50%;
                background: #0b5ed7;
                color: #fff;
                display: grid;
                place-items: center;
              "
            >
              <i class="bi bi-book"></i>
            </div>
            <div>
              <div class="fw-bold">69/87</div>
              <div class="text-muted small">Tín chỉ tích lũy</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-12 col-sm-6 col-lg-3">
      <div class="card border-0 shadow-sm h-100">
        <div class="card-body">
          <div class="d-flex align-items-center gap-3">
            <div
              style="
                width: 50px;
                height: 50px;
                border-radius: 50%;
                background: #198754;
                color: #fff;
                display: grid;
                place-items: center;
              "
            >
              <i class="bi bi-graph-up"></i>
            </div>
            <div>
              <div class="fw-bold text-success">3.45</div>
              <div class="text-muted small">GPA tích lũy</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-12 col-sm-6 col-lg-3">
      <div class="card border-0 shadow-sm h-100">
        <div class="card-body">
          <div class="d-flex align-items-center gap-3">
            <div
              style="
                width: 50px;
                height: 50px;
                border-radius: 50%;
                background: #6c63ff;
                color: #fff;
                display: grid;
                place-items: center;
              "
            >
              <i class="bi bi-award"></i>
            </div>
            <div>
              <div class="fw-bold text-primary">Khá</div>
              <div class="text-muted small">Xếp loại học lực</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-12 col-sm-6 col-lg-3">
      <div class="card border-0 shadow-sm h-100">
        <div class="card-body">
          <div class="d-flex align-items-center gap-3">
            <div
              style="
                width: 50px;
                height: 50px;
                border-radius: 50%;
                background: #fd7e14;
                color: #fff;
                display: grid;
                place-items: center;
              "
            >
              <i class="bi bi-calendar-check"></i>
            </div>
            <div>
              <div class="fw-bold">2</div>
              <div class="text-muted small">Học kỳ hoàn thành</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="card border-0 shadow-sm p-0 overflow-hidden">
    <div class="p-4 bg-primary bg-gradient text-white">
      <h3 class="h6 fw-bold mb-4">
        <i class="bi bi-lightning-charge-fill"></i> Tóm tắt Học kỳ 1 - 2025-2026
      </h3>
      <div class="row g-4">
        <div class="col-4">
          <div class="text-white text-opacity-75 small mb-1">Số môn học</div>
          <div class="h3 fw-bold mb-0">6</div>
        </div>
        <div class="col-4">
          <div class="text-white text-opacity-75 small mb-1">Tín chỉ HK</div>
          <div class="h3 fw-bold mb-0">18</div>
        </div>
        <div class="col-4">
          <div class="text-white text-opacity-75 small mb-1">GPA HK</div>
          <div class="h3 fw-bold mb-0">3.25</div>
        </div>
      </div>
    </div>

    <div class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="bg-light">
          <tr>
            <th class="ps-4">Mã MH</th>
            <th>Tên môn học</th>
            <th class="text-center">Tín chỉ</th>
            <th class="text-center">Giữa kỳ</th>
            <th class="text-center">Cuối kỳ</th>
            <th class="text-center">Trung bình</th>
            <th class="text-center">Điểm chữ</th>
            <th class="pe-4 text-center">Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="ps-4">
              <span class="text-muted font-monospace">IT301</span>
            </td>
            <td class="fw-semibold">Cơ sở dữ liệu</td>
            <td class="text-center">3</td>
            <td class="text-center">8.5</td>
            <td class="text-center">7.5</td>
            <td class="text-center">
              <span class="fw-bold text-primary">7.8</span>
            </td>
            <td class="text-center"><span class="badge bg-info">B+</span></td>
            <td class="pe-4 text-center">
              <span class="badge bg-success">Đã hoàn thành</span>
            </td>
          </tr>
          <tr>
            <td class="ps-4">
              <span class="text-muted font-monospace">IT302</span>
            </td>
            <td class="fw-semibold">Lập trình Web</td>
            <td class="text-center">3</td>
            <td class="text-center">9.0</td>
            <td class="text-center">8.5</td>
            <td class="text-center">
              <span class="fw-bold text-primary">8.7</span>
            </td>
            <td class="text-center"><span class="badge bg-success">A</span></td>
            <td class="pe-4 text-center">
              <span class="badge bg-success">Đã hoàn thành</span>
            </td>
          </tr>
          <tr>
            <td class="ps-4">
              <span class="text-muted font-monospace">IT303</span>
            </td>
            <td class="fw-semibold">Mạng máy tính</td>
            <td class="text-center">3</td>
            <td class="text-center">7.0</td>
            <td class="text-center">--</td>
            <td class="text-center text-muted">--</td>
            <td class="text-center text-muted">--</td>
            <td class="pe-4 text-center">
              <span class="badge bg-warning">Đang cập nhật</span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
