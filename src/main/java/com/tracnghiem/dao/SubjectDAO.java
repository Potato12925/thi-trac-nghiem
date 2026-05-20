package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Subject;

/**
 * DAO layer for Subject (MonHoc) entity.
 * Handles all database operations for MonHoc table.
 */
@Repository
public class SubjectDAO extends GenericDAO<Subject> {
	public void themMonHoc(Subject monHoc) {
		create(monHoc);
	}

    /**
     * Searches subjects by keyword.
     * Searches in both maMH (code) and tenMH (name) fields.
     * 
     * @param keyword Search keyword
     * @return List of matching subjects
     */
    public List<Subject> findByKeyword(String keyword) {
        String hql = "FROM MonHoc m WHERE m.maMH LIKE :keyword OR m.tenMH LIKE :keyword";
        return getSession().createQuery(hql, Subject.class)
                .setParameter("keyword", '%' + keyword + '%')
                .list();
    }

    // ==================== FOREIGN KEY CONSTRAINT CHECKS ====================

    /**
     * Counts how many GiaoVienDangKy (Subject registrations) exist for a subject.
     * Used to validate if subject can be deleted.
     * 
     * @param maMH Subject code
     * @return Count of registrations
     */
    public long countGiaoVienDangKyByMaMH(String maMH) {
        String hql = "SELECT COUNT(g) FROM GiaoVienDangKy g WHERE g.id.maMH = :maMH";
        Long count = getSession().createQuery(hql, Long.class)
                .setParameter("maMH", maMH)
                .uniqueResult();
        return count != null ? count : 0L;
    }

    /**
     * Counts how many BoDe (Questions) exist for a subject.
     * Used to validate if subject can be deleted.
     * 
     * @param maMH Subject code
     * @return Count of questions
     */
    public long countBoDeByMaMH(String maMH) {
        String hql = "SELECT COUNT(b) FROM BoDe b WHERE b.monHoc.maMH = :maMH";
        Long count = getSession().createQuery(hql, Long.class)
                .setParameter("maMH", maMH)
                .uniqueResult();
        return count != null ? count : 0L;
    }

    /**
     * Counts how many BaiThi (Exams) exist for a subject.
     * Used to validate if subject can be deleted.
     * 
     * @param maMH Subject code
     * @return Count of exams
     */
    public long countBaiThiByMaMH(String maMH) {
        String hql = "SELECT COUNT(b) FROM BaiThi b WHERE b.monHoc.maMH = :maMH";
        Long count = getSession().createQuery(hql, Long.class)
                .setParameter("maMH", maMH)
                .uniqueResult();
        return count != null ? count : 0L;
    }

    /**
     * Counts how many BangDiem (Grades) exist for a subject.
     * Used to validate if subject can be deleted.
     * 
     * @param maMH Subject code
     * @return Count of grade records
     */
    public long countBangDiemByMaMH(String maMH) {
        String hql = "SELECT COUNT(b) FROM BangDiem b WHERE b.monHoc.maMH = :maMH";
        Long count = getSession().createQuery(hql, Long.class)
                .setParameter("maMH", maMH)
                .uniqueResult();
        return count != null ? count : 0L;
    }

    /**
     * Checks if a subject exists by code.
     * 
     * @param maMH Subject code
     * @return true if exists, false otherwise
     */
    public boolean exists(String maMH) {
        Subject subject = findById(maMH);
        return subject != null;
    }
}