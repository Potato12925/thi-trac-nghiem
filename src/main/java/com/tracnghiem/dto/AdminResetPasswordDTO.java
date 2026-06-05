package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdminResetPasswordDTO {

	@NotBlank(message = "Mã tài khoản không được để trống")
	@Size(max = 8, message = "Mã tài khoản tối đa 8 ký tự")
	@Pattern(regexp = "^[A-Z0-9]+$", message = "Mã tài khoản không hợp lệ")
	private String username;

	@NotBlank(message = "Mật khẩu mới không được để trống")
	@Size(min = 6, max = 255, message = "Mật khẩu mới phải có ít nhất 6 ký tự")
	private String newPassword;

	@NotBlank(message = "Vui lòng xác nhận mật khẩu")
	@Size(min = 6, max = 255, message = "Mật khẩu xác nhận phải có ít nhất 6 ký tự")
	private String confirmPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
