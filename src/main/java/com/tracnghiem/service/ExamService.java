package com.tracnghiem.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.dao.ExamDetailDAO;
import com.tracnghiem.dao.LecturerRegistrationDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.entity.Exam;
import com.tracnghiem.entity.ExamDetail;
import com.tracnghiem.entity.LecturerRegistration;
import com.tracnghiem.entity.Question;
import com.tracnghiem.entity.Student;
import com.tracnghiem.entity.Subject;
import com.tracnghiem.entity.id.ExamDetailId;
import com.tracnghiem.entity.id.LecturerRegistrationId;

@Service
@Transactional
public class ExamService {

    @Autowired
    private ExamDAO examDAO;

    @Autowired
    private ExamDetailDAO examDetailDAO;

    @Autowired
    private LecturerRegistrationDAO lecturerRegistrationDAO;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    public Exam startExam(String userId, String role, String classId, String subjectId, Short tryNumber) throws Exception {
        Student student = null;

        if ("SINHVIEN".equals(role)) {
            student = studentDAO.findById(userId);
            if (student == null) {
                throw new Exception("Không tìm thấy sinh viên.");
            }

            // Check if student already took this exam
            if (examDAO.findExam(userId, subjectId, tryNumber) != null) {
                throw new Exception("Sinh viên đã thi môn này ở lần thi thứ " + tryNumber + ".");
            }
        }

        Subject subject = subjectDAO.findById(subjectId);
        if (subject == null) {
            throw new Exception("Không tìm thấy môn học.");
        }

        LecturerRegistrationId regId = new LecturerRegistrationId(classId, subjectId, tryNumber);
        LecturerRegistration registration = lecturerRegistrationDAO.findById(regId);
        if (registration == null) {
            throw new Exception("Không tìm thấy đăng ký thi cho lớp, môn và lần thi này.");
        }

        List<Question> questions = registration.getQuestions();
        if (questions == null || questions.isEmpty()) {
            throw new Exception("Bộ đề chưa có câu hỏi nào.");
        }

        // Shuffle questions
        Collections.shuffle(questions);

        // Create new Exam
        Exam exam = new Exam();
        exam.setStudent(student); // null if GIAOVIEN
        exam.setSubject(subject);
        exam.setAttempt(tryNumber);
        exam.setClassId(classId);
        exam.setExamDate(new Date());
        exam.setStartTime(new Date());
        
        // For GIAOVIEN, we don't need to link to student initially, 
        // but we might need to handle this based on role if needed.
        // Currently assuming startExam gets student details from session or controller
        
        examDAO.create(exam);

        // Create ExamDetails
        int order = 1;
        for (Question q : questions) {
            ExamDetail detail = new ExamDetail();
            ExamDetailId detailId = new ExamDetailId(exam.getId(), q.getQuestionId());
            detail.setId(detailId);
            detail.setExam(exam);
            detail.setQuestion(q);
            detail.setQuestionOrder(order++);
            examDetailDAO.create(detail);
        }

        return exam;
    }

    public Exam submitExam(Integer examId, Map<Integer, String> studentAnswers) throws Exception {
        Exam exam = examDAO.findById(examId);
        if (exam == null) {
            throw new Exception("Không tìm thấy bài thi.");
        }

        exam.setEndTime(new Date());

        List<ExamDetail> details = exam.getExamDetails(); // assuming fetch=lazy but active inside transaction
        // if not fetched, might need a specific query in ExamDetailDAO to get by Exam ID

        float correctCount = 0;
        int totalQuestions = details.size();

        for (ExamDetail detail : details) {
            String answer = studentAnswers.get(detail.getQuestion().getQuestionId());
            if (answer != null) {
                detail.setStudentAnswer(answer);
            }
            if (detail.getQuestion().getCorrectAnswer().trim().equalsIgnoreCase(detail.getStudentAnswer() != null ? detail.getStudentAnswer().trim() : "")) {
                correctCount++;
            }
            examDetailDAO.update(detail);
        }

        float scoreValue = (correctCount / totalQuestions) * 10;
        // round to 1 decimal place
        scoreValue = Math.round(scoreValue * 10.0f) / 10.0f;
        exam.setScore(scoreValue);
        examDAO.update(exam);

        return exam;
    }

    public Exam getExam(Integer examId) {
        Exam exam = examDAO.findById(examId);
        if (exam != null) {
            org.hibernate.Hibernate.initialize(exam.getExamDetails());
            for (ExamDetail detail : exam.getExamDetails()) {
                org.hibernate.Hibernate.initialize(detail.getQuestion());
            }
        }
        return exam;
    }
}
