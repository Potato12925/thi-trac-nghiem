package com.tracnghiem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.entity.Student;
import com.tracnghiem.service.AuthService;
import com.tracnghiem.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@Autowired
	AuthService authService;

	@GetMapping()
	public String Index(ModelMap model) {
		Student student = new Student();
		StudentDTO studentDTO = new StudentDTO();
		List<Student> listOfStudents = studentService.getAllStudents();

		model.addAttribute("sinhVienDTO", studentDTO);
		model.addAttribute("sinhVien", student);
		model.addAttribute("danhSachSinhVien", listOfStudents);

		return "Student/Index";
	}

	@GetMapping("/home")
	public String Home(ModelMap model) {
		return "Student/Home";
	}


	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("sinhVienDTO") StudentDTO sinhVien, BindingResult errors,
			ModelMap model) {

		if (errors.hasErrors()) {
			model.addAttribute("danhSachSinhVien", studentService.getAllStudents());

			return "Student/Index";
		}

		try {
			studentService.addStudentWithAccount(sinhVien);
			return "redirect:/student";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("danhSachSinhVien", studentService.getAllStudents());

			return "redirect:/student";
		}
	}

	@PutMapping("/update")
	public String update(@Validated @ModelAttribute("sinhVienDTO") StudentDTO studentDTO, BindingResult errors,
			ModelMap model) {
		if (errors.hasErrors()) {
			model.addAttribute("danhSachSinhVien", studentService.getAllStudents());

			return "Student/Index";

		}

		try {
			studentService.updateStudent(studentDTO);
			return "redirect:/student";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("danhSachSinhVien", studentService.getAllStudents());

			return "Student/Index";

		}
	}

	@DeleteMapping("/delete")
	public String delete(@Validated @ModelAttribute("sinhVienDTO") StudentDTO sinhVienDTO, BindingResult errors,
			ModelMap model) {
		if (errors.hasErrors()) {
			model.addAttribute("danhSachSinhVien", studentService.getAllStudents());

			return "Student/Index";
		}

		try {
			studentService.deleteStudent(sinhVienDTO);
			return "redirect:/student";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("danhSachSinhVien", studentService.getAllStudents());

			return "redirect:/student";
		}
	}
}
