package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;

public class ClassroomDTO {

	@NotBlank(message = "Mã lớp không được để trống")
	private String classId;

	@NotBlank(message = "Tên lớp không được để trống")
	private String className;

	public ClassroomDTO() {
		super();
	}

	public ClassroomDTO(
			@NotBlank(message = "Mã lớp không được để trống") String classId,
			@NotBlank(message = "Tên lớp không được để trống") String className) {
		super();
		this.classId = classId;
		this.className = className;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}