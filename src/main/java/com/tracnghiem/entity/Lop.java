package com.tracnghiem.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Lop")
public class Lop {

    @Id
    @Column(name = "MALOP", length = 15)
    private String maLop;

    @Column(name = "TENLOP", nullable = false, unique = true)
    private String tenLop;

    @OneToMany(mappedBy = "lop")
    private List<SinhVien> sinhViens;

    // getter setter
}