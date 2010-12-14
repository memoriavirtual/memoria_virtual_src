package org.mvirtual.persistence.search;

import org.mvirtual.persistence.entity.Entity;

import java.util.*;

/**
 * Generic search query interface with pagination.
 */
public interface PagedQuery<T extends Entity>
	extends Query<T>
{
	/**
	 * Sets the number of the current page.
	 */
	public void setPage(int n);
	
	/**
	 * Gets the number of the current page.
	 */
	public int getPage();
	
	/**
	 * Sets the number of items per page.
	 */
	public void setItemsPerPage(int n);
	
	/**
	 * Gets the number of items per page.
	 */
	public int getItemsPerPage();
	
	/**
	 * Sets the permission to set the page number as a negative number.
	 */
	public void setNegativePermitted(boolean permission);

	/**
	 * Gets the current permission to set the page number as a negative number.
	 */
	public boolean isNegativePermitted();
}
