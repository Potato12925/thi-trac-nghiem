<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Chuẩn bị thi</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container mt-5">
		<h2 class="mb-4">Chuẩn bị thi trắc nghiệm</h2>
		
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<div class="card shadow">
			<div class="card-body">
				<form:form action="${pageContext.request.contextPath}/exam/start" method="post" modelAttribute="prepareExamDTO">
					<div class="mb-3">
						<label for="classId" class="form-label">Chọn lớp:</label>
						<c:choose>
							<c:when test="${role == 'SINHVIEN'}">
								<input type="text" class="form-control" value="${dsLop[0].classId} - ${dsLop[0].className}" readonly>
								<form:hidden path="classId" value="${dsLop[0].classId}"/>
							</c:when>
							<c:otherwise>
								<form:select path="classId" class="form-select" required="required">
									<option value="">-- Chọn Lớp --</option>
									<c:forEach var="lop" items="${dsLop}">
										<form:option value="${lop.classId}">${lop.classId} - ${lop.className}</form:option>
									</c:forEach>
								</form:select>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="mb-3">
						<label for="subjectId" class="form-label">Chọn môn học:</label>
						<form:select path="subjectId" class="form-select" required="required">
							<option value="">-- Chọn Môn Học --</option>
							<c:forEach var="mh" items="${dsMonHoc}">
								<form:option value="${mh.subjectId}">${mh.subjectId} - ${mh.subjectName}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<div class="mb-3">
						<label for="tryNumber" class="form-label">Lần thi:</label>
						<form:select path="tryNumber" class="form-select" required="required">
							<form:option value="1">Lần 1</form:option>
							<form:option value="2">Lần 2</form:option>
						</form:select>
					</div>

					<button type="submit" class="btn btn-primary">Bắt đầu thi</button>
					<c:choose>
						<c:when test="${role == 'SINHVIEN'}">
							<a href="${pageContext.request.contextPath}/student/home" class="btn btn-secondary">Quay lại</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/lecturer/home" class="btn btn-secondary">Quay lại</a>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
