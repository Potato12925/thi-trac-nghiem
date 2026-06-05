package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Classroom;

@Repository
public class ClassroomDAO extends GenericDAO<Classroom> {

	public List<Classroom> findByKeyword(String keyword) {
		String hql = "FROM Classroom c WHERE (c.classId LIKE :keyword OR c.className LIKE :keyword) AND c.deleted = false";
		return getSession().createQuery(hql, Classroom.class).setParameter("keyword", '%' + keyword + '%').list();
	}

	@Override
	public List<Classroom> findAll() {
		String hql = "FROM Classroom c WHERE c.deleted = false";
		return getSession().createQuery(hql, Classroom.class).list();
	}

	@Override
	public List<Classroom> findPage(int page, int pageSize) {
		String hql = "FROM Classroom c WHERE c.deleted = false";
		int offset = (page - 1) * pageSize;
		return getSession().createQuery(hql, Classroom.class).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public long countAll() {
		String hql = "SELECT COUNT(*) FROM Classroom c WHERE c.deleted = false";
		return getSession().createQuery(hql, Long.class).uniqueResult();
	}

	public boolean existsById(String classRoomId) {
		return findById(classRoomId) != null;
	}
}