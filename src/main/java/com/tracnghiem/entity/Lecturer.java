package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GiaoVien")
public class Lecturer {

	@Id
	@Column(name = "MAGV", length = 8)
	private String lecturerId;

	@Column(name = "HO")
	private String lastName;

	@Column(name = "TEN")
	private String firstName;

	@Column(name = "SODTLL")
	private String phoneNumber;

	@Column(name = "DIACHI")
	private String address;

	public Lecturer() {
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

	@Override
	public String toString() {
		return "Lecturer{" + "lecturerId='" + lecturerId + '\'' + ", lastName='" + lastName + '\'' + ", firstName='"
				+ firstName + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", address='" + address + '\'' + '}';
	}
}