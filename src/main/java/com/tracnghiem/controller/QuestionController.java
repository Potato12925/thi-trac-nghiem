package com.tracnghiem.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            ModelMap model,
            HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        String loggedUser = (String) session.getAttribute("LOGIN_USER");
        boolean isLecturer = "GIAOVIEN".equals(role);

        prepareQuestionPage(model, page, keyword, role, loggedUser);

        QuestionDTO questionDTO = new QuestionDTO();
        if (isLecturer) {
            questionDTO.setLecturerId(loggedUser);
        }
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("keyword", keyword);
        model.addAttribute("isLecturer", isLecturer);
        model.addAttribute("loggedLecturerId", loggedUser);

        return INDEX_VIEW;
    }

    @PostMapping("/add")
    public String createQuestion(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
            BindingResult validationResult,
            ModelMap model,
            HttpSession session) {

        if (validationResult.hasErrors()) {
            return renderQuestionPage(model, page, keyword, session);
        }

        try {
            String role = (String) session.getAttribute("ROLE");
            String loggedUser = (String) session.getAttribute("LOGIN_USER");
            questionService.addQuestion(questionForm, role, loggedUser);
            return REDIRECT_INDEX + buildQuery(page, keyword);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return renderQuestionPage(model, page, keyword, session);
        }
    }

    @PostMapping("/update")
    public String editQuestion(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
            BindingResult validationResult,
            ModelMap model,
            HttpSession session) {

        if (validationResult.hasErrors()) {
            return renderQuestionPage(model, page, keyword, session);
        }

        try {
            String role = (String) session.getAttribute("ROLE");
            String loggedUser = (String) session.getAttribute("LOGIN_USER");
            questionService.updateQuestion(questionForm, role, loggedUser);
            return REDIRECT_INDEX + buildQuery(page, keyword);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return renderQuestionPage(model, page, keyword, session);
        }
    }

    @PostMapping("/delete")
    public String removeQuestion(@RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword,
            @Validated @ModelAttribute("questionDTO") QuestionDTO questionForm,
            BindingResult validationResult,
            ModelMap model,
            HttpSession session) {

        if (validationResult.hasErrors()) {
            return renderQuestionPage(model, page, keyword, session);
        }

        try {
            String role = (String) session.getAttribute("ROLE");
            String loggedUser = (String) session.getAttribute("LOGIN_USER");
            questionService.deleteQuestion(questionForm, role, loggedUser);
            return REDIRECT_INDEX + buildQuery(page, keyword);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return renderQuestionPage(model, page, keyword, session);
        }
    }

	@PostMapping("/save")
	public String save(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam("actionsData") String actionsData, ModelMap model, RedirectAttributes redirectAttributes,
			HttpSession session) {

		if (actionsData == null || actionsData.trim().isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không có thay đổi nào để ghi.");
			return REDIRECT_INDEX + buildQuery(page, keyword);
		}

		try {
			String role = (String) session.getAttribute("ROLE");
			String loggedUser = (String) session.getAttribute("LOGIN_USER");
			questionService.savePendingActions(actionsData, role, loggedUser);
			redirectAttributes.addFlashAttribute("successMessage", "Ghi các thay đổi xuống CSDL thành công.");
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi ghi dữ liệu: " + ex.getMessage());
			return REDIRECT_INDEX + buildQuery(page, keyword);
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống: " + ex.getMessage());
			return REDIRECT_INDEX + buildQuery(page, keyword);
		}
	}

    private void prepareQuestionPage(ModelMap model, int page, String keyword, String role, String loggedUser) {
        int pageSize = 10;

        List<Question> questions = questionService.getQuestions(page, pageSize, keyword, role, loggedUser);

        long totalQuestions = questionService.countQuestion(keyword, role, loggedUser);

        int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);

        model.addAttribute("questions", questions);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
    }

    private String renderQuestionPage(ModelMap model, int page, String keyword, HttpSession session) {
        String role = (String) session.getAttribute("ROLE");
        String loggedUser = (String) session.getAttribute("LOGIN_USER");
        prepareQuestionPage(model, page, keyword, role, loggedUser);
        boolean isLecturer = "GIAOVIEN".equals(role);
        model.addAttribute("keyword", keyword);
        model.addAttribute("isLecturer", isLecturer);
        model.addAttribute("loggedLecturerId", loggedUser);
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
