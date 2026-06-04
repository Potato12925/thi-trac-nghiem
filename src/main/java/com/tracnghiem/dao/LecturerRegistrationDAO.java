package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.LecturerRegistration;

@Repository
public class LecturerRegistrationDAO extends GenericDAO<LecturerRegistration> {
    public List<LecturerRegistration> findByLecturer(String lecturerId) {
        String hql = "FROM LecturerRegistration g WHERE g.lecturer.lecturerId = :lecturerId";
        return getSession().createQuery(hql, LecturerRegistration.class)
                .setParameter("lecturerId", lecturerId)
                .list();
    }

    public List<LecturerRegistration> findByClass(String classId) {
        String hql = "FROM LecturerRegistration g WHERE g.id.classId = :classId";
        return getSession().createQuery(hql, LecturerRegistration.class)
                .setParameter("classId", classId)
                .list();
    }

}