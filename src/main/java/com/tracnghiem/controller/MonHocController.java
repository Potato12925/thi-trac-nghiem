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

import com.tracnghiem.entity.MonHoc;
import com.tracnghiem.service.MonHocService;

@Controller()
@RequestMapping("subject")
public class MonHocController {
	@Autowired
	private MonHocService monHocService;

	@RequestMapping()
	public String index(@RequestParam(value = "search", required = false) String search, ModelMap model) {
		MonHoc monHoc = new MonHoc();

		List<MonHoc> danhSachMonHoc;
		if (search != null && !search.trim().isEmpty()) {
			danhSachMonHoc = monHocService.timKiemTheoMaMonHoc(search.trim());
		} else {
			danhSachMonHoc = monHocService.layTatCaMonHoc();
		}

		model.addAttribute("danhSachMonHoc", danhSachMonHoc);
		model.addAttribute("monHoc", monHoc);

		return "Subject/Index";
	}

	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("monHoc") MonHoc monHoc, BindingResult errors, ModelMap model) {
		if (errors.hasErrors()) {

			List<MonHoc> danhSachMonHoc = monHocService.layTatCaMonHoc();
			model.addAttribute("danhSachMonHoc", danhSachMonHoc);

			return "Subject/Index";
		}

		monHocService.themMonHoc(monHoc);

		return "redirect:/subject";
	}

	@PutMapping("/update")
	public String update(@Validated @ModelAttribute("monHoc") MonHoc monHoc) {
		monHocService.chinhSuaMonHoc(monHoc);

		return "redirect:/subject";
	}

	@DeleteMapping("/delete")
	public String delete(@ModelAttribute("monHoc") MonHoc monHoc) {
		monHocService.xoaMonHoc(monHoc);

		return "redirect:/subject";
	}
}
