package com.tracnghiem.entity;

import javax.persistence.*;
import java.util.Date;
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