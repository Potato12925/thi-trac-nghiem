package com.tracnghiem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracnghiem.dao.QuestionDAO;
import com.tracnghiem.dao.TeacherDAO;
import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.entity.Question;
import com.tracnghiem.entity.Teacher;
import com.tracnghiem.entity.Subject;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private TeacherDAO teacherDAO;

    @GetMapping
    public String index(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "questionId", required = false) Integer questionId,
            Model model) {

        List<Question> questions;
        if (search != null && !search.trim().isEmpty()) {
            questions = questionDAO.findByKeyword(search.trim());
        } else {
            questions = questionDAO.findAll();
        }

        Question selectedQuestion = null;
        if (questionId != null) {
            selectedQuestion = questionDAO.findById(questionId);
        }
        if (selectedQuestion == null) {
            selectedQuestion = new Question();
        }

        List<Subject> subjects = subjectDAO.findAll();
        List<Teacher> teachers = teacherDAO.findAll();

        model.addAttribute("questions", questions);
        model.addAttribute("question", selectedQuestion);
        model.addAttribute("subjects", subjects);
        model.addAttribute("teachers", teachers);
        model.addAttribute("search", search);

        return "Question/Index";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam(value = "questionId", required = false) Integer questionId,
            @RequestParam("subjectId") String subjectId,
            @RequestParam("level") String level,
            @RequestParam("content") String content,
            @RequestParam(value = "A", required = false) String answerA,
            @RequestParam(value = "B", required = false) String answerB,
            @RequestParam(value = "C", required = false) String answerC,
            @RequestParam(value = "D", required = false) String answerD,
            @RequestParam(value = "answer", required = false) String answer,
            @RequestParam("teacherId") String teacherId) {

        Subject subject = subjectDAO.findById(subjectId.trim());
        Teacher teacher = teacherDAO.findById(teacherId.trim());

        if (subject == null || teacher == null) {
            return "redirect:/question";
        }

        Question question = null;
        if (questionId != null) {
            question = questionDAO.findById(questionId);
        }
        if (question == null) {
            question = new Question();
        }

        question.setSubject(subject);
        question.setLevel(level != null ? level.trim() : "");
        question.setContent(content != null ? content.trim() : "");
        question.setOptionA(answerA != null ? answerA.trim() : "");
        question.setOptionB(answerB != null ? answerB.trim() : "");
        question.setOptionC(answerC != null ? answerC.trim() : "");
        question.setOptionD(answerD != null ? answerD.trim() : "");
        question.setCorrectAnswer(answer != null ? answer.trim() : "");
        question.setTeacher(teacher);

        if (question.getQuestionId() == null) {
            questionDAO.create(question);
        } else {
            questionDAO.update(question);
        }

        return "redirect:/question?questionId=" + question.getQuestionId();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("questionId") Integer questionId) {
        if (questionId != null) {
            Question question = questionDAO.findById(questionId);
            if (question != null) {
                questionDAO.delete(question);
            }
        }
        return "redirect:/question";
    }
}