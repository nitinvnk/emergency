package com.emergency.src.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@SuppressWarnings("unchecked")
@Repository
public abstract class EmrDAOImpl<E, K extends Serializable> implements IEmrDAO<E, K> {

	@Autowired
	private SessionFactory sessionFactory;

	protected Class<? extends E> daoType;

	@SuppressWarnings("rawtypes")
	public EmrDAOImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		daoType = (Class) pt.getActualTypeArguments()[0];
	}

	@Override
	public void add(E entity) {
		currentSession().save(entity);
	}

	@Override
	public void saveOrUpdate(E entity) {
		currentSession().saveOrUpdate(entity);
	}

	@Override
	public void update(E entity) {
		currentSession().saveOrUpdate(entity);
	}

	@Override
	public void remove(E entity) {
		currentSession().delete(entity);
	}

	@Override
	public E find(K key) {
		return (E) currentSession().get(daoType, key);
	}

	@Override
	public List<E> getAll() {
		return currentSession().createCriteria(daoType).list();
	}

	public E get(Class<E> entityClass, Map<String, String> queryMap) {
		Criteria criteria = currentSession().createCriteria(entityClass);
		if (!queryMap.isEmpty()) {
			Iterator<String> iterator = queryMap.keySet().iterator();
			while (iterator.hasNext()) {
				String column = iterator.next();
				String columnValue = queryMap.get(column);
				criteria.add(Restrictions.eq(column, columnValue));
			}
			if (!CollectionUtils.isEmpty(criteria.list())) {
				return (E) criteria.list().get(0);
			}
		}
		return null;

	}

	public E get(Class<E> entityClass, String column, String columnValue) {
		Criteria criteria = currentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(column, columnValue));
		if (!CollectionUtils.isEmpty(criteria.list())) {
			return (E) criteria.list().get(0);
		}
		return null;
	}

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
}
