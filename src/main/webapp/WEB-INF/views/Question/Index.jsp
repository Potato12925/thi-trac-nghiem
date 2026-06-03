<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="isLecturer" value="${sessionScope.ROLE eq 'GIAOVIEN'}" />

<%
request.setAttribute("pageTitle", "Question Management");
request.setAttribute("customCss", "question-management.css");
request.setAttribute("customJs", "question-management.js");
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

	<c:if test="${isLecturer}">
		<div class="alert alert-info">Giáo viên chỉ được xem và cập nhật câu hỏi do mình soạn.</div>
	</c:if>

	<div class="border bg-white mb-4 shadow-sm form-section">

		<form method="get" action="${pageContext.request.contextPath}/questions" class="row g-3 mb-4">
			<div class="col-md-9">
				<label class="form-label small text-secondary"> Search keyword </label>
				<input type="text" name="keyword" class="form-control"
					value="${keyword}" placeholder="Search content, level, subject, lecturer" />
			</div>
			<div class="col-md-3 d-flex align-items-end">
				<button type="submit" class="btn btn-outline-secondary w-100">Search</button>
			</div>
		</form>

		<form:form id="questionForm" method="post"
			modelAttribute="questionDTO"
			action="${pageContext.request.contextPath}/questions/add">
			<input type="hidden" name="page" value="${currentPage}" />
			<input type="hidden" name="keyword" value="${keyword}" />

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

				<button type="button" class="btn btn-outline-secondary" id="btnUndo" disabled>Undo</button>
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

		<div class="pagination-wrapper">
			<c:if test="${currentPage > 1}">
				<a class="pagination-item" href="questions?page=1&keyword=${keyword}"> First </a>

				<a class="pagination-item" href="questions?page=${currentPage - 1}&keyword=${keyword}">
					&laquo; </a>
			</c:if>

			<c:if test="${currentPage > 3}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:forEach begin="${currentPage - 2 < 1 ? 1 : currentPage - 2}"
				end="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}"
				var="i">
				<a href="questions?page=${i}&keyword=${keyword}"
					class="pagination-item ${currentPage == i ? 'active' : ''}">
					${i} </a>
			</c:forEach>

			<c:if test="${currentPage < totalPages - 2}">
				<span class="pagination-ellipsis">...</span>
			</c:if>

			<c:if test="${currentPage < totalPages}">
				<a class="pagination-item" href="questions?page=${currentPage + 1}&keyword=${keyword}">
					&raquo; </a>

				<a class="pagination-item" href="questions?page=${totalPages}&keyword=${keyword}">
					Last </a>
			</c:if>
		</div>
	</div>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp"%>