package com.tracnghiem.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.GiaoVienDAO;
import com.tracnghiem.dao.TaiKhoanDAO;
import com.tracnghiem.entity.GiaoVien;
import com.tracnghiem.entity.TaiKhoan;

@Service
public class AuthService {

    @Autowired
    private TaiKhoanDAO taiKhoanDAO;

    @Autowired
    private GiaoVienDAO giaoVienDAO;

    @Transactional
    public boolean login(String username, String rawPassword, HttpSession session) {

        if (isBlank(username) || isBlank(rawPassword)) {
            return false;
        }

        TaiKhoan user = taiKhoanDAO.findByUsername(username.trim());

        if (user == null) {
            return false;
        }

        String inputHash = sha256(rawPassword);

        if (!inputHash.equalsIgnoreCase(user.getPasswordHash())) {
            return false;
        }

        session.setAttribute("LOGIN_USER", user.getUsername());
        session.setAttribute("ROLE", user.getRole());

        return true;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Transactional
    public String register(String username, String rawPassword, String confirmPassword, String role, String maGV) {

        if (isBlank(username) || isBlank(rawPassword) || isBlank(confirmPassword) || isBlank(role)) {
            return "Vui lòng nhập đầy đủ thông tin bắt buộc";
        }

        if (!rawPassword.equals(confirmPassword)) {
            return "Mật khẩu xác nhận không khớp";
        }

        String normalizedUsername = username.trim();
        String normalizedRole = role.trim().toUpperCase();

        if (!isSupportedRole(normalizedRole)) {
            return "Vai trò không hợp lệ";
        }

        TaiKhoan existed = taiKhoanDAO.findByUsername(normalizedUsername);
        if (existed != null) {
            return "Tên đăng nhập đã tồn tại";
        }

        TaiKhoan account = new TaiKhoan();
        account.setUsername(normalizedUsername);
        account.setPasswordHash(sha256(rawPassword));
        account.setRole(normalizedRole);

        if (!isBlank(maGV)) {
            GiaoVien giaoVien = giaoVienDAO.findById(maGV.trim());
            if (giaoVien == null) {
                return "Mã giáo viên không tồn tại";
            }
            account.setGiaoVien(giaoVien);
        }

        taiKhoanDAO.create(account);
        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isSupportedRole(String role) {
        return "PGV".equals(role) || "GIANGVIEN".equals(role) || "SINHVIEN".equals(role);
    }

    private String sha256(String raw) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(hash.length * 2);

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
}
