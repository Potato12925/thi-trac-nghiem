<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<% request.setAttribute("pageTitle", "Xem Bảng Điểm" ); %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<style>
    /* Styles for Screen view */
    body {
        background: #f4f6fb;
    }
    
    .filter-card {
        border: 0;
        border-radius: 16px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
    }
    
    .grade-card {
        border: 0;
        border-radius: 16px;
        box-shadow: 0 8px 30px rgba(0, 0, 0, 0.06);
    }
    
    .table-grade thead th {
        background-color: #f8fafc;
        color: #64748b;
        font-weight: 600;
        font-size: 0.85rem;
        text-transform: uppercase;
        border: 0;
        padding: 1rem;
    }
    
    .table-grade tbody td {
        padding: 1rem;
        vertical-align: middle;
        border-color: #f1f5f9;
        font-size: 0.95rem;
    }
    
    .badge-grade {
        padding: 0.4rem 0.8rem;
        border-radius: 8px;
        font-weight: 600;
        font-size: 0.85rem;
    }
    
    .badge-grade-pass {
        background-color: #ecfeff;
        color: #0891b2;
    }
    
    .badge-grade-fail {
        background-color: #fef2f2;
        color: #dc2626;
    }

    .clickable-row {
        cursor: pointer;
        transition: background-color 0.15s ease;
    }
    .clickable-row:hover {
        background-color: rgba(13, 110, 253, 0.05) !important;
    }

    .score-detail-link:hover {
        text-decoration: underline !important;
        color: #0b5ed7 !important;
    }

    /* Print styles */
    @media print {
        /* Hide everything by default for screen */
        .app-sidebar, 
        .app-header, 
        .btn, 
        .filter-card, 
        .no-print, 
        header, 
        footer {
            display: none !important;
        }
        
        body {
            background: #ffffff !important;
            color: #000000 !important;
            margin: 0 !important;
            padding: 20mm 15mm 20mm 15mm !important;
            font-family: "Times New Roman", Times, serif !important;
            font-size: 13pt !important;
            line-height: 1.5 !important;
        }

        .app-layout {
            display: block !important;
        }

        .app-main, .app-content, .container-fluid, #main-content {
            padding: 0 !important;
            margin: 0 !important;
            width: 100% !important;
            display: block !important;
            border: 0 !important;
            box-shadow: none !important;
        }

        .grade-card {
            box-shadow: none !important;
            border: 0 !important;
            background: transparent !important;
        }

        .grade-card .card-body {
            padding: 0 !important;
        }

        /* Show print-only header */
        .print-header {
            display: block !important;
        }

        /* Show print-only signatures */
        .print-signatures {
            display: block !important;
        }

        /* Format print table */
        .table-print-layout {
            width: 100% !important;
            border-collapse: collapse !important;
            margin-top: 20px !important;
            font-family: "Times New Roman", Times, serif !important;
            font-size: 12pt !important;
        }

        .table-print-layout th, 
        .table-print-layout td {
            border: 1px solid #000000 !important;
            padding: 8px 10px !important;
            text-align: center !important;
            color: #000000 !important;
            background: transparent !important;
        }

        .table-print-layout th {
            font-weight: bold !important;
            text-transform: uppercase !important;
        }

        .table-print-layout td.text-left {
            text-align: left !important;
        }

        .clickable-row {
            cursor: default !important;
            background-color: transparent !important;
        }

        .score-detail-link, a {
            text-decoration: none !important;
            color: #000000 !important;
            pointer-events: none !important;
        }
    }
</style>

<!-- Print-Only Page Header -->
<div class="print-header d-none">
    <table style="width: 100%; border: none; margin-bottom: 20px;">
        <tr style="border: none;">
            <td style="width: 50%; text-align: center; border: none; font-size: 11pt; font-weight: bold; font-family: 'Times New Roman', serif; line-height: 1.3;">
                HỌC VIỆN CÔNG NGHỆ BƯU CHÍNH VIỄN THÔNG<br>
                <span style="font-size: 10pt; font-weight: bold; text-decoration: underline;">KHOA CÔNG NGHỆ THÔNG TIN 1</span>
            </td>
            <td style="width: 50%; text-align: center; border: none; font-size: 11pt; font-weight: bold; font-family: 'Times New Roman', serif; line-height: 1.3;">
                CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM<br>
                <span style="font-size: 11pt; font-weight: bold; text-decoration: underline;">Độc lập - Tự do - Hạnh phúc</span>
            </td>
        </tr>
    </table>
    
    <div style="text-align: center; margin-top: 30px; margin-bottom: 25px; font-family: 'Times New Roman', serif;">
        <h2 style="font-size: 16pt; font-weight: bold; margin-bottom: 10px;">BẢNG ĐIỂM THI HẾT MÔN</h2>
        <div style="font-size: 12pt; margin-bottom: 5px;">
            <strong>Lớp học:</strong> ${selectedClassName} (${selectedClassId})
        </div>
        <div style="font-size: 12pt; margin-bottom: 5px;">
            <strong>Môn học:</strong> ${selectedSubjectName} (${selectedSubjectId})
        </div>
        <div style="font-size: 12pt;">
            <strong>Lần thi:</strong> ${selectedTryNumber}
        </div>
    </div>
</div>

<div class="container-fluid py-4 no-print">
    <!-- Page title on screen -->
    <div class="d-flex justify-content-between align-items-center mb-4 no-print">
        <div>
            <h1 class="h3 mb-0 text-dark fw-bold">Xem Bảng Điểm Thi Hết Môn</h1>
            <p class="text-secondary small mb-0">Tra cứu và in bảng điểm của lớp học theo môn học và lần thi</p>
        </div>
    </div>

    <!-- Selector Form -->
    <div class="card filter-card mb-4">
        <div class="card-body p-4">
            <form:form action="${pageContext.request.contextPath}/scores" method="get" modelAttribute="scoreFilter">
                <div class="row g-3 align-items-end">
                    <div class="col-md-4">
                        <label for="classId" class="form-label small fw-semibold text-secondary">Tên lớp học</label>
                        <form:select path="classId" id="classId" class="form-select rounded-3" required="required">
                            <option value="">-- Chọn lớp học --</option>
                            <form:options items="${dsLop}" itemValue="classId" itemLabel="classDisplayName" />
                        </form:select>
                    </div>

                    <div class="col-md-4">
                        <label for="subjectId" class="form-label small fw-semibold text-secondary">Tên môn học</label>
                        <form:select path="subjectId" id="subjectId" class="form-select rounded-3" required="required">
                            <option value="">-- Chọn môn học --</option>
                            <form:options items="${dsMonHoc}" itemValue="subjectId" itemLabel="subjectDisplayName" />
                        </form:select>
                    </div>

                    <div class="col-md-2">
                        <label for="tryNumber" class="form-label small fw-semibold text-secondary">Lần thi</label>
                        <form:select path="tryNumber" id="tryNumber" class="form-select rounded-3" required="required">
                            <form:option value="1">Lần 1</form:option>
                            <form:option value="2">Lần 2</form:option>
                        </form:select>
                    </div>

                    <div class="col-md-2 d-grid">
                        <button type="submit" class="btn btn-primary rounded-3 py-2">
                            <i class="bi bi-search me-1"></i> Xem bảng điểm
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<!-- Grade sheet results -->
<c:if test="${hasSearched}">
    <div class="container-fluid py-0 px-0 px-md-3">
        <div class="card grade-card">
            <div class="card-header bg-white border-0 py-3 d-flex justify-content-between align-items-center no-print">
                <h5 class="mb-0 fw-bold text-dark">
                    <i class="bi bi-table me-2 text-primary"></i>Kết Quả Bảng Điểm
                </h5>
                <button type="button" class="btn btn-outline-dark rounded-3 px-3 d-inline-flex align-items-center gap-1" onclick="window.print()">
                    <i class="bi bi-printer"></i> In bảng điểm
                </button>
            </div>
            
            <div class="card-body p-4 pt-0">
                <!-- On screen summary details -->
                <div class="alert alert-light border border-light-subtle rounded-3 p-3 mb-3 d-flex flex-wrap gap-4 no-print">
                    <div>
                        <span class="text-secondary small d-block">Lớp học</span>
                        <strong class="text-dark">${selectedClassId} - ${selectedClassName}</strong>
                    </div>
                    <div class="border-end d-none d-md-block"></div>
                    <div>
                        <span class="text-secondary small d-block">Môn học</span>
                        <strong class="text-dark">${selectedSubjectId} - ${selectedSubjectName}</strong>
                    </div>
                    <div class="border-end d-none d-md-block"></div>
                    <div>
                        <span class="text-secondary small d-block">Lần thi</span>
                        <strong class="text-dark">Lần ${selectedTryNumber}</strong>
                    </div>
                    <div class="border-end d-none d-md-block"></div>
                    <div>
                        <span class="text-secondary small d-block">Tổng số sinh viên</span>
                        <strong class="text-dark">${fn:length(scores)} sinh viên</strong>
                    </div>
                </div>

                <div class="table-responsive">
                    <!-- Standard Bootstrap table for screen and customized print table layout -->
                    <table class="table table-hover align-middle table-grade table-print-layout">
                        <thead>
                            <tr>
                                <th scope="col" style="width: 10%; text-align: center;">STT</th>
                                <th scope="col" style="width: 15%; text-align: center;">Mã SV</th>
                                <th scope="col" class="text-left" style="text-align: left !important; width: 25%;">Họ</th>
                                <th scope="col" class="text-left" style="text-align: left !important; width: 20%;">Tên</th>
                                <th scope="col" style="width: 15%; text-align: center;">Điểm</th>
                                <th scope="col" style="width: 15%; text-align: center;">Điểm Chữ</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="scoreRow" items="${scores}" varStatus="status">
                                <c:choose>
                                    <c:when test="${empty scoreRow.examId}">                                        
                                        <tr>
                                            <td style="text-align: center;">${status.index + 1}</td>
                                            <td style="text-align: center;" class="fw-medium text-primary-emphasis">
                                                ${scoreRow.studentId}
                                            </td>
                                            <td class="text-left" style="text-align: left !important;">${scoreRow.lastName}</td>
                                            <td class="text-left" style="text-align: left !important; font-weight: 500;">${scoreRow.firstName}</td>
                                            <td style="text-align: center;" class="fw-bold">
                                                -
                                            </td>
                                            <td style="text-align: center;">
                                                <span class="badge-grade ${scoreRow.letterGrade eq 'F' ? 'badge-grade-fail' : (scoreRow.letterGrade eq '-' ? '' : 'badge-grade-pass')}">
                                                    ${scoreRow.letterGrade}
                                                </span>
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr onclick="window.location='${pageContext.request.contextPath}/history/detail?id=${scoreRow.examId}'" class="clickable-row">
                                            <td style="text-align: center;">${status.index + 1}</td>
                                            <td style="text-align: center;" class="fw-medium text-primary-emphasis">
                                                ${scoreRow.studentId}
                                            </td>
                                            <td class="text-left" style="text-align: left !important;">${scoreRow.lastName}</td>
                                            <td class="text-left" style="text-align: left !important; font-weight: 500;">${scoreRow.firstName}</td>
                                            <td style="text-align: center;" class="fw-bold">
                                                <fmt:formatNumber value="${scoreRow.score}" pattern="0.0" />
                                            </td>
                                            <td style="text-align: center;">
                                                <span class="badge-grade ${scoreRow.letterGrade eq 'F' ? 'badge-grade-fail' : (scoreRow.letterGrade eq '-' ? '' : 'badge-grade-pass')}">
                                                    ${scoreRow.letterGrade}
                                                </span>
                                            </td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${empty scores}">
                                <tr>
                                    <td colspan="6" class="text-center text-muted py-4">
                                        <i class="bi bi-info-circle me-1"></i> Lớp học này hiện tại chưa có sinh viên nào.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</c:if>

<!-- Print-Only Signatures -->
<div class="print-signatures d-none mt-5" style="font-family: 'Times New Roman', serif;">
    <div style="width: 100%; display: flex; justify-content: flex-end;">
        <div style="width: 300px; text-align: center; font-size: 12pt; line-height: 1.4;">
            <p style="margin-bottom: 5px; font-style: italic;">..., Ngày ..... tháng ..... năm 20...</p>
            <p style="font-weight: bold; margin-bottom: 80px;">Giảng viên ký tên</p>
            
            <p style="font-weight: bold; margin-top: 10px;">
                <c:choose>
                    <c:when test="${sessionScope.ROLE eq 'GIAOVIEN'}">
                        Giảng viên
                    </c:when>
                    <c:otherwise>
                        Người lập bảng
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
    </div>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
