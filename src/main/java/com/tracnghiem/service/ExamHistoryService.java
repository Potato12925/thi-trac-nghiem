package com.tracnghiem.service;

import java.util.Comparator;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.entity.Exam;
import com.tracnghiem.entity.ExamDetail;

@Service
@Transactional
public class ExamHistoryService {

    @Autowired
    private ExamDAO examDAO;

    public List<Exam> getExamsByStudent(String studentId) {
        return examDAO.findByStudent(studentId);
    }

    public Exam getExamDetail(Integer examId) {
        Exam exam = examDAO.findById(examId);
        if (exam != null) {
            Hibernate.initialize(exam.getExamDetails());
            for (ExamDetail detail : exam.getExamDetails()) {
                Hibernate.initialize(detail.getQuestion());
            }

            if (exam.getExamDetails() != null) {
                exam.getExamDetails().sort(Comparator.comparing(ExamDetail::getQuestionOrder));
            }
        }
        return exam;
    }
}
