package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Account;

@Repository
public class AccountDAO extends GenericDAO<Account> {

    @Override
    public long countAll() {
        String hql = "SELECT COUNT(a) FROM Account a";
        Long count = getSession().createQuery(hql, Long.class).uniqueResult();
        return count == null ? 0L : count.longValue();
    }

	public Account findByUsername(String username) {
		String hql = "FROM Account a WHERE a.username = :username";

		List<Account> accounts = getSession()
				.createQuery(hql, Account.class)
				.setParameter("username", username)
				.list();

		return accounts.isEmpty() ? null : accounts.get(0);
	}
}
