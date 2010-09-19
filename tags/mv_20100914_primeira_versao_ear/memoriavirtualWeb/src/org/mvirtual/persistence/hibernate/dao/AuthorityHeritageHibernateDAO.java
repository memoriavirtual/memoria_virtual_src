package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.relation.AuthorityHeritage;
import org.mvirtual.persistence.entity.relation.embedded.AuthorityHeritageId;
import org.mvirtual.persistence.dao.AuthorityHeritageDAO;

/**
 * Hibernate-specific implementation of <tt>AuthorityHeritageDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class AuthorityHeritageHibernateDAO
	extends GenericHibernateDAO<AuthorityHeritage, AuthorityHeritageId>
	implements AuthorityHeritageDAO
{
	// This class intentionally left blank
}
