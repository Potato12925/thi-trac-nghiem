<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String role = session.getAttribute("ROLE") != null
        ? session.getAttribute("ROLE").toString()
        : "";

String username = session.getAttribute("LOGIN_USER") != null
        ? session.getAttribute("LOGIN_USER").toString()
        : "User";

/* ================= ACTIVE MENU ================= */

String path = request.getRequestURI();
String contextPath = request.getContextPath();

if (path.startsWith(contextPath)) {
    path = path.substring(contextPath.length());
}

if (path.isEmpty()) {
    path = "/";
}

if (path.length() > 1 && path.endsWith("/")) {
    path = path.substring(0, path.length() - 1);
}

String activeHome =
        path.equals("/") || path.equals("/home")
        ? "active" : "";

String activeClass =
        path.equals("/classRoom") || path.startsWith("/classRoom/")
        ? "active" : "";

String activeStudent =
        path.equals("/student") || path.startsWith("/student/")
        ? "active" : "";

String activeTeacher =
        path.equals("/lecturer") || path.startsWith("/lecturer/")
        ? "active" : "";

String activeSubject =
        path.equals("/subject") || path.startsWith("/subject/")
        ? "active" : "";

String activeQuestion =
        path.equals("/questions") || path.startsWith("/questions/")
        ? "active" : "";

String activeExamRegistration =
        path.equals("/exam-registration")
        || path.startsWith("/exam-registration/")
        ? "active" : "";

String activeScore =
        path.equals("/score") || path.startsWith("/score/")
        ? "active" : "";

String activeAccount =
        path.equals("/account") || path.startsWith("/account/")
        ? "active" : "";

String activeReview =
        path.equals("/review") || path.startsWith("/review/")
        ? "active" : "";

String activeExam =
        path.equals("/exam") || path.startsWith("/exam/")
        ? "active" : "";

String activeHistory =
        path.equals("/history") || path.startsWith("/history/")
        ? "active" : "";

String activeResult =
        path.equals("/result") || path.startsWith("/result/")
        ? "active" : "";
%>

<aside class="app-sidebar" id="appSidebar">

	<!-- Brand -->
	<div class="sidebar-brand">
		<h2>Lập trình web</h2>
		<p>Hệ thống thi trắc nghiệm</p>
	</div>

	<!-- Navigation -->
	<nav class="sidebar-nav">

		<ul>

			<!-- HOME -->
			<li>
				<a class="nav-link <%= activeHome %>"
					href="${pageContext.request.contextPath}/student/home">

					<i class="bi bi-house-door"></i>
					<span>Trang chủ</span>

				</a>
			</li>

			<!-- ================= PGV ================= -->
			<%
			if (role.equals("PGV")) {
			%>

			<li>
				<a class="nav-link <%= activeClass %>"
					href="${pageContext.request.contextPath}/class">

					<i class="bi bi-mortarboard"></i>
					<span>Lớp học</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeStudent %>"
					href="${pageContext.request.contextPath}/student">

					<i class="bi bi-people"></i>
					<span>Sinh viên</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeTeacher %>"
					href="${pageContext.request.contextPath}/teacher">

					<i class="bi bi-person-badge"></i>
					<span>Giảng viên</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeSubject %>"
					href="${pageContext.request.contextPath}/subject">

					<i class="bi bi-book"></i>
					<span>Môn học</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeQuestion %>"
					href="${pageContext.request.contextPath}/question">

					<i class="bi bi-patch-question"></i>
					<span>Bộ đề</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeExamRegistration %>"
					href="${pageContext.request.contextPath}/exam-registration">

					<i class="bi bi-pencil-square"></i>
					<span>Đăng ký thi</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeScore %>"
					href="${pageContext.request.contextPath}/score">

					<i class="bi bi-bar-chart"></i>
					<span>Bảng điểm</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeAccount %>"
					href="${pageContext.request.contextPath}/account">

					<i class="bi bi-person-lock"></i>
					<span>Tài khoản</span>

				</a>
			</li>

			<%
			}
			%>

			<!-- ================= GIAOVIEN ================= -->
			<%
			if (role.equals("GIAOVIEN")) {
			%>

			<li>
				<a class="nav-link <%= activeQuestion %>"
					href="${pageContext.request.contextPath}/question">

					<i class="bi bi-patch-question"></i>
					<span>Câu hỏi thi</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeExamRegistration %>"
					href="${pageContext.request.contextPath}/exam-registration">

					<i class="bi bi-pencil-square"></i>
					<span>Đăng ký thi</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeScore %>"
					href="${pageContext.request.contextPath}/score">

					<i class="bi bi-bar-chart"></i>
					<span>Bảng điểm</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeReview %>"
					href="${pageContext.request.contextPath}/review">

					<i class="bi bi-file-earmark-text"></i>
					<span>Xem bài thi</span>

				</a>
			</li>

			<%
			}
			%>

			<!-- ================= SINHVIEN ================= -->
			<%
			if (role.equals("SINHVIEN")) {
			%>

			<li>
				<a class="nav-link <%= activeExam %>"
					href="${pageContext.request.contextPath}/exam">

					<i class="bi bi-ui-checks-grid"></i>
					<span>Thi trắc nghiệm</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeHistory %>"
					href="${pageContext.request.contextPath}/history">

					<i class="bi bi-clock-history"></i>
					<span>Lịch sử thi</span>

				</a>
			</li>

			<li>
				<a class="nav-link <%= activeResult %>"
					href="${pageContext.request.contextPath}/result">

					<i class="bi bi-award"></i>
					<span>Kết quả</span>

				</a>
			</li>

			<%
			}
			%>

		</ul>

	</nav>

	<!-- Footer -->
	<div class="sidebar-footer">

		<form action="${pageContext.request.contextPath}/auth/logout"
			method="post">

			<button type="submit" class="sidebar-logout">

				<i class="bi bi-box-arrow-left"></i>
				Đăng xuất

			</button>

		</form>

	</div>

</aside>