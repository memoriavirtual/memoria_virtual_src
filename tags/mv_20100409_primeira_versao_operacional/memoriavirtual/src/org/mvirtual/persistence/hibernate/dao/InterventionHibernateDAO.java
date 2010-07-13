package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.Intervention;
import org.mvirtual.persistence.dao.InterventionDAO;

/**
 * Hibernate-specific implementation of <tt>InterventionDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class InterventionHibernateDAO
	extends GenericHibernateDAO<Intervention, Long>
	implements InterventionDAO
{
	// This class intentionally left blank
}
