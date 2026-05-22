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

import com.tracnghiem.dto.ClassRoomDTO;
import com.tracnghiem.entity.ClassRoom;
import com.tracnghiem.service.ClassRoomService;

@Controller
@RequestMapping("/classRoom")
public class ClassController {

	@Autowired
	ClassRoomService classRoomService;

	@GetMapping()
	public String Index(ModelMap model) {
		ClassRoom classRoom = new ClassRoom();
		ClassRoomDTO classRoomDTO = new ClassRoomDTO();
		List<ClassRoom> classRoomList = classRoomService.getAllClassRoomList();

		model.addAttribute("classRoomDTO", classRoomDTO);
		model.addAttribute("classRoom", classRoom);
		model.addAttribute("classRoomList", classRoomList);

		return "ClassRoom/Index";
	}

	@PostMapping("/add")
	public String add(@Validated @ModelAttribute("classRoomDTO") ClassRoomDTO classRoomDTO, BindingResult errors,
			ModelMap model) {
		if (errors.hasErrors()) {
			model.addAttribute("classRoomList", classRoomService.getAllClassRoomList());

			return "ClassRoom/Index";
		}

		try {
			classRoomService.addClassRoom(classRoomDTO);
			return "redirect:/classRoom";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("classRoomList", classRoomService.getAllClassRoomList());

			return "redirect:/classRoom";
		}
	}

	@PutMapping("/update")
	public String update(@Validated @ModelAttribute("classRoomDTO") ClassRoomDTO classRoomDTO, BindingResult errors,
			ModelMap model) {
		if (errors.hasErrors()) {
			model.addAttribute("classRoomList", classRoomService.getAllClassRoomList());

			return "ClassRoom/Index";

		}

		try {
			classRoomService.updateClassRoom(classRoomDTO);
			return "redirect:/classRoom";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("classRoomList", classRoomService.getAllClassRoomList());

			return "ClassRoom/Index";

		}
	}

	@DeleteMapping("/delete")
	public String delete(@Validated @ModelAttribute("classRoomDTO") ClassRoomDTO classRoomDTO, BindingResult errors,
			ModelMap model) {
		if (errors.hasErrors()) {
			model.addAttribute("classRoomList", classRoomService.getAllClassRoomList());

			return "ClassRoom/Index";
		}

		try {
			classRoomService.deleteClassRoom(classRoomDTO);
			return "redirect:/classRoom";

		} catch (IllegalArgumentException e) {

			model.addAttribute("error", e.getMessage());

			model.addAttribute("classRoomList", classRoomService.getAllClassRoomList());

			return "redirect:/classRoom";
		}
	}
}
