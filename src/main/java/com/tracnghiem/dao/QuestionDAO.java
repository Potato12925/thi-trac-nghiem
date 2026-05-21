package com.tracnghiem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Question;

@Repository
public class QuestionDAO extends GenericDAO<Question> {

	public List<Question> findByKeyword(String keyword) {
		String hql = "FROM BoDe b WHERE b.noiDung LIKE :keyword OR b.monHoc.maMH LIKE :keyword OR b.dapAn LIKE :keyword";
		return getSession().createQuery(hql, Question.class).setParameter("keyword", '%' + keyword + '%').list();
	}

	public boolean existsById(Integer questionId) {
		return findById(questionId) != null;
	}
}