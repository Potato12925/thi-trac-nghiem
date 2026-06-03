package com.tracnghiem.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Lecturer;

@Repository
public class LecturerDAO extends GenericDAO<Lecturer> {

    public List<Lecturer> findByKeyword(String keyword) {

        String hql = "FROM Lecturer t WHERE t.deleted = false AND (lower(t.maGV) LIKE :keyword OR lower(t.ho) LIKE :keyword OR lower(t.ten) LIKE :keyword)";

        return getSession()
                .createQuery(hql, Lecturer.class)
                .setParameter("keyword", "%" + keyword.toLowerCase() + "%")
                .list();
    }

    @Override
    public List<Lecturer> findAll() {
        String hql = "FROM Lecturer t WHERE t.deleted = false ORDER BY t.ho, t.ten";
        return getSession().createQuery(hql, Lecturer.class).list();
    }

    @Override
    public List<Lecturer> findPage(int page, int pageSize) {
        String hql = "FROM Lecturer t WHERE t.deleted = false ORDER BY t.ho, t.ten";
        int offset = (page - 1) * pageSize;
        return getSession().createQuery(hql, Lecturer.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .list();
    }

    public List<Lecturer> findPage(int page, int pageSize, String keyword) {
        String hql = "FROM Lecturer t WHERE t.deleted = false";
        String trimmedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        boolean hasKeyword = !trimmedKeyword.isEmpty();
        if (hasKeyword) {
            hql += " AND (lower(t.maGV) LIKE :keyword OR lower(t.ho) LIKE :keyword OR lower(t.ten) LIKE :keyword)";
        }
        hql += " ORDER BY t.ho, t.ten";

        Query<Lecturer> query = getSession().createQuery(hql, Lecturer.class);
        if (hasKeyword) {
            query.setParameter("keyword", "%" + trimmedKeyword + "%");
        }

        int offset = (page - 1) * pageSize;
        return query.setFirstResult(offset).setMaxResults(pageSize).list();
    }

    @Override
    public long countAll() {
        String hql = "SELECT COUNT(*) FROM Lecturer t WHERE t.deleted = false";
        return getSession().createQuery(hql, Long.class).uniqueResult();
    }

    public long countAll(String keyword) {
        String hql = "SELECT COUNT(*) FROM Lecturer t WHERE t.deleted = false";
        String trimmedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        boolean hasKeyword = !trimmedKeyword.isEmpty();
        if (hasKeyword) {
            hql += " AND (lower(t.maGV) LIKE :keyword OR lower(t.ho) LIKE :keyword OR lower(t.ten) LIKE :keyword)";
        }

        Query<Long> query = getSession().createQuery(hql, Long.class);
        if (hasKeyword) {
            query.setParameter("keyword", "%" + trimmedKeyword + "%");
        }
        return query.uniqueResult();
    }

    public boolean existsById(String maGV) {
        return findById(maGV) != null;
    }
}
