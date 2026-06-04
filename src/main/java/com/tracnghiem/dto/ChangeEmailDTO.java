package com.tracnghiem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangeEmailDTO {

	@NotBlank(message = "Email mới không được để trống")
	@Email(message = "Email không hợp lệ")
	@Size(max = 150, message = "Email không được vượt quá 150 ký tự")
	private String newEmail;

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
}
