package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.Descriptor;
import org.mvirtual.persistence.dao.DescriptorDAO;

/**
 * Hibernate-specific implementation of <tt>DescriptorDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class DescriptorHibernateDAO
	extends GenericHibernateDAO<Descriptor, Long>
	implements DescriptorDAO
{
	// This class intentionally left blank
}
