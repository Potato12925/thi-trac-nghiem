package com.tracnghiem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.service.TestService;

@Controller
@RequestMapping({ "/hello" })
public class HelloController {

    @Autowired
    private TestService testService;

    @GetMapping
    public String sayHello(Model model) {

        model.addAttribute(
                "message",
                "Hello, Spring MVC Developer!");

        boolean connected = testService.testConnection();

        if (connected) {

            model.addAttribute(
                    "dbStatus",
                    "Kết nối database thành công");

        } else {

            model.addAttribute(
                    "dbStatus",
                    "Lỗi kết nối database");
        }

        model.addAttribute(
                "dbConnected",
                connected);

        return "hello";
    }
}