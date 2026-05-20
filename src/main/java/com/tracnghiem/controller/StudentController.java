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
import com.tracnghiem.entity.SinhVien;
import com.tracnghiem.service.AuthService;
import com.tracnghiem.service.SinhVienService;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	SinhVienService sinhVienService;

	@Autowired
	AuthService authService;

	@GetMapping()
	public String Index(ModelMap model) {
		SinhVien sinhVien = new SinhVien();
		StudentDTO sinhVienDTO = new StudentDTO();
		List<SinhVien> danhSachSinhVien = sinhVienService.layDanhSachTatCaSinhVien();

		model.addAttribute("sinhVienDTO", sinhVienDTO);
		model.addAttribute("sinhVien", sinhVien);
		model.addAttribute("danhSachSinhVien", danhSachSinhVien);

		return "Student/Index";
	}

	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("sinhVienDTO") StudentDTO sinhVien, BindingResult errors,
			ModelMap model) {

		if (errors.hasErrors()) {
			model.addAttribute("danhSachSinhVien", sinhVienService.layDanhSachTatCaSinhVien());

			return "Student/Index";
		}

		try {
			sinhVienService.themSinhVienVaTaiKhoan(sinhVien);
			return "redirect:/student";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("danhSachSinhVien", sinhVienService.layDanhSachTatCaSinhVien());

			return "redirect:/student";
		}
	}

	@PutMapping("/update")
	public String update(@Validated @ModelAttribute("sinhVienDTO") StudentDTO sinhVienDTO, BindingResult errors,
			ModelMap model) {
		if (errors.hasErrors()) {
			model.addAttribute("danhSachSinhVien", sinhVienService.layDanhSachTatCaSinhVien());

			return "Student/Index";

		}

		try {
			sinhVienService.capNhatSinhVien(sinhVienDTO);
			return "redirect:/student";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("danhSachSinhVien", sinhVienService.layDanhSachTatCaSinhVien());

			return "Student/Index";

		}
	}

	@DeleteMapping("/delete")
	public String delete(@Validated @ModelAttribute("sinhVienDTO") StudentDTO sinhVienDTO, BindingResult errors,
			ModelMap model) {
		if (errors.hasErrors()) {
			model.addAttribute("danhSachSinhVien", sinhVienService.layDanhSachTatCaSinhVien());

			return "Student/Index";
		}

		try {
			sinhVienService.xoaSinhVien(sinhVienDTO);
			return "redirect:/student";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("danhSachSinhVien", sinhVienService.layDanhSachTatCaSinhVien());

			return "redirect:/student";
		}
	}
}
