package com.tracnghiem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.ClassroomDAO;
import com.tracnghiem.dto.ClassroomActionDTO;
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

        return classroomDAO.findPage(page, pageSize);
    }

    public long countClassrooms() {
        return classroomDAO.countAll();
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
        Classroom classRoom = findClassroomById(dto.getClassId());
        if (classRoom == null) {
            throw new IllegalArgumentException("Lớp học không tồn tại");
        }
        classRoom.setClassName(dto.getClassName());
        classroomDAO.update(classRoom);
    }

    public void deleteClassroom(ClassroomDTO dto) {
        Classroom classRoom = findClassroomById(dto.getClassId());
        if (classRoom == null) {
            throw new IllegalArgumentException("Lớp học không tồn tại");
        }
        classRoom.setDeleted(true);
        classroomDAO.update(classRoom);
    }

	@Transactional
	public void savePendingActions(String actionsData) {
		if (actionsData == null || actionsData.trim().isEmpty()) {
			return;
		}

		List<ClassroomActionDTO> actions = new ArrayList<>();
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
			String classId = parts[1].trim();
			String className = parts.length > 2 ? parts[2].trim() : "";

			actions.add(new ClassroomActionDTO(type, classId, className));
		}

		for (ClassroomActionDTO action : actions) {
			ClassroomDTO dto = new ClassroomDTO();
			dto.setClassId(action.getClassId());
			dto.setClassName(action.getClassName());

			if ("ADD".equals(action.getType())) {
				addClassroom(dto);
			} else if ("UPDATE".equals(action.getType())) {
				updateClassroom(dto);
			} else if ("DELETE".equals(action.getType())) {
				deleteClassroom(dto);
			}
		}
	}

	@Transactional
	public void importClassrooms(List<ClassroomDTO> dtos) {
		for (ClassroomDTO dto : dtos) {
			String classId = dto.getClassId();
			String className = dto.getClassName();

			if (classId == null || classId.trim().isEmpty()) {
				throw new IllegalArgumentException("Mã lớp không được để trống.");
			}
			classId = classId.trim();

			if (classId.length() > 15) {
				throw new IllegalArgumentException("Mã lớp không được vượt quá 15 ký tự: " + classId);
			}

			if (className == null || className.trim().isEmpty()) {
				throw new IllegalArgumentException("Tên lớp không được để trống cho mã: " + classId);
			}
			className = className.trim();

			if (className.length() > 50) {
				throw new IllegalArgumentException("Tên lớp không được vượt quá 50 ký tự cho mã: " + classId);
			}

			if (classroomDAO.existsById(classId)) {
				throw new IllegalArgumentException("Mã lớp '" + classId + "' đã tồn tại trong hệ thống.");
			}

			Classroom classroom = new Classroom();
			classroom.setClassId(classId);
			classroom.setClassName(className);

			classroomDAO.create(classroom);
		}
	}
}
