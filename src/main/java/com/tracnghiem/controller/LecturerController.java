package com.tracnghiem.controller;

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

    private void prepareLecturerPage(ModelMap model, int page) {
        int pageSize = 10;

        model.addAttribute("lecturers", lecturerService.getLecturers(page, pageSize));

        long total = lecturerService.countLecturers();

        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    private String renderLecturerPage(ModelMap model, int page) {
        prepareLecturerPage(model, page);
        model.addAttribute("lecturerDTO", new LecturerDTO());
        return INDEX_VIEW;
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
    public String index(@RequestParam(defaultValue = "1") int page, ModelMap model) {
        prepareLecturerPage(model, page);
        model.addAttribute("lecturerDTO", new LecturerDTO());
        return INDEX_VIEW;
    }

    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            return renderLecturerPage(model, page);
        }

        try {
            lecturerService.addLecturer(lecturerDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return renderLecturerPage(model, page);
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
            ModelMap model) {
        if (errors.hasErrors()) {
            return renderLecturerPage(model, page);
        }

        try {
            lecturerService.updateLecturer(lecturerDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return renderLecturerPage(model, page);
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("lecturerDTO") LecturerDTO lecturerDTO, BindingResult errors,
            ModelMap model) {
        if (errors.hasErrors()) {
            return renderLecturerPage(model, page);
        }

        try {
            lecturerService.deleteLecturer(lecturerDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return renderLecturerPage(model, page);
        }
    }
}
