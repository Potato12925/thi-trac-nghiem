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
public class MonHoc {

	@Id
	@Column(name = "MAMH", length = 5)

	@NotBlank(message = "Không được để trống mã môn học")

	@Size(max = 5, message = "Mã môn học tối đa 5 ký tự")

	@Pattern(regexp = "^[A-Z0-9]+$", message = "Mã môn học chỉ được chứa chữ in hoa và số")

	private String maMH;

	@Column(name = "TENMH", nullable = false, unique = true)
	
	@NotBlank(message="Không được để trống tên môn học")
	
	@Size(max = 50, message="Tên môn học chỉ được chứa tối đa 50 ký tự")
	
	private String tenMH;

	@Column(name = "SOTIET_LT", nullable = false)
	
	@Min(value = 0, message="Số tiết lý thuyết phải lớn hơn 0")
	
	private int soTiet_LT;

	@Column(name = "SOTIET_TH", nullable = false)
	
	@Min(value=0, message="Số tiết thực hành phải lớn hơn 0")
	private int soTiet_TH;

	public MonHoc() {
		super();
	}

	public String getMaMH() {
		return maMH;
	}

	public void setMaMH(String maMH) {
		this.maMH = maMH;
	}

	public String getTenMH() {
		return tenMH;
	}

	public void setTenMH(String tenMH) {
		this.tenMH = tenMH;
	}

	public int getSoTiet_LT() {
		return soTiet_LT;
	}

	public void setSoTiet_LT(int soTiet_LT) {
		this.soTiet_LT = soTiet_LT;
	}

	public int getSoTiet_TH() {
		return soTiet_TH;
	}

	public void setSoTiet_TH(int soTiet_TH) {
		this.soTiet_TH = soTiet_TH;
	}
} 
