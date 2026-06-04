<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../Shared/_LayoutStart.jsp"%>

<c:set var="lecturerIdValue"
	value="${empty lecturerProfile.lecturerId ? sessionScope.LOGIN_USER : lecturerProfile.lecturerId}" />
<c:set var="lecturerFullName"
	value="${fn:trim(empty lecturerProfile.lastName ? '' : lecturerProfile.lastName)} ${fn:trim(empty lecturerProfile.firstName ? '' : lecturerProfile.firstName)}" />
<c:set var="lecturerFullNameValue"
	value="${fn:trim(lecturerFullName)}" />
<c:set var="lecturerPhoneValue"
	value="${empty lecturerProfile.phoneNumber ? 'Chưa có dữ liệu' : lecturerProfile.phoneNumber}" />
<c:set var="lecturerAddressValue"
	value="${empty lecturerProfile.address ? 'Chưa có dữ liệu' : lecturerProfile.address}" />
<c:set var="lecturerEmailValue"
	value="${empty lecturerProfile.email ? 'Chưa có dữ liệu' : lecturerProfile.email}" />

<c:set var="totalQuestionsValue"
	value="${empty dashboard ? 0 : dashboard.totalQuestions}" />
<c:set var="totalRegistrationsValue"
	value="${empty dashboard ? 0 : dashboard.totalRegistrations}" />
<c:set var="totalRegisteredSubjectsValue"
	value="${empty dashboard ? 0 : dashboard.totalRegisteredSubjects}" />
<c:set var="totalRegisteredClassesValue"
	value="${empty dashboard ? 0 : dashboard.totalRegisteredClasses}" />
<c:set var="totalRelatedExamsValue"
	value="${empty dashboard ? 0 : dashboard.totalRelatedExams}" />
<c:set var="latestWorkingSubjectValue"
	value="${empty dashboard.latestWorkingSubjectName ? 'Chưa có dữ liệu' : dashboard.latestWorkingSubjectName}" />

<style>
.lecturer-dashboard .hero-card {
	border: 0;
	border-radius: 1.5rem;
	color: #fff;
	background: linear-gradient(135deg, #0f766e 0%, #0ea5e9 100%);
	box-shadow: 0 1rem 2.5rem rgba(14, 165, 233, 0.18);
}

.lecturer-dashboard .info-card,
.lecturer-dashboard .stats-card,
.lecturer-dashboard .table-card {
	border: 0;
	border-radius: 1.25rem;
	box-shadow: 0 0.75rem 2rem rgba(15, 23, 42, 0.08);
}

.lecturer-dashboard .avatar-box {
	width: 5.5rem;
	height: 5.5rem;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	background: rgba(255, 255, 255, 0.16);
	border: 2px solid rgba(255, 255, 255, 0.22);
	font-size: 2rem;
	font-weight: 700;
}

.lecturer-dashboard .stat-value {
	font-size: 1.75rem;
	font-weight: 700;
	color: #0f172a;
}

.lecturer-dashboard .stat-label,
.lecturer-dashboard .detail-label {
	color: #64748b;
	font-size: 0.9rem;
}

.lecturer-dashboard .detail-value {
	color: #0f172a;
	font-weight: 600;
}
</style>

<div class="container-fluid lecturer-dashboard">
	<div class="card hero-card mb-4">
		<div class="card-body p-4 p-lg-5">
			<div class="d-flex flex-column flex-lg-row align-items-lg-center gap-4">
				<div class="avatar-box">
					<c:choose>
						<c:when test="${not empty lecturerProfile.firstName}">
							${fn:substring(fn:trim(lecturerProfile.firstName), 0, 1)}
						</c:when>
						<c:otherwise>G</c:otherwise>
					</c:choose>
				</div>

				<div class="flex-grow-1">
					<h1 class="h2 mb-2">
						<c:choose>
							<c:when test="${not empty lecturerProfile}">
								${empty lecturerFullNameValue ? lecturerIdValue : lecturerFullNameValue}
							</c:when>
							<c:otherwise>${lecturerIdValue}</c:otherwise>
						</c:choose>
					</h1>

					<div class="row g-2 text-white-50">
						<div class="col-md-6">
							<i class="bi bi-person-badge me-2"></i> Mã giảng viên:
							<span class="fw-semibold text-white">${empty lecturerIdValue ? 'Chưa có dữ liệu' : lecturerIdValue}</span>
						</div>
						<div class="col-md-6">
							<i class="bi bi-award me-2"></i> Vai trò:
							<span class="fw-semibold text-white">Giảng viên</span>
						</div>
						<div class="col-md-6">
							<i class="bi bi-telephone me-2"></i> Số điện thoại:
							<span class="fw-semibold text-white">${lecturerPhoneValue}</span>
						</div>
						<div class="col-md-6">
							<i class="bi bi-calendar-event me-2"></i> Hôm nay:
							<span class="fw-semibold text-white"><fmt:formatDate value="${today}" pattern="dd/MM/yyyy" /></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row g-4 mb-4">
		<div class="col-12 col-xl-4">
			<div class="card info-card h-100">
				<div class="card-body p-4">
					<h2 class="h5 mb-4">Thông tin cá nhân</h2>

					<div class="mb-3">
						<div class="detail-label mb-1">Họ và tên</div>
						<div class="detail-value">
							<c:choose>
								<c:when test="${not empty lecturerFullNameValue}">
									${lecturerFullNameValue}
								</c:when>
								<c:otherwise>Chưa có dữ liệu</c:otherwise>
							</c:choose>
						</div>
					</div>

					<div class="mb-3">
						<div class="detail-label mb-1">Địa chỉ</div>
						<div class="detail-value">${lecturerAddressValue}</div>
					</div>

					<div>
						<div class="detail-label mb-1">Email</div>
						<div class="detail-value">${lecturerEmailValue}</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-12 col-xl-8">
			<div class="row g-4">
				<div class="col-12 col-md-6 col-xxl-4">
					<div class="card stats-card h-100">
						<div class="card-body">
							<div class="stat-label mb-2">Tổng câu hỏi đã tạo</div>
							<div class="stat-value">${totalQuestionsValue}</div>
						</div>
					</div>
				</div>

				<div class="col-12 col-md-6 col-xxl-4">
					<div class="card stats-card h-100">
						<div class="card-body">
							<div class="stat-label mb-2">Tổng đợt đăng ký thi</div>
							<div class="stat-value">${totalRegistrationsValue}</div>
						</div>
					</div>
				</div>

				<div class="col-12 col-md-6 col-xxl-4">
					<div class="card stats-card h-100">
						<div class="card-body">
							<div class="stat-label mb-2">Tổng môn đã đăng ký</div>
							<div class="stat-value">${totalRegisteredSubjectsValue}</div>
						</div>
					</div>
				</div>

				<div class="col-12 col-md-6 col-xxl-4">
					<div class="card stats-card h-100">
						<div class="card-body">
							<div class="stat-label mb-2">Tổng lớp đã đăng ký</div>
							<div class="stat-value">${totalRegisteredClassesValue}</div>
						</div>
					</div>
				</div>

				<div class="col-12 col-md-6 col-xxl-4">
					<div class="card stats-card h-100">
						<div class="card-body">
							<div class="stat-label mb-2">Số bài thi liên quan</div>
							<div class="stat-value">${totalRelatedExamsValue}</div>
						</div>
					</div>
				</div>

				<div class="col-12 col-md-6 col-xxl-4">
					<div class="card stats-card h-100">
						<div class="card-body">
							<div class="stat-label mb-2">Môn làm việc gần nhất</div>
							<div class="detail-value">${latestWorkingSubjectValue}</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row g-4">
		<div class="col-12 col-xl-7">
			<div class="card table-card h-100">
				<div class="card-body p-4">
					<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-2 mb-4">
						<h2 class="h5 mb-0">Đăng ký thi gần đây</h2>
						<span class="badge text-bg-info">Tối đa 5 bản ghi mới nhất</span>
					</div>

					<div class="table-responsive">
						<table class="table align-middle mb-0">
							<thead class="table-light">
								<tr>
									<th>Môn học</th>
									<th>Lớp</th>
									<th>Lần thi</th>
									<th>Trình độ</th>
									<th>Ngày thi</th>
									<th>Số câu / phút</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty recentRegistrations}">
										<c:forEach var="registration" items="${recentRegistrations}">
											<tr>
												<td>
													<div class="fw-semibold">${empty registration.subjectName ? 'Chưa có dữ liệu' : registration.subjectName}</div>
													<div class="text-muted small">${empty registration.subjectId ? 'N/A' : registration.subjectId}</div>
												</td>
												<td>
													<div class="fw-semibold">${empty registration.classId ? 'Chưa có dữ liệu' : registration.classId}</div>
													<div class="text-muted small">${empty registration.className ? 'Chưa có dữ liệu' : registration.className}</div>
												</td>
												<td>Lần ${empty registration.tryNumber ? 0 : registration.tryNumber}</td>
												<td>
													<span class="badge rounded-pill text-bg-light border">${empty registration.level ? 'Chưa có dữ liệu' : registration.level}</span>
												</td>
												<td>
													<c:choose>
														<c:when test="${not empty registration.examDate}">
															<fmt:formatDate value="${registration.examDate}" pattern="dd/MM/yyyy" />
														</c:when>
														<c:otherwise>Chưa có dữ liệu</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${not empty registration.numberOfQuestions or not empty registration.duration}">
															${empty registration.numberOfQuestions ? 0 : registration.numberOfQuestions}
															câu /
															${empty registration.duration ? 0 : registration.duration}
															phút
														</c:when>
														<c:otherwise>Chưa có dữ liệu</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="6" class="text-center py-4 text-muted">Chưa có dữ liệu</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="col-12 col-xl-5">
			<div class="card table-card h-100">
				<div class="card-body p-4">
					<div class="d-flex justify-content-between align-items-center mb-4">
						<h2 class="h5 mb-0">Môn/lớp đã đăng ký</h2>
						<span class="badge text-bg-secondary">${fn:length(registeredSubjectClasses)} mục</span>
					</div>

					<div class="table-responsive">
						<table class="table align-middle mb-0">
							<thead class="table-light">
								<tr>
									<th>Môn học</th>
									<th>Lớp</th>
									<th>Đăng ký</th>
									<th>Gần nhất</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty registeredSubjectClasses}">
										<c:forEach var="item" items="${registeredSubjectClasses}">
											<tr>
												<td>
													<div class="fw-semibold">${empty item.subjectName ? 'Chưa có dữ liệu' : item.subjectName}</div>
													<div class="text-muted small">${empty item.subjectId ? 'N/A' : item.subjectId}</div>
												</td>
												<td>
													<div class="fw-semibold">${empty item.classId ? 'Chưa có dữ liệu' : item.classId}</div>
													<div class="text-muted small">${empty item.className ? 'Chưa có dữ liệu' : item.className}</div>
												</td>
												<td>${item.registrationCount}</td>
												<td>
													<c:choose>
														<c:when test="${not empty item.latestExamDate}">
															<fmt:formatDate value="${item.latestExamDate}" pattern="dd/MM/yyyy" />
														</c:when>
														<c:otherwise>Chưa có dữ liệu</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="4" class="text-center py-4 text-muted">Chưa có dữ liệu</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="../Shared/_LayoutEnd.jsp"%>
