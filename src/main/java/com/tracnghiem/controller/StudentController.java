package com.tracnghiem.controller;

import java.util.Date;
import java.util.List;

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

import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.entity.Student;
import com.tracnghiem.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping
	public String index(ModelMap model) {

		List<Student> students = studentService.getAllStudents();

		model.addAttribute("studentDTO", new StudentDTO());
		model.addAttribute("students", students);

		return "Student/Index";
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
	public String addStudent(@Validated @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult errors,
			ModelMap model) {

		if (errors.hasErrors()) {

			model.addAttribute("students", studentService.getAllStudents());

			return "Student/Index";
		}

		try {

			studentService.addStudentWithAccount(studentDTO);

			return "redirect:/students";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("students", studentService.getAllStudents());

			return "Student/Index";
		}
	}

	@PostMapping("/update")
	public String updateStudent(@Validated @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult errors,
			ModelMap model) {

		if (errors.hasErrors()) {

			model.addAttribute("students", studentService.getAllStudents());

			return "Student/Index";
		}

		try {

			studentService.updateStudent(studentDTO);

			return "redirect:/students";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("students", studentService.getAllStudents());

			return "Student/Index";
		}
	}

	@PostMapping("/delete")
	public String deleteStudent(@Valid @ModelAttribute("studentDTO") StudentDTO studentDTO, BindingResult errors,
			ModelMap model) {

		if (errors.hasErrors()) {

			model.addAttribute("students", studentService.getAllStudents());

			return "Student/Index";
		}

		try {

			studentService.deleteStudent(studentDTO);

			return "redirect:/students";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("students", studentService.getAllStudents());

			return "Student/Index";
		}
	}
}