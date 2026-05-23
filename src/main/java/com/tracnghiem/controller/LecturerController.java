package com.tracnghiem.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	private static final String INDEX_VIEW = "Lecturer/Index";
	private static final String REDIRECT_INDEX = "redirect:/lecturers";

	@Autowired
	private LecturerService lecturerService;

	private String loadIndexPage(ModelMap model) {
		populateIndexPageModel(model);
		return INDEX_VIEW;
	}

	private void populateIndexPageModel(ModelMap model) {
		model.addAttribute("lecturerDTO", new LecturerDTO());
		model.addAttribute("lecturers", lecturerService.getAllLecturers());
	}

	@GetMapping("/home")
	public String home(ModelMap model, HttpSession session) {
		String lecturerId = (String) session.getAttribute("LOGIN_USER");
		Lecturer lecturer = lecturerId != null ? lecturerService.findLecturerById(lecturerId) : null;

		model.addAttribute("pageTitle", "Lecturer Homepage");
		model.addAttribute("lecturerProfile", lecturer);
		model.addAttribute("today", new Date());

		return "Lecturer/Home";
	}

	@GetMapping
	public String index(ModelMap model) {
		populateIndexPageModel(model);
		return INDEX_VIEW;
	}

	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
			ModelMap model) {

		if (errors.hasErrors()) {
			return loadIndexPage(model);
		}

		lecturerService.addLecturer(lecturerDTO);
		return REDIRECT_INDEX;
	}

	@PostMapping("/update")
	public String update(@Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
			ModelMap model) {
		if (errors.hasErrors()) {
			errors.getAllErrors().forEach(error -> {
				System.out.println(error.getDefaultMessage());
			});

			return loadIndexPage(model);
		}

		System.out.print(lecturerDTO.getAddress());
		lecturerService.updateLecturer(lecturerDTO);
		return REDIRECT_INDEX;
	}

	@PostMapping("/delete")
	public String delete(@Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
			ModelMap model) {

		if (errors.hasErrors()) {
			return loadIndexPage(model);
		}

		lecturerService.deleteLecturer(lecturerDTO);
		return REDIRECT_INDEX;
	}
}
