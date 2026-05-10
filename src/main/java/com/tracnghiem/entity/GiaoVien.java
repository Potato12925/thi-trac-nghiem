package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GiaoVien")
public class GiaoVien {

    @Id
    @Column(name = "MAGV", length = 8)
    private String maGV;

    @Column(name = "HO")
    private String ho;

    @Column(name = "TEN")
    private String ten;

    @Column(name = "SODTLL")
    private String soDT;

    @Column(name = "DIACHI")
    private String diaChi;

    public GiaoVien() {
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

    @Override
    public String toString() {
        return "GiaoVien{" +
                "maGV='" + maGV + '\'' +
                ", ho='" + ho + '\'' +
                ", ten='" + ten + '\'' +
                ", soDT='" + soDT + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}