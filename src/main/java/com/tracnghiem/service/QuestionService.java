package com.tracnghiem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.QuestionDAO;
import com.tracnghiem.dto.QuestionActionDTO;
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

        Subject subject = subjectService.getSubjectById(dto.getSubjectId());
        Lecturer lecturer = lecturerService.findLecturerById(dto.getLecturerId());

        existing.setSubject(subject);
        existing.setLevel(dto.getLevel());
        existing.setContent(dto.getContent());
        existing.setOptionA(dto.getOptionA());
        existing.setOptionB(dto.getOptionB());
        existing.setOptionC(dto.getOptionC());
        existing.setOptionD(dto.getOptionD());
        existing.setCorrectAnswer(dto.getCorrectAnswer());
        existing.setLecturer(lecturer);

        questionDAO.update(existing);
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

	@Transactional
	public void savePendingActions(String actionsData, String role, String userId) {
		if (actionsData == null || actionsData.trim().isEmpty()) {
			return;
		}

		List<QuestionActionDTO> actions = new ArrayList<>();
		String[] lines = actionsData.split("\n");
		for (String line : lines) {
			if (line.trim().isEmpty()) {
				continue;
			}
			String[] parts = line.split(":::", -1);
			if (parts.length < 2) {
				continue;
			}
			String type = parts[0].trim();
			String questionId = parts[1].trim();
			String subjectId = parts.length > 2 ? parts[2].trim() : "";
			String level = parts.length > 3 ? parts[3].trim() : "";
			String content = parts.length > 4 ? parts[4].trim() : "";
			String optionA = parts.length > 5 ? parts[5].trim() : "";
			String optionB = parts.length > 6 ? parts[6].trim() : "";
			String optionC = parts.length > 7 ? parts[7].trim() : "";
			String optionD = parts.length > 8 ? parts[8].trim() : "";
			String correctAnswer = parts.length > 9 ? parts[9].trim() : "";
			String lecturerId = parts.length > 10 ? parts[10].trim() : "";

			actions.add(new QuestionActionDTO(type, questionId, subjectId, level, content, optionA, optionB, optionC, optionD, correctAnswer, lecturerId));
		}

		for (QuestionActionDTO action : actions) {
			QuestionDTO dto = new QuestionDTO();
			dto.setQuestionId(parseQuestionId(action.getQuestionId()));
			dto.setSubjectId(action.getSubjectId());
			dto.setLevel(action.getLevel());
			dto.setContent(action.getContent());
			dto.setOptionA(action.getOptionA());
			dto.setOptionB(action.getOptionB());
			dto.setOptionC(action.getOptionC());
			dto.setOptionD(action.getOptionD());
			dto.setCorrectAnswer(action.getCorrectAnswer());
			dto.setLecturerId(action.getLecturerId());

			if ("ADD".equals(action.getType())) {
				dto.setQuestionId(null);
				addQuestion(dto, role, userId);
			} else if ("UPDATE".equals(action.getType())) {
				updateQuestion(dto, role, userId);
			} else if ("DELETE".equals(action.getType())) {
				deleteQuestion(dto, role, userId);
			}
		}
	}

	@Transactional
	public void importQuestions(List<QuestionDTO> dtos, String role, String loggedUser) {
		for (QuestionDTO dto : dtos) {
			String subjectId = dto.getSubjectId();
			String level = dto.getLevel();
			String content = dto.getContent();
			String optionA = dto.getOptionA();
			String optionB = dto.getOptionB();
			String optionC = dto.getOptionC();
			String optionD = dto.getOptionD();
			String correctAnswer = dto.getCorrectAnswer();
			String lecturerId = dto.getLecturerId();

			if (subjectId == null || subjectId.trim().isEmpty()) {
				throw new IllegalArgumentException("Mã môn học không được để trống.");
			}
			subjectId = subjectId.trim();
			if (subjectService.getSubjectById(subjectId) == null) {
				throw new IllegalArgumentException("Mã môn học '" + subjectId + "' không tồn tại.");
			}

			if (level == null || level.trim().isEmpty()) {
				throw new IllegalArgumentException("Trình độ không được để trống.");
			}
			level = level.trim();
			if (!"A".equals(level) && !"B".equals(level) && !"C".equals(level)) {
				throw new IllegalArgumentException("Trình độ phải là A, B hoặc C: " + level);
			}

			if (content == null || content.trim().isEmpty()) {
				throw new IllegalArgumentException("Nội dung câu hỏi không được để trống.");
			}
			content = content.trim();

			if (optionA == null || optionA.trim().isEmpty()) {
				throw new IllegalArgumentException("Đáp án A không được để trống.");
			}
			optionA = optionA.trim();

			if (optionB == null || optionB.trim().isEmpty()) {
				throw new IllegalArgumentException("Đáp án B không được để trống.");
			}
			optionB = optionB.trim();

			if (optionC == null || optionC.trim().isEmpty()) {
				throw new IllegalArgumentException("Đáp án C không được để trống.");
			}
			optionC = optionC.trim();

			if (optionD == null || optionD.trim().isEmpty()) {
				throw new IllegalArgumentException("Đáp án D không được để trống.");
			}
			optionD = optionD.trim();

			if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
				throw new IllegalArgumentException("Đáp án đúng không được để trống.");
			}
			correctAnswer = correctAnswer.trim();
			if (!"A".equals(correctAnswer) && !"B".equals(correctAnswer) && !"C".equals(correctAnswer) && !"D".equals(correctAnswer)) {
				throw new IllegalArgumentException("Đáp án đúng phải là A, B, C hoặc D: " + correctAnswer);
			}

			if (lecturerId == null || lecturerId.trim().isEmpty()) {
				throw new IllegalArgumentException("Mã giảng viên không được để trống.");
			}
			lecturerId = lecturerId.trim();
			if (lecturerService.findLecturerById(lecturerId) == null) {
				throw new IllegalArgumentException("Mã giảng viên '" + lecturerId + "' không tồn tại.");
			}

			QuestionDTO cleanDto = new QuestionDTO();
			cleanDto.setSubjectId(subjectId);
			cleanDto.setLevel(level);
			cleanDto.setContent(content);
			cleanDto.setOptionA(optionA);
			cleanDto.setOptionB(optionB);
			cleanDto.setOptionC(optionC);
			cleanDto.setOptionD(optionD);
			cleanDto.setCorrectAnswer(correctAnswer);
			cleanDto.setLecturerId(lecturerId);

			addQuestion(cleanDto, role, loggedUser);
		}
	}

	private Integer parseQuestionId(String idStr) {
		try {
			return Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public List<Question> getAllQuestions(String role, String loggedUser) {
		if ("GIAOVIEN".equals(role)) {
			return questionDAO.findAllQuestions(loggedUser);
		}
		return questionDAO.findAllQuestions(null);
	}
}
