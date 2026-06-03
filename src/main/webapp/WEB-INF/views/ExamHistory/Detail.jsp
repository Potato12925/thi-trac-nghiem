<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<% request.setAttribute("pageTitle", "Chi Tiết Bài Thi"); %>

<%@ include file="../Shared/_LayoutStart.jsp" %>

<div class="container-fluid py-4">
    <!-- Back Button & Header -->
    <div class="mb-4 d-flex justify-content-between align-items-center">
        <div>
            <c:choose>
                <c:when test="${sessionScope.ROLE eq 'SINHVIEN'}">
                    <a href="${pageContext.request.contextPath}/history" class="btn btn-outline-secondary btn-sm rounded-2 px-3 mb-2 d-inline-flex align-items-center gap-1">
                        <i class="bi bi-arrow-left"></i> Quay lại lịch sử thi
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/scores?classId=${exam.classId}&subjectId=${exam.subject.subjectId}&tryNumber=${exam.attempt}" class="btn btn-outline-secondary btn-sm rounded-2 px-3 mb-2 d-inline-flex align-items-center gap-1">
                        <i class="bi bi-arrow-left"></i> Quay lại bảng điểm
                    </a>
                </c:otherwise>
            </c:choose>
            <h1 class="h3 mb-0 text-dark fw-bold">Chi Tiết Kết Quả Bài Thi</h1>
        </div>
    </div>

    <!-- Exam General Information Card -->
    <div class="card border-0 shadow-sm rounded-3 overflow-hidden mb-4">
        <div class="card-body p-4">
            <div class="row align-items-center">
                <div class="col-lg-8 border-end border-light">
                    <div class="d-flex align-items-center gap-3 mb-3">
                        <span class="badge bg-primary px-3 py-2 rounded-2 fs-6">Lần Thi ${exam.attempt}</span>
                        <span class="badge bg-light text-dark border px-3 py-2 rounded-2 fs-6">Lớp: ${exam.classId}</span>
                    </div>
                    
                    <h2 class="text-dark fw-bold mb-2">${exam.subject.subjectName}</h2>
                    <p class="text-muted mb-3"><i class="bi bi-tag-fill me-1 text-primary"></i> Mã Môn Học: ${exam.subject.subjectId}</p>
                    
                    <div class="row g-3">
                        <div class="col-sm-6">
                            <div class="small text-secondary mb-1"><i class="bi bi-calendar3 me-1 text-secondary"></i> Ngày thi:</div>
                            <div class="fw-semibold text-dark">
                                <fmt:formatDate value="${exam.examDate}" pattern="dd-MM-yyyy 'lúc' HH:mm" />
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="small text-secondary mb-1"><i class="bi bi-clock-history me-1 text-secondary"></i> Thời gian làm bài:</div>
                            <div class="fw-semibold text-dark">
                                <fmt:formatDate value="${exam.startTime}" pattern="HH:mm:ss" /> - 
                                <fmt:formatDate value="${exam.endTime}" pattern="HH:mm:ss" />
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4 text-center mt-4 mt-lg-0">
                    <div class="d-inline-block position-relative mb-2">
                        <div class="rounded-circle bg-light border border-5 border-primary d-flex flex-column justify-content-center align-items-center shadow-sm" style="width: 140px; height: 140px;">
                            <span class="display-5 fw-bold text-primary">${exam.score}</span>
                            <span class="small text-muted fw-semibold">Thang điểm 10</span>
                        </div>
                    </div>
                    <div class="fw-semibold text-dark fs-5 mt-2">Kết quả: ${correctCount} / ${totalQuestions} Câu đúng</div>
                    <c:choose>
                        <c:when test="${exam.score >= 8.0}">
                            <span class="badge bg-success-subtle text-success border border-success px-3 py-1 rounded-pill mt-2">Xuất sắc</span>
                        </c:when>
                        <c:when test="${exam.score >= 5.0}">
                            <span class="badge bg-info-subtle text-info border border-info px-3 py-1 rounded-pill mt-2">Đạt</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-danger-subtle text-danger border border-danger px-3 py-1 rounded-pill mt-2">Chưa Đạt</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <!-- Question Details List -->
    <div class="row">
        <div class="col-12">
            <h4 class="mb-3 text-dark fw-bold"><i class="bi bi-patch-question me-2 text-primary"></i>Danh Sách Câu Hỏi & Đáp Án Chi Tiết</h4>
            
            <c:forEach var="detail" items="${exam.examDetails}" varStatus="status">
                <!-- Trim whitespace values of student selected answer and correct answer -->
                <c:set var="studentAns" value="${fn:toUpperCase(fn:trim(detail.studentAnswer))}" />
                <c:set var="correctAns" value="${fn:toUpperCase(fn:trim(detail.question.correctAnswer))}" />

                <div class="card border-0 shadow-sm rounded-3 mb-3 overflow-hidden">
                    <div class="card-header bg-white py-3 border-bottom border-light d-flex justify-content-between align-items-center">
                        <span class="fw-bold text-dark fs-5">Câu Hỏi ${detail.questionOrder}</span>
                        <div>
                            <c:choose>
                                <c:when test="${empty studentAns}">
                                    <span class="badge bg-secondary px-3 py-1.5 rounded-2"><i class="bi bi-dash-circle me-1"></i> Chưa trả lời</span>
                                </c:when>
                                <c:when test="${studentAns eq correctAns}">
                                    <span class="badge bg-success px-3 py-1.5 rounded-2"><i class="bi bi-check-circle me-1"></i> Đúng</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger px-3 py-1.5 rounded-2"><i class="bi bi-x-circle me-1"></i> Sai</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="card-body p-4">
                        <!-- Question Content -->
                        <h5 class="text-dark fw-semibold mb-4 lh-base">${detail.question.content}</h5>

                        <!-- Options List -->
                        <div class="options-container">
                            
                            <!-- OPTION A -->
                            <c:choose>
                                <c:when test="${studentAns eq 'A' && correctAns eq 'A'}">
                                    <div class="alert alert-success d-flex align-items-center justify-content-between mb-3 p-3 border border-success border-2 rounded-3">
                                        <div class="fw-semibold"><strong>A.</strong> ${detail.question.optionA}</div>
                                        <span class="badge bg-success px-3 py-1.5 rounded-pill"><i class="bi bi-check-lg"></i> Bạn chọn đúng</span>
                                    </div>
                                </c:when>
                                <c:when test="${studentAns eq 'A' && correctAns ne 'A'}">
                                    <div class="alert alert-danger d-flex align-items-center justify-content-between mb-3 p-3 border border-danger border-2 rounded-3">
                                        <div class="fw-semibold"><strong>A.</strong> ${detail.question.optionA}</div>
                                        <span class="badge bg-danger px-3 py-1.5 rounded-pill"><i class="bi bi-x-lg"></i> Bạn chọn sai</span>
                                    </div>
                                </c:when>
                                <c:when test="${studentAns ne 'A' && correctAns eq 'A'}">
                                    <div class="alert alert-success-subtle border-success border-2 text-success d-flex align-items-center justify-content-between mb-3 p-3 rounded-3" style="background-color: #e8f5e9;">
                                        <div class="fw-semibold text-success"><strong>A.</strong> ${detail.question.optionA}</div>
                                        <span class="badge bg-success px-3 py-1.5 rounded-pill"><i class="bi bi-check"></i> Đáp án đúng</span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="p-3 border border-light bg-light rounded-3 text-secondary mb-3">
                                        <strong>A.</strong> ${detail.question.optionA}
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <!-- OPTION B -->
                            <c:choose>
                                <c:when test="${studentAns eq 'B' && correctAns eq 'B'}">
                                    <div class="alert alert-success d-flex align-items-center justify-content-between mb-3 p-3 border border-success border-2 rounded-3">
                                        <div class="fw-semibold"><strong>B.</strong> ${detail.question.optionB}</div>
                                        <span class="badge bg-success px-3 py-1.5 rounded-pill"><i class="bi bi-check-lg"></i> Bạn chọn đúng</span>
                                    </div>
                                </c:when>
                                <c:when test="${studentAns eq 'B' && correctAns ne 'B'}">
                                    <div class="alert alert-danger d-flex align-items-center justify-content-between mb-3 p-3 border border-danger border-2 rounded-3">
                                        <div class="fw-semibold"><strong>B.</strong> ${detail.question.optionB}</div>
                                        <span class="badge bg-danger px-3 py-1.5 rounded-pill"><i class="bi bi-x-lg"></i> Bạn chọn sai</span>
                                    </div>
                                </c:when>
                                <c:when test="${studentAns ne 'B' && correctAns eq 'B'}">
                                    <div class="alert alert-success-subtle border-success border-2 text-success d-flex align-items-center justify-content-between mb-3 p-3 rounded-3" style="background-color: #e8f5e9;">
                                        <div class="fw-semibold text-success"><strong>B.</strong> ${detail.question.optionB}</div>
                                        <span class="badge bg-success px-3 py-1.5 rounded-pill"><i class="bi bi-check"></i> Đáp án đúng</span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="p-3 border border-light bg-light rounded-3 text-secondary mb-3">
                                        <strong>B.</strong> ${detail.question.optionB}
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <!-- OPTION C -->
                            <c:choose>
                                <c:when test="${studentAns eq 'C' && correctAns eq 'C'}">
                                    <div class="alert alert-success d-flex align-items-center justify-content-between mb-3 p-3 border border-success border-2 rounded-3">
                                        <div class="fw-semibold"><strong>C.</strong> ${detail.question.optionC}</div>
                                        <span class="badge bg-success px-3 py-1.5 rounded-pill"><i class="bi bi-check-lg"></i> Bạn chọn đúng</span>
                                    </div>
                                </c:when>
                                <c:when test="${studentAns eq 'C' && correctAns ne 'C'}">
                                    <div class="alert alert-danger d-flex align-items-center justify-content-between mb-3 p-3 border border-danger border-2 rounded-3">
                                        <div class="fw-semibold"><strong>C.</strong> ${detail.question.optionC}</div>
                                        <span class="badge bg-danger px-3 py-1.5 rounded-pill"><i class="bi bi-x-lg"></i> Bạn chọn sai</span>
                                    </div>
                                </c:when>
                                <c:when test="${studentAns ne 'C' && correctAns eq 'C'}">
                                    <div class="alert alert-success-subtle border-success border-2 text-success d-flex align-items-center justify-content-between mb-3 p-3 rounded-3" style="background-color: #e8f5e9;">
                                        <div class="fw-semibold text-success"><strong>C.</strong> ${detail.question.optionC}</div>
                                        <span class="badge bg-success px-3 py-1.5 rounded-pill"><i class="bi bi-check"></i> Đáp án đúng</span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="p-3 border border-light bg-light rounded-3 text-secondary mb-3">
                                        <strong>C.</strong> ${detail.question.optionC}
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <!-- OPTION D -->
                            <c:choose>
                                <c:when test="${studentAns eq 'D' && correctAns eq 'D'}">
                                    <div class="alert alert-success d-flex align-items-center justify-content-between mb-3 p-3 border border-success border-2 rounded-3">
                                        <div class="fw-semibold"><strong>D.</strong> ${detail.question.optionD}</div>
                                        <span class="badge bg-success px-3 py-1.5 rounded-pill"><i class="bi bi-check-lg"></i> Bạn chọn đúng</span>
                                    </div>
                                </c:when>
                                <c:when test="${studentAns eq 'D' && correctAns ne 'D'}">
                                    <div class="alert alert-danger d-flex align-items-center justify-content-between mb-3 p-3 border border-danger border-2 rounded-3">
                                        <div class="fw-semibold"><strong>D.</strong> ${detail.question.optionD}</div>
                                        <span class="badge bg-danger px-3 py-1.5 rounded-pill"><i class="bi bi-x-lg"></i> Bạn chọn sai</span>
                                    </div>
                                </c:when>
                                <c:when test="${studentAns ne 'D' && correctAns eq 'D'}">
                                    <div class="alert alert-success-subtle border-success border-2 text-success d-flex align-items-center justify-content-between mb-3 p-3 rounded-3" style="background-color: #e8f5e9;">
                                        <div class="fw-semibold text-success"><strong>D.</strong> ${detail.question.optionD}</div>
                                        <span class="badge bg-success px-3 py-1.5 rounded-pill"><i class="bi bi-check"></i> Đáp án đúng</span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="p-3 border border-light bg-light rounded-3 text-secondary mb-3">
                                        <strong>D.</strong> ${detail.question.optionD}
                                    </div>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp" %>
