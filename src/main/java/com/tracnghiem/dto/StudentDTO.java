package com.tracnghiem.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class StudentDTO {

	@NotBlank(message = "Mã sinh viên không được để trống")
	private String studentId;

	@NotBlank(message = "Họ không được để trống")
	private String lastName;

	@NotBlank(message = "Tên không được để trống")
	private String firstName;

	@NotNull(message = "Ngày sinh không được để trống")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	@NotBlank(message = "Địa chỉ không được để trống")
	private String address;

	@NotBlank(message = "Mã lớp không được để trống")
	private String classId;

	public StudentDTO() {
		super();
	}

	public StudentDTO(@NotBlank(message = "Mã sinh viên không được để trống") String studentId,
			@NotBlank(message = "Họ không được để trống") String lastName,
			@NotBlank(message = "Tên không được để trống") String firstName,
			@NotNull(message = "Ngày sinh không được để trống") Date birthDate,
			@NotBlank(message = "Địa chỉ không được để trống") String address,
			@NotBlank(message = "Mã lớp không được để trống") String classId) {
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