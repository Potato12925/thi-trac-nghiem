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

import com.tracnghiem.dto.QuestionDTO;
import com.tracnghiem.entity.Question;
import com.tracnghiem.service.QuestionService;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@GetMapping
	public String showQuestionPage(ModelMap model) {

		prepareQuestionPage(model);

		model.addAttribute("questionDTO", new QuestionDTO());

		return "Question/Index";
	}

	@PostMapping("/add")
	public String createQuestion(@Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
			BindingResult validationResult, ModelMap model) {

		if (validationResult.hasErrors()) {
			return renderQuestionPage(model);
		}

		try {

			questionService.addQuestion(questionForm);

			return "redirect:/questions";

		} catch (IllegalArgumentException exception) {

			model.addAttribute("errorMessage", exception.getMessage());

			return renderQuestionPage(model);
		}
	}

	@PostMapping("/update")
	public String editQuestion(@Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
			BindingResult validationResult, ModelMap model) {

		if (validationResult.hasErrors()) {
			return renderQuestionPage(model);
		}

		try {

			questionService.updateQuestion(questionForm);

			return "redirect:/questions";

		} catch (IllegalArgumentException exception) {

			model.addAttribute("errorMessage", exception.getMessage());

			return renderQuestionPage(model);
		}
	}

	@PostMapping("/delete")
	public String removeQuestion(@Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
			BindingResult validationResult, ModelMap model) {

		if (validationResult.hasErrors()) {
			return renderQuestionPage(model);
		}

		try {

			questionService.deleteQuestion(questionForm);

			return "redirect:/questions";

		} catch (IllegalArgumentException exception) {

			model.addAttribute("errorMessage", exception.getMessage());

			return renderQuestionPage(model);
		}
	}

	private void prepareQuestionPage(ModelMap model) {

		List<Question> questions = questionService.getAllQuestions();

		model.addAttribute("questions", questions);
	}

	private String renderQuestionPage(ModelMap model) {

		prepareQuestionPage(model);

		return "Question/Index";
	}
}