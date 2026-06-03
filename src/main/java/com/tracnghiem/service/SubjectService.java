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

    public long countSubjects() {
        return subjectDAO.countAll();
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
        ensureSubjectExists(dto.getSubjectId());

        Subject subject = mapToEntity(dto);

        subjectDAO.delete(subject);
    }
}
