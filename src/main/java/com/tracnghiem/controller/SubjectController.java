package com.tracnghiem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.dto.SubjectDTO;
import com.tracnghiem.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	private void loadPageData(ModelMap model) {
		model.addAttribute("subjects", subjectService.getAllSubjects());
		model.addAttribute("subjectDTO", new SubjectDTO());
	}

	@GetMapping
	public String index(ModelMap model) {

		loadPageData(model);

		return "Subject/Index";
	}

	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("subject") SubjectDTO subjectDTO, BindingResult errors,
			ModelMap model) {

		if (errors.hasErrors()) {
			loadPageData(model);
			return "Subject/Index";
		}

		subjectService.addSubject(subjectDTO);

		return "redirect:/subjects";
	}

	@PostMapping("/update")
	public String update(@Validated @ModelAttribute("subject") SubjectDTO subjectDTO, BindingResult errors,
			ModelMap model) {

		if (errors.hasErrors()) {
			loadPageData(model);
			return "Subject/Index";
		}

		subjectService.updateSubject(subjectDTO);

		return "redirect:/subjects";
	}

	@PostMapping("/delete")
	public String delete(@ModelAttribute("subject") SubjectDTO subjectDTO, BindingResult errors, ModelMap model) {

		if (errors.hasErrors()) {
			loadPageData(model);
			return "Subject/Index";
		}

		subjectService.deleteSubject(subjectDTO);

		return "redirect:/subjects";
	}
}
