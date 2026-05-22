package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;

public class LecturerDTO {

	@NotBlank(message = "Mã giáo viên không được để trống")
	private String teacherId;

	@NotBlank(message = "Họ không được để trống")
	private String lastName;

	@NotBlank(message = "Tên không được để trống")
	private String firstName;

	@NotBlank(message = "Số điện thoại không được để trống")
	private String phoneNumber;

	@NotBlank(message = "Địa chỉ không được để trống")
	private String address;

	public LecturerDTO() {
		super();
	}

	public LecturerDTO(
			@NotBlank(message = "Mã giáo viên không được để trống") String teacherId,
			@NotBlank(message = "Họ không được để trống") String lastName,
			@NotBlank(message = "Tên không được để trống") String firstName,
			@NotBlank(message = "Số điện thoại không được để trống") String phoneNumber,
			@NotBlank(message = "Địa chỉ không được để trống") String address) {
		super();
		this.teacherId = teacherId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
