package com.tracnghiem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.ClassroomDAO;
import com.tracnghiem.dto.ClassroomDTO;
import com.tracnghiem.entity.Classroom;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomDAO classroomDAO;

    public Classroom findClassroomById(String classId) {
        return classroomDAO.findById(classId);
    }

    private Classroom convertToEntity(ClassroomDTO dto) {
        Classroom classRoom = new Classroom();

        classRoom.setClassId(dto.getClassId());
        classRoom.setClassName(dto.getClassName());

        return classRoom;
    }

    public List<Classroom> getAllClassrooms() {
        return classroomDAO.findAll();
    }

    public List<Classroom> getClassrooms(int page, int pageSize) {

        return classroomDAO.getPagination(page, pageSize);
    }

    public long countClassrooms() {
        return classroomDAO.count();
    }

    private void ensureClassroomNotExists(String classId) {

        if (classroomDAO.existsById(classId)) {

            throw new IllegalArgumentException("Mã lớp đã tồn tại");
        }
    }

    public void addClassroom(ClassroomDTO dto) {
        ensureClassroomNotExists(dto.getClassId());

        Classroom classRoom = convertToEntity(dto);

        classroomDAO.create(classRoom);
    }

    public void updateClassroom(ClassroomDTO dto) {
        Classroom classRoom = convertToEntity(dto);

        classroomDAO.update(classRoom);
    }

    public void deleteClassroom(ClassroomDTO dto) {
        Classroom classRoom = convertToEntity(dto);

        classroomDAO.delete(classRoom);
    }
}
