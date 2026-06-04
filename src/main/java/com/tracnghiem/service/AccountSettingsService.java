package com.tracnghiem.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.LecturerDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.entity.Account;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.entity.PasswordResetOtp;
import com.tracnghiem.entity.Student;

@Service
public class AccountSettingsService {

	@Autowired
	private AuthService authService;

	@Autowired
	private PasswordResetService passwordResetService;

	@Autowired
	private LecturerDAO lecturerDAO;

	@Autowired
	private StudentDAO studentDAO;

	@Transactional
	public void sendEmailChangeOtp(String username, String role, String newEmail) {
		String normalizedEmail = normalizeEmail(newEmail);
		validateEmailForChange(username, role, normalizedEmail);
		passwordResetService.sendOtpForEmailChange(username, normalizedEmail);
	}

	@Transactional
	public void confirmEmailChange(String username, String role, String newEmail, String otpCode) {
		String normalizedEmail = normalizeEmail(newEmail);
		validateEmailForChange(username, role, normalizedEmail);

		PasswordResetOtp otp = passwordResetService.validateOtp(username, otpCode,PasswordResetService.PURPOSE_EMAIL_CHANGE, normalizedEmail);

		if ("GIAOVIEN".equals(role)) {
			Lecturer lecturer = lecturerDAO.findById(username);
			if (lecturer == null) {
				throw new IllegalArgumentException("Không tìm thấy thông tin giảng viên");
			}
			lecturer.setEmail(normalizedEmail);
		} else if ("SINHVIEN".equals(role)) {
			Student student = studentDAO.findById(username);
			if (student == null) {
				throw new IllegalArgumentException("Không tìm thấy thông tin sinh viên");
			}
			student.setEmail(normalizedEmail);
		} else {
			throw new IllegalArgumentException("Vai trò không hợp lệ");
		}

		otp.setUsed(true);
		otp.setUsedAt(LocalDateTime.now());
	}

	@Transactional
	public void changePassword(String username, String currentPassword, String newPassword) {
		Account account = authService.findAccountByUsername(username);

		if (account == null) {
			throw new IllegalArgumentException("Tài khoản không tồn tại");
		}

		String currentPasswordHash = authService.sha256(currentPassword);
		if (!currentPasswordHash.equalsIgnoreCase(account.getPasswordHash())) {
			throw new IllegalArgumentException("Mật khẩu hiện tại sai");
		}

		authService.updatePassword(username, newPassword);
	}

	private void validateEmailForChange(String username, String role, String normalizedEmail) {
		if (normalizedEmail.isEmpty()) {
			throw new IllegalArgumentException("Email không hợp lệ");
		}

		String currentEmail = getCurrentEmail(username, role);
		if (normalizedEmail.equalsIgnoreCase(currentEmail)) {
			throw new IllegalArgumentException("Email mới phải khác email hiện tại");
		}

		boolean emailExistsInStudents = studentDAO.existsByEmail(normalizedEmail,
				"SINHVIEN".equals(role) ? username : null);
		boolean emailExistsInLecturers = lecturerDAO.existsByEmail(normalizedEmail,
				"GIAOVIEN".equals(role) ? username : null);

		if (emailExistsInStudents || emailExistsInLecturers) {
			throw new IllegalArgumentException("Email đã tồn tại trong hệ thống");
		}
	}

	private String getCurrentEmail(String username, String role) {
		String email;

		if ("GIAOVIEN".equals(role)) {
			email = lecturerDAO.findEmailByLecturerId(username);
		} else if ("SINHVIEN".equals(role)) {
			email = studentDAO.findEmailByStudentId(username);
		} else {
			throw new IllegalArgumentException("Vai trò không hợp lệ");
		}

		return email == null ? "" : email.trim().toLowerCase();
	}

	private String normalizeEmail(String email) {
		return email == null ? "" : email.trim().toLowerCase();
	}
}
