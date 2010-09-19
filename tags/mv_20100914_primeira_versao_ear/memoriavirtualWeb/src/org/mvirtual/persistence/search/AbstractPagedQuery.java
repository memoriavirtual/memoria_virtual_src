package org.mvirtual.persistence.search;

import org.mvirtual.persistence.PersistenceStatics;
import org.mvirtual.persistence.entity.Entity;

import java.util.*;

import org.mvirtual.util.reflect.ReflectionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract implementation for {link:#PagedQuery}.<br/>
 * <p>This abstract class is a helper class for subclassing
 * that implements a basic page handling logic extending
 * the paged concept to indexed/intervaled searching.
 * This can be usefull for using in Hibernate Search Queries, for example.</p>
 * <p>Pages index start with 0 (i.e. zero means the first page)</p>
 * <p>The default value for the number of items per page is defined in PersistenceStatics'
 * field {link:PersistenceStatics#SEARCH_DEFAULT_ITEMS_PER_PAGE}</p>
 * <p>Also, this class instantiates a Class<T> object to reflect
 * the generic class type used in the generic parameter which
 * is available through getModelClass().</p>
 * <p><b>ATTENTION!</b> Modifications to the page number (through {link:#setPage})
 * <b>do modify</b> the firstItem and lastItem values! The inverse is also true:
 * modifications to firstItem ({link:#setFirstItem}) modifies the page number (lastItem value is read-only).</p>
 */
public abstract class AbstractPagedQuery<T extends Entity>
	implements PagedQuery<T>, PersistenceStatics
{
	private final Logger log = LoggerFactory.getLogger(AbstractPagedQuery.class);

	private int page = 0;
	private int ipp = SEARCH_DEFAULT_ITEMS_PER_PAGE;
	private int firstItem = 0;
	private int lastItem = 0;
	private boolean negativePermitted = false;
	private Class<T> modelClass;

	private void updateFirstItem() {
		firstItem = (page * ipp);
	}
	
	protected Class<T> getModelClass() { return modelClass; }

	public AbstractPagedQuery() {
		modelClass = (Class<T>)ReflectionUtils.getRecursiveGenericClassTypes(
				AbstractPagedQuery.class, getClass())[0];
	}

	public int getPage() { return page; }
	public void setPage(int n) {
		page = (n < 0 ? (negativePermitted ? n : 0) : n);
		updateFirstItem();
	}

	public void setItemsPerPage(int n) {
		ipp = (n > 0 ? n : SEARCH_DEFAULT_ITEMS_PER_PAGE);
		updateFirstItem();
	}
	public int getItemsPerPage() { return ipp; }

	public void setNegativePermitted(boolean state) { negativePermitted = state; }
	public boolean isNegativePermitted() { return negativePermitted; }

	public int getFirstItem() { return firstItem; }
	public void setFirstItem(int i) {
		firstItem = (i >= 0 ? i : 0);
		page = (firstItem / ipp);
	}
}
