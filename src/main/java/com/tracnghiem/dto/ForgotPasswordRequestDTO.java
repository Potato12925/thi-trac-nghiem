package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ForgotPasswordRequestDTO {

	@NotBlank(message = "Tên đăng nhập không được để trống")
	@Size(max = 8, message = "Tên đăng nhập tối đa 8 ký tự")
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
