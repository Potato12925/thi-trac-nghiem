package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Lecturer;

@Repository
public class LecturerDAO extends GenericDAO<Lecturer> {

    public List<Lecturer> findByKeyword(String keyword) {

        String hql =
                "FROM Lecturer t WHERE t.lecturerId LIKE :keyword OR t.lastName LIKE :keyword OR t.firstName LIKE :keyword";

        return getSession()
                .createQuery(hql, Lecturer.class)
                .setParameter("keyword", "%" + keyword + "%")
                .list();
    }

    public boolean existsById(String maGV) {
        return findById(maGV) != null;
    }

    public String findEmailByLecturerId(String lecturerId) {
        String hql = "SELECT l.email FROM Lecturer l WHERE l.lecturerId = :lecturerId";

        List<String> emails = getSession()
                .createQuery(hql, String.class)
                .setParameter("lecturerId", lecturerId)
                .list();

        return emails.isEmpty() ? null : emails.get(0);
    }
}
