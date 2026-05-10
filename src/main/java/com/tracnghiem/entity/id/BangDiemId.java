package com.tracnghiem.entity.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class BangDiemId implements Serializable {

    private String masv;
    private String mamh;
    private Short lan;

    // equals hashCode
}