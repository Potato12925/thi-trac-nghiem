package com.tracnghiem.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class StudentDTO {

	@NotBlank(message = "Mã sinh viên không được để trống")
	private String maSV;

	@NotBlank(message = "Họ không được để trống")
	private String ho;

	@NotBlank(message = "Tên không được để trống")
	private String ten;

	@NotNull(message = "Ngày sinh không được để trống")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngaySinh;

	@NotBlank(message = "Địa chỉ không được để trống")
	private String diaChi;

	@NotBlank(message = "Mã lớp không được để trống")
	private String maLop;

	public StudentDTO() {
		super();
	}

	public StudentDTO(@NotBlank(message = "Mã sinh viên không được để trống") String maSV,
			@NotBlank(message = "Họ không được để trống") String ho,
			@NotBlank(message = "Tên không được để trống") String ten,
			@NotNull(message = "Ngày sinh không được để trống") Date ngaySinh,
			@NotBlank(message = "Địa chỉ không được để trống") String diaChi,
			@NotBlank(message = "Mã lớp không được để trống") String maLop) {
		super();
		this.maSV = maSV;
		this.ho = ho;
		this.ten = ten;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.maLop = maLop;
	}

	public String getMaSV() {
		return maSV;
	}

	public void setMaSV(String maSV) {
		this.maSV = maSV;
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

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getMaLop() {
		return maLop;
	}

	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}

}
