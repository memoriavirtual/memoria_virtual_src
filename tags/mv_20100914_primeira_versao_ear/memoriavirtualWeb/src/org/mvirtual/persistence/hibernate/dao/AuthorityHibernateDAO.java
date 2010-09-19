package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.Authority;
import org.mvirtual.persistence.dao.AuthorityDAO;

/**
 * Hibernate-specific implementation of <tt>AuthorityDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class AuthorityHibernateDAO
	extends GenericHibernateDAO<Authority, Long>
	implements AuthorityDAO
{
	// This class intentionally left blank
}
