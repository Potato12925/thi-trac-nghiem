package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreatePgvAccountDTO {

	@NotBlank(message = "Mã tài khoản PGV không được để trống")
	@Size(max = 8, message = "Mã tài khoản tối đa 8 ký tự")
	@Pattern(regexp = "^[A-Z0-9]+$", message = "Mã tài khoản chỉ được chứa chữ in hoa và số")
	private String username;

	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 6, max = 255, message = "Mật khẩu phải có ít nhất 6 ký tự")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
