package com.tracnghiem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.dto.LecturerDTO;
import com.tracnghiem.entity.Teacher;
import com.tracnghiem.service.LecturerService;

@Controller
@RequestMapping("/lecturer")
public class LecturerController {

	@Autowired
	private LecturerService lecturerService;

	@GetMapping
	public String index(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "maGV", required = false) String maGV, ModelMap model) {

		List<Teacher> teachers;
		if (search != null && !search.trim().isEmpty()) {
			teachers = lecturerService.findTeacherByKeyword(search.trim());
		} else {
			teachers = lecturerService.getAllTeachers();
		}

		Teacher teacher = null;
		if (maGV != null && !maGV.trim().isEmpty()) {
			teacher = lecturerService.findTeacherById(maGV.trim());
		}
		if (teacher == null) {
			teacher = new Teacher();
		}

		LecturerDTO lecturerDTO = new LecturerDTO();
		model.addAttribute("giaoVienDTO", lecturerDTO);
		model.addAttribute("teachers", teachers);
		model.addAttribute("teacher", teacher);
		model.addAttribute("search", search);

		return "Lecturer/Index";
	}

	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("giaoVienDTO") LecturerDTO lecturerDTO, BindingResult errors,
			Model model) {

		if (errors.hasErrors()) {
			model.addAttribute("teachers", lecturerService.getAllTeachers());
			return "Lecturer/Index";
		}

		try {
			lecturerService.addTeacherValidate(lecturerDTO);
			return "redirect:/lecturer?maGV=" + lecturerDTO.getMaGV();

		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("teachers", lecturerService.getAllTeachers());
			return "Lecturer/Index";
		}
	}

	@PutMapping("/update")
	public String update(@Validated @ModelAttribute("giaoVienDTO") LecturerDTO lecturerDTO, BindingResult errors,
			Model model) {

		if (errors.hasErrors()) {
			model.addAttribute("teachers", lecturerService.getAllTeachers());
			return "Lecturer/Index";
		}

		try {
			lecturerService.updateTeacher(lecturerDTO);
			return "redirect:/lecturer?maGV=" + lecturerDTO.getMaGV();

		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("teachers", lecturerService.getAllTeachers());
			return "Lecturer/Index";
		}
	}

	@DeleteMapping("/delete")
	public String delete(@Validated @ModelAttribute("giaoVienDTO") LecturerDTO lecturerDTO, BindingResult errors,
			Model model) {

		if (errors.hasErrors()) {
			model.addAttribute("teachers", lecturerService.getAllTeachers());
			return "Lecturer/Index";
		}

		try {
			lecturerService.deleteTeacher(lecturerDTO);
			return "redirect:/lecturer";

		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("teachers", lecturerService.getAllTeachers());
			return "Lecturer/Index";
		}
	}
}
