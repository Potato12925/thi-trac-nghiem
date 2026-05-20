package com.tracnghiem.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.StudentDAO;
import com.tracnghiem.dto.StudentDTO;
import com.tracnghiem.entity.ClassRoom;
import com.tracnghiem.entity.Student;

@Service
public class SinhVienService {

	@Autowired
	StudentDAO sinhVienDAO;

	@Autowired
	AuthService authService;

	@Autowired
	ClassRoomService lopService;

	private Student chuyenDoiSangEntity(StudentDTO dto) {
		ClassRoom lop = lopService.timLopTheoMa(dto.getMaLop());

		Student sinhVien = new Student();
		sinhVien.setMaSV(dto.getMaSV());
		sinhVien.setHo(dto.getHo());
		sinhVien.setTen(dto.getTen());
		sinhVien.setNgaySinh(dto.getNgaySinh());
		sinhVien.setDiaChi(dto.getDiaChi());
		sinhVien.setLop(lop);

		return sinhVien;
	}

	public List<Student> layDanhSachTatCaSinhVien() {
		return sinhVienDAO.findAll();
	}

	public void themSinhVien(StudentDTO dto) {
		Student sinhVien = chuyenDoiSangEntity(dto);
		sinhVienDAO.create(sinhVien);
	}

	public void capNhatSinhVien(StudentDTO dto) {
		Student sinhVien = chuyenDoiSangEntity(dto);
		sinhVienDAO.update(sinhVien);
	}

	public void xoaSinhVien(StudentDTO dto) {
		Student sinhVien = chuyenDoiSangEntity(dto);

		sinhVienDAO.delete(sinhVien);

		authService.xoaTaiKhoan(dto.getMaSV());

	}

	private void validateSinhVienKhongTonTai(String maSV) {

		if (sinhVienDAO.existsById(maSV)) {

			throw new IllegalArgumentException("Mã sinh viên đã tồn tại");
		}
	}

	@Transactional
	public void themSinhVienVaTaiKhoan(StudentDTO dto) {
		validateSinhVienKhongTonTai(dto.getMaSV());

		authService.taoTaiKhoan(dto.getMaSV());

		themSinhVien(dto);
	}

}
