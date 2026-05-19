package com.tracnghiem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SINHVIEN")
public class SinhVien {

	@Id
	@Column(name = "MASV", length = 8)
	private String maSV;

	@Column(name = "HO", length = 40)
	private String ho;

	@Column(name = "TEN", length = 10)
	private String ten;

	@Temporal(TemporalType.DATE)
	@Column(name = "NGAYSINH")
	private Date ngaySinh;

	@Column(name = "DIACHI", length = 100)
	private String diaChi;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MALOP", nullable = false)
	private Lop lop;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MASV", referencedColumnName = "MA", insertable = false, updatable = false)
	private TaiKhoan taiKhoan;

	public SinhVien() {
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

	public Lop getLop() {
		return lop;
	}

	public void setLop(Lop lop) {
		this.lop = lop;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
}