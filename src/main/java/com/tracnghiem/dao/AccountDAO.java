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

	public boolean existsByUsername(String username) {
		String hql = "SELECT COUNT(a.username) FROM Account a WHERE a.username = :username";

		Long count = getSession()
				.createQuery(hql, Long.class)
				.setParameter("username", username)
				.uniqueResult();

		return count != null && count > 0;
	}

	public List<Account> findByFilter(String role, String keyword) {
		String normalizedRole = normalize(role);
		String normalizedKeyword = normalize(keyword);

		String hql = "FROM Account a WHERE 1 = 1";

		if (normalizedRole != null) {
			hql += " AND a.role = :role";
		}

		if (normalizedKeyword != null) {
			hql += " AND lower(function('ltrim', function('rtrim', a.username))) LIKE :keyword";
		}

		hql += " ORDER BY a.role ASC, a.username ASC";

		org.hibernate.query.Query<Account> query = getSession().createQuery(hql, Account.class);

		if (normalizedRole != null) {
			query.setParameter("role", normalizedRole);
		}

		if (normalizedKeyword != null) {
			query.setParameter("keyword", "%" + normalizedKeyword.toLowerCase() + "%");
		}

		return query.list();
	}

	private String normalize(String value) {
		if (value == null) {
			return null;
		}

		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}
