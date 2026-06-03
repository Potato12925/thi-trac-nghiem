package com.tracnghiem.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.tracnghiem.entity.Question;

@Repository
public class QuestionDAO extends GenericDAO<Question> {

    public boolean existsById(Integer questionId) {
        return findById(questionId) != null;
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

    public List<Question> findPage(int page, int pageSize, String keyword) {
        String hql = "FROM Question q";
        String trimmedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        boolean hasKeyword = !trimmedKeyword.isEmpty();

        if (hasKeyword) {
            hql += " WHERE lower(q.content) LIKE :keyword OR lower(q.level) LIKE :keyword OR lower(q.subject.subjectId) LIKE :keyword OR lower(q.lecturer.lecturerId) LIKE :keyword";
        }
        hql += " ORDER BY q.questionId";

        Query<Question> query = getSession().createQuery(hql, Question.class);
        if (hasKeyword) {
            query.setParameter("keyword", "%" + trimmedKeyword + "%");
        }

        int offset = (page - 1) * pageSize;
        return query.setFirstResult(offset).setMaxResults(pageSize).list();
    }

    public long countAll(String keyword) {
        String hql = "SELECT COUNT(q) FROM Question q";
        String trimmedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        boolean hasKeyword = !trimmedKeyword.isEmpty();

        if (hasKeyword) {
            hql += " WHERE lower(q.content) LIKE :keyword OR lower(q.level) LIKE :keyword OR lower(q.subject.subjectId) LIKE :keyword OR lower(q.lecturer.lecturerId) LIKE :keyword";
        }

        Query<Long> query = getSession().createQuery(hql, Long.class);
        if (hasKeyword) {
            query.setParameter("keyword", "%" + trimmedKeyword + "%");
        }
        return query.uniqueResult();
    }

}
