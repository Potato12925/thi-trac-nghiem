package com.tracnghiem.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.entity.Student;
import com.tracnghiem.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final String INDEX_VIEW = "Student/Index";
    private static final String REDIRECT_INDEX = "redirect:/student";

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1") int page, ModelMap model) {
        populateIndexPageModel(model, page);
        model.addAttribute("studentDTO", new StudentDTO());
        return INDEX_VIEW;
    }

    @GetMapping("/home")
    public String home(ModelMap model, HttpSession session) {

        String studentId = (String) session.getAttribute("LOGIN_USER");

        Student student = studentId != null ? studentService.getStudentById(studentId) : null;

        model.addAttribute("pageTitle", "Student Home");
        model.addAttribute("studentProfile", student);
        model.addAttribute("today", new Date());

        return "Student/Home";
    }

    @PostMapping("/add")
    public String addStudent(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            populateIndexPageModel(model, page);
            return INDEX_VIEW;
        }

        try {
            studentService.addStudentWithAccount(studentDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            populateIndexPageModel(model, page);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/update")
    public String updateStudent(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            populateIndexPageModel(model, page);
            return INDEX_VIEW;
        }

        try {
            studentService.updateStudent(studentDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            populateIndexPageModel(model, page);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam(defaultValue = "1") int page, @Valid @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            populateIndexPageModel(model, page);
            return INDEX_VIEW;
        }

        try {
            studentService.deleteStudent(studentDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            populateIndexPageModel(model, page);
            return INDEX_VIEW;
        }
    }

    private void populateIndexPageModel(ModelMap model, int page) {
        int pageSize = 10;

        model.addAttribute("studentDTO", new StudentDTO());
        model.addAttribute("students", studentService.getStudents(page, pageSize));

        long total = studentService.countStudents();

        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }
}
