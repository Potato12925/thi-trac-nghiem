package com.tracnghiem.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.AccountDAO;
import com.tracnghiem.dto.LoginDTO;
import com.tracnghiem.entity.Account;

@Service
public class AuthService {

	@Autowired
	private AccountDAO accountDAO;

	@Transactional
	public String login(LoginDTO dto, HttpSession session) {

		String normalizedMa = dto.getMa().trim();

		Account user = accountDAO.findByMa(normalizedMa);

		if (user == null) {
			return "Mã đăng nhập không tồn tại";
		}

		String inputHash = sha256(dto.getPassword());

		if (!inputHash.equalsIgnoreCase(user.getPasswordHash())) {
			return "Mật khẩu không chính xác";
		}

		session.setAttribute("LOGIN_USER", user.getUsername());
		session.setAttribute("ROLE", user.getRole());

		return null;
	}

	public void logout(HttpSession session) {
		session.invalidate();
	}

	public String sha256(String raw) {

		try {
			// chọn thuật toán băm
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			// biến các kí tự raw thành list mã UTF_8 sau đó băm
			byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
			
			// hash tạo ra 32 byte, x2 tạo chỗ trống cho 64 kí tư
			StringBuilder sb = new StringBuilder(hash.length * 2);
			
			// chuyển byte sang hex
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
}
