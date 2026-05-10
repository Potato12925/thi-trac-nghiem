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
}