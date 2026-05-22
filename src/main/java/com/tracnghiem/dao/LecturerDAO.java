package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Lecturer;

@Repository
public class LecturerDAO extends GenericDAO<Lecturer> {

    public List<Lecturer> findByKeyword(String keyword) {

        String hql = "FROM Teacher t WHERE t.maGV LIKE :keyword OR t.ho LIKE :keyword OR t.ten LIKE :keyword";

        return getSession()
                .createQuery(hql, Lecturer.class)
                .setParameter("keyword", "%" + keyword + "%")
                .list();
    }

    public boolean existsById(String maGV) {
        return findById(maGV) != null;
    }
}