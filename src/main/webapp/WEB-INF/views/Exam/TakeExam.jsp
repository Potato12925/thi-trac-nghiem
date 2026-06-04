<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Bài Thi Trắc Nghiệm</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.timer-container {
	position: fixed;
	top: 20px;
	right: 20px;
	z-index: 1000;
	background: #fff;
	padding: 10px 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0,0,0,0.1);
}
.timer {
	font-size: 1.5rem;
	font-weight: bold;
	color: #dc3545;
}
.question-block {
	margin-bottom: 2rem;
	padding: 1.5rem;
	background: #f8f9fa;
	border-radius: 8px;
}
</style>
</head>
<body>
	<div class="timer-container">
		Thời gian còn lại: <span class="timer" id="timerDisplay">--:--</span>
	</div>

	<div class="container mt-5 mb-5">
		<h2 class="text-center mb-4">Môn: ${exam.subject.subjectName} - Lần thi: ${exam.attempt}</h2>
		
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<form:form id="examForm" action="${pageContext.request.contextPath}/exam/submit" method="post" modelAttribute="exam">
			<c:forEach var="detail" items="${exam.examDetails}" varStatus="status">
				<div class="question-block">
					<h5 class="mb-3">Câu ${status.index + 1}: ${detail.question.content}</h5>
					
					<div class="form-check mb-2">
						<input class="form-check-input" type="radio" name="answers[${detail.question.questionId}]" id="q${detail.question.questionId}_A" value="A">
						<label class="form-check-label" for="q${detail.question.questionId}_A">
							A. ${detail.question.optionA}
						</label>
					</div>
					
					<div class="form-check mb-2">
						<input class="form-check-input" type="radio" name="answers[${detail.question.questionId}]" id="q${detail.question.questionId}_B" value="B">
						<label class="form-check-label" for="q${detail.question.questionId}_B">
							B. ${detail.question.optionB}
						</label>
					</div>
					
					<div class="form-check mb-2">
						<input class="form-check-input" type="radio" name="answers[${detail.question.questionId}]" id="q${detail.question.questionId}_C" value="C">
						<label class="form-check-label" for="q${detail.question.questionId}_C">
							C. ${detail.question.optionC}
						</label>
					</div>
					
					<div class="form-check mb-2">
						<input class="form-check-input" type="radio" name="answers[${detail.question.questionId}]" id="q${detail.question.questionId}_D" value="D">
						<label class="form-check-label" for="q${detail.question.questionId}_D">
							D. ${detail.question.optionD}
						</label>
					</div>
				</div>
			</c:forEach>
			
			<div class="text-center mt-4">
				<button type="button" class="btn btn-primary btn-lg" onclick="confirmSubmit()">Nộp bài</button>
			</div>
		</form:form>
	</div>

	<!-- Pass startTime and duration to JS -->
	<!-- exam.startTime is Date object. we can get time in ms -->
	<script>
		// Use server start time to prevent client side manipulation
		var startTimeStr = "${exam.startTime.time}";
		var startTime = parseInt(startTimeStr);
		var durationMinutes = parseInt("${durationMinutes}");
		var endTime = startTime + durationMinutes * 60 * 1000;
		
		function updateTimer() {
			var now = new Date().getTime();
			var distance = endTime - now;
			
			if (distance <= 0) {
				document.getElementById("timerDisplay").innerHTML = "00:00";
				alert("Hết giờ làm bài! Hệ thống sẽ tự động nộp bài.");
				document.getElementById("examForm").submit();
				return;
			}
			
			var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
			var seconds = Math.floor((distance % (1000 * 60)) / 1000);
			
			document.getElementById("timerDisplay").innerHTML = 
				(minutes < 10 ? "0" + minutes : minutes) + ":" + 
				(seconds < 10 ? "0" + seconds : seconds);
		}
		
		setInterval(updateTimer, 1000);
		updateTimer();
		
		function confirmSubmit() {
			if (confirm("Bạn có chắc chắn muốn nộp bài?")) {
				document.getElementById("examForm").submit();
			}
		}
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
