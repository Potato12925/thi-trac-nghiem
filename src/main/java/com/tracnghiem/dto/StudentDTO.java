package com.tracnghiem.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class StudentDTO {

	@NotBlank(message = "Student ID is required")
	@Size(max = 8, message = "Student ID must contain exactly 8 characters")
	@Pattern(regexp = "^[A-Z0-9]+$", message = "Student ID can only contain uppercase letters and numbers")
	private String studentId;

	@NotBlank(message = "Last name is required")
	@Size(max = 50, message = "Last name must not exceed 50 characters")
	private String lastName;

	@NotBlank(message = "First name is required")
	@Size(max = 10, message = "First name must not exceed 10 characters")
	private String firstName;

	@NotNull(message = "Birth date is required")
	@Past(message = "Birth date must be in the past")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	@NotBlank(message = "Address is required")
	@Size(max = 100, message = "Address must not exceed 100 characters")
	private String address;

	@NotBlank(message = "Class ID is required")
	@Size(max = 15, message = "Class ID must not exceed 15 characters")
	private String classId;

	public StudentDTO() {
		super();
	}

	public StudentDTO(
			@NotBlank(message = "Student ID is required") @Size(min = 8, max = 8, message = "Student ID must contain exactly 8 characters") @Pattern(regexp = "^[A-Z0-9]+$", message = "Student ID can only contain uppercase letters and numbers") String studentId,
			@NotBlank(message = "Last name is required") @Size(max = 50, message = "Last name must not exceed 50 characters") String lastName,
			@NotBlank(message = "First name is required") @Size(max = 10, message = "First name must not exceed 10 characters") String firstName,
			@NotNull(message = "Birth date is required") @Past(message = "Birth date must be in the past") Date birthDate,
			@NotBlank(message = "Address is required") @Size(max = 100, message = "Address must not exceed 100 characters") String address,
			@NotBlank(message = "Class ID is required") @Size(max = 15, message = "Class ID must not exceed 15 characters") String classId) {
		super();
		this.studentId = studentId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
		this.address = address;
		this.classId = classId;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
}