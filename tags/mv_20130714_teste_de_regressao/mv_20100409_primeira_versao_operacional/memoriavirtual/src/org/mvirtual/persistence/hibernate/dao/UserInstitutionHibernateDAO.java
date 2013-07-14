package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.relation.UserInstitution;
import org.mvirtual.persistence.entity.relation.embedded.UserInstitutionId;
import org.mvirtual.persistence.dao.UserInstitutionDAO;

/**
 * Hibernate-specific implementation of <tt>UserInstitutionDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class UserInstitutionHibernateDAO
	extends GenericHibernateDAO<UserInstitution, UserInstitutionId>
	implements UserInstitutionDAO
{
	// This class intentionally left blank
}
