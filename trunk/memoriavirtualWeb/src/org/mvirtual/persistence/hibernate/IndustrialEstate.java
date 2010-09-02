package org.mvirtual.persistence.hibernate;

import org.mvirtual.persistence.dao.DAOFactory;
import org.mvirtual.persistence.search.QueryFactory;

import java.util.Properties;
import java.io.InputStream;
import javax.naming.NamingException;
import javax.naming.InitialContext;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.AnnotationConfiguration;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Class that manages all factories.
 * <p>
 * Uses a static initializer to initialize the factories
 * <tt>DAOFactory</tt> and Hibernate's <tt>SessionFactory</tt>.
 * @author Kiyoshi Murata <kbmurata@gmail.com>
 */
public class IndustrialEstate {
//    private static Logger log = LoggerFactory.getLogger(IndustrialEstate.class);

    private static AnnotationConfiguration configuration;
    private static SessionFactory sessionFactory;
    private static DAOFactory daoFactory;
    private static QueryFactory queryFactory;

    static {
        try {
		//	log.debug("Initializing the industrial estate...");

            configuration = new AnnotationConfiguration();
            configuration.configure();

//			log.debug("Searching for user-defined configurations");

            InputStream ins = IndustrialEstate.class.getClassLoader().getResourceAsStream("hibernate.properties");

            if (ins != null) {
                Properties p = new Properties();
                p.load(ins);
////				log.debug("Injecting user-saved dabase configuration into Hibernate's Configuration");
                configuration.setProperties(p);
//				log.debug("Configuration properties:\n--- START PROPERTIES ---");
//				log.debug(configuration.getProperties().toString());
//				log.debug("--- END PROPERTIES ---");
            } else {
////				log.debug("User-defined configurations not found");
            }

            rebuildSessionFactory(configuration);
//			log.debug("Hibernate SessionFactory is up and running");

            daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
//			log.debug("DAOFactory is up and running");

            queryFactory = QueryFactory.instance(QueryFactory.HIBERNATE);
//			log.debug("QueryFactory is up and running");

//			log.debug("Industrial Estate is ready to production.");
        } catch (Throwable ex) {
            System.err.println (ex.toString());
//			log.error("Building SessionFactory failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Returns the global SessionFactory either from a static variable or a JNDI lookup.
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        String sfName = configuration.getProperty(Environment.SESSION_FACTORY_NAME);
        if (sfName != null) {
//			log.debug("Looking up SessionFactory in JNDI");
            try {
                return (SessionFactory) new InitialContext().lookup(sfName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (sessionFactory == null) {
            rebuildSessionFactory(configuration);
        }
        return sessionFactory;
    }

    /**
     * Rebuild the SessionFactory with the given Hibernate Configuration.
     *
     * @param cfg
     */
    public static void rebuildSessionFactory(AnnotationConfiguration cfg) {
//		log.debug("Rebuilding the SessionFactory from given Configuration");
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
        if (cfg.getProperty(Environment.SESSION_FACTORY_NAME) != null) {
//			log.debug("Managing SessionFactory in JNDI");
            cfg.buildSessionFactory();
        } else {
//			log.debug("Holding SessionFactory in static variable");
            sessionFactory = cfg.buildSessionFactory();
        }
        configuration = cfg;
    }

    /**
     * Rebuild the Configuration with the given Properties instance.
     *
     * @param p
     */
    public static void rebuildConfiguration(Properties p) {
//		log.debug("Rebuilding the Configuration from given Properties");
        configuration.setProperties(p);
//		log.debug("Configuration properties:\n--- START PROPERTIES ---");
//		log.debug(configuration.getProperties().toString());
//		log.debug("--- END PROPERTIES ---");
        rebuildSessionFactory(configuration);
    }

    /**
     * Returns the global DAOFactory.
     *
     * @return DAOFactory
     */
    public static DAOFactory getDAOFactory() {
        return daoFactory;
    }

    /**
     * Returns the global QueryFactory.
     *
     * @return QueryFactory
     */
    public static QueryFactory getQueryFactory() {
        return queryFactory;
    }

    /**
     * Shuts down the industrial estate.
     */
    public static void shutdown() {
//		log.debug("Shutting down the Industrial Estate...");
//         Close caches and connection pools
        getSessionFactory().close();
        sessionFactory = null;
//		log.debug("Shut down Hibernate.");

        daoFactory = null;
//		log.debug("Shut down DAOFactory.");

//		log.info("The Industrial Estate is sealed.");
    }
}
