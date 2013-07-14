package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.User;
import org.mvirtual.persistence.dao.UserDAO;

/**
 * Hibernate-specific implementation of <tt>UserDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class UserHibernateDAO
	extends GenericHibernateDAO<User, Long>
	implements UserDAO
{
	// This class intentionally left blank
}
