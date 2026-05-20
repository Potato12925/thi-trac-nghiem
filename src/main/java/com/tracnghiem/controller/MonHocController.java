package com.tracnghiem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.entity.Subject;
import com.tracnghiem.service.SubjectService;

@Controller()
@RequestMapping("subject")
public class MonHocController {
	@Autowired
	private SubjectService monHocService;

	@RequestMapping()
	public String index(@RequestParam(value = "search", required = false) String search, ModelMap model) {
		Subject monHoc = new Subject();

		List<Subject> danhSachMonHoc;
		if (search != null && !search.trim().isEmpty()) {
			danhSachMonHoc = monHocService.searchSubjects(search.trim());
		} else {
			danhSachMonHoc = monHocService.searchSubjects(null);
		}

		model.addAttribute("danhSachMonHoc", danhSachMonHoc);
		model.addAttribute("monHoc", monHoc);

		return "Subject/Index";
	}

	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("monHoc") Subject monHoc, BindingResult errors, ModelMap model) {
		if (errors.hasErrors()) {

			List<Subject> danhSachMonHoc = monHocService.searchSubjects(null);
			model.addAttribute("danhSachMonHoc", danhSachMonHoc);

			return "Subject/Index";
		}

		monHocService.createSubject(monHoc.getMaMH(),monHoc.getTenMH());

		return "redirect:/subject";
	}

	@PutMapping("/update")
	public String update(@Validated @ModelAttribute("monHoc") Subject monHoc) {
		monHocService.updateSubject(monHoc.getMaMH(), monHoc.getTenMH());

		return "redirect:/subject";
	}

	@DeleteMapping("/delete")
	public String delete(@ModelAttribute("monHoc") Subject monHoc) {
		monHocService.deleteSubject(monHoc.getMaMH());

		return "redirect:/subject";
	}
}
