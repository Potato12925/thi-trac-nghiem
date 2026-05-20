package com.tracnghiem.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Lop")
public class ClassRoom {

	@Id
	@Column(name = "MALOP", length = 15)
	private String maLop;

	@Column(name = "TENLOP", nullable = false, unique = true)
	private String tenLop;

	@OneToMany(mappedBy = "lop")
	private List<SinhVien> sinhViens;

	public ClassRoom() {
		super();
	}

	public ClassRoom(String maLop, String tenLop, List<SinhVien> sinhViens) {
		super();
		this.maLop = maLop;
		this.tenLop = tenLop;
		this.sinhViens = sinhViens;
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

	public List<SinhVien> getSinhViens() {
		return sinhViens;
	}

	public void setSinhViens(List<SinhVien> sinhViens) {
		this.sinhViens = sinhViens;
	}

}
