<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setAttribute("pageTitle", "Nhập câu hỏi thi");
%>
<%@ include file="../Shared/_LayoutStart.jsp" %>
<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Nhập bộ đề thi</h1>
    </div>

    <div class="card border-0 shadow-sm mb-4 p-3">
        <form id="questionForm" action="${pageContext.request.contextPath}/question/save" method="post" class="row g-3 align-items-end">
            <input type="hidden" name="cauHoi" value="${question.cauHoi}" />
            <div class="col-lg-2 col-md-3">
                <label class="form-label text-sm fw-medium">Môn học</label>
                <select name="maMH" class="form-select form-select-sm">
                    <c:forEach items="${subjects}" var="subject">
                        <option value="${subject.maMH}" ${question.monHoc != null && subject.maMH == question.monHoc.maMH ? 'selected' : ''}>${subject.maMH} - ${subject.tenMH}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-lg-2 col-md-3">
                <label class="form-label text-sm fw-medium">Trình độ</label>
                <select name="trinhDo" class="form-select form-select-sm">
                    <option value="A" ${question.trinhDo == 'A' ? 'selected' : ''}>A</option>
                    <option value="B" ${question.trinhDo == 'B' ? 'selected' : ''}>B</option>
                    <option value="C" ${question.trinhDo == 'C' ? 'selected' : ''}>C</option>
                </select>
            </div>
            <div class="col-lg-4 col-md-6">
                <label class="form-label text-sm fw-medium">Nội dung câu hỏi</label>
                <input type="text" name="noiDung" class="form-control form-control-sm" placeholder="Nội dung câu hỏi" value="${question.noiDung}" />
            </div>
            <div class="col-lg-4 col-md-12 text-end">
                <button class="btn btn-primary me-1" type="button" onclick="clearQuestionForm()">Thêm</button>
                <button class="btn btn-outline-primary me-1" type="button" onclick="document.getElementById('questionForm').submit()">Hiệu chỉnh</button>
                <button class="btn btn-success me-1" type="submit">Ghi</button>
                <button class="btn btn-outline-secondary me-1" type="reset">Phục hồi</button>
                <button class="btn btn-outline-danger me-1" type="submit" formaction="${pageContext.request.contextPath}/question/delete" formmethod="post">Xóa</button>
                <button class="btn btn-outline-primary" type="submit" form="searchForm">Tìm</button>
            </div>

            <div class="col-md-6">
                <label class="form-label text-sm fw-medium">Đáp án A</label>
                <input type="text" name="A" class="form-control form-control-sm" placeholder="Phương án A" value="${question.A}" />
            </div>
            <div class="col-md-6">
                <label class="form-label text-sm fw-medium">Đáp án B</label>
                <input type="text" name="B" class="form-control form-control-sm" placeholder="Phương án B" value="${question.B}" />
            </div>
            <div class="col-md-6">
                <label class="form-label text-sm fw-medium">Đáp án C</label>
                <input type="text" name="C" class="form-control form-control-sm" placeholder="Phương án C" value="${question.C}" />
            </div>
            <div class="col-md-6">
                <label class="form-label text-sm fw-medium">Đáp án D</label>
                <input type="text" name="D" class="form-control form-control-sm" placeholder="Phương án D" value="${question.D}" />
            </div>
            <div class="col-md-4">
                <label class="form-label text-sm fw-medium">Đáp án đúng</label>
                <select name="dapAn" class="form-select form-select-sm">
                    <option value="A" ${question.dapAn == 'A' ? 'selected' : ''}>A</option>
                    <option value="B" ${question.dapAn == 'B' ? 'selected' : ''}>B</option>
                    <option value="C" ${question.dapAn == 'C' ? 'selected' : ''}>C</option>
                    <option value="D" ${question.dapAn == 'D' ? 'selected' : ''}>D</option>
                </select>
            </div>
            <div class="col-md-4">
                <label class="form-label text-sm fw-medium">Giáo viên</label>
                <select name="maGV" class="form-select form-select-sm">
                    <c:forEach items="${teachers}" var="teacher">
                        <option value="${teacher.maGV}" ${question.giaoVien != null && teacher.maGV == question.giaoVien.maGV ? 'selected' : ''}>${teacher.maGV} - ${teacher.ho} ${teacher.ten}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4">
                <label class="form-label text-sm fw-medium">Tìm kiếm câu hỏi</label>
                <div class="input-group input-group-sm">
                    <input type="text" name="search" class="form-control" placeholder="Nội dung hoặc Mã MH" value="${search}" />
                    <button class="btn btn-outline-secondary" type="submit" form="searchForm">
                        <i class="bi bi-search"></i>
                    </button>
                </div>
            </div>
        </form>
        <form id="searchForm" action="${pageContext.request.contextPath}/question" method="get" class="d-none"></form>
    </div>

    <div class="card border-0 shadow-sm">
        <div class="table-responsive p-3">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th scope="col">Câu hỏi</th>
                        <th scope="col">Mã MH</th>
                        <th scope="col">Trình độ</th>
                        <th scope="col">Nội dung</th>
                        <th scope="col">A</th>
                        <th scope="col">B</th>
                        <th scope="col">C</th>
                        <th scope="col">D</th>
                        <th scope="col">Đáp án</th>
                        <th scope="col">Mã GV</th>
                        <th scope="col" class="text-end">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${questions}" var="item">
                        <tr>
                            <td class="fw-medium text-primary">${item.cauHoi}</td>
                            <td>${item.monHoc.maMH}</td>
                            <td>${item.trinhDo}</td>
                            <td>${item.noiDung}</td>
                            <td>${item.A}</td>
                            <td>${item.B}</td>
                            <td>${item.C}</td>
                            <td>${item.D}</td>
                            <td>${item.dapAn}</td>
                            <td>${item.giaoVien.maGV}</td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-primary me-1" href="${pageContext.request.contextPath}/question?cauHoi=${item.cauHoi}&search=${search}">
                                    <i class="bi bi-pencil"></i>
                                </a>
                                <form action="${pageContext.request.contextPath}/question/delete" method="post" class="d-inline">
                                    <input type="hidden" name="cauHoi" value="${item.cauHoi}" />
                                    <button class="btn btn-sm btn-outline-danger" type="submit">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty questions}">
                        <tr>
                            <td colspan="11" class="text-center text-muted">Không có câu hỏi.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    function clearQuestionForm() {
        var form = document.getElementById('questionForm');
        form.reset();
        form.elements['cauHoi'].value = '';
    }
</script>
<%@ include file="../Shared/_LayoutEnd.jsp" %>
