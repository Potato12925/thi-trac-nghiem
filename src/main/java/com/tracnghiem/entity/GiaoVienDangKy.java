package com.tracnghiem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tracnghiem.entity.id.GiaoVienDangKyId;

@Entity
@Table(name = "GiaoVien_DangKy")
public class GiaoVienDangKy {

    @EmbeddedId
    private GiaoVienDangKyId id;

    @ManyToOne
    @MapsId("maLop")
    @JoinColumn(name = "MALOP")
    private Lop lop;

    @ManyToOne
    @MapsId("maMH")
    @JoinColumn(name = "MAMH")
    private MonHoc monHoc;

    @ManyToOne
    @JoinColumn(name = "MAGV")
    private GiaoVien giaoVien;

    @Column(name = "TRINHDO")
    private String trinhDo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NGAYTHI")
    private Date ngayThi;

    @Column(name = "SOCAUTHI")
    private Short soCauThi;

    @Column(name = "THOIGIAN")
    private Short thoiGian;

    public GiaoVienDangKy() {
    }

    public GiaoVienDangKyId getId() {
        return id;
    }

    public void setId(GiaoVienDangKyId id) {
        this.id = id;
    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public GiaoVien getGiaoVien() {
        return giaoVien;
    }

    public void setGiaoVien(GiaoVien giaoVien) {
        this.giaoVien = giaoVien;
    }

    public String getTrinhDo() {
        return trinhDo;
    }

    public void setTrinhDo(String trinhDo) {
        this.trinhDo = trinhDo;
    }

    public Date getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(Date ngayThi) {
        this.ngayThi = ngayThi;
    }

    public Short getSoCauThi() {
        return soCauThi;
    }

    public void setSoCauThi(Short soCauThi) {
        this.soCauThi = soCauThi;
    }

    public Short getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Short thoiGian) {
        this.thoiGian = thoiGian;
    }
}