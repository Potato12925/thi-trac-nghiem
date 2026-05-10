package com.tracnghiem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SinhVien")
public class SinhVien {

    @Id
    @Column(name = "MASV", length = 8)
    private String maSV;

    @Column(name = "HO")
    private String ho;

    @Column(name = "TEN")
    private String ten;

    @Temporal(TemporalType.DATE)
    @Column(name = "NGAYSINH")
    private Date ngaySinh;

    @Column(name = "DIACHI")
    private String diaChi;

    @ManyToOne
    @JoinColumn(name = "MALOP")
    private Lop lop;

    // getter setter
}