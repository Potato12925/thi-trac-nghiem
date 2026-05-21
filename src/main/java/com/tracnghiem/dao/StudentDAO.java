package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Student;

@Repository
public class StudentDAO extends GenericDAO<Student> {
	public boolean existsById(String studentId) {
	    return findById(studentId) != null;
	}
}
