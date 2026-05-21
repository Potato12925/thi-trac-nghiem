package com.tracnghiem.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class ExamRegistrationDTO {

    @NotBlank(message = "Vui lòng chọn lớp")
    private String maLop;

    @NotBlank(message = "Vui lòng chọn môn học")
    private String maMh;

    @NotBlank(message = "Vui lòng chọn trình độ")
    private String trinhDo;

    @NotNull(message = "Vui lòng chọn lần thi")
    @Min(value = 1, message = "Lần thi phải là 1 hoặc 2")
    @Max(value = 2, message = "Lần thi phải là 1 hoặc 2")
    private Short lan;

    @NotNull(message = "Vui lòng nhập số câu thi")
    @Min(value = 10, message = "Số câu thi tối thiểu là 10")
    @Max(value = 100, message = "Số câu thi tối đa là 100")
    private Short soCauThi;

    @NotNull(message = "Vui lòng nhập thời gian thi")
    @Min(value = 5, message = "Thời gian thi tối thiểu là 5 phút")
    @Max(value = 60, message = "Thời gian thi tối đa là 60 phút")
    private Short thoiGian;

    @NotNull(message = "Vui lòng chọn ngày thi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayThi;

    // Field for PGV to select a teacher
    private String maGv;

    public ExamRegistrationDTO() {
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMaMh() {
        return maMh;
    }

    public void setMaMh(String maMh) {
        this.maMh = maMh;
    }

    public String getTrinhDo() {
        return trinhDo;
    }

    public void setTrinhDo(String trinhDo) {
        this.trinhDo = trinhDo;
    }

    public Short getLan() {
        return lan;
    }

    public void setLan(Short lan) {
        this.lan = lan;
    }

    public Short getSoCauThi() {
        return soCauThi;
    }

    public void setSoCauThi(Short soCauThi) {
        this.soCauThi = soCauThi;
    }

    public Short getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Short thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Date getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(Date ngayThi) {
        this.ngayThi = ngayThi;
    }

    public String getMaGv() {
        return maGv;
    }

    public void setMaGv(String maGv) {
        this.maGv = maGv;
    }
}
