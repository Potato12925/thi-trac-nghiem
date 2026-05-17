package com.tracnghiem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/class")
public class ClassController {

	@GetMapping("/")
	public String index() {
		return "Class/Index";
	}
}
