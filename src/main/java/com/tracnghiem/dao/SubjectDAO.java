package com.tracnghiem.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Subject;

@Repository
public class SubjectDAO extends GenericDAO<Subject> {

    public boolean existsById(String subjectId) {
        return findById(subjectId) != null;
    }

    public List<Subject> findPage(int page, int pageSize, String keyword) {
        String hql = "FROM Subject";
        String normalized = keyword == null ? "" : keyword.trim().toLowerCase();
        boolean hasKeyword = !normalized.isEmpty();

        if (hasKeyword) {
            hql += " WHERE lower(subjectId) LIKE :keyword OR lower(subjectName) LIKE :keyword";
        }

        int offset = (page - 1) * pageSize;
        Query<Subject> query = getSession().createQuery(hql, Subject.class);

        if (hasKeyword) {
            query.setParameter("keyword", "%" + normalized + "%");
        }

        return query.setFirstResult(offset).setMaxResults(pageSize).list();
    }

    public long countAll(String keyword) {
        String hql = "SELECT COUNT(*) FROM Subject";
        String normalized = keyword == null ? "" : keyword.trim().toLowerCase();
        boolean hasKeyword = !normalized.isEmpty();

        if (hasKeyword) {
            hql += " WHERE lower(subjectId) LIKE :keyword OR lower(subjectName) LIKE :keyword";
        }

        Query<Long> query = getSession().createQuery(hql, Long.class);
        if (hasKeyword) {
            query.setParameter("keyword", "%" + normalized + "%");
        }

        return query.uniqueResult();
    }
}
