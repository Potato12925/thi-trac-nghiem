<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
request.setAttribute("pageTitle", "Quản lý Sinh viên");
%>
<%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Hồ sơ Sinh viên</h1>
    </div>

    <div class="card border-0 shadow-sm mb-4 p-3">
        <h5 class="mb-3">Thông tin Lớp</h5>
        <form id="classForm" action="${pageContext.request.contextPath}/student/class/save" method="post" class="row g-3 align-items-end">
            <div class="col-md-3">
                <label class="form-label text-sm fw-medium">Mã Lớp</label>
                <input type="text" name="maLop" class="form-control form-control-sm" placeholder="Mã lớp" value="${selectedClass.maLop}" />
            </div>
            <div class="col-md-4">
                <label class="form-label text-sm fw-medium">Tên Lớp</label>
                <input type="text" name="tenLop" class="form-control form-control-sm" placeholder="Tên lớp" value="${selectedClass.tenLop}" />
            </div>
            <div class="col-md-3">
                <label class="form-label text-sm fw-medium">Tìm kiếm lớp</label>
                <div class="input-group input-group-sm">
                    <input type="text" name="classSearch" class="form-control" placeholder="Mã lớp hoặc tên lớp" value="${classSearch}" />
                    <button class="btn btn-outline-secondary" type="submit" form="classSearchForm">
                        <i class="bi bi-search"></i>
                    </button>
                </div>
            </div>
            <div class="col-md-2 text-end">
                <button class="btn btn-primary me-1" type="button" onclick="clearClassForm()">Thêm</button>
                <button class="btn btn-success me-1" type="submit">Ghi</button>
                <button class="btn btn-outline-secondary" type="reset">Phục hồi</button>
                <button class="btn btn-outline-danger" type="submit" formaction="${pageContext.request.contextPath}/student/class/delete" formmethod="post">Xóa</button>
            </div>
        </form>
        <form id="classSearchForm" action="${pageContext.request.contextPath}/student" method="get" class="d-none"></form>
    </div>

    <div class="card border-0 shadow-sm mb-4 p-3">
        <h5 class="mb-3">Thông tin Sinh viên</h5>
        <form id="studentForm" action="${pageContext.request.contextPath}/student/student/save" method="post" class="row g-3 align-items-end">
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Mã SV</label>
                <input type="text" name="maSV" class="form-control form-control-sm" placeholder="Mã sinh viên" value="${student.maSV}" />
            </div>
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Họ</label>
                <input type="text" name="ho" class="form-control form-control-sm" placeholder="Họ" value="${student.ho}" />
            </div>
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Tên</label>
                <input type="text" name="ten" class="form-control form-control-sm" placeholder="Tên" value="${student.ten}" />
            </div>
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Ngày sinh</label>
                <input type="date" name="ngaySinh" class="form-control form-control-sm" value="${student.ngaySinh}" />
            </div>
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Lớp</label>
                <select name="maLop" class="form-select form-select-sm">
                    <c:forEach items="${classes}" var="lop">
                        <option value="${lop.maLop}" ${lop.maLop == selectedClass.maLop ? 'selected' : ''}>${lop.maLop} - ${lop.tenLop}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2 text-end">
                <button class="btn btn-primary me-1" type="button" onclick="clearStudentForm()">Thêm</button>
                <button class="btn btn-success me-1" type="submit">Ghi</button>
                <button class="btn btn-outline-secondary me-1" type="reset">Phục hồi</button>
                <button class="btn btn-outline-danger" type="submit" formaction="${pageContext.request.contextPath}/student/student/delete" formmethod="post">Xóa</button>
            </div>
            <div class="col-md-8">
                <label class="form-label text-sm fw-medium">Địa chỉ</label>
                <input type="text" name="diaChi" class="form-control form-control-sm" placeholder="Địa chỉ" value="${student.diaChi}" />
            </div>
            <div class="col-md-4 text-end">
                <button class="btn btn-outline-primary me-1" type="button" onclick="document.getElementById('studentForm').submit()">Hiệu chỉnh</button>
                <button class="btn btn-outline-secondary" type="button" onclick="document.getElementById('studentSearchForm').submit()">Tìm</button>
            </div>
        </form>
        <form id="studentSearchForm" action="${pageContext.request.contextPath}/student" method="get" class="d-none">
            <input type="hidden" name="selectedClass" value="${selectedClass.maLop}" />
        </form>
    </div>

    <div class="card border-0 shadow-sm">
        <div class="table-responsive p-3">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th scope="col">Mã SV</th>
                        <th scope="col">Họ</th>
                        <th scope="col">Tên</th>
                        <th scope="col">Ngày sinh</th>
                        <th scope="col">Địa chỉ</th>
                        <th scope="col">Mã Lớp</th>
                        <th scope="col" class="text-end">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${students}" var="item">
                        <tr>
                            <td class="fw-medium text-primary">${item.maSV}</td>
                            <td>${item.ho}</td>
                            <td>${item.ten}</td>
                            <td><fmt:formatDate value="${item.ngaySinh}" pattern="yyyy-MM-dd" /></td>
                            <td>${item.diaChi}</td>
                            <td>${item.lop.maLop}</td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary me-1" href="${pageContext.request.contextPath}/student?maSV=${item.maSV}&selectedClass=${item.lop.maLop}">
                                    <i class="bi bi-pencil"></i>
                                </a>
                                <form action="${pageContext.request.contextPath}/student/student/delete" method="post" class="d-inline">
                                    <input type="hidden" name="maSV" value="${item.maSV}" />
                                    <input type="hidden" name="selectedClass" value="${item.lop.maLop}" />
                                    <button class="btn btn-sm btn-outline-danger" type="submit">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty students}">
                        <tr>
                            <td colspan="7" class="text-center text-muted">Không có sinh viên.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    function clearClassForm() {
        var form = document.getElementById('classForm');
        form.reset();
        form.elements['maLop'].value = '';
        form.elements['tenLop'].value = '';
    }

    function clearStudentForm() {
        var form = document.getElementById('studentForm');
        form.reset();
        form.elements['maSV'].value = '';
        form.elements['ho'].value = '';
        form.elements['ten'].value = '';
        form.elements['ngaySinh'].value = '';
        form.elements['diaChi'].value = '';
    }
</script>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
