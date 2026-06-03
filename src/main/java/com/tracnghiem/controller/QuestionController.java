package com.tracnghiem.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

import com.tracnghiem.dto.QuestionDTO;
import com.tracnghiem.entity.Question;
import com.tracnghiem.service.QuestionService;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static final String INDEX_VIEW = "Question/Index";
    private static final String REDIRECT_INDEX = "redirect:/questions";

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public String showQuestionPage(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            ModelMap model) {
        prepareQuestionPage(model, page, keyword);

        model.addAttribute("questionDTO", new QuestionDTO());
        model.addAttribute("keyword", keyword);

        return INDEX_VIEW;
    }

    @PostMapping("/add")
    public String createQuestion(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
            BindingResult validationResult,
            ModelMap model) {

        if (validationResult.hasErrors()) {
            return renderQuestionPage(model, page, keyword);
        }

        try {
            questionService.addQuestion(questionForm);
            return REDIRECT_INDEX + buildQuery(page, keyword);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return renderQuestionPage(model, page, keyword);
        }
    }

    @PostMapping("/update")
    public String editQuestion(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
            BindingResult validationResult,
            ModelMap model) {

        if (validationResult.hasErrors()) {
            return renderQuestionPage(model, page, keyword);
        }

        try {
            questionService.updateQuestion(questionForm);
            return REDIRECT_INDEX + buildQuery(page, keyword);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return renderQuestionPage(model, page, keyword);
        }
    }

    @PostMapping("/delete")
    public String removeQuestion(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
            BindingResult validationResult,
            ModelMap model) {

        if (validationResult.hasErrors()) {
            return renderQuestionPage(model, page, keyword);
        }

        try {
            questionService.deleteQuestion(questionForm);
            return REDIRECT_INDEX + buildQuery(page, keyword);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return renderQuestionPage(model, page, keyword);
        }
    }

    private void prepareQuestionPage(ModelMap model, int page, String keyword) {
        int pageSize = 10;

        List<Question> questions = questionService.getQuestions(page, pageSize, keyword);

        long totalQuestions = questionService.countQuestion(keyword);

        int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);

        model.addAttribute("questions", questions);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    private String renderQuestionPage(ModelMap model, int page, String keyword) {
        prepareQuestionPage(model, page, keyword);
        model.addAttribute("keyword", keyword);
        return INDEX_VIEW;
    }

    private String buildQuery(int page, String keyword) {
        StringBuilder query = new StringBuilder("?page=").append(page);
        if (keyword != null && !keyword.trim().isEmpty()) {
            query.append("&keyword=").append(URLEncoder.encode(keyword.trim(), StandardCharsets.UTF_8));
        }
        return query.toString();
    }
}
