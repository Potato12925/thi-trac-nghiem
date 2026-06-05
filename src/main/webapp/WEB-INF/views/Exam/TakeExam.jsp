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
	<div id="startScreen" class="d-flex flex-column justify-content-center align-items-center" style="height: 100vh; background: #f8f9fa;">
		<h2 class="mb-4">Bạn đã sẵn sàng làm bài thi?</h2>
		<p class="text-danger mb-4">Lưu ý: Bài thi yêu cầu làm ở chế độ Toàn Màn Hình. Bạn không được phép chuyển tab hoặc thoát toàn màn hình. Vi phạm 3 lần sẽ bị tự động nộp bài.</p>
		<button id="startExamBtn" class="btn btn-success btn-lg">Bắt Đầu Làm Bài</button>
	</div>

	<div id="resumeScreen" class="d-none flex-column justify-content-center align-items-center" style="height: 100vh; background: #f8f9fa;">
		<h2 class="mb-4 text-danger">BÀI THI TẠM DỪNG DO VI PHẠM QUY CHẾ</h2>
		<p class="mb-4 text-center">Bạn đã chuyển tab, thu nhỏ trình duyệt hoặc thoát toàn màn hình.<br>Vui lòng quay lại chế độ Toàn màn hình để tiếp tục làm bài.</p>
		<button id="resumeExamBtn" class="btn btn-warning btn-lg">Tiếp Tục Làm Bài</button>
	</div>

	<div id="examContent" style="display: none;">
		<div class="timer-container">
			Thời gian còn lại: <span class="timer" id="timerDisplay">--:--</span>
		</div>

		<div class="container mt-5 mb-5">
		<h2 class="text-center mb-4">Môn: ${exam.subject.subjectName} - Lần thi: ${exam.attempt}</h2>
		
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<form:form id="examForm" action="${pageContext.request.contextPath}/exam/submit" method="post" modelAttribute="exam">
			<input type="hidden" name="isViolation" id="isViolationInput" value="false" />
			<c:forEach var="detail" items="${exam.examDetails}" varStatus="status">
				<c:set var="qIdStr">${detail.question.questionId}</c:set>
				<div class="question-block">
					<h5 class="mb-3">Câu ${status.index + 1}: ${detail.question.content}</h5>
					
					<div class="form-check mb-2">
						<input class="form-check-input" type="radio" name="answers[${detail.question.questionId}]" id="q${detail.question.questionId}_A" value="A"
							<c:if test="${savedAnswers[qIdStr] == 'A'}">checked</c:if>>
						<label class="form-check-label" for="q${detail.question.questionId}_A">
							A. ${detail.question.optionA}
						</label>
					</div>
					
					<div class="form-check mb-2">
						<input class="form-check-input" type="radio" name="answers[${detail.question.questionId}]" id="q${detail.question.questionId}_B" value="B"
							<c:if test="${savedAnswers[qIdStr] == 'B'}">checked</c:if>>
						<label class="form-check-label" for="q${detail.question.questionId}_B">
							B. ${detail.question.optionB}
						</label>
					</div>
					
					<div class="form-check mb-2">
						<input class="form-check-input" type="radio" name="answers[${detail.question.questionId}]" id="q${detail.question.questionId}_C" value="C"
							<c:if test="${savedAnswers[qIdStr] == 'C'}">checked</c:if>>
						<label class="form-check-label" for="q${detail.question.questionId}_C">
							C. ${detail.question.optionC}
						</label>
					</div>
					
					<div class="form-check mb-2">
						<input class="form-check-input" type="radio" name="answers[${detail.question.questionId}]" id="q${detail.question.questionId}_D" value="D"
							<c:if test="${savedAnswers[qIdStr] == 'D'}">checked</c:if>>
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
		
		function confirmSubmit() {
			if (confirm("Bạn có chắc chắn muốn nộp bài?")) {
				document.getElementById("examForm").submit();
			}
		}

		// Giám sát thi
		var violationCount = 0;
		var maxViolations = 3;
		var isExamStarted = false;
		var isHandlingViolation = false; // Tránh báo lỗi nhiều lần liên tiếp khi chuyển tab (kích hoạt cả visibility và fullscreen)

		document.getElementById("startExamBtn").addEventListener("click", function() {
			var elem = document.documentElement;
			if (elem.requestFullscreen) {
				elem.requestFullscreen().then(function() {
					startExamContent();
				}).catch(function(err) {
					alert("Trình duyệt không hỗ trợ toàn màn hình hoặc bạn đã từ chối.");
				});
			} else {
				// Fallback cho trình duyệt cũ
				startExamContent();
			}
		});

		document.getElementById("resumeExamBtn").addEventListener("click", function() {
			var elem = document.documentElement;
			if (elem.requestFullscreen) {
				elem.requestFullscreen().then(function() {
					resumeExamContent();
				}).catch(function(err) {
					alert("Trình duyệt không hỗ trợ toàn màn hình hoặc bạn đã từ chối.");
				});
			} else {
				// Fallback
				resumeExamContent();
			}
		});

		function startExamContent() {
			var startScreen = document.getElementById("startScreen");
			startScreen.classList.remove("d-flex");
			startScreen.classList.add("d-none");
			document.getElementById("examContent").style.display = "block";
			isExamStarted = true;
			updateTimer(); // Bắt đầu tính giờ hiển thị
		}

		function resumeExamContent() {
			var resumeScreen = document.getElementById("resumeScreen");
			resumeScreen.classList.remove("d-flex");
			resumeScreen.classList.add("d-none");
			document.getElementById("examContent").style.display = "block";
			
			// Reset cờ xử lý sau khi đã quay lại fullscreen an toàn một lúc
			setTimeout(function() {
				isHandlingViolation = false;
			}, 500);
		}

		function handleViolation(reason) {
			if (!isExamStarted || isHandlingViolation) return;
			
			isHandlingViolation = true;
			violationCount++;
			
			// Ẩn bài thi, hiện màn hình yêu cầu tiếp tục
			document.getElementById("examContent").style.display = "none";
			
			if (violationCount >= maxViolations) {
				alert("Bạn đã vi phạm quy chế thi " + maxViolations + " lần (" + reason + "). Hệ thống sẽ tự động nộp bài!");
				document.getElementById("isViolationInput").value = "true";
				document.getElementById("examForm").submit();
			} else {
				alert("CẢNH BÁO VI PHẠM (" + violationCount + "/" + maxViolations + "): " + reason + "\nBạn phải quay lại chế độ Toàn màn hình để tiếp tục làm bài.");
				
				var resumeScreen = document.getElementById("resumeScreen");
				resumeScreen.classList.remove("d-none");
				resumeScreen.classList.add("d-flex");
			}
		}

		document.addEventListener("visibilitychange", function() {
			if (document.hidden) {
				handleViolation("Chuyển tab hoặc thu nhỏ trình duyệt");
			}
		});

		document.addEventListener("fullscreenchange", function() {
			if (!document.fullscreenElement && isExamStarted) {
				handleViolation("Thoát chế độ toàn màn hình");
			}
		});

		// Auto-save answers progress to Redis via AJAX
		document.querySelectorAll("input[type=radio][name^='answers[']").forEach(function(input) {
			input.addEventListener("change", function() {
				var name = this.getAttribute("name");
				var questionId = name.substring(name.indexOf('[') + 1, name.indexOf(']'));
				var answer = this.value;
				
				var params = new URLSearchParams();
				params.append("questionId", questionId);
				params.append("answer", answer);
				
				fetch("${pageContext.request.contextPath}/exam/save-progress", {
					method: "POST",
					headers: {
						"Content-Type": "application/x-www-form-urlencoded"
					},
					body: params.toString()
				})
				.then(function(res) { return res.json(); })
				.then(function(data) {
					console.log("Auto-save successful:", questionId, answer, data);
				})
				.catch(function(err) {
					console.error("Auto-save error:", err);
				});
			});
		});
	</script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
