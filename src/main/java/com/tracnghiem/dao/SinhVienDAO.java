package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.SinhVien;

@Repository
public class SinhVienDAO extends GenericDAO<SinhVien> {
	public boolean existsById(String maSV) {
	    return findById(maSV) != null;
	}
}
