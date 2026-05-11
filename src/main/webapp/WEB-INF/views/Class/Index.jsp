<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <% request.setAttribute("pageTitle",
"Quản lý Lớp"); %> <%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h1 class="h3 mb-0">Quản lý Lớp Cử Nhân / Kỹ Sư</h1>
    <button class="btn btn-primary">
      <i class="bi bi-plus-circle me-2"></i> Thêm Lớp Mới
    </button>
  </div>
  <div class="card border-0 shadow-sm">
    <div class="table-responsive p-3">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th scope="col">Mã Lớp</th>
            <th scope="col">Tên Lớp</th>
            <th scope="col">Khóa Học</th>
            <th scope="col">Mã Khoa</th>
            <th scope="col" class="text-end">Hành động</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="fw-medium">D15CQCN01-N</td>
            <td>Công nghệ thông tin 01</td>
            <td>2015</td>
            <td>CNTT</td>
            <td class="text-end">
              <button class="btn btn-sm btn-outline-primary me-1">
                <i class="bi bi-pencil"></i>
              </button>
              <button class="btn btn-sm btn-outline-danger">
                <i class="bi bi-trash"></i>
              </button>
            </td>
          </tr>
          <tr>
            <td class="fw-medium">D15CQVT01-N</td>
            <td>Viễn thông 01</td>
            <td>2015</td>
            <td>VT</td>
            <td class="text-end">
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
    <div
      class="p-3 border-top d-flex justify-content-between align-items-center"
    >
      <span class="text-muted">Đang hiển thị 1 đến 10 của 45 mục</span>
      <nav>
        <ul class="pagination pagination-sm mb-0">
          <li class="page-item disabled">
            <a class="page-link" href="#">Trước</a>
          </li>
          <li class="page-item active"><a class="page-link" href="#">1</a></li>
          <li class="page-item"><a class="page-link" href="#">2</a></li>
          <li class="page-item"><a class="page-link" href="#">Sau</a></li>
        </ul>
      </nav>
    </div>
  </div>
</div>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
