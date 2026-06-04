package com.tracnghiem.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.entity.Student;
import com.tracnghiem.service.ClassroomService;
import com.tracnghiem.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final String INDEX_VIEW = "Student/Index";
    private static final String REDIRECT_INDEX = "redirect:/students";

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassroomService classroomService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, name = "filterClassId") String filterClassId,
            ModelMap model) {
        populateIndexPageModel(model, page, keyword, filterClassId);
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
    public String addStudent(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, name = "filterClassId") String filterClassId,
            @Validated @ModelAttribute("studentDTO") StudentDTO studentDTO,
            BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }

        try {
            studentService.addStudentWithAccount(studentDTO);
            return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/update")
    public String updateStudent(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, name = "filterClassId") String filterClassId,
            @Validated @ModelAttribute("studentDTO") StudentDTO studentDTO,
            BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }

        try {
            studentService.updateStudent(studentDTO);
            return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, name = "filterClassId") String filterClassId,
            @Valid @ModelAttribute("studentDTO") StudentDTO studentDTO,
            BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }

        try {
            studentService.deleteStudent(studentDTO);
            return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            populateIndexPageModel(model, page, keyword, filterClassId);
            return INDEX_VIEW;
        }
    }

	@PostMapping("/save")
	public String save(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, name = "filterClassId") String filterClassId,
			@RequestParam("actionsData") String actionsData, ModelMap model, RedirectAttributes redirectAttributes) {

		if (actionsData == null || actionsData.trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không có thay đổi nào để ghi.");
			return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
		}

		try {
			studentService.savePendingActions(actionsData);
			redirectAttributes.addFlashAttribute("successMessage", "Ghi các thay đổi xuống CSDL thành công.");
			return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi ghi dữ liệu: " + ex.getMessage());
			return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống: " + ex.getMessage());
			return REDIRECT_INDEX + buildQuery(page, keyword, filterClassId);
		}
	}

    private void populateIndexPageModel(ModelMap model, int page, String keyword, String filterClassId) {
        int pageSize = 10;

        model.addAttribute("studentDTO", new StudentDTO());
        model.addAttribute("students", studentService.getStudents(page, pageSize, keyword, filterClassId));
        model.addAttribute("classrooms", classroomService.getAllClassrooms());
        model.addAttribute("keyword", keyword);
        model.addAttribute("classId", filterClassId);

        long total = studentService.countStudents(keyword, filterClassId);
        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    private String buildQuery(int page, String keyword, String filterClassId) {
        StringBuilder query = new StringBuilder("?page=").append(page);
        if (keyword != null && !keyword.trim().isEmpty()) {
            query.append("&keyword=").append(URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8));
        }
        if (filterClassId != null && !filterClassId.trim().isEmpty()) {
            query.append("&filterClassId=").append(URLEncoder.encode(filterClassId.trim(), StandardCharsets.UTF_8));
        }
        return query.toString();
    }
}
