package com.tracnghiem.entity;

import javax.persistence.*;

@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD_HASH")
    private String passwordHash;

    @Column(name = "ROLE")
    private String role;

    @ManyToOne
    @JoinColumn(name = "MAGV")
    private GiaoVien giaoVien;

    // getter setter
}