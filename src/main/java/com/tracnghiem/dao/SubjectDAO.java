package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Subject;

@Repository
public class SubjectDAO extends GenericDAO<Subject> {
    public boolean existsById(String subjectId) {
        return findById(subjectId) != null;
    }
}