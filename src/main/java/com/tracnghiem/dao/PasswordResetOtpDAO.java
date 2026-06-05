package com.tracnghiem.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.PasswordResetOtp;

@Repository
public class PasswordResetOtpDAO extends GenericDAO<PasswordResetOtp> {

	public void invalidateActiveOtps(String accountId, String purpose, LocalDateTime usedAt) {
		String hql = "UPDATE PasswordResetOtp p "
				+ "SET p.used = true, p.usedAt = :usedAt "
				+ "WHERE p.accountId = :accountId AND p.purpose = :purpose AND p.used = false";

		getSession()
				.createQuery(hql)
				.setParameter("usedAt", usedAt)
				.setParameter("accountId", accountId)
				.setParameter("purpose", purpose)
				.executeUpdate();
	}

	public PasswordResetOtp findLatestOtp(String accountId, String otpCode, String purpose, String email) {
		String hql = "FROM PasswordResetOtp p "
				+ "WHERE p.accountId = :accountId AND p.otpCode = :otpCode AND p.purpose = :purpose "
				+ "ORDER BY p.createdAt DESC";

		Query<PasswordResetOtp> query = getSession()
				.createQuery(email == null ? hql : hql.replace(" ORDER BY", " AND p.email = :email ORDER BY"),
						PasswordResetOtp.class)
				.setParameter("accountId", accountId)
				.setParameter("otpCode", otpCode)
				.setParameter("purpose", purpose)
				.setMaxResults(1);

		if (email != null) {
			query.setParameter("email", email);
		}

		List<PasswordResetOtp> otps = query.list();

		return otps.isEmpty() ? null : otps.get(0);
	}

	public void deleteByAccountId(String accountId) {
		String hql = "DELETE FROM PasswordResetOtp p WHERE p.accountId = :accountId";

		getSession()
				.createQuery(hql)
				.setParameter("accountId", accountId)
				.executeUpdate();
	}
}
