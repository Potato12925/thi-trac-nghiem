package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MonHoc")
public class Subject {

	@Id
	@Column(name = "MAMH", length = 5)

	@NotBlank(message = "Không được để trống mã môn học")

	@Size(max = 5, message = "Mã môn học tối đa 5 ký tự")

	@Pattern(
		regexp = "^[A-Z0-9]+$",
		message = "Mã môn học chỉ được chứa chữ in hoa và số"
	)

	private String subjectId;

	@Column(name = "TENMH", nullable = false, unique = true)

	@NotBlank(message = "Không được để trống tên môn học")

	@Size(max = 50, message = "Tên môn học chỉ được chứa tối đa 50 ký tự")

	private String subjectName;

	@Column(name = "SOTIET_LT", nullable = false)

	@Min(value = 0, message = "Số tiết lý thuyết phải lớn hơn 0")

	private int theoryLessons;

	@Column(name = "SOTIET_TH", nullable = false)

	@Min(value = 0, message = "Số tiết thực hành phải lớn hơn 0")

	private int practiceLessons;

	public Subject() {
		super();
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getTheoryLessons() {
		return theoryLessons;
	}

	public void setTheoryLessons(int theoryLessons) {
		this.theoryLessons = theoryLessons;
	}

	public int getPracticeLessons() {
		return practiceLessons;
	}

	public void setPracticeLessons(int practiceLessons) {
		this.practiceLessons = practiceLessons;
	}
}