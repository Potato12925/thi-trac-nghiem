<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String role = session.getAttribute("ROLE") != null ? session.getAttribute("ROLE").toString() : "";
String username = session.getAttribute("LOGIN_USER") != null ? session.getAttribute("LOGIN_USER").toString() : "User";
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
String activeHome = path.equals("/") || path.equals("/home") ? "active" : "";
String activeClass = path.equals("/class") || path.startsWith("/class/") ? "active" : "";
String activeCreditClass = path.equals("/credit-class") || path.startsWith("/credit-class/") ? "active" : "";
String activeGrade = path.equals("/grade") || path.startsWith("/grade/") ? "active" : "";
String activeStudent = path.equals("/student") || path.startsWith("/student/") ? "active" : "";
String activeLecturer = path.equals("/lecturer") || path.startsWith("/lecturer/") ? "active" : "";
String activeRegistration = path.equals("/registration") || path.startsWith("/registration/") ? "active" : "";
String activeReport = path.equals("/report") || path.startsWith("/report/") ? "active" : "";
String activeSubject = path.equals("/subject") || path.startsWith("/subject/") ? "active" : "";
String activeQuestion = path.equals("/question") || path.startsWith("/question/") ? "active" : "";
%>
<aside class="app-sidebar" id="appSidebar">
	<div class="sidebar-brand">
		<h2>QLDSV_HTC</h2>
		<p>Quản lý điểm sinh viên</p>
	</div>
	<nav class="sidebar-nav" aria-label="Main Navigation">
		<ul>
			<li><a class="nav-link <%= activeHome %>"
				href="${pageContext.request.contextPath}/"><i
					class="bi bi-house"></i> Trang chủ</a></li>
			<li><a class="nav-link <%= activeClass %>"
				href="${pageContext.request.contextPath}/class"><i
					class="bi bi-journals"></i> Lớp học</a></li>
			<li><a class="nav-link <%= activeCreditClass %>"
				href="${pageContext.request.contextPath}/credit-class"><i
					class="bi bi-journal-text"></i> Lớp tín chỉ</a></li>
			<li><a class="nav-link <%= activeGrade %>"
				href="${pageContext.request.contextPath}/grade"><i
					class="bi bi-graph-up"></i> Bảng điểm</a></li>
			<li><a class="nav-link <%= activeStudent %>"
				href="${pageContext.request.contextPath}/student"><i
					class="bi bi-people"></i> Sinh viên</a></li>
			<li><a class="nav-link <%= activeLecturer %>"
				href="${pageContext.request.contextPath}/lecturer"><i
					class="bi bi-person-badge"></i> Giảng viên</a></li>
			<li><a class="nav-link <%= activeRegistration %>"
				href="${pageContext.request.contextPath}/registration"><i
					class="bi bi-pencil-square"></i> Đăng ký</a></li>
			<li><a class="nav-link <%= activeReport %>"
				href="${pageContext.request.contextPath}/report"><i
					class="bi bi-file-earmark-text"></i> Báo cáo</a></li>
			<li><a class="nav-link <%= activeSubject %>"
				href="${pageContext.request.contextPath}/subject"><i
					class="bi bi-book"></i> Môn học</a></li>
			<li><a class="nav-link <%= activeQuestion %>"
				href="${pageContext.request.contextPath}/question"><i
					class="bi bi-question-circle"></i> Câu hỏi</a></li>
		</ul>
	</nav>
	<div class="sidebar-footer">
		<div class="sidebar-roles">
			<span class="role-badge">PGV</span> <span class="role-badge">KHOA</span>
			<span class="role-badge">SV</span>
		</div>
		<form action="${pageContext.request.contextPath}/logout" method="post">
			<button type="submit" class="sidebar-logout"
				aria-label="Đăng xuất khỏi hệ thống">
				<i class="bi bi-box-arrow-left" aria-hidden="true"></i> Đăng xuất
			</button>
		</form>
	</div>
</aside>

<%
String role = session.getAttribute("ROLE") != null ? session.getAttribute("ROLE").toString() : "";

String username = session.getAttribute("LOGIN_USER") != null ? session.getAttribute("LOGIN_USER").toString() : "User";
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

			<!-- Dashboard -->
			<li><a class="nav-link active"
				href="${pageContext.request.contextPath}/home"> <i
					class="bi bi-house-door"></i> <span>Trang chủ</span>
			</a></li>

			<!-- ================= PGV ================= -->
			<%
			if (role.equals("PGV")) {
			%>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/class"> <i
					class="bi bi-mortarboard"></i> <span>Lớp học</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/student"> <i
					class="bi bi-people"></i> <span>Sinh viên</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/teacher"> <i
					class="bi bi-person-badge"></i> <span>Giảng viên</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/subject"> <i
					class="bi bi-book"></i> <span>Môn học</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/question"> <i
					class="bi bi-patch-question"></i> <span>Bộ đề</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/exam-registration"> <i
					class="bi bi-pencil-square"></i> <span>Đăng ký thi</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/score"> <i
					class="bi bi-bar-chart"></i> <span>Bảng điểm</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/account"> <i
					class="bi bi-person-lock"></i> <span>Tài khoản</span>
			</a></li>

			<%
			}
			%>

			<!-- ================= GIANG VIEN ================= -->
			<%
			if (role.equals("GIAOVIEN")) {
			%>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/question"> <i
					class="bi bi-patch-question"></i> <span>Câu hỏi thi</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/exam-registration"> <i
					class="bi bi-pencil-square"></i> <span>Đăng ký thi</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/score"> <i
					class="bi bi-bar-chart"></i> <span>Bảng điểm</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/review"> <i
					class="bi bi-file-earmark-text"></i> <span>Xem bài thi</span>
			</a></li>

			<%
			}
			%>

			<!-- ================= SINH VIEN ================= -->
			<%
			if (role.equals("SINHVIEN")) {
			%>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/exam"> <i
					class="bi bi-ui-checks-grid"></i> <span>Thi trắc nghiệm</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/history"> <i
					class="bi bi-clock-history"></i> <span>Lịch sử thi</span>
			</a></li>

			<li><a class="nav-link"
				href="${pageContext.request.contextPath}/result"> <i
					class="bi bi-award"></i> <span>Kết quả</span>
			</a></li>

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
				<i class="bi bi-box-arrow-left"></i> Đăng xuất
			</button>

		</form>

	</div>

</aside>
