<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <% request.setAttribute("pageTitle",
"Quản lý Sinh viên"); %> <%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="h3 mb-0">Hồ sơ Sinh viên</h1>
    <button class="btn btn-primary">
      <i class="bi bi-person-plus me-2"></i> Thêm Sinh viên
    </button>
  </div>

  <div class="card border-0 shadow-sm mb-4 p-3">
    <form class="row g-3">
      <div class="col-md-3">
        <label class="form-label text-sm fw-medium">Khoa</label>
        <select class="form-select form-select-sm">
          <option selected>Công nghệ thông tin</option>
          <option>Viễn thông</option>
        </select>
      </div>
      <div class="col-md-3">
        <label class="form-label text-sm fw-medium">Lớp</label>
        <select class="form-select form-select-sm">
          <option selected>D15CQCN01-N</option>
          <option>D15CQCN02-N</option>
        </select>
      </div>
      <div class="col-md-4">
        <label class="form-label text-sm fw-medium">Tìm kiếm</label>
        <div class="input-group input-group-sm">
          <input
            type="text"
            class="form-control"
            placeholder="Mã SV hoặc Tên SV"
          />
          <button class="btn btn-outline-secondary" type="button">
            <i class="bi bi-search"></i>
          </button>
        </div>
      </div>
    </form>
  </div>

  <div class="card border-0 shadow-sm">
    <div class="table-responsive p-3">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th scope="col">Mã SV</th>
            <th scope="col">Họ và Tên</th>
            <th scope="col">Phái</th>
            <th scope="col">Ngày sinh</th>
            <th scope="col">Mã Lớp</th>
            <th scope="col" class="text-end">Hành động</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="fw-medium text-primary">N15DCCN001</td>
            <td>Nguyễn Văn A</td>
            <td>Nam</td>
            <td>01/01/1997</td>
            <td>D15CQCN01-N</td>
            <td class="text-end">
              <button class="btn btn-sm btn-outline-info me-1" title="Chi tiết">
                <i class="bi bi-eye"></i>
              </button>
              <button class="btn btn-sm btn-outline-primary me-1">
                <i class="bi bi-pencil"></i>
              </button>
              <button class="btn btn-sm btn-outline-danger">
                <i class="bi bi-trash"></i>
              </button>
            </td>
          </tr>
          <tr>
            <td class="fw-medium text-primary">N15DCCN002</td>
            <td>Trần Thị B</td>
            <td>Nữ</td>
            <td>15/05/1997</td>
            <td>D15CQCN01-N</td>
            <td class="text-end">
              <button class="btn btn-sm btn-outline-info me-1" title="Chi tiết">
                <i class="bi bi-eye"></i>
              </button>
              <button class="btn btn-sm btn-outline-primary me-1">
                <i class="bi bi-pencil"></i>
              </button>
              <button class="btn btn-sm btn-outline-danger">
                <i class="bi bi-trash"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
