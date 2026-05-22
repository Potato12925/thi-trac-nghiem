<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
request.setAttribute("pageTitle", "Quản lý Giảng viên");
%>
<%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Danh sách Giảng viên</h1>
    </div>

    <h3>${error}</h3>

    <div class="border rounded-3 bg-white p-4 mb-4">
        <form:form id="lecturerForm"
            action="${pageContext.request.contextPath}/lecturer/add" method="post"
            modelAttribute="giaoVienDTO">

            <input type="hidden" id="_method" name="_method" value="POST" />

            <div class="row g-3">
                <div class="col-md-2">
                    <label class="form-label small text-secondary">Mã GV</label>
                    <form:input path="teacherId" cssClass="form-control" />
                    <form:errors path="teacherId" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="col-md-2">
                    <label class="form-label small text-secondary">Họ</label>
                    <form:input path="lastName" cssClass="form-control" />
                    <form:errors path="lastName" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="col-md-2">
                    <label class="form-label small text-secondary">Tên</label>
                    <form:input path="firstName" cssClass="form-control" />
                    <form:errors path="firstName" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="col-md-2">
                    <label class="form-label small text-secondary">SĐT</label>
                    <form:input path="phoneNumber" cssClass="form-control" />
                    <form:errors path="phoneNumber" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="col-md-4">
                    <label class="form-label small text-secondary">Địa chỉ</label>
                    <form:input path="address" cssClass="form-control" />
                    <form:errors path="address" cssClass="text-danger small mt-1 d-block" />
                </div>
            </div>

            <div class="d-flex gap-2 mt-4">
                <button type="submit" class="btn btn-dark px-4"
                    onclick="submitForm('add', 'POST')">
                    Thêm
                </button>
                <button type="submit" class="btn btn-outline-secondary px-4"
                    onclick="submitForm('update', 'PUT')">
                    Chỉnh sửa
                </button>
                <button type="submit" class="btn btn-outline-danger px-4"
                    onclick="submitForm('delete', 'DELETE')">
                    Xóa
                </button>
                <button type="button" class="btn btn-outline-dark"
                    onclick="resetForm()">Reset</button>
            </div>

        </form:form>
    </div>

    <div class="card border-0 shadow-sm">
        <div class="table-responsive p-3">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th scope="col">Mã GV</th>
                        <th scope="col">Họ</th>
                        <th scope="col">Tên</th>
                        <th scope="col">Số điện thoại</th>
                        <th scope="col">Địa chỉ</th>
                        <th scope="col" class="text-end">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${teachers}" var="item">
                        <tr>
                            <td class="fw-medium text-success">${item.teacherId}</td>
                            <td>${item.lastName}</td>
                            <td>${item.firstName}</td>
                            <td>${item.phoneNumber}</td>
                            <td>${item.address}</td>
                            <td class="text-end">
                                <button class="btn btn-sm btn-outline-secondary me-2 btn-edit">
                                    <i class="bi bi-pencil"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty teachers}">
                        <tr>
                            <td colspan="6" class="text-center text-muted">Không có giảng viên.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    function fillFormFromRow(row) {
        const cells = row.querySelectorAll("td");
        
        const teacherId = cells[0].innerText;
        const lastName = cells[1].innerText;
        const firstName = cells[2].innerText;
        const phoneNumber = cells[3].innerText;
        const address = cells[4].innerText;
        
        document.getElementById("teacherId").value = teacherId;
        document.getElementById("lastName").value = lastName;
        document.getElementById("firstName").value = firstName;
        document.getElementById("phoneNumber").value = phoneNumber;
        document.getElementById("address").value = address;
        
        document.getElementById("teacherId").readOnly = true;
    }

    const editButtons = document.querySelectorAll(".btn-edit");
    
    editButtons.forEach(button => {
        button.addEventListener("click", function () {
            const row = this.closest("tr");
            fillFormFromRow(row);
        });
    });

    function submitForm(action, method) {
        const form = document.getElementById("lecturerForm");
        form.action = "${pageContext.request.contextPath}/lecturer/" + action;
        document.getElementById("_method").value = method;
    }
    
    function resetForm() {
        document.getElementById("lecturerForm").reset();
        document.getElementById("teacherId").readOnly = false;
    }
</script>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
<script>
    function clearLecturerForm() {
        var form = document.getElementById('lecturerForm');
        form.reset();
        form.elements['teacherId'].value = '';
        form.elements['lastName'].value = '';
        form.elements['firstName'].value = '';
        form.elements['phoneNumber'].value = '';
        form.elements['address'].value = '';
    }
</script>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
