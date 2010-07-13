/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

import org.mvirtual.persistence.hibernate.IndustrialEstate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.log4j.Logger;


//import org.mvirtual.persistence.entity.relation.AuthorityHeritage;
//import org.mvirtual.persistence.entity.relation.embedded.AuthorityHeritageId;

import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.entity.Descriptor;
import org.mvirtual.persistence.entity.Authority;
import org.mvirtual.persistence.entity.User;
import org.mvirtual.persistence.entity.Authority;
import org.mvirtual.persistence.entity.Heritage;
//
import java.math.BigDecimal;

/**
 *
 * @author Fabricio
 */
public class ContextListener implements ServletContextListener{
    private static Logger logger = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        IndustrialEstate.shutdown();
    }
}
