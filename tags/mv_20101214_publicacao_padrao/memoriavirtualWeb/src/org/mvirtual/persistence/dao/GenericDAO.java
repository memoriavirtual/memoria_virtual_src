package org.mvirtual.persistence.dao;

import java.util.List;
import java.io.Serializable;

/**
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UDPATE statement function) that provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
 */
public interface GenericDAO<T, ID extends Serializable>
{
	public T findById(ID id);
	public T findById(ID id, boolean lock);
	public List<T> findAll();
	public List<T> findByExample(T exampleInstance, String... excludeProperty);
	public T makePersistent(T entity);
	public void makeTransient(T entity);
	public void flush();
	public void clear();
}
