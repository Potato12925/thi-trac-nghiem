package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.ClassRoom;

@Repository
public class ClassRoomDAO extends GenericDAO<ClassRoom> {

	public List<ClassRoom> findByKeyword(String keyword) {
		String hql = "FROM Lop l WHERE l.maLop LIKE :keyword OR l.tenLop LIKE :keyword";
		return getSession().createQuery(hql, ClassRoom.class).setParameter("keyword", '%' + keyword + '%').list();
	}

	public boolean existsById(String maLop) {
		return findById(maLop) != null;
	}
}