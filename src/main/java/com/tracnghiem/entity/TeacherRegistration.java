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

import com.tracnghiem.entity.id.TeacherRegistrationId;

@Entity
@Table(name = "GiaoVien_DangKy")
public class TeacherRegistration {

    @EmbeddedId
    private TeacherRegistrationId id;

    @ManyToOne
    @MapsId("maLop")
    @JoinColumn(name = "MALOP")
    private ClassRoom lop;

    @ManyToOne
    @MapsId("maMH")
    @JoinColumn(name = "MAMH")
    private Subject monHoc;

    @ManyToOne
    @JoinColumn(name = "MAGV")
    private Lecturer giaoVien;

    @Column(name = "TRINHDO")
    private String trinhDo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NGAYTHI")
    private Date ngayThi;

    @Column(name = "SOCAUTHI")
    private Short soCauThi;

    @Column(name = "THOIGIAN")
    private Short thoiGian;

    public TeacherRegistration() {
    }

    public TeacherRegistrationId getId() {
        return id;
    }

    public void setId(TeacherRegistrationId id) {
        this.id = id;
    }

    public ClassRoom getLop() {
        return lop;
    }

    public void setLop(ClassRoom lop) {
        this.lop = lop;
    }

    public Subject getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(Subject monHoc) {
        this.monHoc = monHoc;
    }

    public Lecturer getGiaoVien() {
        return giaoVien;
    }

    public void setGiaoVien(Lecturer giaoVien) {
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