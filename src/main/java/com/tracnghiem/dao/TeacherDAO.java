package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Teacher;

@Repository
public class TeacherDAO extends GenericDAO<Teacher> {

    public List<Teacher> findByKeyword(String keyword) {
        String hql = "FROM GiaoVien g WHERE g.maGV LIKE :keyword OR g.ho LIKE :keyword OR g.ten LIKE :keyword";
        return getSession().createQuery(hql, Teacher.class)
                .setParameter("keyword", '%' + keyword + '%')
                .list();
    }
} 