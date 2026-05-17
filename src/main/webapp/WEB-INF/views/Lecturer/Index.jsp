<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setAttribute("pageTitle", "Quản lý Giảng viên");
%>
<%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Danh sách Giảng viên</h1>
    </div>

    <div class="card border-0 shadow-sm mb-4 p-3">
        <form id="lecturerForm" action="${pageContext.request.contextPath}/lecturer/save" method="post" class="row g-3 align-items-end">
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Mã GV</label>
                <input type="text" name="maGV" class="form-control form-control-sm" placeholder="Mã giảng viên" value="${teacher.maGV}" />
            </div>
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Họ</label>
                <input type="text" name="ho" class="form-control form-control-sm" placeholder="Họ" value="${teacher.ho}" />
            </div>
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Tên</label>
                <input type="text" name="ten" class="form-control form-control-sm" placeholder="Tên" value="${teacher.ten}" />
            </div>
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">SĐT</label>
                <input type="text" name="soDT" class="form-control form-control-sm" placeholder="Số điện thoại" value="${teacher.soDT}" />
            </div>
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Địa chỉ</label>
                <input type="text" name="diaChi" class="form-control form-control-sm" placeholder="Địa chỉ" value="${teacher.diaChi}" />
            </div>
            <div class="col-md-2 text-end">
                <button class="btn btn-primary me-1" type="button" onclick="clearLecturerForm()">Thêm</button>
                <button class="btn btn-success me-1" type="submit">Ghi</button>
                <button class="btn btn-outline-secondary me-1" type="reset">Phục hồi</button>
                <button class="btn btn-outline-danger me-1" type="submit" formaction="${pageContext.request.contextPath}/lecturer/delete" formmethod="post">Xóa</button>
                <button class="btn btn-outline-primary" type="button" onclick="document.getElementById('lecturerForm').submit()">Hiệu chỉnh</button>
            </div>
            <div class="col-md-5">
                <label class="form-label text-sm fw-medium">Tìm kiếm</label>
                <div class="input-group input-group-sm">
                    <input type="text" name="search" class="form-control" placeholder="Mã GV hoặc Tên GV" value="${search}" />
                    <button class="btn btn-outline-secondary" type="submit" form="searchForm">
                        <i class="bi bi-search"></i>
                    </button>
                </div>
            </div>
            <div class="col-md-7 text-end">
                <button class="btn btn-outline-primary" type="submit" form="searchForm">Tìm</button>
            </div>
        </form>
        <form id="searchForm" action="${pageContext.request.contextPath}/lecturer" method="get" class="d-none"></form>
    </div>

    <div class="card border-0 shadow-sm">
        <div class="table-responsive p-3">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th scope="col">Mã GV</th>
                        <th scope="col">Họ và Tên</th>
                        <th scope="col">Số điện thoại</th>
                        <th scope="col">Địa chỉ</th>
                        <th scope="col" class="text-end">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${teachers}" var="item">
                        <tr>
                            <td class="fw-medium text-success">${item.maGV}</td>
                            <td>${item.ho} ${item.ten}</td>
                            <td>${item.soDT}</td>
                            <td>${item.diaChi}</td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary me-1" href="${pageContext.request.contextPath}/lecturer?maGV=${item.maGV}&search=${search}">
                                    <i class="bi bi-pencil"></i>
                                </a>
                                <form action="${pageContext.request.contextPath}/lecturer/delete" method="post" class="d-inline">
                                    <input type="hidden" name="maGV" value="${item.maGV}" />
                                    <button class="btn btn-sm btn-outline-danger" type="submit">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty teachers}">
                        <tr>
                            <td colspan="5" class="text-center text-muted">Không có giảng viên.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    function clearLecturerForm() {
        var form = document.getElementById('lecturerForm');
        form.reset();
        form.elements['maGV'].value = '';
        form.elements['ho'].value = '';
        form.elements['ten'].value = '';
        form.elements['soDT'].value = '';
        form.elements['diaChi'].value = '';
    }
</script>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
