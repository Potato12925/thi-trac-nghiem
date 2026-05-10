package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.TaiKhoan;

@Repository
public class TaiKhoanDAO extends GenericDAO<TaiKhoan> {

    public TaiKhoan findByUsername(String username) {

        String hql = "FROM TaiKhoan t WHERE t.username = :username";

        return getSession()
                .createQuery(hql, TaiKhoan.class)
                .setParameter("username", username)
                .uniqueResult();
    }
}
