package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BoDe")
public class BoDe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAUHOI")
    private Integer cauHoi;

    @ManyToOne
    @JoinColumn(name = "MAMH")
    private MonHoc monHoc;

    @Column(name = "TRINHDO")
    private String trinhDo;

    @Column(name = "NOIDUNG")
    private String noiDung;

    private String A;
    private String B;
    private String C;
    private String D;

    @Column(name = "DAP_AN")
    private String dapAn;

    @ManyToOne
    @JoinColumn(name = "MAGV")
    private GiaoVien giaoVien;

    public BoDe() {
    }

    public Integer getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(Integer cauHoi) {
        this.cauHoi = cauHoi;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public String getTrinhDo() {
        return trinhDo;
    }

    public void setTrinhDo(String trinhDo) {
        this.trinhDo = trinhDo;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }

    public GiaoVien getGiaoVien() {
        return giaoVien;
    }

    public void setGiaoVien(GiaoVien giaoVien) {
        this.giaoVien = giaoVien;
    }
} 