package com.tracnghiem.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.AccountDAO;
import com.tracnghiem.dao.LecturerDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dto.LoginDTO;
import com.tracnghiem.entity.Account;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.entity.Student;

@Service
public class AuthService {

	private static final Duration SESSION_STALE_DURATION = Duration.ofMinutes(30);

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private LecturerDAO lecturerDAO;

	@Transactional
	public String login(LoginDTO dto, HttpSession session) {
		String normalizedUsername = dto.getUsername().trim();
		Account user = accountDAO.findByUsernameForUpdate(normalizedUsername);

		if (user == null) {
			return "Tên đăng nhập không tồn tại";
		}

		if ("SINHVIEN".equals(user.getRole())) {
			Student student = studentDAO.findById(user.getUsername());
			if (student == null || student.isDeleted()) {
				return "Tên đăng nhập không tồn tại";
			}
		} else if ("GIAOVIEN".equals(user.getRole())) {
			Lecturer lecturer = lecturerDAO.findById(user.getUsername());
			if (lecturer == null || lecturer.isDeleted()) {
				return "Tên đăng nhập không tồn tại";
			}
		}

		String inputHash = sha256(dto.getPassword());
		if (!inputHash.equalsIgnoreCase(user.getPasswordHash())) {
			return "Mật khẩu không chính xác";
		}

		String currentSessionId = session.getId();
		if (hasActiveSessionElsewhere(user, currentSessionId)) {
			return "Tài khoản này đang được đăng nhập ở nơi khác.";
		}

		LocalDateTime now = LocalDateTime.now();
		user.setCurrentSessionId(currentSessionId);
		user.setLoginAt(now);
		user.setLastActiveAt(now);

		session.setAttribute("LOGIN_USER", user.getUsername());
		session.setAttribute("ROLE", user.getRole());
		return null;
	}

	@Transactional
	public void logout(HttpSession session) {
		if (session == null) {
			return;
		}

		String username = (String) session.getAttribute("LOGIN_USER");
		if (username != null) {
			clearLoginSession(username, session.getId());
		}

		session.invalidate();
	}

	@Transactional(readOnly = true)
	public boolean isCurrentSessionValid(String username, String sessionId) {
		Account account = accountDAO.findByUsername(username);
		if (account == null) {
			return false;
		}

		return sessionId != null && sessionId.equals(account.getCurrentSessionId());
	}

	@Transactional
	public void touchCurrentSession(String username, String sessionId) {
		if (username == null || sessionId == null) {
			return;
		}

		accountDAO.updateLastActiveAt(username, sessionId, LocalDateTime.now());
	}

	@Transactional
	public void clearLoginSession(String username, String sessionId) {
		if (username == null || sessionId == null) {
			return;
		}

		accountDAO.clearCurrentSession(username, sessionId);
	}

	public String sha256(String raw) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder(hash.length * 2);

			for (byte b : hash) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 algorithm not available", e);
		}
	}

	public Account createAccount(String username) {
		Account account = new Account();
		account.setUsername(username);

		String passwordHash = sha256(username);
		account.setPasswordHash(passwordHash);
		account.setRole("SINHVIEN");

		accountDAO.create(account);
		return account;
	}

	public Account deleteAccount(String studentId) {
		Account accountToDelete = accountDAO.findById(studentId);
		accountDAO.delete(accountToDelete);
		return accountToDelete;
	}

	public Account findAccountByUsername(String username) {
		if (username == null) {
			return null;
		}

		return accountDAO.findByUsername(username.trim());
	}

	@Transactional
	public void updatePassword(String username, String rawPassword) {
		Account account = accountDAO.findByUsername(username);
		if (account == null) {
			throw new IllegalArgumentException("Tài khoản không tồn tại");
		}

		account.setPasswordHash(sha256(rawPassword));
	}

	private boolean hasActiveSessionElsewhere(Account account, String currentSessionId) {
		String storedSessionId = account.getCurrentSessionId();
		if (storedSessionId == null || storedSessionId.trim().isEmpty()) {
			return false;
		}

		if (storedSessionId.equals(currentSessionId)) {
			return false;
		}

		return !isSessionStale(account.getLastActiveAt());
	}

	private boolean isSessionStale(LocalDateTime lastActiveAt) {
		if (lastActiveAt == null) {
			return true;
		}

		return lastActiveAt.plus(SESSION_STALE_DURATION).isBefore(LocalDateTime.now());
	}
}
