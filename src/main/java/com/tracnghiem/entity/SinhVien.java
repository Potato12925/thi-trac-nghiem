package com.tracnghiem.entity;

import javax.persistence.*;
import java.util.Date;

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