<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Kết quả thi</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container mt-5">
		<div class="card shadow">
			<div class="card-body text-center">
				<c:if test="${not empty violationMessage}">
					<h2 class="text-danger mb-4">Bài thi bị khóa!</h2>
					<div class="alert alert-danger" role="alert">
						${violationMessage}
					</div>
				</c:if>
				<c:if test="${empty violationMessage}">
					<h2 class="text-success mb-4">Nộp bài thành công!</h2>
				</c:if>
				<h4 class="mb-4">Điểm số của bạn là: <strong>${score} / 10</strong></h4>
				
				<a href="${pageContext.request.contextPath}/students/home" class="btn btn-primary mt-3">Về trang chủ</a>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
