package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateLecturerAccountDTO {

	@NotBlank(message = "Vui lòng chọn giảng viên")
	@Size(max = 8, message = "Mã giảng viên tối đa 8 ký tự")
	@Pattern(regexp = "^\\s*[A-Z0-9]+\\s*$", message = "Mã giảng viên không hợp lệ")
	private String lecturerId;

	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 6, max = 255, message = "Mật khẩu phải có ít nhất 6 ký tự")
	private String password;

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
