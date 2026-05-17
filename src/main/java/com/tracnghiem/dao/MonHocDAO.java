package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.MonHoc;

@Repository
public class MonHocDAO extends GenericDAO<MonHoc> {
	public void themMonHoc(MonHoc monHoc) {
		create(monHoc);
	}

	public void chinhSuaMonHoc(MonHoc monHoc) {
		update(monHoc);
	}

	public void xoaMonHoc(MonHoc monHoc) {
		delete(monHoc);
	}
}