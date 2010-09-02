package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.InformationSource;
import org.mvirtual.persistence.dao.InformationsourceDAO;

/**
 * Hibernate-specific implementation of <tt>InformationsourceDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class InformationsourceHibernateDAO
	extends GenericHibernateDAO<InformationSource, Long>
	implements InformationsourceDAO
{
	// This class intentionally left blank
}
