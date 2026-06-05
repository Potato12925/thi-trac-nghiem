package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateAccountRoleDTO {

	@NotBlank(message = "Mã tài khoản không được để trống")
	@Size(max = 8, message = "Mã tài khoản tối đa 8 ký tự")
	@Pattern(regexp = "^[A-Z0-9]+$", message = "Mã tài khoản không hợp lệ")
	private String username;

	@NotBlank(message = "Role không được để trống")
	@Pattern(regexp = "^(PGV|GIAOVIEN|SINHVIEN)$", message = "Role không hợp lệ")
	private String role;

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
}
