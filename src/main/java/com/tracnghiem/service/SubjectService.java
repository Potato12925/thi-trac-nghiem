package com.tracnghiem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.dto.SubjectActionDTO;
import com.tracnghiem.dto.SubjectDTO;
import com.tracnghiem.entity.Subject;

@Service
public class SubjectService {

	@Autowired
	private SubjectDAO subjectDAO;

	private Subject mapToEntity(SubjectDTO dto) {
		Subject subject = new Subject();

		subject.setSubjectId(dto.getSubjectId());
		subject.setSubjectName(dto.getSubjectName());

		return subject;
	}

	public List<Subject> getAllSubjects() {
		return subjectDAO.findAll();
	}

	public List<Subject> getSubjects(int page, int pageSize) {
		return subjectDAO.findPage(page, pageSize);
	}

	public List<Subject> getSubjects(int page, int pageSize, String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return getSubjects(page, pageSize);
		}
		return subjectDAO.findPage(page, pageSize, keyword.trim());
	}

	public long countSubjects() {
		return subjectDAO.countAll();
	}

	public long countSubjects(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return countSubjects();
		}
		return subjectDAO.countAll(keyword);
	}

	public Subject getSubjectById(String subjectId) {
		return subjectDAO.findById(subjectId);
	}

	private void ensureSubjectNotExists(String subjectId) {

		if (subjectDAO.existsById(subjectId)) {
			throw new IllegalArgumentException("Mã môn học đã tồn tại");
		}
	}

	private void ensureSubjectExists(String subjectId) {

		if (!subjectDAO.existsById(subjectId)) {
			throw new IllegalArgumentException("Môn học không tồn tại");
		}
	}

	public void addSubject(SubjectDTO dto) {
		ensureSubjectNotExists(dto.getSubjectId());

		Subject subject = mapToEntity(dto);

		subjectDAO.create(subject);
	}

	public void updateSubject(SubjectDTO dto) {
		ensureSubjectExists(dto.getSubjectId());

		Subject subject = mapToEntity(dto);

		subjectDAO.update(subject);
	}

	public void deleteSubject(SubjectDTO dto) {
		Subject subject = getSubjectById(dto.getSubjectId());
		if (subject == null) {
			throw new IllegalArgumentException("Môn học không tồn tại");
		}
		subject.setDeleted(true);
		subjectDAO.update(subject);
	}

	@Transactional
	public void importSubjects(List<SubjectDTO> dtos) {
		for (SubjectDTO dto : dtos) {
			String subjectId = dto.getSubjectId();
			String subjectName = dto.getSubjectName();

			if (subjectId == null || subjectId.trim().isEmpty()) {
				throw new IllegalArgumentException("Mã môn học không được để trống.");
			}
			subjectId = subjectId.trim();

			if (subjectId.length() > 5) {
				throw new IllegalArgumentException("Mã môn học không được vượt quá 5 ký tự: " + subjectId);
			}

			if (subjectName == null || subjectName.trim().isEmpty()) {
				throw new IllegalArgumentException("Tên môn học không được để trống cho mã: " + subjectId);
			}
			subjectName = subjectName.trim();

			if (subjectDAO.existsById(subjectId)) {
				throw new IllegalArgumentException("Mã môn học '" + subjectId + "' đã tồn tại trong hệ thống.");
			}

			if (subjectDAO.existsByName(subjectName)) {
				throw new IllegalArgumentException("Tên môn học '" + subjectName + "' đã tồn tại trong hệ thống.");
			}

			Subject subject = new Subject();
			subject.setSubjectId(subjectId);
			subject.setSubjectName(subjectName);
			subject.setDeleted(false);
			subjectDAO.create(subject);
		}
	}

	@Transactional
	public void savePendingActions(String actionsData) {
		if (actionsData == null || actionsData.trim().isEmpty()) {
			return;
		}

		List<SubjectActionDTO> actions = new ArrayList<>();
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
			String subjectId = parts[1].trim();
			String subjectName = parts.length > 2 ? parts[2].trim() : "";

			actions.add(new SubjectActionDTO(type, subjectId, subjectName));
		}

		for (SubjectActionDTO action : actions) {
			if ("ADD".equals(action.getType())) {
				SubjectDTO dto = new SubjectDTO(action.getSubjectId(), action.getSubjectName());
				addSubject(dto);
			} else if ("UPDATE".equals(action.getType())) {
				SubjectDTO dto = new SubjectDTO(action.getSubjectId(), action.getSubjectName());
				updateSubject(dto);
			} else if ("DELETE".equals(action.getType())) {
				SubjectDTO dto = new SubjectDTO();
				dto.setSubjectId(action.getSubjectId());
				deleteSubject(dto);
			}
		}
	}
}