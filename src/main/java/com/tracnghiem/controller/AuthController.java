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
@RequestMapping
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        boolean ok = authService.login(username, password, session);

        if (!ok) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            model.addAttribute("username", username);
            return "login";
        }

        return "redirect:/hello";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "redirect:/login";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("role") String role,
            @RequestParam(value = "maGV", required = false) String maGV,
            Model model) {

        String error = authService.register(username, password, confirmPassword, role, maGV);

        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("username", username);
            model.addAttribute("role", role);
            model.addAttribute("maGV", maGV);
            return "register";
        }

        return "redirect:/login";
    }
}
