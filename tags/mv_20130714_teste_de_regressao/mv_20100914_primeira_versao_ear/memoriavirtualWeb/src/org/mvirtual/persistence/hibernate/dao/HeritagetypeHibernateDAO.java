package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.HeritageType;
import org.mvirtual.persistence.dao.HeritagetypeDAO;

/**
 * Hibernate-specific implementation of <tt>HeritagetypeDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class HeritagetypeHibernateDAO
	extends GenericHibernateDAO<HeritageType, Long>
	implements HeritagetypeDAO
{
	// This class intentionally left blank
}
