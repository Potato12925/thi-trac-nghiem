package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LecturerDTO {

	@NotBlank(message = "Lecturer ID is required")
	@Size(max = 8, message = "Lecturer ID must contain exactly 8 characters")
	@Pattern(regexp = "^[A-Z0-9]+$", message = "Lecturer ID can only contain uppercase letters and numbers")
	private String lecturerId;

	@NotBlank(message = "Last name is required")
	@Size(max = 40, message = "Last name must not exceed 40 characters")
	private String lastName;

	@NotBlank(message = "First name is required")
	@Size(max = 10, message = "First name must not exceed 10 characters")
	private String firstName;

	@NotBlank(message = "Phone number is required")
	@Size(max = 15, message = "Phone number must not exceed 15 characters")
	@Pattern(regexp = "^[0-9+\\-\\s]+$", message = "Phone number is invalid")
	private String phoneNumber;

	@NotBlank(message = "Address is required")
	@Size(max = 50, message = "Address must not exceed 50 characters")
	private String address;

	public LecturerDTO() {
	}

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
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