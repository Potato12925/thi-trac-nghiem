package com.tracnghiem.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Exam;
@Repository
public class ExamDAO extends GenericDAO<Exam> {

    public Exam findExam(String studentId, String subjectId, Short tryNumber) {
        Session session = getSession();
        Query<Exam> query = session.createQuery(
                "FROM Exam e WHERE e.student.studentId = :studentId " +
                        "AND e.subject.subjectId = :subjectId " +
                        "AND e.tryNumber = :tryNumber",
                Exam.class);
        query.setParameter("studentId", studentId);
        query.setParameter("subjectId", subjectId);
        query.setParameter("tryNumber", tryNumber);

        return query.uniqueResult();
    }

    public List<Exam> findByStudent(String studentId) {
        Session session = getSession();
        Query<Exam> query = session.createQuery(
                "FROM Exam e WHERE e.student.studentId = :studentId ORDER BY e.examDate DESC", Exam.class);
        query.setParameter("studentId", studentId);
        return query.list();
    }

    public List<Exam> findClassExams(String classId, String subjectId, Short tryNumber) {
        Session session = getSession();
        Query<Exam> query = session.createQuery(
                "FROM Exam e WHERE e.classId = :classId " +
                        "AND e.subject.subjectId = :subjectId " +
                        "AND e.tryNumber = :tryNumber",
                Exam.class);
        query.setParameter("classId", classId);
        query.setParameter("subjectId", subjectId);
        query.setParameter("tryNumber", tryNumber);
        return query.list();
    }

    public long countDistinctCompletedSubjectsByStudent(String studentId) {
        String hql = "SELECT COUNT(DISTINCT e.subject.subjectId) "
                + "FROM Exam e "
                + "WHERE function('ltrim', function('rtrim', e.student.studentId)) = :studentId "
                + "AND e.score IS NOT NULL";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("studentId", studentId)
                .uniqueResult();

        return count == null ? 0L : count.longValue();
    }

    public Double findAverageScoreByStudent(String studentId) {
        String hql = "SELECT AVG(e.score) "
                + "FROM Exam e "
                + "WHERE function('ltrim', function('rtrim', e.student.studentId)) = :studentId "
                + "AND e.score IS NOT NULL";

        return getSession()
                .createQuery(hql, Double.class)
                .setParameter("studentId", studentId)
                .uniqueResult();
    }

    public Exam findLatestCompletedExamByStudent(String studentId) {
        String hql = "FROM Exam e "
                + "JOIN FETCH e.subject s "
                + "WHERE function('ltrim', function('rtrim', e.student.studentId)) = :studentId "
                + "AND e.score IS NOT NULL "
                + "ORDER BY COALESCE(e.endTime, e.examDate) DESC, e.id DESC";

        List<Exam> exams = getSession()
                .createQuery(hql, Exam.class)
                .setParameter("studentId", studentId)
                .setMaxResults(1)
                .list();

        return exams.isEmpty() ? null : exams.get(0);
    }
}
