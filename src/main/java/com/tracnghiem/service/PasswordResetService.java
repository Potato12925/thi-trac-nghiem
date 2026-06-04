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
	public static final String PURPOSE_PASSWORD_RESET = "PASSWORD_RESET";
	public static final String PURPOSE_EMAIL_CHANGE = "EMAIL_CHANGE";

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
		Account account = findExistingAccount(username);

		if ("PGV".equals(account.getRole())) {
			throw new IllegalArgumentException("Tài khoản này hiện chưa hỗ trợ khôi phục mật khẩu qua email");
		}

		String email = resolveEmail(account);
		LocalDateTime now = LocalDateTime.now();

		passwordResetOtpDAO.invalidateActiveOtps(account.getUsername(), PURPOSE_PASSWORD_RESET, now);

		PasswordResetOtp otp = createOtp(account.getUsername(), email, PURPOSE_PASSWORD_RESET, now);

		try {
			mailSender.send(buildOtpMessage(email, account.getUsername(), otp.getOtpCode(), "Mã OTP khôi phục mật khẩu",
					"Bạn vừa yêu cầu khôi phục mật khẩu cho tài khoản " + account.getUsername() + "."));
		} catch (MailException ex) {
			throw new IllegalStateException("Không thể gửi email OTP. Vui lòng thử lại sau.");
		}
	}

	@Transactional
	public void resetPassword(String username, String otpCode, String newPassword) {
		Account account = findExistingAccount(username);
		PasswordResetOtp otp = validateOtpInternal(account.getUsername(), otpCode, PURPOSE_PASSWORD_RESET, null);

		authService.updatePassword(account.getUsername(), newPassword);

		otp.setUsed(true);
		otp.setUsedAt(LocalDateTime.now());
	}

	@Transactional
	public void sendOtpForEmailChange(String username, String newEmail) {
		Account account = findExistingAccount(username);
		String normalizedEmail = normalizeEmail(newEmail);
		LocalDateTime now = LocalDateTime.now();

		passwordResetOtpDAO.invalidateActiveOtps(account.getUsername(), PURPOSE_EMAIL_CHANGE, now);

		PasswordResetOtp otp = createOtp(account.getUsername(), normalizedEmail, PURPOSE_EMAIL_CHANGE, now);

		try {
			mailSender.send(buildOtpMessage(normalizedEmail, account.getUsername(), otp.getOtpCode(),
					"Mã OTP xác nhận đổi email",
					"Bạn vừa yêu cầu cập nhật email mới cho tài khoản " + account.getUsername() + "."));
		} catch (MailException ex) {
			throw new IllegalStateException("Không thể gửi email OTP. Vui lòng thử lại sau.");
		}
	}

	public PasswordResetOtp validateOtp(String username, String otpCode, String purpose, String email) {
		Account account = findExistingAccount(username);
		return validateOtpInternal(account.getUsername(), otpCode, purpose, email);
	}

	private PasswordResetOtp validateOtpInternal(String accountId, String otpCode, String purpose, String email) {
		String normalizedOtpCode = otpCode == null ? "" : otpCode.trim();
		String normalizedEmail = email == null ? null : normalizeEmail(email);

		PasswordResetOtp otp = passwordResetOtpDAO.findLatestOtp(accountId, normalizedOtpCode, purpose,
				normalizedEmail);

		if (otp == null) {
			throw new IllegalArgumentException("Mã OTP không hợp lệ");
		}

		if (otp.isUsed()) {
			throw new IllegalArgumentException("Mã OTP đã được sử dụng hoặc không còn hiệu lực");
		}

		if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Mã OTP đã hết hạn. Vui lòng yêu cầu mã mới");
		}

		return otp;
	}

	private Account findExistingAccount(String username) {
		String normalizedUsername = normalizeUsername(username);
		Account account = authService.findAccountByUsername(normalizedUsername);

		if (account == null) {
			throw new IllegalArgumentException("Tên đăng nhập không tồn tại");
		}

		return account;
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

	private PasswordResetOtp createOtp(String accountId, String email, String purpose, LocalDateTime now) {
		PasswordResetOtp otp = new PasswordResetOtp();
		otp.setAccountId(accountId);
		otp.setEmail(email);
		otp.setPurpose(purpose);
		otp.setOtpCode(generateOtp());
		otp.setCreatedAt(now);
		otp.setExpiresAt(now.plusMinutes(OTP_EXPIRE_MINUTES));
		otp.setUsed(false);

		passwordResetOtpDAO.create(otp);
		return otp;
	}

	private String normalizeUsername(String username) {
		return username == null ? "" : username.trim();
	}

	private String normalizeEmail(String email) {
		return email == null ? "" : email.trim().toLowerCase();
	}

	private String generateOtp() {
		int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
		return String.valueOf(otp);
	}

	private SimpleMailMessage buildOtpMessage(String email, String username, String otpCode, String subject,
			String introLine) {
		SimpleMailMessage message = new SimpleMailMessage();
		if (mailSender instanceof JavaMailSenderImpl) {
			message.setFrom(((JavaMailSenderImpl) mailSender).getUsername());
		}
		message.setTo(email);
		message.setSubject(subject);
		message.setText("Xin chào,\n\n"
				+ introLine + "\n"
				+ "Mã OTP của bạn là: " + otpCode + "\n"
				+ "Mã này có hiệu lực trong 5 phút.\n\n"
				+ "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.\n");
		return message;
	}
}
