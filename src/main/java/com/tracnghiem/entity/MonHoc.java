package com.tracnghiem.entity;

import javax.persistence.*;

@Entity
@Table(name = "MonHoc")
public class MonHoc {

    @Id
    @Column(name = "MAMH", length = 5)
    private String maMH;

    @Column(name = "TENMH", nullable = false, unique = true)
    private String tenMH;

    // getter setter
}