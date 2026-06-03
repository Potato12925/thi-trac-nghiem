package com.tracnghiem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tracnghiem.dto.SubjectDTO;
import com.tracnghiem.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private static final String INDEX_VIEW = "Subject/Index";
    private static final String REDIRECT_INDEX = "redirect:/subjects";

    @Autowired
    private SubjectService subjectService;

    private void loadPageData(ModelMap model, int page) {
        int pageSize = 10;

        model.addAttribute("subjects", subjectService.getSubjects(page, pageSize));
        model.addAttribute("subjectDTO", new SubjectDTO());

        long total = subjectService.countSubjects();

        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "1") int page, ModelMap model) {
        loadPageData(model, page);
        return INDEX_VIEW;
    }

    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("subject") SubjectDTO subjectDTO, BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            loadPageData(model, page);
            return INDEX_VIEW;
        }

        try {
            subjectService.addSubject(subjectDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            loadPageData(model, page);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("subject") SubjectDTO subjectDTO, BindingResult errors,
            ModelMap model) {

        if (errors.hasErrors()) {
            loadPageData(model, page);
            return INDEX_VIEW;
        }

        try {
            subjectService.updateSubject(subjectDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            loadPageData(model, page);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(defaultValue = "1") int page, @ModelAttribute("subject") SubjectDTO subjectDTO, BindingResult errors, ModelMap model) {

        if (errors.hasErrors()) {
            loadPageData(model, page);
            return INDEX_VIEW;
        }

        try {
            subjectService.deleteSubject(subjectDTO);
            return REDIRECT_INDEX;
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            loadPageData(model, page);
            return INDEX_VIEW;
        }
    }
}
