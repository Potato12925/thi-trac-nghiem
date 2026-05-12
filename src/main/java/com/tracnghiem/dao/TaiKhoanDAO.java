package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.TaiKhoan;

@Repository
public class TaiKhoanDAO extends GenericDAO<TaiKhoan> {

    public TaiKhoan findByMa(String ma) {

        String hql = "FROM TaiKhoan t WHERE t.ma = :ma";

        return getSession()
                .createQuery(hql, TaiKhoan.class)
                .setParameter("ma", ma)
                .uniqueResult();
    }
}