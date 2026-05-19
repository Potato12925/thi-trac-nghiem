package com.tracnghiem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.LopDAO;
import com.tracnghiem.entity.Lop;

@Service
public class LopService {
	@Autowired
	LopDAO lopDAO;

	public Lop timLopTheoMa(String maLop) {
		return lopDAO.findById(maLop);
	}
}
