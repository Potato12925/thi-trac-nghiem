package com.tracnghiem.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Lecturer;

@Repository
public class LecturerDAO extends GenericDAO<Lecturer> {

    public List<Lecturer> findByKeyword(String keyword) {

        String hql = "FROM Lecturer t WHERE t.deleted = false AND (lower(t.lecturerId) LIKE :keyword OR lower(t.lastName) LIKE :keyword OR lower(t.firstName) LIKE :keyword)";

        return getSession()
                .createQuery(hql, Lecturer.class)
                .setParameter("keyword", "%" + keyword.toLowerCase() + "%")
                .list();
    }

    @Override
    public List<Lecturer> findAll() {
        String hql = "FROM Lecturer t WHERE t.deleted = false ORDER BY t.lastName, t.firstName";
        return getSession().createQuery(hql, Lecturer.class).list();
    }

    @Override
    public List<Lecturer> findPage(int page, int pageSize) {
        String hql = "FROM Lecturer t WHERE t.deleted = false ORDER BY t.lastName, t.firstName";
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
            hql += " AND (lower(t.lecturerId) LIKE :keyword OR lower(t.lastName) LIKE :keyword OR lower(t.firstName) LIKE :keyword)";
        }
        hql += " ORDER BY t.lastName, t.firstName";

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
            hql += " AND (lower(t.lecturerId) LIKE :keyword OR lower(t.lastName) LIKE :keyword OR lower(t.firstName) LIKE :keyword)";
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

    public Lecturer findNormalizedById(String lecturerId) {
        String hql = "FROM Lecturer l "
                + "WHERE function('ltrim', function('rtrim', l.lecturerId)) = :lecturerId";

        List<Lecturer> lecturers = getSession()
                .createQuery(hql, Lecturer.class)
                .setParameter("lecturerId", lecturerId)
                .setMaxResults(1)
                .list();

        return lecturers.isEmpty() ? null : lecturers.get(0);
    }

    public List<Lecturer> findActiveLecturersWithoutAccount() {
        String hql = "FROM Lecturer l "
                + "WHERE l.deleted = false "
                + "AND NOT EXISTS ("
                + "    SELECT 1 FROM Account a "
                + "    WHERE function('ltrim', function('rtrim', a.username)) = "
                + "          function('ltrim', function('rtrim', l.lecturerId))"
                + ") "
                + "ORDER BY l.lastName, l.firstName";

        return getSession().createQuery(hql, Lecturer.class).list();
    }

    public Lecturer findDashboardProfileByLecturerId(String lecturerId) {
        String hql = "FROM Lecturer l "
                + "WHERE l.deleted = false "
                + "AND function('ltrim', function('rtrim', l.lecturerId)) = :lecturerId";

        List<Lecturer> lecturers = getSession()
                .createQuery(hql, Lecturer.class)
                .setParameter("lecturerId", lecturerId)
                .list();

        return lecturers.isEmpty() ? null : lecturers.get(0);
    }

    public String findEmailByLecturerId(String lecturerId) {
        String hql = "SELECT l.email FROM Lecturer l WHERE l.lecturerId = :lecturerId";

        List<String> emails = getSession()
                .createQuery(hql, String.class)
                .setParameter("lecturerId", lecturerId)
                .list();

        return emails.isEmpty() ? null : emails.get(0);
    }

    public boolean existsByEmail(String email, String excludedLecturerId) {
        String hql = "SELECT COUNT(l.lecturerId) FROM Lecturer l "
                + "WHERE lower(l.email) = :email "
                + "AND (:excludedLecturerId IS NULL OR l.lecturerId <> :excludedLecturerId)";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("email", email)
                .setParameter("excludedLecturerId", excludedLecturerId)
                .uniqueResult();

        return count != null && count > 0;
    }
}

