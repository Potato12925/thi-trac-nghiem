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

    public List<LecturerRegistration> findByClass(String classId) {
        String hql = "FROM LecturerRegistration g WHERE g.id.classId = :classId";
        return getSession().createQuery(hql, LecturerRegistration.class)
                .setParameter("classId", classId)
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

    public long countByLecturer(String lecturerId) {
        String hql = "SELECT COUNT(g) FROM LecturerRegistration g "
                + "WHERE function('ltrim', function('rtrim', g.lecturer.lecturerId)) = :lecturerId";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("lecturerId", lecturerId)
                .uniqueResult();

        return count == null ? 0L : count.longValue();
    }

    public long countDistinctSubjectsByLecturer(String lecturerId) {
        String hql = "SELECT COUNT(DISTINCT g.subject.subjectId) FROM LecturerRegistration g "
                + "WHERE function('ltrim', function('rtrim', g.lecturer.lecturerId)) = :lecturerId";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("lecturerId", lecturerId)
                .uniqueResult();

        return count == null ? 0L : count.longValue();
    }

    public long countDistinctClassesByLecturer(String lecturerId) {
        String hql = "SELECT COUNT(DISTINCT g.classRoom.classId) FROM LecturerRegistration g "
                + "WHERE function('ltrim', function('rtrim', g.lecturer.lecturerId)) = :lecturerId";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("lecturerId", lecturerId)
                .uniqueResult();

        return count == null ? 0L : count.longValue();
    }

    public List<LecturerRegistration> findRecentByLecturer(String lecturerId, int limit) {
        String hql = "SELECT g FROM LecturerRegistration g "
                + "JOIN FETCH g.subject s "
                + "JOIN FETCH g.classRoom c "
                + "WHERE function('ltrim', function('rtrim', g.lecturer.lecturerId)) = :lecturerId "
                + "ORDER BY g.examDate DESC, g.id.tryNumber DESC";

        return getSession()
                .createQuery(hql, LecturerRegistration.class)
                .setParameter("lecturerId", lecturerId)
                .setMaxResults(limit)
                .list();
    }

    public LecturerRegistration findLatestByLecturer(String lecturerId) {
        List<LecturerRegistration> registrations = findRecentByLecturer(lecturerId, 1);
        return registrations.isEmpty() ? null : registrations.get(0);
    }

    public List<Object[]> findSubjectClassSummaryByLecturer(String lecturerId) {
        String hql = "SELECT c.classId, c.className, s.subjectId, s.subjectName, MAX(g.examDate), COUNT(g) "
                + "FROM LecturerRegistration g "
                + "JOIN g.classRoom c "
                + "JOIN g.subject s "
                + "WHERE function('ltrim', function('rtrim', g.lecturer.lecturerId)) = :lecturerId "
                + "GROUP BY c.classId, c.className, s.subjectId, s.subjectName "
                + "ORDER BY MAX(g.examDate) DESC, c.classId ASC, s.subjectId ASC";

        return getSession()
                .createQuery(hql, Object[].class)
                .setParameter("lecturerId", lecturerId)
                .list();
    }
}
