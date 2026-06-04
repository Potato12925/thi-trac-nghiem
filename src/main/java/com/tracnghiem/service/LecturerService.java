package com.tracnghiem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.LecturerDAO;
import com.tracnghiem.dto.LecturerActionDTO;
import com.tracnghiem.dto.LecturerDTO;
import com.tracnghiem.entity.Lecturer;

@Service
public class LecturerService {

    @Autowired
    private LecturerDAO lecturerDAO;

    private Lecturer convertToEntity(LecturerDTO dto) {
        Lecturer lecturer = new Lecturer();

        lecturer.setLecturerId(dto.getLecturerId());
        lecturer.setLastName(dto.getLastName());
        lecturer.setFirstName(dto.getFirstName());
        lecturer.setPhoneNumber(dto.getPhoneNumber());
        lecturer.setAddress(dto.getAddress());
        lecturer.setEmail(dto.getEmail());

        return lecturer;
    }

    public List<Lecturer> getAllLecturers() {

        return lecturerDAO.findAll();
    }

    public List<Lecturer> getLecturers(int page, int pageSize) {
        return lecturerDAO.findPage(page, pageSize);
    }

    public List<Lecturer> getLecturers(int page, int pageSize, String keyword) {
        return lecturerDAO.findPage(page, pageSize, keyword);
    }

    public long countLecturers() {
        return lecturerDAO.countAll();
    }

    public long countLecturers(String keyword) {
        return lecturerDAO.countAll(keyword);
    }

    public Lecturer findLecturerById(String lecturerId) {
        return lecturerDAO.findById(lecturerId);
    }

    public List<Lecturer> findLecturerByKeyword(String keyword) {
        return lecturerDAO.findByKeyword(keyword);
    }

    public void addLecturer(LecturerDTO dto) {
        validateLecturerNotExists(dto.getLecturerId());
        Lecturer lecturer = convertToEntity(dto);
        lecturerDAO.create(lecturer);
    }

    public void updateLecturer(LecturerDTO dto) {
        Lecturer lecturer = convertToEntity(dto);
        lecturerDAO.update(lecturer);
    }

    public void deleteLecturer(LecturerDTO dto) {
        Lecturer lecturer = lecturerDAO.findById(dto.getLecturerId());
        if (lecturer == null) {
            throw new IllegalArgumentException("Giảng viên không tồn tại");
        }
        lecturer.setDeleted(true);
        lecturerDAO.update(lecturer);
    }

    private void validateLecturerNotExists(String lecturerId) {
        if (lecturerDAO.existsById(lecturerId)) {
            throw new IllegalArgumentException("Lecturer ID already exists");
        }
    }

	@Transactional
	public void savePendingActions(String actionsData) {
		if (actionsData == null || actionsData.trim().isEmpty()) {
			return;
		}

		List<LecturerActionDTO> actions = new ArrayList<>();
		String[] lines = actionsData.split("\n");
		for (String line : lines) {
			if (line.trim().isEmpty()) {
				continue;
			}
			String[] parts = line.split(":::", -1);
			if (parts.length < 2) {
				continue;
			}
			String type = parts[0].trim();
			String lecturerId = parts[1].trim();
			String lastName = parts.length > 2 ? parts[2].trim() : "";
			String firstName = parts.length > 3 ? parts[3].trim() : "";
			String phoneNumber = parts.length > 4 ? parts[4].trim() : "";
			String address = parts.length > 5 ? parts[5].trim() : "";
			String email = parts.length > 6 ? parts[6].trim() : "";

			actions.add(new LecturerActionDTO(type, lecturerId, lastName, firstName, phoneNumber, address, email));
		}

		for (LecturerActionDTO action : actions) {
			LecturerDTO dto = new LecturerDTO();
			dto.setLecturerId(action.getLecturerId());
			dto.setLastName(action.getLastName());
			dto.setFirstName(action.getFirstName());
			dto.setPhoneNumber(action.getPhoneNumber());
			dto.setAddress(action.getAddress());
			dto.setEmail(action.getEmail());

			if ("ADD".equals(action.getType())) {
				addLecturer(dto);
			} else if ("UPDATE".equals(action.getType())) {
				updateLecturer(dto);
			} else if ("DELETE".equals(action.getType())) {
				deleteLecturer(dto);
			}
		}
	}

	@Transactional
	public void importLecturers(List<LecturerDTO> dtos) {
		for (LecturerDTO dto : dtos) {
			String lecturerId = dto.getLecturerId();
			String lastName = dto.getLastName();
			String firstName = dto.getFirstName();
			String phoneNumber = dto.getPhoneNumber();
			String address = dto.getAddress();

			if (lecturerId == null || lecturerId.trim().isEmpty()) {
				throw new IllegalArgumentException("Mã giảng viên không được để trống.");
			}
			lecturerId = lecturerId.trim();
			if (lecturerId.length() > 8) {
				throw new IllegalArgumentException("Mã giảng viên không được vượt quá 8 ký tự: " + lecturerId);
			}
			if (!lecturerId.matches("^[A-Z0-9]+$")) {
				throw new IllegalArgumentException("Mã giảng viên chỉ được chứa chữ in hoa và số: " + lecturerId);
			}

			if (lastName == null || lastName.trim().isEmpty()) {
				throw new IllegalArgumentException("Họ giảng viên không được để trống cho mã: " + lecturerId);
			}
			lastName = lastName.trim();
			if (lastName.length() > 40) {
				throw new IllegalArgumentException("Họ giảng viên không được vượt quá 40 ký tự cho mã: " + lecturerId);
			}

			if (firstName == null || firstName.trim().isEmpty()) {
				throw new IllegalArgumentException("Tên giảng viên không được để trống cho mã: " + lecturerId);
			}
			firstName = firstName.trim();
			if (firstName.length() > 10) {
				throw new IllegalArgumentException("Tên giảng viên không được vượt quá 10 ký tự cho mã: " + lecturerId);
			}

			if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
				throw new IllegalArgumentException("Số điện thoại không được để trống cho mã: " + lecturerId);
			}
			phoneNumber = phoneNumber.trim();
			if (phoneNumber.length() > 15) {
				throw new IllegalArgumentException("Số điện thoại không được vượt quá 15 ký tự cho mã: " + lecturerId);
			}
			if (!phoneNumber.matches("^[0-9+\\-\\s]+$")) {
				throw new IllegalArgumentException("Số điện thoại không hợp lệ cho mã: " + lecturerId);
			}

			if (address == null || address.trim().isEmpty()) {
				throw new IllegalArgumentException("Địa chỉ không được để trống cho mã: " + lecturerId);
			}
			address = address.trim();
			if (address.length() > 50) {
				throw new IllegalArgumentException("Địa chỉ không được vượt quá 50 ký tự cho mã: " + lecturerId);
			}

			if (lecturerDAO.existsById(lecturerId)) {
				throw new IllegalArgumentException("Mã giảng viên '" + lecturerId + "' đã tồn tại trong hệ thống.");
			}

			LecturerDTO cleanDto = new LecturerDTO();
			cleanDto.setLecturerId(lecturerId);
			cleanDto.setLastName(lastName);
			cleanDto.setFirstName(firstName);
			cleanDto.setPhoneNumber(phoneNumber);
			cleanDto.setAddress(address);

			addLecturer(cleanDto);
		}
	}
}
