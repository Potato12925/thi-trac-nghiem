package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAIKHOAN")
public class Account {

	@Id
	@Column(name = "MA")
	private String id;

	@Column(name = "PASSWORD_HASH", nullable = false)
	private String passwordHash;

	@Column(name = "ROLE", nullable = false)
	private String role;

	public Account() {
	}

	public Account(String id, String passwordHash, String role) {
		super();
		this.id = id;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}