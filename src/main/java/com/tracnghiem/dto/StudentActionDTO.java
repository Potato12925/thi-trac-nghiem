package com.tracnghiem.dto;

public class StudentActionDTO {
	private String type; // ADD, UPDATE, DELETE
	private String studentId;
	private String lastName;
	private String firstName;
	private String birthDate;
	private String address;
	private String email;
	private String classId;

	public StudentActionDTO() {
	}

	public StudentActionDTO(String type, String studentId, String lastName, String firstName, String birthDate,
			String address, String email, String classId) {
		this.type = type;
		this.studentId = studentId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
		this.address = address;
		this.email = email;
		this.classId = classId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
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

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
}
