package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Lop;

@Repository
public class LopDAO extends GenericDAO<Lop> {

    public List<Lop> findByKeyword(String keyword) {
        String hql = "FROM Lop l WHERE l.maLop LIKE :keyword OR l.tenLop LIKE :keyword";
        return getSession().createQuery(hql, Lop.class)
                .setParameter("keyword", '%' + keyword + '%')
                .list();
    }
} 