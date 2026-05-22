<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Quản lý câu hỏi");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<style>
* {
	box-sizing: border-box;
}

.page-wrapper {
	width: 100%;
	max-width: 1600px;
	margin: 0 auto;
	padding-left: 12px;
	padding-right: 12px;
	overflow-x: hidden;
}

.form-section {
	border-radius: 16px;
	padding: 24px;
	width: 100%;
	max-width: 100%;
	overflow: hidden;
}

.form-section .form-label {
	font-size: 0.85rem;
	font-weight: 500;
	margin-bottom: 6px;
}

.form-section .form-control {
	height: 46px;
	border-radius: 10px;
	font-size: 0.95rem;
	padding-left: 14px;
}

.form-section .form-control:focus {
	box-shadow: none;
}

.form-actions {
	padding-top: 12px;
	border-top: 1px solid #e9ecef;
	margin-top: 32px;
}

.form-actions .btn {
	min-width: 120px;
	height: 44px;
	border-radius: 10px;
	font-weight: 500;
}

.question-table-card {
	border-radius: 16px;
	overflow: hidden;
}

.question-table-wrapper {
	max-height: 650px;
	overflow: auto;
}

.question-table {
	min-width: 1200px;
}

.question-table thead th {
	position: sticky;
	top: 0;
	z-index: 10;
	white-space: nowrap;
	font-size: 0.85rem;
	font-weight: 600;
	padding-top: 14px;
	padding-bottom: 14px;
}

.question-table tbody td {
	vertical-align: middle;
	padding-top: 14px;
	padding-bottom: 14px;
	font-size: 0.94rem;
}

.question-table tbody tr {
	transition: all 0.15s ease;
}

.question-table tbody tr:hover {
	transform: scale(1.002);
}

.btn-edit-question {
	width: 36px;
	height: 36px;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	border-radius: 10px;
}

.page-title-section {
	margin-bottom: 28px;
}

.page-title-section h1 {
	font-weight: 700;
	letter-spacing: -0.5px;
}

.alert {
	border-radius: 12px;
	padding: 14px 18px;
}

@media ( max-width : 768px) {
	.page-wrapper {
		padding-left: 8px;
		padding-right: 8px;
	}
	.form-section {
		padding: 16px;
	}
	.form-actions {
		display: grid !important;
		grid-template-columns: 1fr;
	}
	.form-actions .btn {
		width: 100%;
	}
}

.form-section {
	overflow-x: hidden;
}

.form-section .row {
	--bs-gutter-x: 1rem;
	margin-left: calc(var(--bs-gutter-x)* -0.5);
	margin-right: calc(var(--bs-gutter-x)* -0.5);
}

.form-section .col-lg-3, .form-section .col-md-6 {
	min-width: 0;
}

.form-section .row>* {
	padding-left: 0.5rem;
	padding-right: 0.5rem;
}

.form-section .form-control {
	width: 100%;
	min-width: 0;
}
</style>

<div class="container-fluid page-wrapper">
	<div
		class="d-flex justify-content-between align-items-center page-title-section">
		<h1 class="h3 mb-0">Quản lý câu hỏi</h1>

	</div>

	<c:if test="${not empty errorMessage}">

		<div class="alert alert-danger">${errorMessage}</div>

	</c:if>

	<div class="border bg-white mb-4 shadow-sm form-section">
		<form:form id="questionForm" method="post"
			modelAttribute="questionForm"
			action="${pageContext.request.contextPath}/questions/create">

			<input type="hidden" id="_method" name="_method" value="POST" />

			<div class="row g-4">

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Mã câu hỏi
					</label>

					<form:input path="questionId" cssClass="form-control" />

					<form:errors path="questionId"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Mã môn học
					</label>

					<form:input path="subjectId" cssClass="form-control" />

					<form:errors path="subjectId"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Trình độ </label>

					<form:input path="level" cssClass="form-control" />

					<form:errors path="level" cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Nội dung
						câu hỏi </label>

					<form:input path="content" cssClass="form-control" />

					<form:errors path="content"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Đáp án A </label>

					<form:input path="optionA" cssClass="form-control" />

					<form:errors path="optionA"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Đáp án B </label>

					<form:input path="optionB" cssClass="form-control" />

					<form:errors path="optionB"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Đáp án C </label>

					<form:input path="optionC" cssClass="form-control" />

					<form:errors path="optionC"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Đáp án D </label>

					<form:input path="optionD" cssClass="form-control" />

					<form:errors path="optionD"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Đáp án đúng
					</label>

					<form:input path="correctAnswer" cssClass="form-control" />

					<form:errors path="correctAnswer"
						cssClass="text-danger small mt-1 d-block" />

				</div>

				<div class="col-lg-3 col-md-6">

					<label class="form-label small text-secondary"> Mã giáo
						viên </label>

					<form:input path="teacherId" cssClass="form-control" />

					<form:errors path="teacherId"
						cssClass="text-danger small mt-1 d-block" />

				</div>

			</div>

			<div class="d-flex gap-3 flex-wrap form-actions">
				<button type="submit" class="btn btn-dark px-4"
					onclick="configureFormAction('create', 'POST')">Thêm</button>

				<button type="submit" class="btn btn-outline-secondary px-4"
					onclick="configureFormAction('edit', 'PUT')">Cập nhật</button>

				<button type="submit" class="btn btn-outline-danger px-4"
					onclick="configureFormAction('remove', 'DELETE')">Xóa</button>

				<button type="button" class="btn btn-outline-dark"
					onclick="clearQuestionForm()">Làm mới</button>

			</div>

		</form:form>

	</div>

	<div class="card border-0 shadow-sm question-table-card">
		<div class="table-responsive p-0 question-table-wrapper">
			<table class="table table-hover align-middle mb-0 question-table">
				<thead class="table-light">

					<tr>

						<th>ID</th>
						<th>Môn học</th>
						<th>Trình độ</th>
						<th>Nội dung</th>
						<th>A</th>
						<th>B</th>
						<th>C</th>
						<th>D</th>
						<th>Đáp án</th>
						<th>Giáo viên</th>
						<th class="text-end">Hành động</th>

					</tr>

				</thead>

				<tbody>

					<c:forEach var="question" items="${questionList}">

						<tr>

							<td>${question.questionId}</td>

							<td>${question.subject.subjectId}</td>

							<td>${question.level}</td>

							<td>${question.content}</td>

							<td>${question.optionA}</td>

							<td>${question.optionB}</td>

							<td>${question.optionC}</td>

							<td>${question.optionD}</td>

							<td>${question.correctAnswer}</td>

							<td>${question.teacher.teacherId}</td>

							<td class="text-end pe-4">

								<button type="button"
									class="btn btn-sm btn-outline-secondary btn-edit-question">

									<i class="bi bi-pencil"></i>

								</button>

							</td>

						</tr>

					</c:forEach>

				</tbody>

			</table>

		</div>

	</div>

</div>

<script>

	function populateQuestionForm(tableRow) {

		const tableCells = tableRow.querySelectorAll("td");

		document.getElementById("questionId").value =
			tableCells[0].innerText;

		document.getElementById("subjectId").value =
			tableCells[1].innerText;

		document.getElementById("level").value =
			tableCells[2].innerText;

		document.getElementById("content").value =
			tableCells[3].innerText;

		document.getElementById("optionA").value =
			tableCells[4].innerText;

		document.getElementById("optionB").value =
			tableCells[5].innerText;

		document.getElementById("optionC").value =
			tableCells[6].innerText;

		document.getElementById("optionD").value =
			tableCells[7].innerText;

		document.getElementById("correctAnswer").value =
			tableCells[8].innerText;

		document.getElementById("teacherId").value =
			tableCells[9].innerText;

		document.getElementById("questionId").readOnly = true;
	}

	const editQuestionButtons =
		document.querySelectorAll(".btn-edit-question");

	editQuestionButtons.forEach(button => {

		button.addEventListener("click", function () {

			const selectedRow =
				this.closest("tr");

			populateQuestionForm(selectedRow);
		});
	});

	function configureFormAction(actionPath, httpMethod) {

		const questionForm =
			document.getElementById("questionForm");

		questionForm.action =
			"${pageContext.request.contextPath}/questions/"
			+ actionPath;

		document.getElementById("_method").value =
			httpMethod;
	}

	function clearQuestionForm() {

		document
			.getElementById("questionForm")
			.reset();

		document
			.getElementById("questionId")
			.readOnly = false;
	}

</script>

<%@ include file="../Shared/_LayoutEnd.jsp"%>