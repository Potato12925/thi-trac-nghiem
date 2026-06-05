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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tracnghiem.dto.ChangeEmailDTO;
import com.tracnghiem.dto.ChangePasswordDTO;
import com.tracnghiem.dto.ConfirmEmailChangeDTO;
import com.tracnghiem.dto.ForgotPasswordRequestDTO;
import com.tracnghiem.dto.LoginDTO;
import com.tracnghiem.dto.ResetPasswordDTO;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.entity.Student;
import com.tracnghiem.service.AccountSettingsService;
import com.tracnghiem.service.AuthService;
import com.tracnghiem.service.LecturerService;
import com.tracnghiem.service.PasswordResetService;
import com.tracnghiem.service.StudentService;
import com.tracnghiem.utils.RoleConstants;
import com.tracnghiem.utils.RoleNavigationUtils;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private PasswordResetService passwordResetService;

	@Autowired
	private AccountSettingsService accountSettingsService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private LecturerService lecturerService;

	@GetMapping("/login")
	public String loginForm(
			@RequestParam(value = "resetSuccess", required = false) String resetSuccess,
			Model model) {

		model.addAttribute("taiKhoan", new LoginDTO());
		if (resetSuccess != null) {
			model.addAttribute("success", "Äáº·t láº¡i máº­t kháº©u thÃ nh cÃ´ng. Vui lÃ²ng Ä‘Äƒng nháº­p láº¡i");
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
		if (!RoleNavigationUtils.isAuthenticatedRole(role)) {
			model.addAttribute("error", "Role khÃ´ng há»£p lá»‡");
			return "Account/Login";
		}

		return RoleNavigationUtils.getHomeRedirect(role);
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
			model.addAttribute("success", "MÃ£ OTP Ä‘Ã£ Ä‘Æ°á»£c gá»­i tá»›i email Ä‘Äƒng kÃ½ cá»§a báº¡n");
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
			result.rejectValue("confirmPassword", "confirmPassword.mismatch", "Máº­t kháº©u xÃ¡c nháº­n khÃ´ng khá»›p");
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

	@PostMapping("/change-password")
	public String changePassword(
			@Valid @ModelAttribute("changePasswordDTO") ChangePasswordDTO dto,
			BindingResult result,
			HttpSession session,
			Model model,
			RedirectAttributes redirectAttributes) {

		String role = (String) session.getAttribute("ROLE");
		String username = (String) session.getAttribute("LOGIN_USER");

		if (username == null || role == null) {
			return "redirect:/auth/login";
		}

		if (!result.hasFieldErrors("confirmPassword")
				&& dto.getNewPassword() != null
				&& !dto.getNewPassword().equals(dto.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "confirmPassword.mismatch", "XÃ¡c nháº­n máº­t kháº©u khÃ´ng khá»›p");
		}

		if (result.hasErrors()) {
			populateSettingsPageModel(model, session, dto);
			return resolveSettingsView(role);
		}

		try {
			accountSettingsService.changePassword(username, dto.getCurrentPassword(), dto.getNewPassword());
			redirectAttributes.addFlashAttribute("successMessage", "Äá»•i máº­t kháº©u thÃ nh cÃ´ng");
			return "redirect:" + resolveSettingsPath(role);
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			populateSettingsPageModel(model, session, dto);
			return resolveSettingsView(role);
		}
	}

	private void populateSettingsPageModel(Model model, HttpSession session, ChangePasswordDTO changePasswordDTO) {
		String role = (String) session.getAttribute("ROLE");
		String username = (String) session.getAttribute("LOGIN_USER");

		if (RoleConstants.STUDENT.equals(role)) {
			Student student = studentService.getStudentById(username);
			model.addAttribute("pageTitle", "CÃ i Ä‘áº·t sinh viÃªn");
			model.addAttribute("studentProfile", student);
		} else if (RoleConstants.LECTURER.equals(role)) {
			Lecturer lecturer = lecturerService.findLecturerById(username);
			model.addAttribute("pageTitle", "CÃ i Ä‘áº·t giáº£ng viÃªn");
			model.addAttribute("lecturerProfile", lecturer);
		}

		if (!model.containsAttribute("changeEmailDTO")) {
			model.addAttribute("changeEmailDTO", new ChangeEmailDTO());
		}

		if (!model.containsAttribute("confirmEmailChangeDTO")) {
			model.addAttribute("confirmEmailChangeDTO", new ConfirmEmailChangeDTO());
		}

		model.addAttribute("changePasswordDTO", changePasswordDTO);
	}

	private String resolveSettingsView(String role) {
		if (RoleConstants.STUDENT.equals(role)) {
			return "Student/Settings";
		}

		if (RoleConstants.LECTURER.equals(role)) {
			return "Lecturer/Settings";
		}

		return "redirect:/auth/login";
	}

	private String resolveSettingsPath(String role) {
		if (RoleConstants.STUDENT.equals(role)) {
			return "/students/settings";
		}

		if (RoleConstants.LECTURER.equals(role)) {
			return "/lecturers/settings";
		}

		return "/auth/login";
	}
}
