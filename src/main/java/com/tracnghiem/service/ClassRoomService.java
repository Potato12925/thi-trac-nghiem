package com.tracnghiem.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.ClassRoomDAO;
import com.tracnghiem.dto.ClassRoomDTO;
import com.tracnghiem.entity.ClassRoom;

@Service
public class ClassRoomService {
	@Autowired
	ClassRoomDAO classRoomDAO;

	public ClassRoom timLopTheoMa(String maLop) {
		return classRoomDAO.findById(maLop);
	}

	private ClassRoom chuyenDoiSangEntity(ClassRoomDTO dto) {
		ClassRoom classRoom = new ClassRoom();

		classRoom.setMaLop(dto.getMaLop());
		classRoom.setTenLop(dto.getTenLop());

		return classRoom;
	}

	public List<ClassRoom> getAllClassRoomList() {
		return classRoomDAO.findAll();
	}

	private void validateExitsClassRoom(String maLop) {

		if (classRoomDAO.existsById(maLop)) {

			throw new IllegalArgumentException("Mã lớp đã tồn tại");
		}
	}

	public void addClassRoom(ClassRoomDTO dto) {
		validateExitsClassRoom(dto.getMaLop());

		ClassRoom classRoom = chuyenDoiSangEntity(dto);

		classRoomDAO.create(classRoom);
	}

	public void updateClassRoom(ClassRoomDTO dto) {
		ClassRoom classRoom = chuyenDoiSangEntity(dto);

		classRoomDAO.update(classRoom);
	}

	public void deleteClassRoom(ClassRoomDTO dto) {
		ClassRoom classRoom = chuyenDoiSangEntity(dto);

		classRoomDAO.delete(classRoom);
	}

}
