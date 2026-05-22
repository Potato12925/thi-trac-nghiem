package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Question;

@Repository
public class QuestionDAO extends GenericDAO<Question> {
	public boolean existsById(Integer questionId) {
		return findById(questionId) != null;
	}
}