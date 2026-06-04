package com.tracnghiem.dto;

public class ClassroomActionDTO {
	private String type; // ADD, UPDATE, DELETE
	private String classId;
	private String className;

	public ClassroomActionDTO() {
	}

	public ClassroomActionDTO(String type, String classId, String className) {
		this.type = type;
		this.classId = classId;
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
