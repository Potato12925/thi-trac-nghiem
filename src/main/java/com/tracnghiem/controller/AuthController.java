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

import com.tracnghiem.dto.LoginDTO;
import com.tracnghiem.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@GetMapping("/login")
	public String loginForm(Model model) {

		model.addAttribute("taiKhoan", new LoginDTO());

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
				return "redirect:/lecturer/home";

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
}
