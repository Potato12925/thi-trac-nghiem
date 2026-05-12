<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setAttribute("pageTitle", "Quản lý Môn học");
%>
<%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Danh mục Môn học</h1>
    </div>

    <div class="card border-0 shadow-sm mb-4 p-3">
        <form id="subjectForm" action="${pageContext.request.contextPath}/subject/save" method="post" class="row g-3 align-items-end">
            <div class="col-md-2">
                <label class="form-label text-sm fw-medium">Mã MH</label>
                <input type="text" name="maMH" class="form-control form-control-sm" placeholder="MAMH" value="${subject.maMH}" />
            </div>
            <div class="col-md-4">
                <label class="form-label text-sm fw-medium">Tên Môn học</label>
                <input type="text" name="tenMH" class="form-control form-control-sm" placeholder="Nhập tên môn học" value="${subject.tenMH}" />
            </div>
            <div class="col-md-3">
                <label class="form-label text-sm fw-medium">Tìm kiếm</label>
                <div class="input-group input-group-sm">
                    <input type="text" name="search" class="form-control" placeholder="Mã MH hoặc Tên MH" value="${search}" />
                    <button class="btn btn-outline-secondary" type="submit" form="searchForm">
                        <i class="bi bi-search"></i>
                    </button>
                </div>
            </div>
            <div class="col-md-3 text-end">
                <button class="btn btn-primary me-1" type="button" onclick="clearSubjectForm()">Thêm</button>
                <button class="btn btn-outline-primary me-1" type="button" onclick="document.getElementById('subjectForm').submit()">Hiệu chỉnh</button>
                <button class="btn btn-success me-1" type="submit">Ghi</button>
                <button class="btn btn-outline-secondary me-1" type="reset">Phục hồi</button>
                <button class="btn btn-outline-danger" type="button" onclick="submitDelete()">Xóa</button>
            </div>
        </form>
        <form id="searchForm" action="${pageContext.request.contextPath}/subject" method="get" class="d-none"></form>
    </div>

    <div class="card border-0 shadow-sm">
        <div class="table-responsive p-3">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th scope="col">Mã MH</th>
                        <th scope="col">Tên Môn học</th>
                        <th scope="col" class="text-end">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${subjects}" var="item">
                        <tr>
                            <td class="fw-medium text-primary">${item.maMH}</td>
                            <td>${item.tenMH}</td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary me-1" href="${pageContext.request.contextPath}/subject?maMH=${item.maMH}&search=${search}">
                                    <i class="bi bi-pencil"></i>
                                </a>
                                <form action="${pageContext.request.contextPath}/subject/delete" method="post" class="d-inline">
                                    <input type="hidden" name="maMH" value="${item.maMH}" />
                                    <button class="btn btn-sm btn-outline-danger" type="submit">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty subjects}">
                        <tr>
                            <td colspan="3" class="text-center text-muted">Không có môn học nào.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<form id="deleteForm" action="${pageContext.request.contextPath}/subject/delete" method="post">
    <input type="hidden" name="maMH" id="deleteMaMH" />
</form>

<script>
    function clearSubjectForm() {
        var form = document.getElementById('subjectForm');
        form.reset();
        form.elements['maMH'].value = '';
        form.elements['tenMH'].value = '';
    }

    function submitDelete() {
        var value = document.getElementById('subjectForm').elements['maMH'].value;
        if (!value) {
            return;
        }
        document.getElementById('deleteMaMH').value = value;
        document.getElementById('deleteForm').submit();
    }
</script>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
