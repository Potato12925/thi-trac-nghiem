package com.tracnghiem.dto;

public class SubjectActionDTO {
	private String type; // ADD, UPDATE, DELETE
	private String subjectId;
	private String subjectName;

	public SubjectActionDTO() {
	}

	public SubjectActionDTO(String type, String subjectId, String subjectName) {
		this.type = type;
		this.subjectId = subjectId;
		this.subjectName = subjectName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}
