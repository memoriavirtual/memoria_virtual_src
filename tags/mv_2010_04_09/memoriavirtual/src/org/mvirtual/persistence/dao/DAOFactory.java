package org.mvirtual.persistence.dao;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Defines an abstract DAO Factory that produces concrete DAOs.
 * <p>
 * This abstract class should be the top-level instatiation class that
 * generates concrete DAO Factories with the <tt>instance()</tt> method called
 * passing the implementing class type of this abstract class as argument.
 * <p>
 * When you implement a new persistence system, you should add here a constant
 * representing its class, so this class will know about it and could instance() it.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public abstract class DAOFactory
{
//	private static Logger log = LoggerFactory.getLogger(DAOFactory.class);

	/**
	 * Creates a standalone DAOFactory that returns unmanaged DAO
	 * beans for use in any environment Hibernate has been configured
	 * for. Uses IndustrialEstate/SessionFactory and Hibernate context
	 * propagation (CurrentSessionContext), thread-bound or transaction-bound,
	 * and transaction scoped.
	 */
	public static final Class HIBERNATE = org.mvirtual.persistence.hibernate.dao.HibernateDAOFactory.class;

	/**
	 * Factory method for instantiation of concrete factories.
	 */
	public static DAOFactory instance(Class factory) throws Exception {
		try {
//			log.debug("Creating concrete DAO factory: " + factory);
			return (DAOFactory)factory.newInstance();
		} catch (Exception ex) {
			throw new Exception("Couldn't create DAOFactory: " + factory);
		}
	}

	public abstract AuthorityDAO getAuthorityDAO();
	public abstract InstitutionTelephoneDAO getInstitutionTelephoneDAO();
	public abstract UserInstitutionDAO getUserInstitutionDAO();
	public abstract InstitutionDAO getInstitutionDAO();
	public abstract HeritagetypeDAO getHeritagetypeDAO();
	public abstract UserDAO getUserDAO();
	public abstract HeritageSubjectDAO getHeritageSubjectDAO();
	public abstract InterventionDAO getInterventionDAO();
	public abstract UserHeritageReviewDAO getUserHeritageReviewDAO();
	public abstract HeritageDAO getHeritageDAO();
	public abstract InformationsourceDAO getInformationsourceDAO();
	public abstract AuthorityHeritageDAO getAuthorityHeritageDAO();
	public abstract MultimediaDAO getMultimediaDAO();
	public abstract HeritageResearcherresponsibleDAO getHeritageResearcherresponsibleDAO();
	public abstract UserHeritageCatalogueDAO getUserHeritageCatalogueDAO();
	public abstract DescriptorDAO getDescriptorDAO();
}
