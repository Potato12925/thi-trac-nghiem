<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
request.setAttribute("pageTitle", "Question Management");
%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<div class="container-fluid page-wrapper">

	<div
		class="d-flex justify-content-between align-items-center page-title-section">

		<h1 class="h3 mb-0">Question Management</h1>

	</div>

	<c:if test="${not empty errorMessage}">

		<div class="alert alert-danger">${errorMessage}</div>

	</c:if>

	<div class="border bg-white mb-4 shadow-sm form-section">

		<form:form id="questionForm" method="post"
			modelAttribute="questionDTO"
			action="${pageContext.request.contextPath}/questions/add">

			<div class="row g-4">
				<div class="col-lg-3 col-md-6">
					<label class="form-label small text-secondary"> Question ID
					</label>

					<form:input path="questionId" id="questionId"
						cssClass="form-control" readonly="true" />

					<form:errors path="questionId"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-lg-3 col-md-6">
					<label class="form-label small text-secondary"> Subject ID
					</label>

					<form:input path="subjectId" id="subjectId" cssClass="form-control" />

					<form:errors path="subjectId"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-lg-3 col-md-6">
					<label class="form-label small text-secondary"> Level </label>

					<form:select path="level" id="level" cssClass="form-select">

						<form:option value="">
							Select level
						</form:option>

						<form:option value="A">
							A
						</form:option>

						<form:option value="B">
							B
						</form:option>

						<form:option value="C">
							C
						</form:option>

					</form:select>

					<form:errors path="level" cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-lg-3 col-md-6">
					<label class="form-label small text-secondary"> Correct
						Answer </label>

					<form:select path="correctAnswer" id="correctAnswer"
						cssClass="form-select">

						<form:option value="">
							Select answer
						</form:option>

						<form:option value="A">
							A
						</form:option>

						<form:option value="B">
							B
						</form:option>

						<form:option value="C">
							C
						</form:option>

						<form:option value="D">
							D
						</form:option>

					</form:select>

					<form:errors path="correctAnswer"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-12">
					<label class="form-label small text-secondary"> Question
						Content </label>

					<form:textarea path="content" id="content" rows="3"
						cssClass="form-control" />

					<form:errors path="content"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-lg-6">
					<label class="form-label small text-secondary"> Option A </label>

					<form:input path="optionA" id="optionA" cssClass="form-control" />

					<form:errors path="optionA"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-lg-6">
					<label class="form-label small text-secondary"> Option B </label>

					<form:input path="optionB" id="optionB" cssClass="form-control" />

					<form:errors path="optionB"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-lg-6">
					<label class="form-label small text-secondary"> Option C </label>

					<form:input path="optionC" id="optionC" cssClass="form-control" />

					<form:errors path="optionC"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-lg-6">
					<label class="form-label small text-secondary"> Option D </label>

					<form:input path="optionD" id="optionD" cssClass="form-control" />

					<form:errors path="optionD"
						cssClass="text-danger small mt-1 d-block" />
				</div>

				<div class="col-lg-4">
					<label class="form-label small text-secondary"> Lecturer ID
					</label>

					<form:input path="lecturerId" id="lecturerId"
						cssClass="form-control" />

					<form:errors path="lecturerId"
						cssClass="text-danger small mt-1 d-block" />
				</div>
			</div>

			<div class="d-flex gap-3 flex-wrap form-actions">
				<button type="submit" class="btn btn-dark px-4"
					onclick="configureFormAction('add')">Add</button>

				<button type="submit" class="btn btn-outline-secondary px-4"
					onclick="configureFormAction('update')">Update</button>

				<button type="submit" class="btn btn-outline-danger px-4"
					onclick="configureFormAction('delete')">Delete</button>

				<button type="button" class="btn btn-outline-dark"
					onclick="clearQuestionForm()">Reset</button>
			</div>
		</form:form>
	</div>

	<div class="card border-0 shadow-sm question-table-card">
		<div class="table-responsive p-0 question-table-wrapper">
			<table class="table table-hover align-middle mb-0 question-table">
				<thead class="table-light">
					<tr>
						<th>ID</th>

						<th>Subject</th>

						<th>Level</th>

						<th>Content</th>

						<th>A</th>

						<th>B</th>

						<th>C</th>

						<th>D</th>

						<th>Answer</th>

						<th>Lecturer</th>

						<th class="text-end">Actions</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="question" items="${questions}">
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

							<td>${question.lecturer.lecturerId}</td>

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

		const tableCells =
			tableRow.querySelectorAll("td");

		document.getElementById("questionId").value =
			tableCells[0].innerText.trim();

		document.getElementById("subjectId").value =
			tableCells[1].innerText.trim();

		document.getElementById("level").value =
			tableCells[2].innerText.trim();

		document.getElementById("content").value =
			tableCells[3].innerText.trim();

		document.getElementById("optionA").value =
			tableCells[4].innerText.trim();

		document.getElementById("optionB").value =
			tableCells[5].innerText.trim();

		document.getElementById("optionC").value =
			tableCells[6].innerText.trim();

		document.getElementById("optionD").value =
			tableCells[7].innerText.trim();

		document.getElementById("correctAnswer").value =
			tableCells[8].innerText.trim();

		document.getElementById("lecturerId").value =
			tableCells[9].innerText.trim();

		document.getElementById("questionId").readOnly =
			true;
	}

	document
		.querySelectorAll(".btn-edit-question")
		.forEach(button => {

			button.addEventListener("click", function () {

				const selectedRow =
					this.closest("tr");

				populateQuestionForm(selectedRow);

			});

		});

	function configureFormAction(actionPath) {

		const questionForm =
			document.getElementById("questionForm");

		questionForm.action =
			"${pageContext.request.contextPath}/questions/"
			+ actionPath;
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