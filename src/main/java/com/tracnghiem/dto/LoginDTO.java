package com.tracnghiem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DangNhapDTO {

    @NotBlank(message = "Mã đăng nhập không được để trống")
    @Size(max = 8, message = "Mã đăng nhập tối đa 8 ký tự")
    private String ma;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu tối thiểu 6 ký tự")
    private String password;

    public DangNhapDTO() {
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}