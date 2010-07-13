package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.entity.relation.UserHeritageReview;
import org.mvirtual.persistence.entity.relation.embedded.UserHeritageReviewId;
import org.mvirtual.persistence.dao.UserHeritageReviewDAO;

/**
 * Hibernate-specific implementation of <tt>UserHeritageReviewDAO</tt>.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class UserHeritageReviewHibernateDAO
	extends GenericHibernateDAO<UserHeritageReview, UserHeritageReviewId>
	implements UserHeritageReviewDAO
{
	// This class intentionally left blank
}
