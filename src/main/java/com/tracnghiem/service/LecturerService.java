package com.tracnghiem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.LecturerDAO;
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
        return lecturerDAO.getPagination(page, pageSize);
    }

    public long countLecturers() {
        return lecturerDAO.count();
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
        Lecturer lecturer = convertToEntity(dto);
        lecturerDAO.delete(lecturer);
    }

    private void validateLecturerNotExists(String lecturerId) {
        if (lecturerDAO.existsById(lecturerId)) {
            throw new IllegalArgumentException("Lecturer ID already exists");
        }
    }
}
