package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.relation.HeritageSubject;
import org.mvirtual.persistence.entity.relation.embedded.HeritageSubjectId;
import org.mvirtual.persistence.dao.HeritageSubjectDAO;

/**
 * Hibernate-specific implementation of <tt>HeritageSubjectDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class HeritageSubjectHibernateDAO
	extends GenericHibernateDAO<HeritageSubject, HeritageSubjectId>
	implements HeritageSubjectDAO
{
	// This class intentionally left blank
}
