package com.tracnghiem.dto;

public class LecturerActionDTO {
	private String type; // ADD, UPDATE, DELETE
	private String lecturerId;
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private String address;
	private String email;

	public LecturerActionDTO() {
	}

	public LecturerActionDTO(String type, String lecturerId, String lastName, String firstName, String phoneNumber,
			String address, String email) {
		this.type = type;
		this.lecturerId = lecturerId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
