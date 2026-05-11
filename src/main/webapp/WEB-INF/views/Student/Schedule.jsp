<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <% request.setAttribute("pageTitle",
"Lịch học"); %> <%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <div>
      <h2 class="h3 fw-bold mb-1">Thời khóa biểu</h2>
      <div class="d-flex align-items-center gap-3 text-muted small">
        <span
          ><i class="bi bi-calendar-event me-1"></i> Học kỳ 1 - 2024-2025</span
        >
        <span class="text-primary fw-medium"
          ><i class="bi bi-clock-history me-1"></i> Tuần 12 (Hiện tại)</span
        >
      </div>
    </div>
  </div>

  <div class="alert alert-info" role="alert">
    <i class="bi bi-info-circle me-2"></i>
    <strong>Lưu ý:</strong> Bảng lịch học dưới đây hiển thị chi tiết các buổi
    học trong tuần hiện tại
  </div>

  <div class="card border-0 shadow-sm mb-4">
    <div class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="bg-light">
          <tr>
            <th class="ps-4">Thứ</th>
            <th>Môn học / Tiết học</th>
            <th>Địa điểm</th>
            <th>Giảng viên</th>
            <th class="text-center pe-4">Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="ps-4 fw-bold text-primary">Thứ 2</td>
            <td>
              <div class="d-flex flex-column">
                <span class="fw-bold">Cơ sở dữ liệu</span>
                <span class="text-xs text-muted"
                  ><i class="bi bi-clock me-1"></i>07:00 - 09:30 (Tiết
                  1-3)</span
                >
              </div>
            </td>
            <td><span class="badge bg-info text-dark">Phòng H301</span></td>
            <td>ThS. Nguyễn Văn A</td>
            <td class="text-center pe-4">
              <span class="badge bg-success">Bình thường</span>
            </td>
          </tr>
          <tr>
            <td class="ps-4 fw-bold text-primary">Thứ 3</td>
            <td>
              <div class="d-flex flex-column">
                <span class="fw-bold">Mạng máy tính</span>
                <span class="text-xs text-muted"
                  ><i class="bi bi-clock me-1"></i>09:30 - 11:30 (Tiết
                  4-6)</span
                >
              </div>
            </td>
            <td><span class="badge bg-warning text-dark">Phòng H205</span></td>
            <td>PGS. Lê Văn C</td>
            <td class="text-center pe-4">
              <span class="badge bg-info">Học bù</span>
            </td>
          </tr>
          <tr>
            <td class="ps-4 fw-bold text-primary">Thứ 4</td>
            <td>
              <div class="d-flex flex-column">
                <span class="fw-bold">Lập trình Web</span>
                <span class="text-xs text-muted"
                  ><i class="bi bi-clock me-1"></i>13:30 - 16:00 (Tiết
                  7-9)</span
                >
              </div>
            </td>
            <td><span class="badge bg-success text-dark">Lab 2</span></td>
            <td>TS. Trần Thị B</td>
            <td class="text-center pe-4">
              <span class="badge bg-success">Bình thường</span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
