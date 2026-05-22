package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SubjectDTO {
	@NotBlank(message = "Không được để trống mã môn học")
	@Size(max = 5, message = "Mã môn học tối đa 5 ký tự")
	@Pattern(regexp = "^[A-Z0-9]+$", message = "Mã môn học chỉ được chứa chữ in hoa và số")
	private String subjectId;

	@NotBlank(message = "Không được để trống tên môn học")
	@Size(max = 50, message = "Tên môn học chỉ được chứa tối đa 50 ký tự")
	private String subjectName;

	public SubjectDTO() {
		super();
	}

	public SubjectDTO(
			@NotBlank(message = "Không được để trống mã môn học") @Size(max = 5, message = "Mã môn học tối đa 5 ký tự") @Pattern(regexp = "^[A-Z0-9]+$", message = "Mã môn học chỉ được chứa chữ in hoa và số") String subjectId,
			@NotBlank(message = "Không được để trống tên môn học") @Size(max = 50, message = "Tên môn học chỉ được chứa tối đa 50 ký tự") String subjectName) {
		super();
		this.subjectId = subjectId;
		this.subjectName = subjectName;
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
