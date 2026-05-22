package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.TeacherRegistration;

@Repository
public class TeacherRegistrationDAO extends GenericDAO<TeacherRegistration> {
    public java.util.List<TeacherRegistration> findByTeacher(String maGv) {
        String hql = "FROM TeacherRegistration g WHERE g.giaoVien.maGV = :maGv";
        return getSession().createQuery(hql, TeacherRegistration.class)
                .setParameter("maGv", maGv)
                .list();
    }

}