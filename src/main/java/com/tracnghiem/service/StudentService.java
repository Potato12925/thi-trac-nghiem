package com.tracnghiem.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.entity.ClassRoom;
import com.tracnghiem.entity.Student;

@Service
public class StudentService {

	@Autowired
	StudentDAO studentDAO;

	@Autowired
	AuthService authService;

	@Autowired
	ClassRoomService classRoomService;

	private Student changeToEntity(StudentDTO dto) {
		ClassRoom classRoom = classRoomService.timLopTheoMa(dto.getClassId());

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

	public Student getStudentById(String studentId) {
		return studentDAO.findById(studentId);
	}

	public void addStudent(StudentDTO dto) {
		Student student = changeToEntity(dto);
		
		studentDAO.create(student);
	}

	public void updateStudent(StudentDTO dto) {
		Student student = changeToEntity(dto);
		
		studentDAO.update(student);
	}

	public void deleteStudent(StudentDTO dto) {
		Student student = changeToEntity(dto);

		studentDAO.delete(student);

		authService.deleteAccount(dto.getStudentId());

	}

	private void validatestudentKhongTonTai(String maSV) {

		if (studentDAO.existsById(maSV)) {

			throw new IllegalArgumentException("Mã sinh viên đã tồn tại");
		}
	}

	@Transactional
	public void addStudentWithAccount(StudentDTO dto) {
		validatestudentKhongTonTai(dto.getStudentId());

		authService.createAccount(dto.getStudentId());

		addStudent(dto);
	}

}
