package com.tracnghiem.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.TaiKhoanDAO;
import com.tracnghiem.dto.LoginDTO;
import com.tracnghiem.entity.TaiKhoan;

@Service
public class AuthService {

	@Autowired
	private TaiKhoanDAO taiKhoanDAO;

	@Transactional
	public String login(LoginDTO dto, HttpSession session) {

		String normalizedMa = dto.getMa().trim();

		TaiKhoan user = taiKhoanDAO.findByMa(normalizedMa);

		if (user == null) {
			return "Mã đăng nhập không tồn tại";
		}

		String inputHash = sha256(dto.getPassword());

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

	public TaiKhoan taoTaiKhoan(String maSV) {
		TaiKhoan taiKhoan = new TaiKhoan();
		taiKhoan.setMa(maSV);

		String passwordHash = sha256(maSV);

		taiKhoan.setPasswordHash(passwordHash);
		taiKhoan.setRole("SINHVIEN");

		taiKhoanDAO.create(taiKhoan);
		return taiKhoan;
	}

	public TaiKhoan xoaTaiKhoan(String maSV) {
		TaiKhoan taiKhoanCanXoa = taiKhoanDAO.findById(maSV);
		taiKhoanDAO.delete(taiKhoanCanXoa);
		return taiKhoanCanXoa;
	}
}
