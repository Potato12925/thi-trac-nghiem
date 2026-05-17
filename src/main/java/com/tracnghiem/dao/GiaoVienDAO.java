package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.GiaoVien;

@Repository
public class GiaoVienDAO extends GenericDAO<GiaoVien> {

    public List<GiaoVien> findByKeyword(String keyword) {
        String hql = "FROM GiaoVien g WHERE g.maGV LIKE :keyword OR g.ho LIKE :keyword OR g.ten LIKE :keyword";
        return getSession().createQuery(hql, GiaoVien.class)
                .setParameter("keyword", '%' + keyword + '%')
                .list();
    }
} 