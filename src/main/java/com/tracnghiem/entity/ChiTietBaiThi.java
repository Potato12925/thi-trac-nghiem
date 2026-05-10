package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ChiTietBaiThi", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "BAITHI_ID", "CAUHOI" })
})
public class ChiTietBaiThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "BAITHI_ID")
    private BaiThi baiThi;

    @ManyToOne
    @JoinColumn(name = "CAUHOI")
    private BoDe boDe;

    @Column(name = "DAPAN_SV")
    private String dapAnSV;

    public ChiTietBaiThi() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BaiThi getBaiThi() {
        return baiThi;
    }

    public void setBaiThi(BaiThi baiThi) {
        this.baiThi = baiThi;
    }

    public BoDe getBoDe() {
        return boDe;
    }

    public void setBoDe(BoDe boDe) {
        this.boDe = boDe;
    }

    public String getDapAnSV() {
        return dapAnSV;
    }

    public void setDapAnSV(String dapAnSV) {
        this.dapAnSV = dapAnSV;
    }
}