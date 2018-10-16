package com.emergency.src.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface IEmrDAO<E, K> {
	public void add(E entity);

	public void saveOrUpdate(E entity);

	public void update(E entity);

	public void remove(E entity);

	public E find(K key);

	public List<E> getAll();
}
