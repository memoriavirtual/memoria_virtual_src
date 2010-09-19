package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.Multimedia;
import org.mvirtual.persistence.dao.MultimediaDAO;

/**
 * Hibernate-specific implementation of <tt>MultimediaDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class MultimediaHibernateDAO
	extends GenericHibernateDAO<Multimedia, Long>
	implements MultimediaDAO
{
	// This class intentionally left blank
}
