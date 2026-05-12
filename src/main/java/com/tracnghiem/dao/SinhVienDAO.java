package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.SinhVien;

@Repository
public class SinhVienDAO extends GenericDAO<SinhVien> {

    public List<SinhVien> findByKeyword(String keyword) {
        String hql = "FROM SinhVien s WHERE s.maSV LIKE :keyword OR s.ho LIKE :keyword OR s.ten LIKE :keyword";
        return getSession().createQuery(hql, SinhVien.class)
                .setParameter("keyword", '%' + keyword + '%')
                .list();
    }

    public List<SinhVien> findByLop(String maLop) {
        String hql = "FROM SinhVien s WHERE s.lop.maLop = :maLop";
        return getSession().createQuery(hql, SinhVien.class)
                .setParameter("maLop", maLop)
                .list();
    }

    public List<SinhVien> findByLopAndKeyword(String maLop, String keyword) {
        String hql = "FROM SinhVien s WHERE s.lop.maLop = :maLop AND (s.maSV LIKE :keyword OR s.ho LIKE :keyword OR s.ten LIKE :keyword)";
        return getSession().createQuery(hql, SinhVien.class)
                .setParameter("maLop", maLop)
                .setParameter("keyword", '%' + keyword + '%')
                .list();
    }
} 