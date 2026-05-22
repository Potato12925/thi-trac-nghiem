package com.tracnghiem.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.entity.Teacher;
import com.tracnghiem.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@GetMapping("/home")
	public String Home(ModelMap model, HttpSession session) {
		String teacherId = (String) session.getAttribute("LOGIN_USER");
		Teacher teacher = teacherId != null ? teacherService.findByTeacherId(teacherId) : null;

		model.addAttribute("pageTitle", "Trang chu giang vien");
		model.addAttribute("teacherProfile", teacher);
		model.addAttribute("today", new Date());
		return "Teacher/Home";
	}
}
