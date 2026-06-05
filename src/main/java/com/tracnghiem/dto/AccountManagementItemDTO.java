package com.tracnghiem.dto;

public class AccountManagementItemDTO {

	private String username;

	private String role;

	private String displayName;

	private String email;

	private String accountType;

	private boolean canResetPassword;

	private boolean canUpdateRole;

	private boolean canDelete;

	private boolean linkedToLecturer;

	private boolean linkedToStudent;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public boolean isCanResetPassword() {
		return canResetPassword;
	}

	public void setCanResetPassword(boolean canResetPassword) {
		this.canResetPassword = canResetPassword;
	}

	public boolean isCanUpdateRole() {
		return canUpdateRole;
	}

	public void setCanUpdateRole(boolean canUpdateRole) {
		this.canUpdateRole = canUpdateRole;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public boolean isLinkedToLecturer() {
		return linkedToLecturer;
	}

	public void setLinkedToLecturer(boolean linkedToLecturer) {
		this.linkedToLecturer = linkedToLecturer;
	}

	public boolean isLinkedToStudent() {
		return linkedToStudent;
	}

	public void setLinkedToStudent(boolean linkedToStudent) {
		this.linkedToStudent = linkedToStudent;
	}
}
