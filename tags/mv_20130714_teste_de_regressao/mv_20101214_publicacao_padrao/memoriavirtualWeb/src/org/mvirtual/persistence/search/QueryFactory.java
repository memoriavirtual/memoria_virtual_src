package org.mvirtual.persistence.search;

import org.mvirtual.persistence.entity.Entity;

import java.util.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public abstract class QueryFactory
{
//	private static Logger log = LoggerFactory.getLogger(QueryFactory.class);
	
	public static final Class HIBERNATE = org.mvirtual.persistence.hibernate.search.HibernateQueryFactory.class;
	
	/**
	 * Factory method for instantiation of concrete factories.
	 */
	public static QueryFactory instance(Class factory) throws Exception {
		try {
//			log.debug("Creating concrete Query factory: " + factory);
			return (QueryFactory)factory.newInstance();
		} catch (Exception ex) {
			throw new Exception("Couldn't create QueryFactory: " + factory);
		}
	}

	public Map<String, Query<? extends Entity>> getModelQueryMap() {
		return new HashMap<String, Query<? extends Entity>>(){{
			put("heritage", getHeritageQuery());
			put("multimedia", getMultimediaQuery());
		}};
	}

	public abstract HeritageQuery getHeritageQuery();
	public abstract MultimediaQuery getMultimediaQuery();
}
