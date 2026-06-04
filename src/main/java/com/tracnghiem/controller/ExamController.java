package com.tracnghiem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.dao.LecturerRegistrationDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dto.ExamSubmissionDTO;
import com.tracnghiem.dto.PrepareExamDTO;
import com.tracnghiem.entity.Exam;
import com.tracnghiem.entity.LecturerRegistration;
import com.tracnghiem.entity.Student;
import com.tracnghiem.entity.Subject;
import com.tracnghiem.entity.id.LecturerRegistrationId;
import com.tracnghiem.service.ExamService;

@Controller
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private LecturerRegistrationDAO lecturerRegistrationDAO;

    @Autowired
    private StudentDAO studentDAO;

    @GetMapping
    public String prepare(ModelMap model, HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        if (!"SINHVIEN".equals(role)) {
            return "redirect:/auth/login";
        }

        String studentId = (String) session.getAttribute("LOGIN_USER");
        Student student = studentDAO.findById(studentId);
        String classId = student.getClassRoom().getClassId();
        List<Subject> dsMonHoc = examService.getSubjectsForClass(classId);

        model.addAttribute("lopSinhVien", student.getClassRoom());
        model.addAttribute("dsMonHoc", dsMonHoc);
        model.addAttribute("role", role);
        model.addAttribute("prepareExamDTO", new PrepareExamDTO());

        return "Exam/PrepareExam";
    }

    @PostMapping("/start")
    public String start(
            @ModelAttribute("prepareExamDTO") PrepareExamDTO form,
            HttpSession session,
            ModelMap model) {

        String classId = form.getClassId();
        String subjectId = form.getSubjectId();
        Short tryNumber = form.getTryNumber();

        String role = (String) session.getAttribute("ROLE");
        String userId = (String) session.getAttribute("LOGIN_USER");

        if (!"SINHVIEN".equals(role)) {
            return "redirect:/auth/login";
        }

        Student student = studentDAO.findById(userId);
        if (student != null && student.getClassRoom() != null) {
            classId = student.getClassRoom().getClassId();
        }

        try {
            Exam exam = examService.startExam(userId, role, classId, subjectId, tryNumber);
            session.setAttribute("CURRENT_EXAM_ID", exam.getId());
            session.setAttribute("CURRENT_EXAM_CLASS_ID", classId);
            return "redirect:/exam/take";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (e.getCause() != null) {
                errorMessage += " | Cause: " + e.getCause().getMessage();
            }
            if (e.getCause() != null && e.getCause().getCause() != null) {
                errorMessage += " | Root: " + e.getCause().getCause().getMessage();
            }
            model.addAttribute("error", errorMessage);

            student = studentDAO.findById(userId);
            model.addAttribute("lopSinhVien", student.getClassRoom());
            
            List<Subject> dsMonHoc = examService.getSubjectsForClass(student.getClassRoom().getClassId());
            model.addAttribute("dsMonHoc", dsMonHoc);
            model.addAttribute("role", role);
            return "Exam/PrepareExam";
        }
    }

    @GetMapping("/take")
    public String take(ModelMap model, HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        Integer examId = (Integer) session.getAttribute("CURRENT_EXAM_ID");
        String classId = (String) session.getAttribute("CURRENT_EXAM_CLASS_ID");

        if (!"SINHVIEN".equals(role)) {
            return "redirect:/auth/login";
        }

        if (examId == null) {
            return "redirect:/exam/prepare";
        }

        Exam exam = examService.getExam(examId);
        if (exam == null) {
            session.removeAttribute("CURRENT_EXAM_ID");
            session.removeAttribute("CURRENT_EXAM_CLASS_ID");
            return "redirect:/exam/prepare";
        }

        if (classId == null && exam.getStudent() != null) {
            classId = exam.getStudent().getClassRoom().getClassId();
        }

        String subjectId = exam.getSubject().getSubjectId();
        Short attempt = exam.getAttempt();

        LecturerRegistrationId regId = new LecturerRegistrationId(classId, subjectId, attempt);
        LecturerRegistration registration = lecturerRegistrationDAO.findById(regId);
        
        Short duration = (registration != null) ? registration.getDuration() : 60;

        model.addAttribute("exam", exam);
        model.addAttribute("durationMinutes", duration);
        
        return "Exam/TakeExam";
    }

    @PostMapping("/submit")
    public String submit(
            @ModelAttribute("exam") ExamSubmissionDTO submission,
            HttpSession session,
            ModelMap model) {
        String role = (String) session.getAttribute("ROLE");
        Integer examId = (Integer) session.getAttribute("CURRENT_EXAM_ID");

        if (!"SINHVIEN".equals(role)) {
            return "redirect:/auth/login";
        }

        if (examId == null) {
            return "redirect:/exam/prepare";
        }

        Map<Integer, String> studentAnswers = submission.getAnswers();
        if (studentAnswers == null) {
            studentAnswers = new HashMap<>();
        }

        try {
            Exam finishedExam = examService.submitExam(examId, studentAnswers);
            session.removeAttribute("CURRENT_EXAM_ID");
            session.removeAttribute("CURRENT_EXAM_CLASS_ID");
            model.addAttribute("score", finishedExam.getScore());
            return "Exam/Result";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Exam/TakeExam"; // Simplified error handling
        }
    }
}
