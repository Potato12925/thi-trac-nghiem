package com.tracnghiem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.TeacherDAO;
import com.tracnghiem.entity.Teacher;

@Service
public class TeacherService {
	@Autowired
	TeacherDAO teacherDAO;

	public Teacher findByTeacherId(String teacherId) {
		return teacherDAO.findById(teacherId);
	}
}
