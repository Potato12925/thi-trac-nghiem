package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Classroom;

@Repository
public class ClassroomDAO extends GenericDAO<Classroom> {

	public List<Classroom> findByKeyword(String keyword) {
		String hql = "FROM Classroom c WHERE c.classId LIKE :keyword OR c.className LIKE :keyword";
		return getSession().createQuery(hql, Classroom.class).setParameter("keyword", '%' + keyword + '%').list();
	}

	public boolean existsById(String classRoomId) {
		return findById(classRoomId) != null;
	}
}