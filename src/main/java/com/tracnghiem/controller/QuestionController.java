package com.tracnghiem.controller;

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

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public String showQuestionPage(@RequestParam(defaultValue = "1") int page, ModelMap model) {
        prepareQuestionPage(model, page);

        model.addAttribute("questionDTO", new QuestionDTO());

        return "Question/Index";
    }

    @PostMapping("/add")
    public String createQuestion(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
            BindingResult validationResult, ModelMap model) {

        if (validationResult.hasErrors()) {
            return renderQuestionPage(model, page);
        }

        try {

            questionService.addQuestion(questionForm);

            return "redirect:/questions";

        } catch (IllegalArgumentException exception) {

            model.addAttribute("errorMessage", exception.getMessage());

            return renderQuestionPage(model, page);
        }
    }

    @PostMapping("/update")
    public String editQuestion(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
            BindingResult validationResult, ModelMap model) {

        if (validationResult.hasErrors()) {
            return renderQuestionPage(model, page);
        }

        try {

            questionService.updateQuestion(questionForm);

            return "redirect:/questions";

        } catch (IllegalArgumentException exception) {

            model.addAttribute("errorMessage", exception.getMessage());

            return renderQuestionPage(model, page);
        }
    }

    @PostMapping("/delete")
    public String removeQuestion(@RequestParam(defaultValue = "1") int page, @Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
            BindingResult validationResult, ModelMap model) {

        if (validationResult.hasErrors()) {
            return renderQuestionPage(model, page);
        }

        try {

            questionService.deleteQuestion(questionForm);

            return "redirect:/questions";

        } catch (IllegalArgumentException exception) {

            model.addAttribute("errorMessage", exception.getMessage());

            return renderQuestionPage(model, page);
        }
    }

    private void prepareQuestionPage(ModelMap model, int page) {
        int pageSize = 10;

        List<Question> questions = questionService.getQuestions(page, pageSize);

        long totalQuestions = questionService.countQuestion();

        int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);

        model.addAttribute("questions", questions);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    private String renderQuestionPage(ModelMap model, int page) {

        prepareQuestionPage(model, page);

        return "Question/Index";
    }
}
