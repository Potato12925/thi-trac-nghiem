package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.BoDe;

@Repository
public class BoDeDAO extends GenericDAO<BoDe> {

    public List<BoDe> findByKeyword(String keyword) {
        String hql = "FROM BoDe b WHERE b.noiDung LIKE :keyword OR b.monHoc.maMH LIKE :keyword OR b.dapAn LIKE :keyword";
        return getSession().createQuery(hql, BoDe.class)
                .setParameter("keyword", '%' + keyword + '%')
                .list();
    }
} 