package com.tracnghiem.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.dto.ForgotPasswordRequestDTO;
import com.tracnghiem.dto.LoginDTO;
import com.tracnghiem.dto.ResetPasswordDTO;
import com.tracnghiem.service.AuthService;
import com.tracnghiem.service.PasswordResetService;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private PasswordResetService passwordResetService;

	@GetMapping("/login")
	public String loginForm(
			@RequestParam(value = "resetSuccess", required = false) String resetSuccess,
			Model model) {

		model.addAttribute("taiKhoan", new LoginDTO());
		if (resetSuccess != null) {
			model.addAttribute("success", "Đặt lại mật khẩu thành công. Vui lòng đăng nhập lại");
		}

		return "Account/Login";
	}

	@PostMapping("/login")
	public String login(
			@Valid @ModelAttribute("taiKhoan") LoginDTO dto,
			BindingResult result,
			HttpSession session,
			Model model) {

		if (result.hasErrors()) {
			return "Account/Login";
		}

		String error = authService.login(dto, session);

		if (error != null) {
			model.addAttribute("error", error);
			return "Account/Login";
		}

		String role = (String) session.getAttribute("ROLE");

		switch (role) {
			case "SINHVIEN":
				return "redirect:/students/home";

			case "GIAOVIEN":
				return "redirect:/lecturers/home";

			case "PGV":
				return "redirect:/admin/home";

			default:
				model.addAttribute("error", "Role không hợp lệ");
				return "Account/Login";
		}
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		authService.logout(session);
		return "Hello/Index";
	}

	@GetMapping("/forgot-password")
	public String forgotPasswordForm(Model model) {
		model.addAttribute("forgotPasswordForm", new ForgotPasswordRequestDTO());
		return "Account/ForgotPassword";
	}

	@PostMapping({ "/forgot-password", "/forgot-password/otp" })
	public String sendOtp(
			@Valid @ModelAttribute("forgotPasswordForm") ForgotPasswordRequestDTO dto,
			BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			return "Account/ForgotPassword";
		}

		try {
			passwordResetService.sendOtp(dto.getUsername());
			ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
			resetPasswordDTO.setUsername(dto.getUsername().trim());
			model.addAttribute("resetPasswordForm", resetPasswordDTO);
			model.addAttribute("success", "Mã OTP đã được gửi tới email đăng ký của bạn");
			return "Account/ResetPassword";
		} catch (IllegalArgumentException | IllegalStateException ex) {
			model.addAttribute("error", ex.getMessage());
			return "Account/ForgotPassword";
		}
	}

	@GetMapping("/reset-password")
	public String resetPasswordForm(
			@RequestParam(value = "username", required = false) String username,
			Model model) {
		ResetPasswordDTO dto = new ResetPasswordDTO();
		dto.setUsername(username);
		model.addAttribute("resetPasswordForm", dto);
		return "Account/ResetPassword";
	}

	@PostMapping("/reset-password")
	public String resetPassword(
			@Valid @ModelAttribute("resetPasswordForm") ResetPasswordDTO dto,
			BindingResult result,
			Model model) {

		if (!result.hasFieldErrors("confirmPassword")
				&& dto.getNewPassword() != null
				&& !dto.getNewPassword().equals(dto.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "confirmPassword.mismatch", "Mật khẩu xác nhận không khớp");
		}

		if (result.hasErrors()) {
			return "Account/ResetPassword";
		}

		try {
			passwordResetService.resetPassword(dto.getUsername(), dto.getOtpCode(), dto.getNewPassword());
			return "redirect:/auth/login?resetSuccess=1";
		} catch (IllegalArgumentException ex) {
			model.addAttribute("error", ex.getMessage());
			return "Account/ResetPassword";
		}
	}
}
