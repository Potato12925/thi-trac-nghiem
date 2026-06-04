package com.tracnghiem.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.PasswordResetOtp;

@Repository
public class PasswordResetOtpDAO extends GenericDAO<PasswordResetOtp> {

	public void invalidateActiveOtps(String accountId, LocalDateTime usedAt) {
		String hql = "UPDATE PasswordResetOtp p "
				+ "SET p.used = true, p.usedAt = :usedAt "
				+ "WHERE p.accountId = :accountId AND p.used = false";

		getSession()
				.createQuery(hql)
				.setParameter("usedAt", usedAt)
				.setParameter("accountId", accountId)
				.executeUpdate();
	}

	public PasswordResetOtp findLatestOtp(String accountId, String otpCode) {
		String hql = "FROM PasswordResetOtp p "
				+ "WHERE p.accountId = :accountId AND p.otpCode = :otpCode "
				+ "ORDER BY p.createdAt DESC";

		List<PasswordResetOtp> otps = getSession()
				.createQuery(hql, PasswordResetOtp.class)
				.setParameter("accountId", accountId)
				.setParameter("otpCode", otpCode)
				.setMaxResults(1)
				.list();

		return otps.isEmpty() ? null : otps.get(0);
	}
}
