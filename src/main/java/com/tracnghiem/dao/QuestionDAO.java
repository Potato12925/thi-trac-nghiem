package com.tracnghiem.dao;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Question;

@Repository
public class QuestionDAO extends GenericDAO<Question> {
	public boolean existsById(Integer questionId) {
		return findById(questionId) != null;
	}

	public long countAvailableQuestions(String maMh, String trinhDo) {
        String hql = "SELECT COUNT(b) FROM Question b WHERE b.subject.subjectId = :maMh AND b.level = :trinhDo";
        return getSession().createQuery(hql, Long.class)
                .setParameter("maMh", maMh)
                .setParameter("trinhDo", trinhDo)
                .uniqueResult();
    }

}