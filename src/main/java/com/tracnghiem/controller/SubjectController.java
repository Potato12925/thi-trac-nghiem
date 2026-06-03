package com.tracnghiem.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tracnghiem.dto.SubjectDTO;
import com.tracnghiem.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private static final String INDEX_VIEW = "Subject/Index";
    private static final String REDIRECT_INDEX = "redirect:/subjects";
    private static final String SUBJECT_MODEL_ATTRIBUTE = "subjectDTO";

    @Autowired
    private SubjectService subjectService;

    private void loadPageData(ModelMap model, int page, String search) {
        int pageSize = 10;

        if (page < 1) {
            page = 1;
        }

        model.addAttribute("subjects", subjectService.getSubjects(page, pageSize, search));
        model.addAttribute(SUBJECT_MODEL_ATTRIBUTE, new SubjectDTO());
        model.addAttribute("search", search);

        long total = subjectService.countSubjects(search);
        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String search,
            ModelMap model) {
        loadPageData(model, page, search);
        return INDEX_VIEW;
    }

    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String search,
            @Validated @ModelAttribute(SUBJECT_MODEL_ATTRIBUTE) SubjectDTO subjectDTO,
            BindingResult errors, ModelMap model, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            loadPageData(model, page, search);
            return INDEX_VIEW;
        }

        try {
            subjectService.addSubject(subjectDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm môn học thành công.");
            return buildRedirectUrl(page, search);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            loadPageData(model, page, search);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String search,
            @Validated @ModelAttribute(SUBJECT_MODEL_ATTRIBUTE) SubjectDTO subjectDTO,
            BindingResult errors, ModelMap model, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            loadPageData(model, page, search);
            return INDEX_VIEW;
        }

        try {
            subjectService.updateSubject(subjectDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật môn học thành công.");
            return buildRedirectUrl(page, search);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            loadPageData(model, page, search);
            return INDEX_VIEW;
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String search,
            @ModelAttribute(SUBJECT_MODEL_ATTRIBUTE) SubjectDTO subjectDTO,
            ModelMap model, RedirectAttributes redirectAttributes) {

        try {
            subjectService.deleteSubject(subjectDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa môn học thành công.");
            return buildRedirectUrl(page, search);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            loadPageData(model, page, search);
            return INDEX_VIEW;
        }
    }

    private String buildRedirectUrl(int page, String search) {
        String url = REDIRECT_INDEX + "?page=" + page;
        if (search != null && !search.trim().isEmpty()) {
            String encodedSearch = URLEncoder.encode(search.trim(), StandardCharsets.UTF_8);
            url += "&search=" + encodedSearch;
        }
        return url;
    }
}
