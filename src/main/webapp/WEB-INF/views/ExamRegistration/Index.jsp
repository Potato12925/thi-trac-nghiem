<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
request.setAttribute("pageTitle", "Đăng Ký Thi");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>
<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Quản lý Đăng Ký Thi</h1>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <div class="border rounded-3 bg-white p-4 mb-4">

        <form:form id="examRegistrationForm" action="${pageContext.request.contextPath}/exam-registration/add" method="post" modelAttribute="registrationDTO">
            <input type="hidden" id="_method" name="_method" value="POST" />

            <div class="row g-3">
                <div class="col-md-3">
                    <label class="form-label small text-secondary">Lớp <span class="text-danger">*</span></label>
                    <form:select path="maLop" class="form-select" id="maLop">
                        <option value="">-- Chọn lớp --</option>
                        <form:options items="${dsLop}" itemValue="maLop" itemLabel="tenLop" />
                    </form:select>
                    <form:errors path="maLop" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="col-md-3">
                    <label class="form-label small text-secondary">Môn Học <span class="text-danger">*</span></label>
                    <form:select path="maMh" class="form-select" id="maMh">
                        <option value="">-- Chọn môn học --</option>
                        <form:options items="${dsMonHoc}" itemValue="maMH" itemLabel="tenMH" />
                    </form:select>
                    <form:errors path="maMh" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="col-md-3">
                    <label class="form-label small text-secondary">Trình Độ <span class="text-danger">*</span></label>
                    <form:select path="trinhDo" class="form-select" id="trinhDo">
                        <option value="">-- Chọn trình độ --</option>
                        <option value="A">Mức A</option>
                        <option value="B">Mức B</option>
                        <option value="C">Mức C</option>
                    </form:select>
                    <form:errors path="trinhDo" cssClass="text-danger small mt-1 d-block" />
                </div>

                <div class="col-md-3">
                    <label class="form-label small text-secondary">Lần Thi <span class="text-danger">*</span></label>
                    <form:select path="lan" class="form-select" id="lan">
                        <option value="1">Lần 1</option>
                        <option value="2">Lần 2</option>
                    </form:select>
                    <form:errors path="lan" cssClass="text-danger small mt-1 d-block" />
                </div>

                <c:if test="${sessionScope.ROLE eq 'PGV'}">
                    <div class="col-md-3">
                        <label class="form-label small text-secondary">Giáo Viên <span class="text-danger">*</span></label>
                        <form:select path="maGv" class="form-select" id="maGv">
                            <option value="">-- Chọn giáo viên --</option>
                            <c:forEach items="${dsGiaoVien}" var="gv">
                                <option value="${gv.maGV}">${gv.maGV} - ${gv.ho} ${gv.ten}</option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="maGv" cssClass="text-danger small mt-1 d-block" />
                    </div>
                </c:if>

                <div class="col-md-3">
                    <label class="form-label small text-secondary">Số Câu Thi <span class="text-danger">*</span></label>
                    <form:input path="soCauThi" type="number" class="form-control" id="soCauThi" min="10" max="100"/>
                    <form:errors path="soCauThi" cssClass="text-danger small mt-1 d-block" />
                </div>
                
                <div class="col-md-3">
                    <label class="form-label small text-secondary">Thời Gian (Phút) <span class="text-danger">*</span></label>
                    <form:input path="thoiGian" type="number" class="form-control" id="thoiGian" min="5" max="60"/>
                    <form:errors path="thoiGian" cssClass="text-danger small mt-1 d-block" />
                </div>
                
                <div class="col-md-3">
                    <label class="form-label small text-secondary">Ngày Thi <span class="text-danger">*</span></label>
                    <form:input path="ngayThi" type="date" class="form-control" id="ngayThi"/>
                    <form:errors path="ngayThi" cssClass="text-danger small mt-1 d-block" />
                </div>
            </div>

            <div class="d-flex gap-2 mt-4">
                <button type="submit" class="btn btn-dark px-4" onclick="submitForm('add', 'POST')">Thêm</button>
                <!-- Uncomment to support update later -->
                <!-- <button type="submit" class="btn btn-outline-secondary px-4" onclick="submitForm('update', 'PUT')">Chỉnh sửa</button> -->
                <button type="submit" class="btn btn-outline-danger px-4" onclick="submitForm('delete', 'DELETE')">Xóa</button>
                <button type="button" class="btn btn-outline-dark" onclick="resetForm()">Reset</button>
            </div>
        </form:form>
    </div>

    <div class="card border-0 shadow-sm">
        <div class="table-responsive p-3">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th scope="col">Mã Lớp</th>
                        <th scope="col">Mã Môn</th>
                        <th scope="col">Lần</th>
                        <th scope="col">Trình Độ</th>
                        <th scope="col">Giáo Viên</th>
                        <th scope="col">Số Câu</th>
                        <th scope="col">Thời Gian (p)</th>
                        <th scope="col">Ngày Thi</th>
                        <th scope="col" class="text-end">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reg" items="${registrations}">
                        <tr>
                            <td class="px-4 fw-medium">${reg.lop.maLop}</td>
                            <td>${reg.monHoc.maMH}</td>
                            <td>${reg.id.lan}</td>
                            <td>${reg.trinhDo}</td>
                            <td>${reg.giaoVien.maGV}</td>
                            <td>${reg.soCauThi}</td>
                            <td>${reg.thoiGian}</td>
                            <td>
                                <fmt:formatDate value="${reg.ngayThi}" pattern="yyyy-MM-dd" var="fNgayThi"/>
                                ${fNgayThi}
                            </td>
                            <td class="text-end pe-4">
                                <c:if test="${sessionScope.ROLE eq 'PGV' or sessionScope.LOGIN_USER eq reg.giaoVien.maGV}">
                                    <button class="btn btn-sm btn-outline-secondary me-2 btn-edit">
                                        <i class="bi bi-pencil"></i>
                                    </button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty registrations}">
                        <tr>
                            <td colspan="9" class="text-center text-muted py-3">Chưa có lịch thi nào được đăng ký.</td>
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
        
        const maLop = cells[0].innerText.trim();
        const maMh = cells[1].innerText.trim();
        const lan = cells[2].innerText.trim();
        const trinhDo = cells[3].innerText.trim();
        const maGv = cells[4].innerText.trim();
        const soCauThi = cells[5].innerText.trim();
        const thoiGian = cells[6].innerText.trim();
        const ngayThi = cells[7].innerText.trim();
        
        document.getElementById("maLop").value = maLop;
        document.getElementById("maMh").value = maMh;
        document.getElementById("lan").value = lan;
        document.getElementById("trinhDo").value = trinhDo;
        if(document.getElementById("maGv")) {
            document.getElementById("maGv").value = maGv;
        }
        document.getElementById("soCauThi").value = soCauThi;
        document.getElementById("thoiGian").value = thoiGian;
        document.getElementById("ngayThi").value = ngayThi;
        
        document.getElementById("maLop").disabled = true;
        document.getElementById("maMh").disabled = true;
        document.getElementById("lan").disabled = true;
        
        // Add hidden inputs to ensure data is passed to backend since disabled fields are omitted in POST
        appendHiddenInput("hidden_maLop", "maLop", maLop);
        appendHiddenInput("hidden_maMh", "maMh", maMh);
        appendHiddenInput("hidden_lan", "lan", lan);
    }

    function appendHiddenInput(id, name, value) {
        let hiddenInput = document.getElementById(id);
        if(!hiddenInput) {
            hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.id = id;
            hiddenInput.name = name;
            document.getElementById("examRegistrationForm").appendChild(hiddenInput);
        }
        hiddenInput.value = value;
    }

    const editButtons = document.querySelectorAll(".btn-edit");
    
    editButtons.forEach(button => {
        button.addEventListener("click", function () {
            const row = this.closest("tr");
            fillFormFromRow(row);
        });
    });

    function submitForm(action, method) {
        const form = document.getElementById("examRegistrationForm");
        form.action = "${pageContext.request.contextPath}/exam-registration/" + action;
        document.getElementById("_method").value = method;
        
        // Un-disable fields right before submit so they can be validated (or sent if hidden inputs are missing)
        document.getElementById("maLop").disabled = false;
        document.getElementById("maMh").disabled = false;
        document.getElementById("lan").disabled = false;
    }
    
    function resetForm() {
        document.getElementById("examRegistrationForm").reset();
        document.getElementById("maLop").disabled = false;
        document.getElementById("maMh").disabled = false;
        document.getElementById("lan").disabled = false;
        
        if(document.getElementById("hidden_maLop")) document.getElementById("hidden_maLop").remove();
        if(document.getElementById("hidden_maMh")) document.getElementById("hidden_maMh").remove();
        if(document.getElementById("hidden_lan")) document.getElementById("hidden_lan").remove();
    }
</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
