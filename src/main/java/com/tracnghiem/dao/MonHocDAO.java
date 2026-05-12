package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.MonHoc;

@Repository
public class MonHocDAO extends GenericDAO<MonHoc> {

    public List<MonHoc> findByKeyword(String keyword) {
        String hql = "FROM MonHoc m WHERE m.maMH LIKE :keyword OR m.tenMH LIKE :keyword";
        return getSession().createQuery(hql, MonHoc.class)
                .setParameter("keyword", '%' + keyword + '%')
                .list();
    }
}