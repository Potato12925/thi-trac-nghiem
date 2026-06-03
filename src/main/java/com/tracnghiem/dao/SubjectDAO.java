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

    @Override
    public List<Subject> findAll() {
        String hql = "FROM Subject s WHERE s.deleted = false";
        return getSession().createQuery(hql, Subject.class).list();
    }

    @Override
    public List<Subject> findPage(int page, int pageSize) {
        String hql = "FROM Subject s WHERE s.deleted = false ORDER BY s.subjectId";
        int offset = (page - 1) * pageSize;
        return getSession().createQuery(hql, Subject.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .list();
    }

    public List<Subject> findPage(int page, int pageSize, String keyword) {
        String hql = "FROM Subject s WHERE s.deleted = false";
        String normalized = keyword == null ? "" : keyword.trim().toLowerCase();
        boolean hasKeyword = !normalized.isEmpty();

        if (hasKeyword) {
            hql += " AND (lower(s.subjectId) LIKE :keyword OR lower(s.subjectName) LIKE :keyword)";
        }

        int offset = (page - 1) * pageSize;
        Query<Subject> query = getSession().createQuery(hql, Subject.class);

        if (hasKeyword) {
            query.setParameter("keyword", "%" + normalized + "%");
        }

        return query.setFirstResult(offset).setMaxResults(pageSize).list();
    }

    @Override
    public long countAll() {
        String hql = "SELECT COUNT(*) FROM Subject s WHERE s.deleted = false";
        return getSession().createQuery(hql, Long.class).uniqueResult();
    }

    public long countAll(String keyword) {
        String hql = "SELECT COUNT(*) FROM Subject s WHERE s.deleted = false";
        String normalized = keyword == null ? "" : keyword.trim().toLowerCase();
        boolean hasKeyword = !normalized.isEmpty();

        if (hasKeyword) {
            hql += " AND (lower(s.subjectId) LIKE :keyword OR lower(s.subjectName) LIKE :keyword)";
        }

        Query<Long> query = getSession().createQuery(hql, Long.class);
        if (hasKeyword) {
            query.setParameter("keyword", "%" + normalized + "%");
        }

        return query.uniqueResult();
    }
}
