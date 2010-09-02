package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.relation.InstitutionTelephone;
import org.mvirtual.persistence.entity.relation.embedded.InstitutionTelephoneId;
import org.mvirtual.persistence.dao.InstitutionTelephoneDAO;

/**
 * Hibernate-specific implementation of <tt>InstitutionTelephoneDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class InstitutionTelephoneHibernateDAO
	extends GenericHibernateDAO<InstitutionTelephone, InstitutionTelephoneId>
	implements InstitutionTelephoneDAO
{
	// This class intentionally left blank
}
