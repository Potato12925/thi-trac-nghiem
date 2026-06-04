<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String role = session.getAttribute("ROLE") != null ? session.getAttribute("ROLE").toString() : "";

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

String activeHome = path.equals("/") || path.equals("/home") || path.equals("/admin/home")
		|| path.equals("/lecturers/home") || path.equals("/students/home") ? "active" : "";

String activeClass = path.equals("/classrooms") || path.startsWith("/classrooms/") ? "active" : "";
String activeStudent = path.equals("/students") || path.startsWith("/students/") ? "active" : "";
String activeLecturer = path.equals("/lecturers") || path.startsWith("/lecturers/") ? "active" : "";
String activeSubject = path.equals("/subjects") || path.startsWith("/subjects/") ? "active" : "";
String activeQuestion = path.equals("/questions") || path.startsWith("/questions/") ? "active" : "";
String activeLecturerRegistration = path.equals("/lecturer-registration")
		|| path.startsWith("/lecturer-registration/") ? "active" : "";
String activeScore = path.equals("/score") || path.startsWith("/score/") || path.equals("/scores")
		|| path.startsWith("/scores/") ? "active" : "";
String activeAccount = path.equals("/account") || path.startsWith("/account/") ? "active" : "";
String activeReview = path.equals("/review") || path.startsWith("/review/") ? "active" : "";
String activeExam = path.equals("/exam") || path.startsWith("/exam/") ? "active" : "";
String activeHistory = path.equals("/history") || path.startsWith("/history/") ? "active" : "";
String activeResult = path.equals("/result") || path.startsWith("/result/") ? "active" : "";
String activeSettings = path.equals("/lecturers/settings") || path.startsWith("/lecturers/settings/")
		|| path.equals("/students/settings") || path.startsWith("/students/settings/") ? "active" : "";
%>

<aside class="app-sidebar" id="appSidebar">

	<div class="sidebar-brand">
		<h2>Lập trình web</h2>
		<p>Hệ thống thi trắc nghiệm</p>
	</div>

	<nav class="sidebar-nav">
		<ul>
			<%
			String homeUrl = "/";

			if (role.equals("PGV")) {
				homeUrl = "/admin/home";
			} else if (role.equals("GIAOVIEN")) {
				homeUrl = "/lecturers/home";
			} else if (role.equals("SINHVIEN")) {
				homeUrl = "/students/home";
			}
			%>

			<li><a class="nav-link <%= activeHome %>"
				href="${pageContext.request.contextPath}<%= homeUrl %>"> <i
					class="bi bi-house-door"></i> <span>Trang chủ</span>
			</a></li>

			<%
			if (role.equals("PGV")) {
			%>
			<li><a class="nav-link <%= activeClass %>"
				href="${pageContext.request.contextPath}/classrooms"> <i
					class="bi bi-mortarboard"></i> <span>Lớp học</span>
			</a></li>

			<li><a class="nav-link <%= activeStudent %>"
				href="${pageContext.request.contextPath}/students"> <i
					class="bi bi-people"></i> <span>Sinh viên</span>
			</a></li>

			<li><a class="nav-link <%= activeLecturer %>"
				href="${pageContext.request.contextPath}/lecturers"> <i
					class="bi bi-person-badge"></i> <span>Giảng viên</span>
			</a></li>

			<li><a class="nav-link <%= activeSubject %>"
				href="${pageContext.request.contextPath}/subjects"> <i
					class="bi bi-book"></i> <span>Môn học</span>
			</a></li>

			<li><a class="nav-link <%= activeQuestion %>"
				href="${pageContext.request.contextPath}/questions"> <i
					class="bi bi-patch-question"></i> <span>Bộ đề</span>
			</a></li>

			<li><a class="nav-link <%= activeLecturerRegistration %>"
				href="${pageContext.request.contextPath}/lecturer-registration"> <i
					class="bi bi-pencil-square"></i> <span>Đăng ký thi</span>
			</a></li>

			<li><a class="nav-link <%= activeScore %>"
				href="${pageContext.request.contextPath}/scores"> <i
					class="bi bi-bar-chart"></i> <span>Bảng điểm</span>
			</a></li>

			<li><a class="nav-link <%= activeAccount %>"
				href="${pageContext.request.contextPath}/account"> <i
					class="bi bi-person-lock"></i> <span>Tài khoản</span>
			</a></li>
			<%
			}
			%>

			<%
			if (role.equals("GIAOVIEN")) {
			%>
			<li><a class="nav-link <%= activeQuestion %>"
				href="${pageContext.request.contextPath}/questions"> <i
					class="bi bi-patch-question"></i> <span>Câu hỏi thi</span>
			</a></li>

			<li><a class="nav-link <%= activeLecturerRegistration %>"
				href="${pageContext.request.contextPath}/lecturer-registration"> <i
					class="bi bi-pencil-square"></i> <span>Đăng ký thi</span>
			</a></li>

			<li><a class="nav-link <%= activeScore %>"
				href="${pageContext.request.contextPath}/scores"> <i
					class="bi bi-bar-chart"></i> <span>Bảng điểm</span>
			</a></li>

			<li><a class="nav-link <%= activeSettings %>"
				href="${pageContext.request.contextPath}/lecturers/settings"> <i
					class="bi bi-gear"></i> <span>Cài đặt</span>
			</a></li>
			<%
			}
			%>

			<%
			if (role.equals("SINHVIEN")) {
			%>
			<li><a class="nav-link <%= activeExam %>"
				href="${pageContext.request.contextPath}/exam"> <i
					class="bi bi-ui-checks-grid"></i> <span>Thi trắc nghiệm</span>
			</a></li>
			<li><a class="nav-link <%= activeHistory %>"
				href="${pageContext.request.contextPath}/history"> <i
					class="bi bi-clock-history"></i> <span>Lịch sử thi</span>
			</a></li>
			<li><a class="nav-link <%= activeSettings %>"
				href="${pageContext.request.contextPath}/students/settings"> <i
					class="bi bi-gear"></i> <span>Cài đặt</span>
			</a></li>
			<%
			}
			%>
		</ul>
	</nav>

	<div class="sidebar-footer">
		<form action="${pageContext.request.contextPath}/auth/logout"
			method="post">
			<button type="submit" class="sidebar-logout">
				<i class="bi bi-box-arrow-left"></i> Đăng xuất
			</button>
		</form>
	</div>

</aside>
