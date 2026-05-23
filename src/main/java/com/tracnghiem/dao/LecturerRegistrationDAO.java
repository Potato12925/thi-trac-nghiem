package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.LecturerRegistration;

@Repository
public class LecturerRegistrationDAO extends GenericDAO<LecturerRegistration> {
    public java.util.List<LecturerRegistration> findByLecturer(String lecturerId) {
        String hql = "FROM LecturerRegistration g WHERE g.lecturer.lecturerId = :lecturerId";
        return getSession().createQuery(hql, LecturerRegistration.class)
                .setParameter("lecturerId", lecturerId)
                .list();
    }

}