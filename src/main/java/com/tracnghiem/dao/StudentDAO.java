package com.tracnghiem.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Student;

@Repository
public class StudentDAO extends GenericDAO<Student> {

    public boolean existsById(String studentId) {
        return findById(studentId) != null;
    }

    @Override
    public List<Student> findAll() {
        String hql = "FROM Student s WHERE s.deleted = false ORDER BY s.lastName, s.firstName";
        return getSession().createQuery(hql, Student.class).list();
    }

	public String findEmailByStudentId(String studentId) {
		String hql = "SELECT s.email FROM Student s WHERE s.studentId = :studentId";

		List<String> emails = getSession()
				.createQuery(hql, String.class)
				.setParameter("studentId", studentId)
				.list();

		return emails.isEmpty() ? null : emails.get(0);
	}

	public boolean existsByEmail(String email, String excludedStudentId) {
		String hql = "SELECT COUNT(s.studentId) FROM Student s "
				+ "WHERE lower(s.email) = :email "
				+ "AND (:excludedStudentId IS NULL OR s.studentId <> :excludedStudentId)";

		Long count = getSession()
				.createQuery(hql, Long.class)
				.setParameter("email", email)
				.setParameter("excludedStudentId", excludedStudentId)
				.uniqueResult();

		return count != null && count > 0;
	}

    public List<Student> findByClassId(String classId) {
        String hql = "FROM Student s WHERE s.deleted = false AND s.classRoom.classId = :classId ORDER BY s.lastName, s.firstName";
        return getSession().createQuery(hql, Student.class)
                .setParameter("classId", classId)
                .list();
    }

	public Student findDashboardProfileByStudentId(String studentId) {
		String hql = "SELECT s FROM Student s "
				+ "JOIN FETCH s.classRoom c "
				+ "WHERE function('ltrim', function('rtrim', s.studentId)) = :studentId";

		List<Student> students = getSession()
				.createQuery(hql, Student.class)
				.setParameter("studentId", studentId)
				.list();

		return students.isEmpty() ? null : students.get(0);
	}

    public List<Student> findPage(int page, int pageSize, String keyword, String classId) {
        String trimmedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        String trimmedClassId = classId == null ? "" : classId.trim();
        boolean hasKeyword = !trimmedKeyword.isEmpty();
        boolean hasClass = !trimmedClassId.isEmpty();

        String hql = "FROM Student s WHERE s.deleted = false";

        if (hasKeyword) {
            hql += " AND (lower(s.studentId) LIKE :keyword OR lower(s.lastName) LIKE :keyword OR lower(s.firstName) LIKE :keyword)";
        }

        if (hasClass) {
            hql += " AND s.classRoom.classId = :classId";
        }

        hql += " ORDER BY s.lastName, s.firstName";

        Query<Student> query = getSession().createQuery(hql, Student.class);

        if (hasKeyword) {
            query.setParameter("keyword", "%" + trimmedKeyword + "%");
        }

        if (hasClass) {
            query.setParameter("classId", trimmedClassId);
        }

        int offset = (page - 1) * pageSize;
        return query.setFirstResult(offset).setMaxResults(pageSize).list();
    }

    @Override
    public long countAll() {
        String hql = "SELECT COUNT(*) FROM Student s WHERE s.deleted = false";
        return getSession().createQuery(hql, Long.class).uniqueResult();
    }

    public long countAll(String keyword, String classId) {
        String trimmedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        String trimmedClassId = classId == null ? "" : classId.trim();
        boolean hasKeyword = !trimmedKeyword.isEmpty();
        boolean hasClass = !trimmedClassId.isEmpty();

        String hql = "SELECT COUNT(*) FROM Student s WHERE s.deleted = false";

        if (hasKeyword) {
            hql += " AND (lower(s.studentId) LIKE :keyword OR lower(s.lastName) LIKE :keyword OR lower(s.firstName) LIKE :keyword)";
        }

        if (hasClass) {
            hql += " AND s.classRoom.classId = :classId";
        }

        Query<Long> query = getSession().createQuery(hql, Long.class);

        if (hasKeyword) {
            query.setParameter("keyword", "%" + trimmedKeyword + "%");
        }

        if (hasClass) {
            query.setParameter("classId", trimmedClassId);
        }

        return query.uniqueResult();
    }

    @Override
    public List<Student> findPage(int page, int pageSize) {
        String hql = "FROM Student s WHERE s.deleted = false ORDER BY s.lastName, s.firstName";
        int offset = (page - 1) * pageSize;
        return getSession().createQuery(hql, Student.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .list();
    }
}
