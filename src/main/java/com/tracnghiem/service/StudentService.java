package com.tracnghiem.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.ExamDAO;
import com.tracnghiem.dao.LecturerRegistrationDAO;
import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.dto.StudentDashboardDTO;
import com.tracnghiem.dto.UpcomingExamDTO;
import com.tracnghiem.entity.Classroom;
import com.tracnghiem.entity.Exam;
import com.tracnghiem.entity.LecturerRegistration;
import com.tracnghiem.entity.Student;

@Service
public class StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private AuthService authService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ExamDAO examDAO;

    @Autowired
    private LecturerRegistrationDAO lecturerRegistrationDAO;

    private Student convertToEntity(StudentDTO dto) {
        Classroom classRoom = classroomService.findClassroomById(dto.getClassId());

        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setLastName(dto.getLastName());
        student.setFirstName(dto.getFirstName());
        student.setBirthDate(dto.getBirthDate());
        student.setAddress(dto.getAddress());
        student.setEmail(dto.getEmail());
        student.setClassRoom(classRoom);

        return student;
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public List<Student> getStudents(int page, int pageSize) {
        return studentDAO.getPagination(page, pageSize);
    }

    public long countStudents() {
        return studentDAO.count();
    }

    public Student getStudentById(String studentId) {
        return studentDAO.findById(studentId);
    }

    public void addStudent(StudentDTO dto) {
        Student student = convertToEntity(dto);
        studentDAO.create(student);
    }

    public void updateStudent(StudentDTO dto) {
        Student student = convertToEntity(dto);
        studentDAO.update(student);
    }

    public void deleteStudent(StudentDTO dto) {
        Student student = convertToEntity(dto);
        studentDAO.delete(student);
        authService.deleteAccount(dto.getStudentId());
    }

    private void ensureStudentNotExists(String studentId) {
        if (studentDAO.existsById(studentId)) {
            throw new IllegalArgumentException("MÃ£ sinh viÃªn Ä‘Ã£ tá»“n táº¡i");
        }
    }

    @Transactional
    public void addStudentWithAccount(StudentDTO dto) {
        ensureStudentNotExists(dto.getStudentId());
        authService.createAccount(dto.getStudentId());
        addStudent(dto);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Student getDashboardStudentProfile(String loginUser) {
        String normalizedStudentId = normalize(loginUser);
        if (normalizedStudentId == null) {
            return null;
        }

        Student student = studentDAO.findDashboardProfileByStudentId(normalizedStudentId);
        return sanitizeStudentProfile(student);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public StudentDashboardDTO getStudentDashboardStats(Student student, Date now) {
        StudentDashboardDTO dashboard = new StudentDashboardDTO();
        dashboard.setAverageScore(0.0d);
        dashboard.setLatestExamSubjectName("Chưa có dữ liệu");

        if (student == null) {
            return dashboard;
        }

        String studentId = normalize(student.getStudentId());
        dashboard.setCompletedSubjectCount(examDAO.countDistinctCompletedSubjectsByStudent(studentId));

        Double averageScore = examDAO.findAverageScoreByStudent(studentId);
        if (averageScore != null) {
            dashboard.setAverageScore(averageScore);
        }

        Exam latestCompletedExam = examDAO.findLatestCompletedExamByStudent(studentId);
        dashboard.setLatestExamSubjectName(resolveLatestSubjectName(latestCompletedExam));

        List<UpcomingExamDTO> upcomingExams = getUpcomingExams(student, now);
        dashboard.setPendingExamCount(upcomingExams.size());

        return dashboard;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<UpcomingExamDTO> getUpcomingExams(Student student, Date now) {
        if (student == null || student.getClassRoom() == null) {
            return Collections.emptyList();
        }

        String studentId = normalize(student.getStudentId());
        String classId = normalize(student.getClassRoom().getClassId());
        if (studentId == null || classId == null) {
            return Collections.emptyList();
        }

        List<LecturerRegistration> registrations = lecturerRegistrationDAO.findUpcomingExamsForStudent(studentId, classId, now);
        return registrations.stream()
                .map(this::toUpcomingExamDTO)
                .collect(Collectors.toList());
    }

    private Student sanitizeStudentProfile(Student source) {
        if (source == null) {
            return null;
        }

        Student student = new Student();
        student.setStudentId(normalize(source.getStudentId()));
        student.setLastName(normalize(source.getLastName()));
        student.setFirstName(normalize(source.getFirstName()));
        student.setBirthDate(source.getBirthDate());
        student.setAddress(normalize(source.getAddress()));
        student.setEmail(normalize(source.getEmail()));

        if (source.getClassRoom() != null) {
            Classroom classRoom = new Classroom();
            classRoom.setClassId(normalize(source.getClassRoom().getClassId()));
            classRoom.setClassName(normalize(source.getClassRoom().getClassName()));
            student.setClassRoom(classRoom);
        }

        return student;
    }

    private UpcomingExamDTO toUpcomingExamDTO(LecturerRegistration registration) {
        UpcomingExamDTO dto = new UpcomingExamDTO();
        dto.setSubjectId(registration.getSubject() == null ? null : normalize(registration.getSubject().getSubjectId()));
        dto.setSubjectName(registration.getSubject() == null ? null : normalize(registration.getSubject().getSubjectName()));
        dto.setExamDate(registration.getExamDate());
        dto.setTryNumber(registration.getId() == null ? null : registration.getId().getTryNumber());
        dto.setLevel(normalize(registration.getLevel()));
        dto.setDuration(registration.getDuration());
        return dto;
    }

    private String resolveLatestSubjectName(Exam latestCompletedExam) {
        if (latestCompletedExam == null || latestCompletedExam.getSubject() == null) {
            return "Chưa có dữ liệu";
        }

        String subjectName = normalize(latestCompletedExam.getSubject().getSubjectName());
        return subjectName == null ? "Chưa có dữ liệu" : subjectName;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
