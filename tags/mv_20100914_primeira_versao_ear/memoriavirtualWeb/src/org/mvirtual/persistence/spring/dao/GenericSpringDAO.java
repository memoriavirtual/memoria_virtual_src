package org.mvirtual.persistence.spring.dao;

import org.mvirtual.persistence.dao.GenericDAO;
import org.mvirtual.persistence.entity.PersistentObject;

import java.util.Collection;
import java.util.List;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Implements the generic CRUD data access operations using Spring API.
 * <p>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Assuming that you have a traditional 1:1 appraoch for Entity:DAO design.
 * <p>
 * A Hibernate SessionFactory will be injected by Spring.
 *
 * @see SpringDAOFactory
 */
public abstract class GenericSpringDAO<T, ID extends Serializable>
	extends HibernateDaoSupport
	implements GenericDAO<T, ID>
{
	private Logger log = LoggerFactory.getLogger(GenericSpringDAO.class);

	private final Class<T> pClass = fetchPersistentClass();

	/*public GenericSpringDAO(Class<T> clazz) {
		pClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
		pClass = clazz;
	}*/

	private Class<T> fetchPersistentClass()
	{
		Type type = getClass().getGenericSuperclass();
		while (true) {
			if (type instanceof ParameterizedType) {
				Type[] arguments = ((ParameterizedType)type).getActualTypeArguments();
				for (Type argument : arguments) {
					if (argument instanceof Class &&
							PersistentObject.class.isAssignableFrom(((Class)argument))) {
						return (Class)argument;
					}
				}
			}
			type = ((Class)type).getGenericSuperclass();
			if (type == Object.class) {
				throw new RuntimeException("Could not find a PersistentObject subclass parameterized type");
			}
		}
	}

	public Class<T> getPersistentClass() { return pClass; }

        @Override
	public T findById(ID id) {
		return findById(id, false);
	}

        @Override
	public T findById(ID id, boolean lock) {

		log.debug("findById() with lock turned " + lock);

		T entity = null;

		if (lock)
			entity = (T) getHibernateTemplate().get(getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (T) getHibernateTemplate().get(getPersistentClass(), id);

		log.debug("Entity found: " + entity);

		return entity;
	}

        @Override
	public List<T> findAll() {
		log.debug("finding all");
		return findByCriteria();
	}

        @Override
	public List<T> findByExample(T exampleInstance, String... excludeProperties)
	{
		log.debug("finding by example");
		DetachedCriteria crit = DetachedCriteria.forClass(getPersistentClass());
		Example ex = Example.create(exampleInstance);
		for (String excludeProperty : excludeProperties) {
			ex.excludeProperty(excludeProperty);
		}
		crit.add(ex);
		return getHibernateTemplate().findByCriteria(crit);
	}

        @Override
	public T makePersistent(T entity) {
		log.debug("persisting entity");
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	public void makeAllPersistent(Collection<T> entities) {
		log.debug("persisting all entities");
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

        @Override
	public void makeTransient(T entity) {
		log.debug("deleting entity");
		getHibernateTemplate().delete(entity);
	}

	public void makeAllTransient(Collection<T> entities) {
		log.debug("deleting all entities");
		getHibernateTemplate().deleteAll(entities);
	}

        @Override
	public void flush() {
		log.debug("flushing session");
		getHibernateTemplate().flush();
	}

        @Override
	public void clear() {
		log.debug("clearing session");
		getHibernateTemplate().clear();
	}

	protected List<T> findByCriteria(Criterion... criterion) {
		log.debug("finding by criteria");
		DetachedCriteria crit = DetachedCriteria.forClass(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		List<T> list = getHibernateTemplate().findByCriteria(crit);
		log.debug("entities found: " + list.size());
		return list;
	}
}
