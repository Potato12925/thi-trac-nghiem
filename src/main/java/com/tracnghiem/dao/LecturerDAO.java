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
}
