package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.relation.UserHeritageCatalogue;
import org.mvirtual.persistence.entity.relation.embedded.UserHeritageCatalogueId;
import org.mvirtual.persistence.dao.UserHeritageCatalogueDAO;

/**
 * Hibernate-specific implementation of <tt>UserHeritageCatalogueDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class UserHeritageCatalogueHibernateDAO
	extends GenericHibernateDAO<UserHeritageCatalogue, UserHeritageCatalogueId>
	implements UserHeritageCatalogueDAO
{
	// This class intentionally left blank
}
