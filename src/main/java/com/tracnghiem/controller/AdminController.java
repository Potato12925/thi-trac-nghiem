package com.tracnghiem.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.service.AdminDashboardService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("/home")
    public String Home(ModelMap model, HttpSession session) {

        model.addAttribute("pageTitle", "Trang chủ quản lý đào tạo");
        model.addAttribute("accountId", session.getAttribute("LOGIN_USER"));
        model.addAttribute("accountRole", session.getAttribute("ROLE"));
        model.addAttribute("totalClassrooms", adminDashboardService.getTotalClassrooms());
        model.addAttribute("totalStudents", adminDashboardService.getTotalStudents());
        model.addAttribute("totalLecturers", adminDashboardService.getTotalLecturers());
        model.addAttribute("totalSubjects", adminDashboardService.getTotalSubjects());
        model.addAttribute("totalQuestions", adminDashboardService.getTotalQuestions());
        model.addAttribute("totalExams", adminDashboardService.getTotalExams());
        model.addAttribute("totalAccounts", adminDashboardService.getTotalAccounts());
        model.addAttribute("recentExamRegistrations", adminDashboardService.getRecentExamRegistrations());
        model.addAttribute("today", new Date());
        return "Admin/Home";
    }
}
