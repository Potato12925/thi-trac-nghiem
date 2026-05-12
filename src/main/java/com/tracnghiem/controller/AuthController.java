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

    @PostMapping("/login")
    public String login(
            @RequestParam("ma") String ma,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            HttpSession session,
            Model model) {

        String error = authService.login(ma, password, role, session);

        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("ma", ma);
            model.addAttribute("role", role);
            return "login";
        }

        return "redirect:/hello";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "redirect:/login";
    }
}
