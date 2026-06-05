package com.tracnghiem.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.AccountDAO;
import com.tracnghiem.dao.LecturerDAO;
import com.tracnghiem.dao.PasswordResetOtpDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dto.AccountFilterDTO;
import com.tracnghiem.dto.AccountManagementItemDTO;
import com.tracnghiem.dto.AdminResetPasswordDTO;
import com.tracnghiem.dto.CreateLecturerAccountDTO;
import com.tracnghiem.dto.CreatePgvAccountDTO;
import com.tracnghiem.dto.UpdateAccountRoleDTO;
import com.tracnghiem.entity.Account;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.entity.Student;

@Service
public class AccountManagementService {

	private static final List<String> VALID_ROLES = Collections.unmodifiableList(
			Arrays.asList("PGV", "GIAOVIEN", "SINHVIEN"));

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private LecturerDAO lecturerDAO;

	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private PasswordResetOtpDAO passwordResetOtpDAO;

	@Autowired
	private AuthService authService;

	@Autowired
	private JavaMailSender mailSender;

	@Transactional(readOnly = true)
	public List<Account> getAccounts(AccountFilterDTO filterDTO) {
		String role = normalizeRole(filterDTO == null ? null : filterDTO.getRole());
		String keyword = normalize(filterDTO == null ? null : filterDTO.getKeyword());
		return accountDAO.findByFilter(role, keyword);
	}

	@Transactional(readOnly = true)
	public List<Lecturer> getAvailableLecturers() {
		return lecturerDAO.findActiveLecturersWithoutAccount();
	}

	@Transactional(readOnly = true)
	public AccountManagementItemDTO buildAccountItem(Account account, String currentUsername) {
		String normalizedUsername = normalize(account.getUsername());
		String normalizedRole = normalizeRole(account.getRole());

		Lecturer lecturer = lecturerDAO.findNormalizedById(normalizedUsername);
		Student student = studentDAO.findNormalizedById(normalizedUsername);

		AccountManagementItemDTO item = new AccountManagementItemDTO();
		item.setUsername(normalizedUsername);
		item.setRole(normalizedRole);
		item.setLinkedToLecturer(lecturer != null);
		item.setLinkedToStudent(student != null);
		item.setDisplayName(resolveDisplayName(normalizedRole, lecturer, student));
		item.setEmail(resolveEmail(normalizedRole, lecturer, student));
		item.setAccountType(resolveAccountType(normalizedRole, lecturer, student));
		item.setCanResetPassword(canResetPassword(normalizedRole));
		item.setCanDelete(canDeleteAccount(normalizedUsername, normalizedRole, currentUsername));
		item.setCanUpdateRole(canUpdateRole(normalizedUsername, normalizedRole, currentUsername, lecturer, student));
		return item;
	}

	@Transactional
	public void createPgvAccount(CreatePgvAccountDTO dto) {
		String username = normalize(dto.getUsername());
		String password = dto.getPassword() == null ? "" : dto.getPassword().trim();

		validateUsername(username);
		validatePassword(password);
		ensureAccountNotExists(username);

		Account account = new Account();
		account.setUsername(username);
		account.setPasswordHash(authService.sha256(password));
		account.setRole("PGV");

		accountDAO.create(account);
	}

	@Transactional
	public void createLecturerAccount(CreateLecturerAccountDTO dto) {
		String lecturerId = normalize(dto.getLecturerId());
		String password = dto.getPassword() == null ? "" : dto.getPassword().trim();

		validateUsername(lecturerId);
		validatePassword(password);

		Lecturer lecturer = lecturerDAO.findNormalizedById(lecturerId);
		if (lecturer == null || lecturer.isDeleted()) {
			throw new IllegalArgumentException("Giảng viên không tồn tại");
		}

		ensureAccountNotExists(lecturerId);

		Account account = new Account();
		account.setUsername(lecturerId);
		account.setPasswordHash(authService.sha256(password));
		account.setRole("GIAOVIEN");

		accountDAO.create(account);
	}

	@Transactional
	public void resetPassword(AdminResetPasswordDTO dto) {
		String username = normalize(dto.getUsername());
		String newPassword = dto.getNewPassword() == null ? "" : dto.getNewPassword().trim();

		validateUsername(username);
		validatePassword(newPassword);

		Account account = getExistingAccount(username);
		validateResettableRole(account);
		String recipientEmail = resolveRecipientEmail(account);

		authService.updatePassword(account.getUsername(), newPassword);

		try {
			mailSender.send(buildCredentialEmail(recipientEmail, account.getUsername(), newPassword, account.getRole()));
		} catch (MailException ex) {
			throw new IllegalStateException(
					"Đã đặt lại mật khẩu nhưng không thể gửi email thông báo. Vui lòng thử lại sau.");
		}
	}

	@Transactional
	public void updateRole(UpdateAccountRoleDTO dto, String currentUsername) {
		String username = normalize(dto.getUsername());
		String targetRole = normalizeRole(dto.getRole());

		validateUsername(username);
		validateRole(targetRole);

		Account account = getExistingAccount(username);
		String currentRole = normalizeRole(account.getRole());

		if (username.equals(normalize(currentUsername))) {
			throw new IllegalArgumentException("Không thể đổi role của chính tài khoản đang đăng nhập");
		}

		if (!"PGV".equals(currentRole)) {
			throw new IllegalArgumentException("Chỉ cho phép đổi role với tài khoản hiện đang là PGV");
		}

		if (targetRole.equals(currentRole)) {
			throw new IllegalArgumentException("Vai trò mới phải khác vai trò hiện tại");
		}

		Lecturer lecturer = lecturerDAO.findNormalizedById(username);
		Student student = studentDAO.findNormalizedById(username);

		if ("GIAOVIEN".equals(targetRole) && lecturer == null) {
			throw new IllegalArgumentException("Không thể đổi sang role giảng viên khi chưa có dữ liệu GIAOVIEN tương ứng");
		}

		if ("SINHVIEN".equals(targetRole) && student == null) {
			throw new IllegalArgumentException("Không thể đổi sang role sinh viên khi chưa có dữ liệu SINHVIEN tương ứng");
		}

		account.setRole(targetRole);
	}

	@Transactional
	public void deleteAccount(String username, String currentUsername) {
		String normalizedUsername = normalize(username);
		validateUsername(normalizedUsername);

		if (normalizedUsername.equals(normalize(currentUsername))) {
			throw new IllegalArgumentException("Không thể xóa chính tài khoản đang đăng nhập");
		}

		Account account = getExistingAccount(normalizedUsername);
		String role = normalizeRole(account.getRole());

		if ("SINHVIEN".equals(role) || studentDAO.findNormalizedById(normalizedUsername) != null) {
			throw new IllegalArgumentException("Không thể xóa tài khoản sinh viên");
		}

		passwordResetOtpDAO.deleteByAccountId(account.getUsername());
		accountDAO.delete(account);
	}

	private Account getExistingAccount(String username) {
		Account account = accountDAO.findByUsername(username);
		if (account == null) {
			throw new IllegalArgumentException("Tài khoản không tồn tại");
		}
		return account;
	}

	private void ensureAccountNotExists(String username) {
		if (accountDAO.existsByUsername(username)) {
			throw new IllegalArgumentException("Mã tài khoản đã tồn tại");
		}
	}

	private boolean canDeleteAccount(String username, String role, String currentUsername) {
		if (username != null && username.equals(normalize(currentUsername))) {
			return false;
		}

		return "PGV".equals(role) || "GIAOVIEN".equals(role);
	}

	private boolean canResetPassword(String role) {
		return "GIAOVIEN".equals(role) || "SINHVIEN".equals(role);
	}

	private boolean canUpdateRole(String username, String role, String currentUsername, Lecturer lecturer, Student student) {
		if (username != null && username.equals(normalize(currentUsername))) {
			return false;
		}

		if (!"PGV".equals(role)) {
			return false;
		}

		return lecturer != null || student != null;
	}

	private String resolveDisplayName(String role, Lecturer lecturer, Student student) {
		if ("GIAOVIEN".equals(role) && lecturer != null) {
			return joinName(lecturer.getLastName(), lecturer.getFirstName());
		}

		if ("SINHVIEN".equals(role) && student != null) {
			return joinName(student.getLastName(), student.getFirstName());
		}

		if (lecturer != null) {
			return joinName(lecturer.getLastName(), lecturer.getFirstName());
		}

		if (student != null) {
			return joinName(student.getLastName(), student.getFirstName());
		}

		return "Tài khoản PGV";
	}

	private String resolveEmail(String role, Lecturer lecturer, Student student) {
		if ("GIAOVIEN".equals(role) && lecturer != null) {
			return normalize(lecturer.getEmail());
		}

		if ("SINHVIEN".equals(role) && student != null) {
			return normalize(student.getEmail());
		}

		if (lecturer != null) {
			return normalize(lecturer.getEmail());
		}

		if (student != null) {
			return normalize(student.getEmail());
		}

		return null;
	}

	private String resolveAccountType(String role, Lecturer lecturer, Student student) {
		if ("SINHVIEN".equals(role) || student != null) {
			return "Tài khoản sinh viên";
		}

		if ("GIAOVIEN".equals(role) || lecturer != null) {
			return "Tài khoản giảng viên";
		}

		return "Tài khoản PGV";
	}

	private String joinName(String lastName, String firstName) {
		String normalizedLastName = normalize(lastName);
		String normalizedFirstName = normalize(firstName);

		if (normalizedLastName == null && normalizedFirstName == null) {
			return "Chưa có thông tin";
		}

		if (normalizedLastName == null) {
			return normalizedFirstName;
		}

		if (normalizedFirstName == null) {
			return normalizedLastName;
		}

		return normalizedLastName + " " + normalizedFirstName;
	}

	private void validateUsername(String username) {
		if (username == null) {
			throw new IllegalArgumentException("Mã tài khoản không được để trống");
		}

		if (username.length() > 8) {
			throw new IllegalArgumentException("Mã tài khoản tối đa 8 ký tự");
		}

		if (!username.matches("^[A-Z0-9]+$")) {
			throw new IllegalArgumentException("Mã tài khoản chỉ được chứa chữ in hoa và số");
		}
	}

	private void validatePassword(String password) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Mật khẩu không được để trống");
		}
	}

	private void validateRole(String role) {
		if (!VALID_ROLES.contains(role)) {
			throw new IllegalArgumentException("Role không hợp lệ");
		}
	}

	private void validateResettableRole(Account account) {
		String role = normalizeRole(account.getRole());
		if (!canResetPassword(role)) {
			throw new IllegalArgumentException("Chỉ hỗ trợ reset mật khẩu cho giảng viên và sinh viên");
		}
	}

	private String resolveRecipientEmail(Account account) {
		String role = normalizeRole(account.getRole());
		String email;

		if ("GIAOVIEN".equals(role)) {
			email = lecturerDAO.findEmailByLecturerId(account.getUsername());
		} else if ("SINHVIEN".equals(role)) {
			email = studentDAO.findEmailByStudentId(account.getUsername());
		} else {
			email = null;
		}

		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Tài khoản chưa có email để gửi thông tin đăng nhập");
		}

		return email.trim();
	}

	private SimpleMailMessage buildCredentialEmail(String email, String username, String newPassword, String role) {
		SimpleMailMessage message = new SimpleMailMessage();
		if (mailSender instanceof JavaMailSenderImpl) {
			message.setFrom(((JavaMailSenderImpl) mailSender).getUsername());
		}

		String roleLabel = "SINHVIEN".equals(normalizeRole(role)) ? "sinh viên" : "giảng viên";

		message.setTo(email);
		message.setSubject("Thông tin tài khoản hệ thống thi trắc nghiệm");
		message.setText("Xin chào,\n\n"
				+ "Phòng giáo vụ vừa đặt lại thông tin đăng nhập cho tài khoản " + roleLabel + " của bạn.\n"
				+ "Mã tài khoản: " + username + "\n"
				+ "Mật khẩu mới: " + newPassword + "\n\n"
				+ "Vui lòng đăng nhập và đổi lại mật khẩu sau khi nhận được email này.\n");
		return message;
	}

	private String normalizeRole(String role) {
		String normalized = normalize(role);
		return normalized == null ? null : normalized.toUpperCase();
	}

	private String normalize(String value) {
		if (value == null) {
			return null;
		}

		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}
