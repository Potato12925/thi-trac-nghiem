package com.tracnghiem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@GetMapping("/login")
	public String showLogin() {
		return "Account/Login";
	}

	@PostMapping("/login")
	public String login(@RequestParam("ma") String ma, @RequestParam("password") String password, HttpSession session,
			Model model) {

		String error = authService.login(ma, password, session);

		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("ma", ma);
			return "Account/Login";
		}

		return "Home/Index";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		authService.logout(session);
		return "Hello/Index";
	}
}
