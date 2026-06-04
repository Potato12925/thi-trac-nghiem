package com.tracnghiem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ConfirmEmailChangeDTO {

	@NotBlank(message = "Email mới không được để trống")
	@Email(message = "Email không hợp lệ")
	@Size(max = 150, message = "Email không được vượt quá 150 ký tự")
	private String newEmail;

	@NotBlank(message = "Mã OTP không được để trống")
	@Pattern(regexp = "^\\d{6}$", message = "Mã OTP phải gồm đúng 6 chữ số")
	private String otpCode;

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}
}
