package com.tracnghiem.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.dto.LecturerDTO;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.service.LecturerService;

@Controller
@RequestMapping("/lecturers")
public class LecturerController {

	@Autowired
	private LecturerService lecturerService;

	@GetMapping
	public String index(ModelMap model) {

		List<Lecturer> lectures = lecturerService.getAllLecturers();

		LecturerDTO lecturerDTO = new LecturerDTO();

		model.addAttribute("lecturerDTO", lecturerDTO);
		model.addAttribute("lecturers", lectures);

		return "Lecturer/Index";
	}

	@GetMapping("/home")
	public String Home(ModelMap model, HttpSession session) {
		String lecturerId = (String) session.getAttribute("LOGIN_USER");
		Lecturer Lecturer = lecturerId != null ? lecturerService.findLecturerById(lecturerId) : null;

		model.addAttribute("pageTitle", "Trang chu giang vien");
		model.addAttribute("lecturerProfile", Lecturer);
		model.addAttribute("today", new Date());
		return "Lecturer/Home";
	}

	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
			Model model) {

		if (errors.hasErrors()) {
			model.addAttribute("lecturers", lecturerService.getAllLecturers());
			return "Lecturer/Index";
		}

		try {
			lecturerService.addLecturer(lecturerDTO);

			return "redirect:/lecturer";

		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("lecturers", lecturerService.getAllLecturers());
			return "Lecturer/Index";
		}
	}

	@PostMapping("/update")
	public String update(@Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
			Model model) {

		if (errors.hasErrors()) {
			model.addAttribute("lecturers", lecturerService.getAllLecturers());
			return "Lecturer/Index";
		}

		try {
			lecturerService.updateLecturer(lecturerDTO);
			return "redirect:/lecturers";

		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("lecturers", lecturerService.getAllLecturers());
			return "Lecturer/Index";
		}
	}

	@PostMapping("/delete")
	public String delete(@Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
			Model model) {

		if (errors.hasErrors()) {
			model.addAttribute("lecturers", lecturerService.getAllLecturers());
			return "Lecturer/Index";
		}

		try {
			lecturerService.deleteLecturer(lecturerDTO);
			return "redirect:/lecturers";

		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("lecturers", lecturerService.getAllLecturers());
			return "Lecturer/Index";
		}
	}
}
