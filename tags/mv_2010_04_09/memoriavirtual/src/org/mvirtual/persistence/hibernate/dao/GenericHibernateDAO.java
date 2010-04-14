package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.hibernate.IndustrialEstate;
import org.mvirtual.persistence.dao.GenericDAO;

import java.util.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.*;
import org.hibernate.criterion.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the generic CRUD data access operations using Hibernate APIs.
 * <p>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Assuming that you have a traditional 1:1 appraoch for Entity:DAO design.
 * <p>
 * You have to inject a current Hibernate <tt>Session</tt> to use a DAO.
 *
 * @see HibernateDAOFactory
 */
public abstract class GenericHibernateDAO<T, ID extends Serializable>
	implements GenericDAO<T, ID>
{
	private Logger log = LoggerFactory.getLogger(GenericHibernateDAO.class);

	private Class<T> persistentClass;
	private Session session;

	public GenericHibernateDAO() {
		persistentClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
		session = null;
	}

	protected Session getSession()
	{	
		if (session == null) {
			log.debug("Session object is null, getting current session");
			session = IndustrialEstate.getSessionFactory().getCurrentSession();
		}

		if (!session.isOpen()) {
			log.debug("Session is NOT null, but is closed. Getting new session");
			session = IndustrialEstate.getSessionFactory().getCurrentSession();
		}

		return session;
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T findById(ID id, boolean lock) {

		log.debug("findById() with lock turned " + lock);

		T entity;

		if (lock)
			entity = (T) getSession().get(getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (T) getSession().get(getPersistentClass(), id);

		log.debug("Entity found? " + (entity != null));

		return entity;
	}

	public T findById(ID id)
	{
		return findById(id, false);
	}

	public List<T> findAll() {
		log.debug("finding all");
		return findByCriteria();
	}

	public List<T> findByExample(T exampleInstance, String... excludeProperty)
	{
		log.debug("finding by example");
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example =  Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	public List<T> findByCriteria(Criterion... criterion) {
		log.debug("finding by criteria");
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		List<T> list = crit.list();
		log.debug("entities found: " + list.size());
		return list;
	}

	public T makePersistent(T instance) {
		log.debug("persisting instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("persisting successful");
		}
		catch (RuntimeException re) {
			log.error("persisting failed", re);
			throw re;
		}
		return instance;
	}

	public void makeTransient(T entity) {
		log.debug("deleting instance");
		try {
			getSession().delete(entity);
			log.debug("delete successful");
		}
		catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void flush() {
		log.debug("flushing session");
		getSession().flush();
	}

	public void clear() {
		log.debug("clearing session");
		getSession().clear();
	}
}
