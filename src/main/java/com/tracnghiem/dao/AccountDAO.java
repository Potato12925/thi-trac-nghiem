package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Account;

@Repository
public class AccountDAO extends GenericDAO<Account> {

    public Account findByMa(String ma) {

        String hql = "FROM TaiKhoan t WHERE t.ma = :ma";

        return getSession()
                .createQuery(hql, Account.class)
                .setParameter("ma", ma)
                .uniqueResult();
    }
}