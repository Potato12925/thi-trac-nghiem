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
						<label for="classId" class="form-label">Lớp:</label>
						<p class="form-control-plaintext fw-bold">${lopSinhVien.classId} - ${lopSinhVien.className}</p>
					</div>

					<div class="mb-3">
						<label for="subjectId" class="form-label">Chọn môn học:</label>
						<form:select path="subjectId" class="form-select" required="required">
							<option value="">-- Chọn Môn Học --</option>
							<form:options items="${dsMonHoc}" itemValue="subjectId" itemLabel="subjectDisplayName" />
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
					<a href="${pageContext.request.contextPath}/students/home" class="btn btn-secondary">Quay lại</a>
				</form:form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
