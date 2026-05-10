package com.tracnghiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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