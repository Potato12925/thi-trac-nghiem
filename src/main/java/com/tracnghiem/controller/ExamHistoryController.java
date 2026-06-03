package com.tracnghiem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tracnghiem.entity.Exam;
import com.tracnghiem.entity.ExamDetail;
import com.tracnghiem.service.ExamHistoryService;

@Controller
@RequestMapping("/history")
public class ExamHistoryController {

    @Autowired
    private ExamHistoryService examHistoryService;

    @GetMapping
    public String index(ModelMap model, HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        if (!"SINHVIEN".equals(role)) {
            return "redirect:/auth/login";
        }

        String studentId = (String) session.getAttribute("LOGIN_USER");
        if (studentId == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("pageTitle", "Lịch Sử Thi");
        model.addAttribute("exams", examHistoryService.getExamsByStudent(studentId));
        return "ExamHistory/Index";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("id") Integer examId, ModelMap model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("ROLE");
        if (role == null || (!"SINHVIEN".equals(role) && !"GIAOVIEN".equals(role) && !"PGV".equals(role))) {
            return "redirect:/auth/login";
        }

        Exam exam = examHistoryService.getExamDetail(examId);
        if (exam == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bài thi.");
            return "SINHVIEN".equals(role) ? "redirect:/history" : "redirect:/scores";
        }

        // Security check: Ensure this exam belongs to the logged-in student (only for SINHVIEN role)
        if ("SINHVIEN".equals(role)) {
            String studentId = (String) session.getAttribute("LOGIN_USER");
            if (studentId == null || exam.getStudent() == null || !exam.getStudent().getStudentId().trim().equals(studentId.trim())) {
                redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xem chi tiết bài thi này.");
                return "redirect:/history";
            }
        }

        // Calculate total and correct answers
        int totalQuestions = 0;
        int correctCount = 0;
        if (exam.getExamDetails() != null) {
            totalQuestions = exam.getExamDetails().size();
            for (ExamDetail d : exam.getExamDetails()) {
                String correctAns = d.getQuestion().getCorrectAnswer() != null
                        ? d.getQuestion().getCorrectAnswer().trim()
                        : "";
                String studentAns = d.getStudentAnswer() != null ? d.getStudentAnswer().trim() : "";
                if (!correctAns.isEmpty() && correctAns.equalsIgnoreCase(studentAns)) {
                    correctCount++;
                }
            }
        }

        model.addAttribute("pageTitle", "Chi Tiết Bài Thi");
        model.addAttribute("exam", exam);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("correctCount", correctCount);

        return "ExamHistory/Detail";
    }
}
