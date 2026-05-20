package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;

public class ClassRoomDTO {

	@NotBlank(message = "Mã lớp không được để trống")

	private String maLop;

	@NotBlank(message = "Tên lớp không được để trống")

	private String tenLop;

	public ClassRoomDTO() {
		super();
	}

	public ClassRoomDTO(@NotBlank(message = "Mã lớp không được để trống") String maLop,
			@NotBlank(message = "Tên lớp không được để trống") String tenLop) {
		super();
		this.maLop = maLop;
		this.tenLop = tenLop;
	}

	public String getMaLop() {
		return maLop;
	}

	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}
}
