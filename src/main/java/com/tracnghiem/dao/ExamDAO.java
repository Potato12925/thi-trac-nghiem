package com.tracnghiem.dao;

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

    public java.util.List<Exam> findByStudent(String studentId) {
        Session session = getSession();
        Query<Exam> query = session.createQuery(
                "FROM Exam e WHERE e.student.studentId = :studentId ORDER BY e.examDate DESC", Exam.class);
        query.setParameter("studentId", studentId);
        return query.list();
    }

    public java.util.List<Exam> findClassExams(String classId, String subjectId, Short tryNumber) {
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
}