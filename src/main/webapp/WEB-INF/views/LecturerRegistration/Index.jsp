<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                <% request.setAttribute("pageTitle", "Đăng Ký Thi" ); %>

                    <%@ include file="../Shared/_LayoutStart.jsp" %>
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

                                <form:form id="lecturerRegistrationForm"
                                    action="${pageContext.request.contextPath}/lecturer-registration/add" method="post"
                                    modelAttribute="registrationDTO">
                                    <input type="hidden" id="_method" name="_method" value="POST" />

                                    <div class="row g-3">
                                        <div class="col-md-3">
                                            <label class="form-label small text-secondary">Lớp <span
                                                    class="text-danger">*</span></label>
                                            <form:select path="classId" class="form-select" id="classId">
                                                <option value="">-- Chọn lớp --</option>
                                                <form:options items="${dsLop}" itemValue="classId"
                                                    itemLabel="classDisplayName" />
                                            </form:select>
                                            <form:errors path="classId" cssClass="text-danger small mt-1 d-block" />
                                        </div>

                                        <div class="col-md-3">
                                            <label class="form-label small text-secondary">Môn Học <span
                                                    class="text-danger">*</span></label>
                                            <form:select path="subjectId" class="form-select" id="subjectId">
                                                <option value="">-- Chọn môn học --</option>
                                                <form:options items="${dsMonHoc}" itemValue="subjectId"
                                                    itemLabel="subjectDisplayName" />
                                            </form:select>
                                            <form:errors path="subjectId" cssClass="text-danger small mt-1 d-block" />
                                        </div>

                                        <div class="col-md-3">
                                            <label class="form-label small text-secondary">Trình Độ <span
                                                    class="text-danger">*</span></label>
                                            <form:select path="level" class="form-select" id="level">
                                                <option value="">-- Chọn trình độ --</option>
                                                <option value="A">Mức A</option>
                                                <option value="B">Mức B</option>
                                                <option value="C">Mức C</option>
                                            </form:select>
                                            <form:errors path="level" cssClass="text-danger small mt-1 d-block" />
                                        </div>

                                        <div class="col-md-3">
                                            <label class="form-label small text-secondary">Lần Thi <span
                                                    class="text-danger">*</span></label>
                                            <form:select path="tryNumber" class="form-select" id="tryNumber">
                                                <option value="1">Lần 1</option>
                                                <option value="2">Lần 2</option>
                                            </form:select>
                                            <form:errors path="tryNumber" cssClass="text-danger small mt-1 d-block" />
                                        </div>

                                        <c:if test="${sessionScope.ROLE eq 'PGV'}">
                                            <div class="col-md-3">
                                                <label class="form-label small text-secondary">Giáo Viên <span
                                                        class="text-danger">*</span></label>
                                                <form:select path="lecturerId" class="form-select" id="lecturerId">
                                                    <option value="">-- Chọn giáo viên --</option>
                                                    <form:options items="${dsGiaoVien}" itemValue="lecturerId" itemLabel="displayLecturer" />
                                                </form:select>
                                                <form:errors path="lecturerId"
                                                    cssClass="text-danger small mt-1 d-block" />
                                            </div>
                                        </c:if>

                                        <div class="col-md-3">
                                            <label class="form-label small text-secondary">Số Câu Thi <span
                                                    class="text-danger">*</span></label>
                                            <form:input path="numberOfQuestions" type="number" class="form-control"
                                                id="numberOfQuestions" min="10" max="100" />
                                            <form:errors path="numberOfQuestions"
                                                cssClass="text-danger small mt-1 d-block" />
                                        </div>

                                        <div class="col-md-3">
                                            <label class="form-label small text-secondary">Thời Gian (Phút) <span
                                                    class="text-danger">*</span></label>
                                            <form:input path="duration" type="number" class="form-control" id="duration"
                                                min="5" max="60" />
                                            <form:errors path="duration" cssClass="text-danger small mt-1 d-block" />
                                        </div>

                                        <div class="col-md-3">
                                            <label class="form-label small text-secondary">Ngày Thi <span
                                                    class="text-danger">*</span></label>
                                            <form:input path="examDate" type="date" class="form-control"
                                                id="examDate" />
                                            <form:errors path="examDate" cssClass="text-danger small mt-1 d-block" />
                                        </div>
                                    </div>

                                    <div class="d-flex gap-2 mt-4">
                                        <button type="submit" class="btn btn-dark px-4"
                                            onclick="submitForm('add', 'POST')" id="btnAdd">Thêm</button>
                                        <button type="submit" class="btn btn-primary px-4 d-none" 
                                            onclick="submitForm('update', 'POST')" id="btnUpdate">Lưu</button>
                                        <button type="submit" class="btn btn-outline-danger px-4"
                                            onclick="submitForm('delete', 'DELETE')" id="btnDelete">Xóa</button>
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
                                                    <td class="px-4 fw-medium">${reg.classRoom.classId}</td>
                                                    <td>${reg.subject.subjectId}</td>
                                                    <td>${reg.id.tryNumber}</td>
                                                    <td>${reg.level}</td>
                                                    <td>${reg.lecturer.lecturerId}</td>
                                                    <td>${reg.numberOfQuestions}</td>
                                                    <td>${reg.duration}</td>
                                                    <td>
                                                        <fmt:formatDate value="${reg.examDate}" pattern="yyyy-MM-dd"
                                                            var="fexamDate" />
                                                        ${fexamDate}
                                                    </td>
                                                    <td class="text-end pe-4">
                                                        <c:if
                                                            test="${sessionScope.ROLE eq 'PGV' or sessionScope.LOGIN_USER eq reg.lecturer.lecturerId}">
                                                            <button
                                                                class="btn btn-sm btn-outline-secondary me-2 btn-edit">
                                                                <i class="bi bi-pencil"></i>
                                                            </button>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty registrations}">
                                                <tr>
                                                    <td colspan="9" class="text-center text-muted py-3">Chưa có lịch thi
                                                        nào được đăng ký.</td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <script>
                            function setSelectValueTrimmed(selectId, valueToMatch) {
                                const select = document.getElementById(selectId);
                                if (!select) return;
                                for (let i = 0; i < select.options.length; i++) {
                                    if (select.options[i].value.trim() === valueToMatch) {
                                        select.selectedIndex = i;
                                        break;
                                    }
                                }
                            }

                            function fillFormFromRow(row) {
                                const cells = row.querySelectorAll("td");

                                const classId = cells[0].innerText.trim();
                                const subjectId = cells[1].innerText.trim();
                                const tryNumber = cells[2].innerText.trim();
                                const level = cells[3].innerText.trim();
                                const lecturerId = cells[4].innerText.trim();
                                const numberOfQuestions = cells[5].innerText.trim();
                                const duration = cells[6].innerText.trim();
                                const examDate = cells[7].innerText.trim();

                                setSelectValueTrimmed("classId", classId);
                                setSelectValueTrimmed("subjectId", subjectId);
                                document.getElementById("tryNumber").value = tryNumber;
                                document.getElementById("level").value = level;
                                if (document.getElementById("lecturerId")) {
                                    setSelectValueTrimmed("lecturerId", lecturerId);
                                }
                                document.getElementById("numberOfQuestions").value = numberOfQuestions;
                                document.getElementById("duration").value = duration;
                                document.getElementById("examDate").value = examDate;

                                document.getElementById("classId").disabled = true;
                                document.getElementById("subjectId").disabled = true;
                                document.getElementById("tryNumber").disabled = true;

                                // Switch to edit mode
                                document.getElementById("btnAdd").classList.add("d-none");
                                document.getElementById("btnUpdate").classList.remove("d-none");
                                document.getElementById("btnDelete").classList.add("d-none");


                            }

                            const editButtons = document.querySelectorAll(".btn-edit");

                            editButtons.forEach(button => {
                                button.addEventListener("click", function () {
                                    const row = this.closest("tr");
                                    fillFormFromRow(row);
                                });
                            });

                            function submitForm(action, method) {
                                const form = document.getElementById("lecturerRegistrationForm");
                                form.action = "${pageContext.request.contextPath}/lecturer-registration/" + action;
                                document.getElementById("_method").value = method;

                                document.getElementById("classId").disabled = false;
                                document.getElementById("subjectId").disabled = false;
                                document.getElementById("tryNumber").disabled = false;
                            }

                            function resetForm() {
                                document.getElementById("lecturerRegistrationForm").reset();
                                document.getElementById("classId").disabled = false;
                                document.getElementById("subjectId").disabled = false;
                                document.getElementById("tryNumber").disabled = false;

                                document.getElementById("btnAdd").classList.remove("d-none");
                                document.getElementById("btnUpdate").classList.add("d-none");
                                document.getElementById("btnDelete").classList.remove("d-none");


                            }
                        </script>

                        <%@ include file="../Shared/_LayoutEnd.jsp" %>