package com.tracnghiem.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Student;

@Repository
public class StudentDAO extends GenericDAO<Student> {
	public boolean existsById(String studentId) {
	    return findById(studentId) != null;
	}

	public String findEmailByStudentId(String studentId) {
		String hql = "SELECT s.email FROM Student s WHERE s.studentId = :studentId";

		List<String> emails = getSession()
				.createQuery(hql, String.class)
				.setParameter("studentId", studentId)
				.list();

		return emails.isEmpty() ? null : emails.get(0);
	}

	public List<Student> findByClassId(String classId) {
		String hql = "FROM Student s WHERE s.classRoom.classId = :classId ORDER BY s.firstName, s.lastName";
		return getSession().createQuery(hql, Student.class)
				.setParameter("classId", classId)
				.list();
	}
}

