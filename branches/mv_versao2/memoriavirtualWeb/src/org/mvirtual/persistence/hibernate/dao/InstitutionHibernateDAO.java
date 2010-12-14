package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.dao.InstitutionDAO;

/**
 * Hibernate-specific implementation of <tt>InstitutionDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class InstitutionHibernateDAO
	extends GenericHibernateDAO<Institution, Long>
	implements InstitutionDAO
{
	// This class intentionally left blank
}
