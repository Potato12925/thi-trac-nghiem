package com.tracnghiem.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Question;

@Repository
public class QuestionDAO extends GenericDAO<Question> {

    public boolean existsById(Integer questionId) {
        return findById(questionId) != null;
    }

    public List<Question> getQuestions(int page, int pageSize) {
        String hql = "FROM Question";

        int offset = (page - 1) * pageSize;

        return getSession().createQuery(hql, Question.class).setFirstResult(offset).setMaxResults(pageSize)
                .getResultList();
    }

    public long countQuestions() {
        String hql = "SELECT COUNT(q) FROM Question q";
        return getSession().createQuery(hql, Long.class).uniqueResult();
    }

    public long countAvailableQuestions(String maMh, String trinhDo) {
        String hql = "SELECT COUNT(b) FROM Question b WHERE b.subject.subjectId = :maMh AND b.level = :trinhDo";
        return getSession().createQuery(hql, Long.class).setParameter("maMh", maMh).setParameter("trinhDo", trinhDo)
                .uniqueResult();
    }

    public List<Question> getRandomQuestions(String maMh, String trinhDo, int limit) {
        String hql = "FROM Question b WHERE b.subject.subjectId = :maMh AND b.level = :trinhDo";
        List<Question> list = getSession().createQuery(hql, Question.class).setParameter("maMh", maMh)
                .setParameter("trinhDo", trinhDo).getResultList();
        Collections.shuffle(list);
        return list.subList(0, Math.min(limit, list.size()));
    }

}
