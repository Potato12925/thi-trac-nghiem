package com.tracnghiem.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.dto.LecturerDTO;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.service.LecturerService;

@Controller
@RequestMapping("/lecturers")
public class LecturerController {

    private static final String INDEX_VIEW = "Lecturer/Index";
    private static final String REDIRECT_INDEX = "redirect:/lecturers";

    @Autowired
    private LecturerService lecturerService;

    private void prepareLecturerPage(ModelMap model, int page, String keyword) {
        int pageSize = 10;

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("lecturers", lecturerService.getLecturers(page, pageSize, keyword));
            model.addAttribute("keyword", keyword);
            long total = lecturerService.countLecturers(keyword);
            int totalPages = (int) Math.ceil((double) total / pageSize);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
        } else {
            model.addAttribute("lecturers", lecturerService.getLecturers(page, pageSize));
            long total = lecturerService.countLecturers();
            int totalPages = (int) Math.ceil((double) total / pageSize);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
        }
    }

    @GetMapping("/home")
    public String home(ModelMap model, HttpSession session) {
        String lecturerId = (String) session.getAttribute("LOGIN_USER");
        Lecturer lecturer = lecturerId != null ? lecturerService.findLecturerById(lecturerId) : null;

        model.addAttribute("pageTitle", "Lecturer Homepage");
        model.addAttribute("lecturerProfile", lecturer);
        model.addAttribute("today", new Date());

        return "Lecturer/Home";
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            ModelMap model) {
        prepareLecturerPage(model, page, keyword);
        model.addAttribute("lecturerDTO", new LecturerDTO());
        return INDEX_VIEW;
    }

    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO,
            BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            prepareLecturerPage(model, page, keyword);
            model.addAttribute("lecturerDTO", lecturerDTO);
            return INDEX_VIEW;
        }

        try {
            lecturerService.addLecturer(lecturerDTO);
            return REDIRECT_INDEX + buildQuery(page, keyword);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            prepareLecturerPage(model, page, keyword);
            model.addAttribute("lecturerDTO", lecturerDTO);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO,
            BindingResult errors,
            ModelMap model) {
        if (errors.hasErrors()) {
            prepareLecturerPage(model, page, keyword);
            model.addAttribute("lecturerDTO", lecturerDTO);
            return INDEX_VIEW;
        }

        try {
            lecturerService.updateLecturer(lecturerDTO);
            return REDIRECT_INDEX + buildQuery(page, keyword);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            prepareLecturerPage(model, page, keyword);
            model.addAttribute("lecturerDTO", lecturerDTO);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO,
            BindingResult errors,
            ModelMap model) {
        if (errors.hasErrors()) {
            prepareLecturerPage(model, page, keyword);
            model.addAttribute("lecturerDTO", lecturerDTO);
            return INDEX_VIEW;
        }

        try {
            lecturerService.deleteLecturer(lecturerDTO);
            return REDIRECT_INDEX + buildQuery(page, keyword);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            prepareLecturerPage(model, page, keyword);
            model.addAttribute("lecturerDTO", lecturerDTO);
            return INDEX_VIEW;
        }
    }

    private String buildQuery(int page, String keyword) {
        StringBuilder query = new StringBuilder("?page=").append(page);
        if (keyword != null && !keyword.trim().isEmpty()) {
            query.append("&keyword=").append(URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8));
        }
        return query.toString();
    }
}
