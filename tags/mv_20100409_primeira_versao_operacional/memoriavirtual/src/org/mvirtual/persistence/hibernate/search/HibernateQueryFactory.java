package org.mvirtual.persistence.hibernate.search;

import org.mvirtual.persistence.search.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Returns Hibernate-specific instances of Queries.
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class HibernateQueryFactory extends QueryFactory
{
//	private Logger log = LoggerFactory.getLogger(HibernateQueryFactory.class);
	
	private Query instantiateQuery(Class<? extends Query> clazz)
	{
		try {
//			log.debug("Instantiating Query: " + clazz);
			return (Query)clazz.newInstance();
		} catch (Exception ex) {
//			log.error("Could not instantiate Query of type " + clazz + ": " + ex);
			throw new RuntimeException("Could not instantiate Query: " + clazz, ex);
		}
	}

        @Override
	public HeritageQuery getHeritageQuery() {
		return (HeritageQuery)instantiateQuery(HibernateHeritageQuery.class);
	}

        @Override
	public MultimediaQuery getMultimediaQuery() {
		return (MultimediaQuery)instantiateQuery(HibernateMultimediaQuery.class);
	}
}
