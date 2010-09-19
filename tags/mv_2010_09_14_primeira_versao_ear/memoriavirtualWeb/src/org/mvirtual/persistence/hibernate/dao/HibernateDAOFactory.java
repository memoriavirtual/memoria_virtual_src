package org.mvirtual.persistence.hibernate.dao;

import org.mvirtual.persistence.dao.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Returns Hibernate-specific instances of DAOs.
 * <p>
 * If for a particular DAO there is no additional non-CRUD functionality, we use
 * a nested static class to implement the interface in a generic way. This allows clean
 * refactoring later on, should the interface implement business data access
 * methods at some later time. Then, we would externalize the implementation into
 * its own first-level class.
 *
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class HibernateDAOFactory extends DAOFactory
{
//	private Logger log = LoggerFactory.getLogger(HibernateDAOFactory.class);

        @Override
	public AuthorityDAO getAuthorityDAO() {
		AuthorityDAO instance = null;
		try {
			instance = (AuthorityDAO) instantiateDAO(AuthorityHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate AuthorityDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public InstitutionTelephoneDAO getInstitutionTelephoneDAO() {
		InstitutionTelephoneDAO instance = null;
		try {
			instance = (InstitutionTelephoneDAO) instantiateDAO(InstitutionTelephoneHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate InstitutionTelephoneDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public UserInstitutionDAO getUserInstitutionDAO() {
		UserInstitutionDAO instance = null;
		try {
			instance = (UserInstitutionDAO) instantiateDAO(UserInstitutionHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate UserInstitutionDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public InstitutionDAO getInstitutionDAO() {
		InstitutionDAO instance = null;
		try {
			instance = (InstitutionDAO) instantiateDAO(InstitutionHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate InstitutionDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public HeritagetypeDAO getHeritagetypeDAO() {
		HeritagetypeDAO instance = null;
		try {
			instance = (HeritagetypeDAO) instantiateDAO(HeritagetypeHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate HeritagetypeDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public UserDAO getUserDAO() {
		UserDAO instance = null;
		try {
			instance = (UserDAO) instantiateDAO(UserHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate UserDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public HeritageSubjectDAO getHeritageSubjectDAO() {
		HeritageSubjectDAO instance = null;
		try {
			instance = (HeritageSubjectDAO) instantiateDAO(HeritageSubjectHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate HeritageSubjectDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public InterventionDAO getInterventionDAO() {
		InterventionDAO instance = null;
		try {
			instance = (InterventionDAO) instantiateDAO(InterventionHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate InterventionDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public UserHeritageReviewDAO getUserHeritageReviewDAO() {
		UserHeritageReviewDAO instance = null;
		try {
			instance = (UserHeritageReviewDAO) instantiateDAO(UserHeritageReviewHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate UserHeritageReviewDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public HeritageDAO getHeritageDAO() {
		HeritageDAO instance = null;
		try {
			instance = (HeritageDAO) instantiateDAO(HeritageHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate HeritageDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public InformationsourceDAO getInformationsourceDAO() {
		InformationsourceDAO instance = null;
		try {
			instance = (InformationsourceDAO) instantiateDAO(InformationsourceHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate InformationsourceDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public AuthorityHeritageDAO getAuthorityHeritageDAO() {
		AuthorityHeritageDAO instance = null;
		try {
			instance = (AuthorityHeritageDAO) instantiateDAO(AuthorityHeritageHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate AuthorityHeritageDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public MultimediaDAO getMultimediaDAO() {
		MultimediaDAO instance = null;
		try {
			instance = (MultimediaDAO) instantiateDAO(MultimediaHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate MultimediaDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public HeritageResearcherresponsibleDAO getHeritageResearcherresponsibleDAO() {
		HeritageResearcherresponsibleDAO instance = null;
		try {
			instance = (HeritageResearcherresponsibleDAO) instantiateDAO(HeritageResearcherresponsibleHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate HeritageResearcherresponsibleDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public UserHeritageCatalogueDAO getUserHeritageCatalogueDAO() {
		UserHeritageCatalogueDAO instance = null;
		try {
			instance = (UserHeritageCatalogueDAO) instantiateDAO(UserHeritageCatalogueHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate UserHeritageCatalogueDAO. Error: " + e);
		}
		return instance;
	}

        @Override
	public DescriptorDAO getDescriptorDAO() {
		DescriptorDAO instance = null;
		try {
			instance = (DescriptorDAO) instantiateDAO(DescriptorHibernateDAO.class);
		} catch (Exception e) {
//			log.debug("Coult not instatiate DescriptorDAO. Error: " + e);
		}
		return instance;
	}

	private GenericHibernateDAO instantiateDAO(Class daoClass)
		throws Exception
	{
		try {
//			log.debug("Instantiating DAO: " + daoClass);
			return (GenericHibernateDAO)daoClass.newInstance();
		} catch (Exception ex) {
			throw new Exception("Can not instantiate DAO: " + daoClass, ex);
		}
	}
}

