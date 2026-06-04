package com.tracnghiem.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericDAO<T> {

	@Autowired
	protected SessionFactory factory;

	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDAO() {

		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return factory.getCurrentSession();
	}

	// CREATE
	public void create(T entity) {
		getSession().save(entity);
	}

	// UPDATE
	public void update(T entity) {
		getSession().update(entity);
	}

	// DELETE
	public void delete(T entity) {
		getSession().delete(entity);
	}

	// FIND BY ID
	public T findById(Serializable id) {
		return getSession().get(entityClass, id);
	}

	// FIND ALL
	public List<T> findAll() {

		String hql = "FROM " + entityClass.getSimpleName();

		return getSession().createQuery(hql, entityClass).list();
	}

	public List<T> findPage(int page, int pageSize) {
		String hql = "FROM " + entityClass.getSimpleName();

		int offset = (page - 1) * pageSize;

		return getSession().createQuery(hql, entityClass).setFirstResult(offset).setMaxResults(pageSize).list();
	}

	public long countAll() {
		String hql = "SELECT COUNT(*) FROM " + entityClass.getSimpleName();
		return getSession().createQuery(hql, Long.class).uniqueResult();
	}
}
