package com.tracnghiem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.QuestionDAO;
import com.tracnghiem.dto.QuestionDTO;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.entity.Question;
import com.tracnghiem.entity.Subject;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    SubjectService subjectService;

    @Autowired
    LecturerService lecturerService;

    public Question findByQuestionId(String questionId) {
        return questionDAO.findById(questionId);
    }

    private Question mapToEntity(QuestionDTO dto) {
        Question question = new Question();

        Subject subject = subjectService.getSubjectById(dto.getSubjectId());

        Lecturer lecturer = lecturerService.findLecturerById(dto.getLecturerId());

        question.setQuestionId(dto.getQuestionId());
        question.setSubject(subject);
        question.setLevel(dto.getLevel());
        question.setContent(dto.getContent());
        question.setOptionA(dto.getOptionA());
        question.setOptionB(dto.getOptionB());
        question.setOptionC(dto.getOptionC());
        question.setOptionD(dto.getOptionD());
        question.setCorrectAnswer(dto.getCorrectAnswer());
        question.setLecturer(lecturer);

        return question;
    }

    public List<Question> getQuestions(int page, int pageSize) {
        return questionDAO.findPage(page, pageSize);
    }

    public List<Question> getQuestions(int page, int pageSize, String keyword) {
        return questionDAO.findPage(page, pageSize, keyword);
    }

    public List<Question> getQuestions(int page, int pageSize, String keyword, String role, String loggedUser) {
        if ("GIAOVIEN".equals(role)) {
            return questionDAO.findPage(page, pageSize, keyword, loggedUser);
        }
        return questionDAO.findPage(page, pageSize, keyword);
    }

    public long countQuestion() {
        return questionDAO.countAll();
    }

    public long countQuestion(String keyword) {
        return questionDAO.countAll(keyword);
    }

    public long countQuestion(String keyword, String role, String loggedUser) {
        if ("GIAOVIEN".equals(role)) {
            return questionDAO.countAll(keyword, loggedUser);
        }
        return questionDAO.countAll(keyword);
    }

    private void ensureQuestionNotExists(Integer questionId) {
        if (questionDAO.existsById(questionId)) {
            throw new IllegalArgumentException("Mã câu hỏi đã tồn tại");
        }
    }

    public void addQuestion(QuestionDTO dto, String role, String userId) {
        if ("GIAOVIEN".equals(role)) {
            throw new IllegalArgumentException("Giáo viên không có quyền thêm câu hỏi");
        }
        ensureQuestionNotExists(dto.getQuestionId());

        Question question = mapToEntity(dto);

        questionDAO.create(question);
    }

    public void updateQuestion(QuestionDTO dto, String role, String userId) {
        Question existing = questionDAO.findById(dto.getQuestionId());
        if (existing == null) {
            throw new IllegalArgumentException("Câu hỏi không tồn tại");
        }
        if ("GIAOVIEN".equals(role)) {
            if (userId == null || !userId.equals(existing.getLecturer().getLecturerId())) {
                throw new IllegalArgumentException("Giáo viên chỉ được cập nhật câu hỏi do mình soạn");
            }
            if (dto.getLecturerId() == null || !userId.equals(dto.getLecturerId())) {
                throw new IllegalArgumentException("Giáo viên chỉ được cập nhật câu hỏi do mình soạn");
            }
        }

        Question question = mapToEntity(dto);

        questionDAO.update(question);
    }

    public void deleteQuestion(QuestionDTO dto, String role, String userId) {
        if ("GIAOVIEN".equals(role)) {
            throw new IllegalArgumentException("Giáo viên không có quyền xóa câu hỏi");
        }

        Question existing = questionDAO.findById(dto.getQuestionId());
        if (existing == null) {
            throw new IllegalArgumentException("Câu hỏi không tồn tại");
        }

        existing.setDeleted(true);
        questionDAO.update(existing);
    }
}
