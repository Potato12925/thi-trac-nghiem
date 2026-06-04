package com.tracnghiem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SINHVIEN")
public class Student {

	@Id
	@Column(name = "MASV", length = 8)
	private String studentId;

	@Column(name = "HO", length = 40)
	private String lastName;

	@Column(name = "TEN", length = 10)
	private String firstName;

	@Temporal(TemporalType.DATE)
	@Column(name = "NGAYSINH")
	private Date birthDate;

	@Column(name = "DIACHI", length = 100)
	private String address;

	@Column(name = "EMAIL", length = 150)
	private String email;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MALOP", nullable = false)
	private Classroom classRoom;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MASV", referencedColumnName = "MA", insertable = false, updatable = false)
	private Account account;

	public Student() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Classroom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(Classroom classRoom) {
		this.classRoom = classRoom;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
