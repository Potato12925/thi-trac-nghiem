package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Subject;

/**
 * DAO layer for Subject entity.
 * Handles all database operations for Subject table.
 */
@Repository
public class SubjectDAO extends GenericDAO<Subject> {

    public void addSubject(Subject subject) {
        create(subject);
    }

    /**
     * Searches subjects by keyword.
     * Searches in both subjectId and subjectName fields.
     *
     * @param keyword Search keyword
     * @return List of matching subjects
     */
    public List<Subject> findByKeyword(String keyword) {

        String hql = " FROM Subject s WHERE s.subjectId LIKE :keyword OR s.subjectName LIKE :keyword ";

        return getSession()
                .createQuery(hql, Subject.class)
                .setParameter("keyword", '%' + keyword + '%')
                .list();
    }

    // ==================== FOREIGN KEY CONSTRAINT CHECKS ====================

    /**
     * Counts TeacherRegistration records by subject.
     * Used to validate whether subject can be deleted.
     *
     * @param subjectId Subject ID
     * @return Number of registrations
     */
    public long countTeacherRegistrationsBySubjectId(String subjectId) {

        String hql = " SELECT COUNT(tr) FROM TeacherRegistration tr WHERE tr.id.subjectId = :subjectId ";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("subjectId", subjectId)
                .uniqueResult();

        return count != null ? count : 0L;
    }

    /**
     * Counts Question records by subject.
     * Used to validate whether subject can be deleted.
     *
     * @param subjectId Subject ID
     * @return Number of questions
     */
    public long countQuestionsBySubjectId(String subjectId) {

        String hql = " SELECT COUNT(q) FROM Question q WHERE q.subject.subjectId = :subjectId ";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("subjectId", subjectId)
                .uniqueResult();

        return count != null ? count : 0L;
    }

    /**
     * Counts Exam records by subject.
     * Used to validate whether subject can be deleted.
     *
     * @param subjectId Subject ID
     * @return Number of exams
     */
    public long countExamsBySubjectId(String subjectId) {

        String hql = " SELECT COUNT(e) FROM Exam e WHERE e.subject.subjectId = :subjectId ";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("subjectId", subjectId)
                .uniqueResult();

        return count != null ? count : 0L;
    }

    /**
     * Counts Score records by subject.
     * Used to validate whether subject can be deleted.
     *
     * @param subjectId Subject ID
     * @return Number of score records
     */
    public long countScoresBySubjectId(String subjectId) {

        String hql = " SELECT COUNT(s) FROM Score s WHERE s.subject.subjectId = :subjectId ";

        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("subjectId", subjectId)
                .uniqueResult();

        return count != null ? count : 0L;
    }

    /**
     * Checks whether subject exists.
     *
     * @param subjectId Subject ID
     * @return true if exists, otherwise false
     */
    public boolean exists(String subjectId) {

        Subject subject = findById(subjectId);

        return subject != null;
    }
}