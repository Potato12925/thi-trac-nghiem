<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <% request.setAttribute("pageTitle",
"Đăng ký tín chỉ"); %> <%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <div>
      <h2 class="h3 fw-bold mb-1">Đăng ký tín chỉ</h2>
      <p class="text-muted small">
        <i class="bi bi-info-circle me-1"></i> Đăng ký các lớp tín chỉ trong học
        kỳ mới
      </p>
    </div>
  </div>

  <div class="card border-0 shadow-sm mb-4 p-3">
    <div class="row align-items-center">
      <div class="col-md-auto mb-3 mb-md-0">
        <div
          style="
            width: 60px;
            height: 60px;
            border-radius: 50%;
            background: #0b5ed7;
            color: #fff;
            display: grid;
            place-items: center;
            font-weight: 700;
            font-size: 1.5rem;
          "
        >
          AN
        </div>
      </div>
      <div class="col">
        <div class="row g-4">
          <div class="col-6 col-md-3">
            <div class="text-muted small">Mã sinh viên</div>
            <div class="h6 mb-0">SV001</div>
          </div>
          <div class="col-6 col-md-3">
            <div class="text-muted small">Họ tên</div>
            <div class="h6 mb-0">Nguyễn Văn An</div>
          </div>
          <div class="col-6 col-md-3">
            <div class="text-muted small">Lớp</div>
            <div class="h6 mb-0">DH20CNTT01</div>
          </div>
          <div class="col-6 col-md-3">
            <div class="text-muted small">Niên khóa</div>
            <div class="h6 mb-0">2020 - 2024</div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="row g-4 mb-4">
    <div class="col-lg-8">
      <div class="card border-0 shadow-sm h-100">
        <div class="p-3">
          <div class="row g-3 mb-3 align-items-end">
            <div class="col-md-4">
              <label class="form-label small fw-bold">Niên khóa</label>
              <select class="form-select border-0 bg-light">
                <option value="2023-2024">2023-2024</option>
                <option value="2024-2025" selected>2024-2025</option>
              </select>
            </div>
            <div class="col-md-4">
              <label class="form-label small fw-bold">Học kỳ</label>
              <select class="form-select border-0 bg-light">
                <option value="1">Học kỳ 1</option>
                <option value="2">Học kỳ 2</option>
                <option value="3">Học kỳ 3</option>
              </select>
            </div>
            <div class="col-md-4">
              <label class="form-label small fw-bold">Tìm kiếm môn học</label>
              <div class="input-group input-group-sm">
                <span class="input-group-text bg-transparent border-0"
                  ><i class="bi bi-search"></i
                ></span>
                <input
                  type="text"
                  placeholder="Tên môn, mã môn..."
                  class="form-control border-0 bg-light"
                />
              </div>
            </div>
          </div>

          <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
              <thead class="bg-light">
                <tr>
                  <th class="ps-3 small fw-bold">Mã MH</th>
                  <th class="small fw-bold">Tên môn học</th>
                  <th class="text-center small fw-bold">Nhóm</th>
                  <th class="small fw-bold">Giảng viên</th>
                  <th class="text-center small fw-bold">Sĩ số</th>
                  <th class="pe-3 text-center small fw-bold">Thao tác</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="ps-3">
                    <span class="text-muted small">MH001</span>
                  </td>
                  <td><span class="fw-semibold">Cơ sở dữ liệu</span></td>
                  <td class="text-center">1</td>
                  <td><span class="small">ThS. Nguyễn Văn Anh</span></td>
                  <td class="text-center">28/40</td>
                  <td class="pe-3 text-center">
                    <button
                      class="btn btn-sm btn-outline-primary rounded-pill px-3"
                    >
                      <i class="bi bi-plus-circle"></i> Chọn
                    </button>
                  </td>
                </tr>
                <tr>
                  <td class="ps-3">
                    <span class="text-muted small">MH002</span>
                  </td>
                  <td>
                    <span class="fw-semibold">Lập trình hướng đối tượng</span>
                  </td>
                  <td class="text-center">1</td>
                  <td><span class="small">TS. Trần Thị Bảo</span></td>
                  <td class="text-center">35/40</td>
                  <td class="pe-3 text-center">
                    <button
                      class="btn btn-sm btn-outline-danger rounded-pill px-3"
                    >
                      <i class="bi bi-dash-circle"></i> Hủy
                    </button>
                  </td>
                </tr>
                <tr>
                  <td class="ps-3">
                    <span class="text-muted small">MH003</span>
                  </td>
                  <td>
                    <span class="fw-semibold"
                      >Cấu trúc dữ liệu và giải thuật</span
                    >
                  </td>
                  <td class="text-center">1</td>
                  <td><span class="small">PGS. Lê Văn Cường</span></td>
                  <td class="text-center text-danger">40/40</td>
                  <td class="pe-3 text-center">
                    <button
                      class="btn btn-sm btn-secondary rounded-pill px-3"
                      disabled
                    >
                      <i class="bi bi-plus-circle"></i> Đầy
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <div class="col-lg-4">
      <div class="card border-0 shadow-sm sticky-top" style="top: 5rem">
        <div class="p-3">
          <div class="d-flex align-items-center justify-content-between mb-4">
            <h3 class="h6 fw-bold mb-0">Danh sách đã chọn</h3>
            <span class="badge rounded-pill bg-primary">2</span>
          </div>

          <div style="max-height: 300px; overflow-y: auto">
            <div
              class="p-3 bg-light border-start border-4 border-primary rounded-end mb-2"
            >
              <div class="d-flex justify-content-between align-items-start">
                <div>
                  <div class="fw-bold small">Lập trình hướng đối tượng</div>
                  <div class="text-muted small">Nhóm 1 • MH002</div>
                </div>
                <button class="btn btn-sm text-danger p-0">
                  <i class="bi bi-trash"></i>
                </button>
              </div>
            </div>
            <div
              class="p-3 bg-light border-start border-4 border-primary rounded-end mb-2"
            >
              <div class="d-flex justify-content-between align-items-start">
                <div>
                  <div class="fw-bold small">Lập trình Web</div>
                  <div class="text-muted small">Nhóm 1 • MH007</div>
                </div>
                <button class="btn btn-sm text-danger p-0">
                  <i class="bi bi-trash"></i>
                </button>
              </div>
            </div>
          </div>

          <div class="mt-4 pt-4 border-top">
            <div class="d-flex justify-content-between align-items-center mb-3">
              <span class="text-muted small">Số tín chỉ đăng ký:</span>
              <span class="fw-bold">12</span>
            </div>
            <button class="btn btn-primary w-100 py-2 fw-bold">
              Xác nhận đăng ký <i class="bi bi-arrow-right ms-2"></i>
            </button>
            <p class="text-center text-muted small mt-3 mb-0">
              <i class="bi bi-shield-check me-1"></i> Hệ thống tự động ghi nhận
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
