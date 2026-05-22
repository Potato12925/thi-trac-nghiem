package com.tracnghiem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.TeacherDAO;
import com.tracnghiem.dto.LecturerDTO;
import com.tracnghiem.entity.Teacher;

@Service
public class LecturerService {

	@Autowired
	private TeacherDAO teacherDAO;

	private Teacher changeToEntity(LecturerDTO dto) {
		Teacher teacher = new Teacher();
		teacher.setTeacherId(dto.getTeacherId());
		teacher.setLastName(dto.getLastName());
		teacher.setFirstName(dto.getFirstName());
		teacher.setPhoneNumber(dto.getPhoneNumber());
		teacher.setAddress(dto.getAddress());
		return teacher;
	}

	public List<Teacher> getAllTeachers() {
		return teacherDAO.findAll();
	}

	public Teacher findTeacherById(String maGV) {
		return teacherDAO.findById(maGV);
	}

	public List<Teacher> findTeacherByKeyword(String keyword) {
		return teacherDAO.findByKeyword(keyword);
	}

	public void addTeacher(LecturerDTO dto) {
		Teacher teacher = changeToEntity(dto);
		teacherDAO.create(teacher);
	}

	public void updateTeacher(LecturerDTO dto) {
		Teacher teacher = changeToEntity(dto);
		teacherDAO.update(teacher);
	}

	public void deleteTeacher(LecturerDTO dto) {
		Teacher teacher = changeToEntity(dto);
		teacherDAO.delete(teacher);
	}

	private void validateTeacherKhongTonTai(String maGV) {
		if (teacherDAO.existsById(maGV)) {
			throw new IllegalArgumentException("Mã giáo viên đã tồn tại");
		}
	}

	public void addTeacherValidate(LecturerDTO dto) {
		validateTeacherKhongTonTai(dto.getTeacherId());
		addTeacher(dto);
	}
}
