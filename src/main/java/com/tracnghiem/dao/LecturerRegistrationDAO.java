package com.tracnghiem.dao;

import java.util.Date;
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

    public List<LecturerRegistration> findUpcomingExamsForStudent(String studentId, String classId, Date now) {
        String hql = "SELECT g FROM LecturerRegistration g "
                + "JOIN FETCH g.subject s "
                + "WHERE function('ltrim', function('rtrim', g.classRoom.classId)) = :classId "
                + "AND g.examDate >= :now "
                + "AND NOT EXISTS ("
                + "    SELECT 1 FROM Exam e "
                + "    WHERE function('ltrim', function('rtrim', e.student.studentId)) = :studentId "
                + "    AND e.subject = g.subject "
                + "    AND e.tryNumber = g.id.tryNumber"
                + ") "
                + "ORDER BY g.examDate ASC, g.id.tryNumber ASC";

        return getSession()
                .createQuery(hql, LecturerRegistration.class)
                .setParameter("classId", classId)
                .setParameter("studentId", studentId)
                .setParameter("now", now)
                .list();
    }
}
