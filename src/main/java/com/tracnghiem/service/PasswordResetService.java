package com.tracnghiem.service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.LecturerDAO;
import com.tracnghiem.dao.PasswordResetOtpDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.entity.Account;
import com.tracnghiem.entity.PasswordResetOtp;

@Service
public class PasswordResetService {

	private static final int OTP_EXPIRE_MINUTES = 5;

	@Autowired
	private AuthService authService;

	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private LecturerDAO lecturerDAO;

	@Autowired
	private PasswordResetOtpDAO passwordResetOtpDAO;

	@Autowired
	private JavaMailSender mailSender;

	@Transactional
	public void sendOtp(String username) {
		String normalizedUsername = normalizeUsername(username);
		Account account = authService.findAccountByUsername(normalizedUsername);

		if (account == null) {
			throw new IllegalArgumentException("Tên đăng nhập không tồn tại");
		}

		if ("PGV".equals(account.getRole())) {
			throw new IllegalArgumentException("Tài khoản này hiện chưa hỗ trợ khôi phục mật khẩu qua email");
		}

		String email = resolveEmail(account);
		LocalDateTime now = LocalDateTime.now();

		passwordResetOtpDAO.invalidateActiveOtps(account.getUsername(), now);

		PasswordResetOtp otp = new PasswordResetOtp();
		otp.setAccountId(account.getUsername());
		otp.setEmail(email);
		otp.setOtpCode(generateOtp());
		otp.setCreatedAt(now);
		otp.setExpiresAt(now.plusMinutes(OTP_EXPIRE_MINUTES));
		otp.setUsed(false);

		passwordResetOtpDAO.create(otp);

		try {
			mailSender.send(buildOtpMessage(email, account.getUsername(), otp.getOtpCode()));
		} catch (MailException ex) {
			throw new IllegalStateException("Không thể gửi email OTP. Vui lòng thử lại sau.");
		}
	}

	@Transactional
	public void resetPassword(String username, String otpCode, String newPassword) {
		String normalizedUsername = normalizeUsername(username);
		String normalizedOtpCode = otpCode == null ? "" : otpCode.trim();
		Account account = authService.findAccountByUsername(normalizedUsername);

		if (account == null) {
			throw new IllegalArgumentException("Tên đăng nhập không tồn tại");
		}

		PasswordResetOtp otp = passwordResetOtpDAO.findLatestOtp(account.getUsername(), normalizedOtpCode);

		if (otp == null) {
			throw new IllegalArgumentException("Mã OTP không hợp lệ");
		}

		if (otp.isUsed()) {
			throw new IllegalArgumentException("Mã OTP đã được sử dụng hoặc không còn hiệu lực");
		}

		if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Mã OTP đã hết hạn. Vui lòng yêu cầu mã mới");
		}

		authService.updatePassword(account.getUsername(), newPassword);

		otp.setUsed(true);
		otp.setUsedAt(LocalDateTime.now());
	}

	private String resolveEmail(Account account) {
		String email;

		if ("SINHVIEN".equals(account.getRole())) {
			email = studentDAO.findEmailByStudentId(account.getUsername());
		} else if ("GIAOVIEN".equals(account.getRole())) {
			email = lecturerDAO.findEmailByLecturerId(account.getUsername());
		} else {
			email = null;
		}

		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Tài khoản chưa có email để nhận OTP");
		}

		return email.trim();
	}

	private String normalizeUsername(String username) {
		return username == null ? "" : username.trim();
	}

	private String generateOtp() {
		int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
		return String.valueOf(otp);
	}

	private SimpleMailMessage buildOtpMessage(String email, String username, String otpCode) {
		SimpleMailMessage message = new SimpleMailMessage();
		if (mailSender instanceof JavaMailSenderImpl) {
			message.setFrom(((JavaMailSenderImpl) mailSender).getUsername());
		}
		message.setTo(email);
		message.setSubject("Ma OTP khoi phuc mat khau");
		message.setText("Xin chao,\n\n"
				+ "Ban vua yeu cau khoi phuc mat khau cho tai khoan " + username + ".\n"
				+ "Ma OTP cua ban la: " + otpCode + "\n"
				+ "Ma nay co hieu luc trong 5 phut.\n\n"
				+ "Neu ban khong thuc hien yeu cau nay, vui long bo qua email.\n");
		return message;
	}
}
