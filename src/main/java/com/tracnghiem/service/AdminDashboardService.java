package com.tracnghiem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.AccountDAO;
import com.tracnghiem.dao.ClassroomDAO;
import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.dao.LecturerDAO;
import com.tracnghiem.dao.LecturerRegistrationDAO;
import com.tracnghiem.dao.QuestionDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.dto.RecentExamRegistrationDTO;
import com.tracnghiem.entity.Lecturer;
import com.tracnghiem.entity.LecturerRegistration;

@Service
public class AdminDashboardService {

    private static final int DEFAULT_RECENT_LIMIT = 7;

    @Autowired
    private ClassroomDAO classroomDAO;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private LecturerDAO lecturerDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private ExamDAO examDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private LecturerRegistrationDAO lecturerRegistrationDAO;

    @Transactional(readOnly = true)
    public long getTotalClassrooms() {
        return classroomDAO.countAll();
    }

    @Transactional(readOnly = true)
    public long getTotalStudents() {
        return studentDAO.countAll();
    }

    @Transactional(readOnly = true)
    public long getTotalLecturers() {
        return lecturerDAO.countAll();
    }

    @Transactional(readOnly = true)
    public long getTotalSubjects() {
        return subjectDAO.countAll();
    }

    @Transactional(readOnly = true)
    public long getTotalQuestions() {
        return questionDAO.countAll();
    }

    @Transactional(readOnly = true)
    public long getTotalExams() {
        return examDAO.countAll();
    }

    @Transactional(readOnly = true)
    public long getTotalAccounts() {
        return accountDAO.countAll();
    }

    @Transactional(readOnly = true)
    public List<RecentExamRegistrationDTO> getRecentExamRegistrations() {
        List<LecturerRegistration> registrations = lecturerRegistrationDAO.findRecentForAdmin(DEFAULT_RECENT_LIMIT);
        if (registrations == null || registrations.isEmpty()) {
            return Collections.emptyList();
        }

        List<RecentExamRegistrationDTO> result = new ArrayList<>();
        for (LecturerRegistration registration : registrations) {
            result.add(toRecentExamRegistrationDTO(registration));
        }
        return result;
    }

    private RecentExamRegistrationDTO toRecentExamRegistrationDTO(LecturerRegistration registration) {
        RecentExamRegistrationDTO dto = new RecentExamRegistrationDTO();
        dto.setLecturerId(registration.getLecturer() == null ? null : normalize(registration.getLecturer().getLecturerId()));
        dto.setLecturerName(resolveLecturerName(registration.getLecturer()));
        dto.setClassId(registration.getClassRoom() == null ? null : normalize(registration.getClassRoom().getClassId()));
        dto.setClassName(registration.getClassRoom() == null ? null : normalize(registration.getClassRoom().getClassName()));
        dto.setSubjectId(registration.getSubject() == null ? null : normalize(registration.getSubject().getSubjectId()));
        dto.setSubjectName(registration.getSubject() == null ? null : normalize(registration.getSubject().getSubjectName()));
        dto.setAttempt(registration.getId() == null ? null : registration.getId().getTryNumber());
        dto.setLevel(normalize(registration.getLevel()));
        dto.setExamDate(registration.getExamDate());
        dto.setQuestionCount(registration.getNumberOfQuestions());
        dto.setDuration(registration.getDuration());
        return dto;
    }

    private String resolveLecturerName(Lecturer lecturer) {
        if (lecturer == null) {
            return null;
        }

        String lastName = normalize(lecturer.getLastName());
        String firstName = normalize(lecturer.getFirstName());
        String fullName = ((lastName == null ? "" : lastName) + " " + (firstName == null ? "" : firstName)).trim();

        if (!fullName.isEmpty()) {
            return fullName;
        }

        return normalize(lecturer.getLecturerId());
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
