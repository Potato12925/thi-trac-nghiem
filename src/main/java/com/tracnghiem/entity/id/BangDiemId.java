package com.tracnghiem.entity.id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BangDiemId implements Serializable {

    private String masv;
    private String mamh;
    private Short lan;

    // equals hashCode
}