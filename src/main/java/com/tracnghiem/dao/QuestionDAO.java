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

	public long countByLecturer(String lecturerId) {
		String hql = "SELECT COUNT(q) FROM Question q "
				+ "WHERE q.deleted = false AND function('ltrim', function('rtrim', q.lecturer.lecturerId)) = :lecturerId";

		Long count = getSession()
				.createQuery(hql, Long.class)
				.setParameter("lecturerId", lecturerId)
				.uniqueResult();

		return count == null ? 0L : count.longValue();
	}

	public Question findLatestQuestionByLecturer(String lecturerId) {
		String hql = "FROM Question q "
				+ "JOIN FETCH q.subject s "
				+ "WHERE q.deleted = false "
				+ "AND function('ltrim', function('rtrim', q.lecturer.lecturerId)) = :lecturerId "
				+ "ORDER BY q.questionId DESC";

		List<Question> questions = getSession()
				.createQuery(hql, Question.class)
				.setParameter("lecturerId", lecturerId)
				.setMaxResults(1)
				.list();

		return questions.isEmpty() ? null : questions.get(0);
	}

	public long countAvailableQuestions(String maMh, String trinhDo) {
		String hql = "SELECT COUNT(b) FROM Question b WHERE b.deleted = false AND b.subject.subjectId = :maMh AND b.level = :trinhDo";
		return getSession().createQuery(hql, Long.class).setParameter("maMh", maMh).setParameter("trinhDo", trinhDo)
				.uniqueResult();
	}

	public List<Question> getRandomQuestions(String maMh, String trinhDo, int limit) {
		String hql = "FROM Question b WHERE b.deleted = false AND b.subject.subjectId = :maMh AND b.level = :trinhDo";
		List<Question> list = getSession().createQuery(hql, Question.class).setParameter("maMh", maMh)
				.setParameter("trinhDo", trinhDo).getResultList();
		Collections.shuffle(list);
		return list.subList(0, Math.min(limit, list.size()));
	}

	public List<Question> findPage(int page, int pageSize, String keyword) {
		return findPage(page, pageSize, keyword, null);
	}

	@Override
	public List<Question> findPage(int page, int pageSize) {
		String hql = "FROM Question q WHERE q.deleted = false ORDER BY q.questionId";
		int offset = (page - 1) * pageSize;
		return getSession().createQuery(hql, Question.class).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	public List<Question> findPage(int page, int pageSize, String keyword, String lecturerId) {
		String hql = "FROM Question q WHERE q.deleted = false";
		String trimmedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
		boolean hasKeyword = !trimmedKeyword.isEmpty();
		boolean hasLecturerFilter = lecturerId != null && !lecturerId.trim().isEmpty();

		if (hasKeyword) {
			hql += " AND (" + "lower(q.content) LIKE :keyword OR " + "lower(q.level) LIKE :keyword OR "
					+ "lower(q.subject.subjectId) LIKE :keyword OR " + "lower(q.lecturer.lecturerId) LIKE :keyword"
					+ ")";
		}

		if (hasLecturerFilter) {
			hql += " AND q.lecturer.lecturerId = :lecturerId";
		}

		hql += " ORDER BY q.questionId";

		Query<Question> query = getSession().createQuery(hql, Question.class);
		if (hasKeyword) {
			query.setParameter("keyword", "%" + trimmedKeyword + "%");
		}
		if (hasLecturerFilter) {
			query.setParameter("lecturerId", lecturerId);
		}

		int offset = (page - 1) * pageSize;
		return query.setFirstResult(offset).setMaxResults(pageSize).list();
	}

	@Override
	public long countAll() {
		String hql = "SELECT COUNT(q) FROM Question q WHERE q.deleted = false";
		return getSession().createQuery(hql, Long.class).uniqueResult();
	}

	public long countAll(String keyword) {
		return countAll(keyword, null);
	}

	public long countAll(String keyword, String lecturerId) {
		String hql = "SELECT COUNT(q) FROM Question q WHERE q.deleted = false";
		String trimmedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
		boolean hasKeyword = !trimmedKeyword.isEmpty();
		boolean hasLecturerFilter = lecturerId != null && !lecturerId.trim().isEmpty();

		if (hasKeyword) {
			hql += " AND (" + "lower(q.content) LIKE :keyword OR " + "lower(q.level) LIKE :keyword OR "
					+ "lower(q.subject.subjectId) LIKE :keyword OR " + "lower(q.lecturer.lecturerId) LIKE :keyword"
					+ ")";
		}

		if (hasLecturerFilter) {
			hql += " AND q.lecturer.lecturerId = :lecturerId";
		}

		Query<Long> query = getSession().createQuery(hql, Long.class);
		if (hasKeyword) {
			query.setParameter("keyword", "%" + trimmedKeyword + "%");
		}
		if (hasLecturerFilter) {
			query.setParameter("lecturerId", lecturerId);
		}
		return query.uniqueResult();
	}

	public List<Question> findAllQuestions(String lecturerId) {
		String hql = "FROM Question q WHERE q.deleted = false";
		boolean hasLecturer = lecturerId != null && !lecturerId.trim().isEmpty();
		if (hasLecturer) {
			hql += " AND q.lecturer.lecturerId = :lecturerId";
		}
		hql += " ORDER BY q.questionId";
		Query<Question> query = getSession().createQuery(hql, Question.class);
		if (hasLecturer) {
			query.setParameter("lecturerId", lecturerId);
		}
		return query.list();
	}
}
