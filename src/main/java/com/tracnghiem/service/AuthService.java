package com.tracnghiem.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.TaiKhoanDAO;
import com.tracnghiem.entity.TaiKhoan;

@Service
public class AuthService {

	@Autowired
	private TaiKhoanDAO taiKhoanDAO;

	@Transactional
	public String login(String ma, String rawPassword, HttpSession session) {

		if (isBlank(ma)) {
			return "Vui lòng nhập mã đăng nhập";
		}

		if (isBlank(rawPassword)) {
			return "Vui lòng nhập mật khẩu";
		}

		String normalizedMa = ma.trim();

		TaiKhoan user = taiKhoanDAO.findByMa(normalizedMa);

		if (user == null) {
			return "Mã đăng nhập không tồn tại";
		}

		String inputHash = sha256(rawPassword);

		if (!inputHash.equalsIgnoreCase(user.getPasswordHash())) {
			return "Mật khẩu không chính xác";
		}

		session.setAttribute("LOGIN_USER", user.getMa());
		session.setAttribute("ROLE", user.getRole());

		return null;
	}

	public void logout(HttpSession session) {
		session.invalidate();
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	private boolean isSupportedRole(String role) {
		return "PGV".equals(role) || "GIANGVIEN".equals(role) || "SINHVIEN".equals(role);
	}

	private String sha256(String raw) {

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
}
