<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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