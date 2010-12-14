package org.mvirtual.persistence.spring.dao;

import org.mvirtual.persistence.entity.User;
import org.mvirtual.persistence.dao.UserDAO;

/**
 * Spring-specific implementation of the <tt>UserDAO</tt>
 * non-CRUD data access object.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class UserSpringDAO
	extends GenericSpringDAO<User, Long>
	implements UserDAO
{
}

