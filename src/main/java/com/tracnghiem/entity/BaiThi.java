package com.tracnghiem.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "BaiThi")
public class BaiThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "MASV")
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "MAMH")
    private MonHoc monHoc;

    @Column(name = "LAN")
    private Short lan;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NGAYTHI")
    private Date ngayThi;

    @Column(name = "DIEM")
    private Float diem;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "THOIGIANBATDAU")
    private Date thoiGianBatDau;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "THOIGIANKETTHUC")
    private Date thoiGianKetThuc;

    @OneToMany(mappedBy = "baiThi")
    private List<ChiTietBaiThi> chiTietBaiThis;

    public BaiThi() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public Short getLan() {
        return lan;
    }

    public void setLan(Short lan) {
        this.lan = lan;
    }

    public Date getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(Date ngayThi) {
        this.ngayThi = ngayThi;
    }

    public Float getDiem() {
        return diem;
    }

    public void setDiem(Float diem) {
        this.diem = diem;
    }

    public Date getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Date thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Date getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public List<ChiTietBaiThi> getChiTietBaiThis() {
        return chiTietBaiThis;
    }

    public void setChiTietBaiThis(List<ChiTietBaiThi> chiTietBaiThis) {
        this.chiTietBaiThis = chiTietBaiThis;
    }
}