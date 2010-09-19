package org.mvirtual.persistence.search;

import org.mvirtual.persistence.entity.Entity;

import java.util.*;

/**
 * Generic search query interface.
 */
public interface Query<T extends Entity>
{
	/**
	 * Returns the query defined in the query instance.
	 */
	public String getQuery();

	/**
	 * Sets a query to be executed.
	 */
	public void setQuery(String query);
	
	/**
	 * Returns the query set defined in the query instance.
	 */
	public String[] getMultiQuery();

	/**
	 * Sets a set of queries to be executed.
	 */
	public void setMultiQuery(String[] query);
	
	/**
	 * Returns the result set after a call to {link#execute}.
	 */
	public List<T> getResult();

	/**
	 * Perform query execution.
	 * This should be the real 'do-all-the-work' method. It should do the most expensive
	 * operations like database queries.<br/>
	 */
	public void execute();
}
