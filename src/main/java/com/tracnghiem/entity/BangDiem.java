package com.tracnghiem.entity;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tracnghiem.entity.id.BangDiemId;

@Entity
@Table(name = "BangDiem")
public class BangDiem {

    @EmbeddedId
    private BangDiemId id;

    @ManyToOne
    @MapsId("masv")
    @JoinColumn(name = "MASV")
    private SinhVien sinhVien;

    @ManyToOne
    @MapsId("mamh")
    @JoinColumn(name = "MAMH")
    private MonHoc monHoc;

    @Temporal(TemporalType.DATE)
    private Date ngayThi;

    private Float diem;
}