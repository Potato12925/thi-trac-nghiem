package com.tracnghiem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracnghiem.dao.SubjectDAO;
import com.tracnghiem.dto.SubjectDTO;
import com.tracnghiem.entity.Subject;

@Service
public class SubjectService {

	@Autowired
	private SubjectDAO subjectDAO;

	private Subject changeToEntity(SubjectDTO dto) {
		Subject subject = new Subject();

		subject.setSubjectId(dto.getSubjectId());
		subject.setSubjectName(dto.getSubjectName());
        
		return subject;
	}

	public List<Subject> getAllSubjects() {
		return subjectDAO.findAll();
	}

	public Subject getSubjectById(String subjectId) {
		return subjectDAO.findById(subjectId);
	}

	private void validateSubjectDoesNotExist(String subjectId) {
		if (subjectDAO.existsById(subjectId)) {
			throw new IllegalArgumentException("Mã môn học đã tồn tại");
		}
	}

	public void addSubject(SubjectDTO dto) {
		validateSubjectDoesNotExist(dto.getSubjectId());

		Subject subject = changeToEntity(dto);

		subjectDAO.create(subject);
	}

	public void updateSubject(SubjectDTO dto) {
		Subject subject = changeToEntity(dto);

		subjectDAO.update(subject);
	}

	public void deleteSubject(SubjectDTO dto) {
		Subject subject = changeToEntity(dto);

		subjectDAO.delete(subject);
	}
}
