package com.tracnghiem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.MonHocDAO;
import com.tracnghiem.entity.MonHoc;

@Service
public class MonHocService {
	@Autowired
	private MonHocDAO monHocDao;

	public void themMonHoc(MonHoc monHoc) {
		monHocDao.themMonHoc(monHoc);
	}
	
	public List<MonHoc> layTatCaMonHoc() {
		return monHocDao.findAll();
	}
	
	public void chinhSuaMonHoc(MonHoc monHoc) {
		monHocDao.chinhSuaMonHoc(monHoc);
	}
	
	public void xoaMonHoc(MonHoc monHoc) {
		monHocDao.xoaMonHoc(monHoc);
	}
	
	public List<MonHoc> timKiemTheoMaMonHoc(String maMonHoc) {
		MonHoc monHocTheoTimKiem =  monHocDao.findById(maMonHoc);
		
		List<MonHoc> danhSachMonHoc = new ArrayList<MonHoc>();
		danhSachMonHoc.add(monHocTheoTimKiem);
		
		return danhSachMonHoc;
		
	}
}
