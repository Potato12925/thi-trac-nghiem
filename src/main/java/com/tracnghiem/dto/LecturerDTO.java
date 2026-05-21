package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;

public class LecturerDTO {

	@NotBlank(message = "Mã giáo viên không được để trống")
	private String maGV;

	@NotBlank(message = "Họ không được để trống")
	private String ho;

	@NotBlank(message = "Tên không được để trống")
	private String ten;

	@NotBlank(message = "Số điện thoại không được để trống")
	private String soDT;

	@NotBlank(message = "Địa chỉ không được để trống")
	private String diaChi;

	public LecturerDTO() {
		super();
	}

	public LecturerDTO(
			@NotBlank(message = "Mã giáo viên không được để trống") String maGV,
			@NotBlank(message = "Họ không được để trống") String ho,
			@NotBlank(message = "Tên không được để trống") String ten,
			@NotBlank(message = "Số điện thoại không được để trống") String soDT,
			@NotBlank(message = "Địa chỉ không được để trống") String diaChi) {
		super();
		this.maGV = maGV;
		this.ho = ho;
		this.ten = ten;
		this.soDT = soDT;
		this.diaChi = diaChi;
	}

	public String getMaGV() {
		return maGV;
	}

	public void setMaGV(String maGV) {
		this.maGV = maGV;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
}
