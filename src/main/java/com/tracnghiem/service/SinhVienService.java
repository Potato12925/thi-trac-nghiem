package com.tracnghiem.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.SinhVienDAO;
import com.tracnghiem.dto.SinhVienDTO;
import com.tracnghiem.entity.Lop;
import com.tracnghiem.entity.SinhVien;

@Service
public class SinhVienService {

	@Autowired
	SinhVienDAO sinhVienDAO;

	@Autowired
	AuthService authService;

	@Autowired
	LopService lopService;

	private SinhVien chuyenDoiSangEntity(SinhVienDTO dto) {
		Lop lop = lopService.timLopTheoMa(dto.getMaLop());

		SinhVien sinhVien = new SinhVien();
		sinhVien.setMaSV(dto.getMaSV());
		sinhVien.setHo(dto.getHo());
		sinhVien.setTen(dto.getTen());
		sinhVien.setNgaySinh(dto.getNgaySinh());
		sinhVien.setDiaChi(dto.getDiaChi());
		sinhVien.setLop(lop);

		return sinhVien;
	}

	public List<SinhVien> layDanhSachTatCaSinhVien() {
		return sinhVienDAO.findAll();
	}

	public void themSinhVien(SinhVienDTO dto) {
		SinhVien sinhVien = chuyenDoiSangEntity(dto);
		sinhVienDAO.create(sinhVien);
	}

	public void capNhatSinhVien(SinhVienDTO dto) {
		SinhVien sinhVien = chuyenDoiSangEntity(dto);
		sinhVienDAO.update(sinhVien);
	}

	public void xoaSinhVien(SinhVienDTO dto) {
		SinhVien sinhVien = chuyenDoiSangEntity(dto);

		sinhVienDAO.delete(sinhVien);

		authService.xoaTaiKhoan(dto.getMaSV());

	}

	private void validateSinhVienKhongTonTai(String maSV) {

		if (sinhVienDAO.existsById(maSV)) {

			throw new IllegalArgumentException("Mã sinh viên đã tồn tại");
		}
	}

	@Transactional
	public void themSinhVienVaTaiKhoan(SinhVienDTO dto) {
		validateSinhVienKhongTonTai(dto.getMaSV());

		authService.taoTaiKhoan(dto.getMaSV());

		themSinhVien(dto);
	}

}
