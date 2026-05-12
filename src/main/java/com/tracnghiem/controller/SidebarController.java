package com.tracnghiem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class SidebarController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "Home/Index";
    }

    @GetMapping("/class")
    public String classIndex() {
        return "Class/Index";
    }

    @GetMapping("/credit-class")
    public String creditClassIndex() {
        return "CreditClass/Index";
    }

    @GetMapping("/grade")
    public String gradeIndex() {
        return "Grade/Index";
    }

    @GetMapping("/registration")
    public String registrationIndex() {
        return "Registration/Index";
    }

    @GetMapping("/report")
    public String reportIndex() {
        return "Report/Index";
    }

}

