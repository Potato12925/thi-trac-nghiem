package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.ClassRoom;

@Repository
public class ClassRoomDAO extends GenericDAO<ClassRoom> {

	public List<ClassRoom> findByKeyword(String keyword) {
		String hql = "FROM ClassRoom c WHERE c.classRoomId LIKE :keyword OR c.className LIKE :keyword";
		return getSession().createQuery(hql, ClassRoom.class).setParameter("keyword", '%' + keyword + '%').list();
	}

	public boolean existsById(String classRoomId) {
		return findById(classRoomId) != null;
	}
}