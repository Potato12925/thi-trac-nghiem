package com.tracnghiem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dto.StudentScoreDTO;
import com.tracnghiem.entity.Exam;
import com.tracnghiem.entity.Student;

@Service
@Transactional
public class ScoreService {

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private ExamDAO examDAO;

    public String convertToLetterGrade(Float score) {
        if (score == null) return "-";
        if (score >= 9.0f) return "A+";
        if (score >= 8.5f) return "A";
        if (score >= 8.0f) return "B+";
        if (score >= 7.0f) return "B";
        if (score >= 6.5f) return "C+";
        if (score >= 5.5f) return "C";
        if (score >= 5.0f) return "D+";
        if (score >= 4.0f) return "D";
        return "F";
    }

    public List<StudentScoreDTO> getClassScores(String classId, String subjectId, Short tryNumber) {
        List<Student> students = studentDAO.findByClassId(classId);
        List<Exam> exams = examDAO.findClassExams(classId, subjectId, tryNumber);

        Map<String, Exam> examMap = exams.stream()
                .filter(e -> e.getStudent() != null)
                .collect(Collectors.toMap(
                        e -> e.getStudent().getStudentId(),
                        e -> e,
                        (e1, e2) -> e1 // keep first in case of duplicates
                ));

        List<StudentScoreDTO> list = new ArrayList<>();
        for (Student s : students) {
            Exam exam = examMap.get(s.getStudentId());
            Float score = (exam != null) ? exam.getScore() : null;
            Integer examId = (exam != null) ? exam.getId() : null;
            Boolean isViolation = (exam != null) ? exam.getIsViolation() : null;
            String letterGrade = convertToLetterGrade(score);
            list.add(new StudentScoreDTO(
                    s.getStudentId(),
                    s.getLastName(),
                    s.getFirstName(),
                    score,
                    letterGrade,
                    examId,
                    isViolation
            ));
        }
        return list;
    }
}
