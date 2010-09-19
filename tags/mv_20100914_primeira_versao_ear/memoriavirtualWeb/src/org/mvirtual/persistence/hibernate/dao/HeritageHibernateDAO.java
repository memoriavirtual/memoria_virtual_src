package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.dao.HeritageDAO;

/**
 * Hibernate-specific implementation of <tt>HeritageDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class HeritageHibernateDAO
	extends GenericHibernateDAO<Heritage, Long>
	implements HeritageDAO
{
	// This class intentionally left blank
}
