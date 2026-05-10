package com.tracnghiem.controller;

import java.sql.Connection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import utils.DBConnection;

@Controller
@RequestMapping({ "/hello", "/" })
public class HelloController {

    @GetMapping
    public String sayHello(Model model) {
        model.addAttribute("message", "Hello, Spring MVC Developer!");

        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                model.addAttribute("dbStatus", "Kết nối database thành công");
                model.addAttribute("dbConnected", true);
            } else {
                model.addAttribute("dbStatus", "Kết nối database thất bại");
                model.addAttribute("dbConnected", false);
            }
        } catch (Exception e) {
            model.addAttribute("dbStatus", "Lỗi kết nối database: " + e.getMessage());
            model.addAttribute("dbConnected", false);
        }

        return "hello";
    }
}
