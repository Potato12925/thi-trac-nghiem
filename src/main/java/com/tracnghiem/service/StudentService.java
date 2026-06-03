package com.tracnghiem.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.entity.Classroom;
import com.tracnghiem.entity.Student;

@Service
public class StudentService {

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    AuthService authService;

    @Autowired
    private ClassroomService classroomService;

    private Student convertToEntity(StudentDTO dto) {
        Classroom classRoom = classroomService.findClassroomById(dto.getClassId());

        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setLastName(dto.getLastName());
        student.setFirstName(dto.getFirstName());
        student.setBirthDate(dto.getBirthDate());
        student.setAddress(dto.getAddress());
        student.setClassRoom(classRoom);

        return student;
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public List<Student> getStudents(int page, int pageSize) {
        return studentDAO.findPage(page, pageSize);
    }

    public List<Student> getStudents(int page, int pageSize, String keyword, String classId) {
        return studentDAO.findPage(page, pageSize, keyword, classId);
    }

    public long countStudents() {
        return studentDAO.countAll();
    }

    public long countStudents(String keyword, String classId) {
        return studentDAO.countAll(keyword, classId);
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
        Student student = studentDAO.findById(dto.getStudentId());
        if (student == null) {
            throw new IllegalArgumentException("Sinh viên không tồn tại");
        }
        student.setDeleted(true);
        studentDAO.update(student);

        authService.deleteAccount(dto.getStudentId());
    }

    private void ensureStudentNotExists(String studentId) {

        if (studentDAO.existsById(studentId)) {

            throw new IllegalArgumentException("Mã sinh viên đã tồn tại");
        }
    }

    @Transactional
    public void addStudentWithAccount(StudentDTO dto) {
        ensureStudentNotExists(dto.getStudentId());

        authService.createAccount(dto.getStudentId());

        addStudent(dto);
    }

}
