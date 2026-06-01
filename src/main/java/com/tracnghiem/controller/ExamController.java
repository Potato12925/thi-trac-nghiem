package com.tracnghiem.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.dao.ClassRoomDAO;
import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.dto.PrepareExamDTO;
import com.tracnghiem.entity.Exam;
import com.tracnghiem.entity.LecturerRegistration;
import com.tracnghiem.entity.Student;
import com.tracnghiem.entity.id.LecturerRegistrationId;
import com.tracnghiem.service.ExamService;

@Controller
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ClassRoomDAO classRoomDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private com.tracnghiem.dao.LecturerRegistrationDAO lecturerRegistrationDAO;

    @Autowired
    private com.tracnghiem.dao.StudentDAO studentDAO;

    @GetMapping
    public String prepare(ModelMap model, HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        if (!"SINHVIEN".equals(role) && !"GIAOVIEN".equals(role)) {
            return "redirect:/auth/login";
        }

        if ("SINHVIEN".equals(role)) {
            String studentId = (String) session.getAttribute("LOGIN_USER");
            Student student = studentDAO.findById(studentId);
            model.addAttribute("dsLop", Collections.singletonList(student.getClassRoom()));
        } else {
            model.addAttribute("dsLop", classRoomDAO.findAll());
        }

        model.addAttribute("dsMonHoc", subjectDAO.findAll());
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

        if (!"SINHVIEN".equals(role) && !"GIAOVIEN".equals(role)) {
            return "redirect:/auth/login";
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
            if ("SINHVIEN".equals(role)) {
                Student student = studentDAO.findById(userId);
                model.addAttribute("dsLop", java.util.Collections.singletonList(student.getClassRoom()));
            } else {
                model.addAttribute("dsLop", classRoomDAO.findAll());
            }
            
            model.addAttribute("dsMonHoc", subjectDAO.findAll());
            model.addAttribute("role", role);
            return "Exam/PrepareExam";
        }
    }

    @GetMapping("/take")
    public String take(ModelMap model, HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        Integer examId = (Integer) session.getAttribute("CURRENT_EXAM_ID");
        String classId = (String) session.getAttribute("CURRENT_EXAM_CLASS_ID");

        if (!"SINHVIEN".equals(role) && !"GIAOVIEN".equals(role)) {
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

        LecturerRegistrationId regId = new LecturerRegistrationId(classId, exam.getSubject().getSubjectId(), exam.getAttempt());
        LecturerRegistration registration = lecturerRegistrationDAO.findById(regId);
        
        Short duration = (registration != null) ? registration.getDuration() : 60; // Default 60 mins

        model.addAttribute("exam", exam);
        model.addAttribute("durationMinutes", duration);
        
        return "Exam/TakeExam";
    }

    @PostMapping("/submit")
    public String submit(HttpServletRequest request, HttpSession session, ModelMap model) {
        String role = (String) session.getAttribute("ROLE");
        Integer examId = (Integer) session.getAttribute("CURRENT_EXAM_ID");

        if (!"SINHVIEN".equals(role) && !"GIAOVIEN".equals(role)) {
            return "redirect:/auth/login";
        }

        if (examId == null) {
            return "redirect:/exam/prepare";
        }

        Map<Integer, String> studentAnswers = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        
        for (String paramName : parameterMap.keySet()) {
            if (paramName.startsWith("answer_")) {
                String questionIdStr = paramName.substring("answer_".length());
                try {
                    Integer questionId = Integer.parseInt(questionIdStr);
                    String answer = parameterMap.get(paramName)[0];
                    studentAnswers.put(questionId, answer);
                } catch (NumberFormatException e) {
                    // Ignore invalid params
                }
            }
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
