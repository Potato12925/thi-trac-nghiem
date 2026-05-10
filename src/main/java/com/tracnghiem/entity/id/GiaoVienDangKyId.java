package com.tracnghiem.entity.id;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GiaoVienDangKyId implements Serializable {

    @Column(name = "MALOP")
    private String maLop;

    @Column(name = "MAMH")
    private String maMH;

    @Column(name = "LAN")
    private Short lan;

    public GiaoVienDangKyId() {
    }

    public GiaoVienDangKyId(String maLop, String maMH, Short lan) {
        this.maLop = maLop;
        this.maMH = maMH;
        this.lan = lan;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public Short getLan() {
        return lan;
    }

    public void setLan(Short lan) {
        this.lan = lan;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lan, maLop, maMH);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
			return true;
		}

        if (!(obj instanceof GiaoVienDangKyId)) {
			return false;
		}

        GiaoVienDangKyId other = (GiaoVienDangKyId) obj;

        return Objects.equals(maLop, other.maLop)
                && Objects.equals(maMH, other.maMH)
                && Objects.equals(lan, other.lan);
    }
}