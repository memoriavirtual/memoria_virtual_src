/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import java.util.List;

import org.hibernate.Session;

import org.mvirtual.persistence.hibernate.IndustrialEstate;

import org.mvirtual.persistence.entity.Heritage;


/**
 * This class loads all data necessary to render Heritage page.
 * 
 * @author Fabricio
 */
public class RenderListOfHeritages extends ActionSupport {
    private static Logger logger = LogManager.getLogger (RenderAuthorityTab.class);

    public List <Heritage> getListOfHeritages() {
        Session session = IndustrialEstate.getSessionFactory().openSession();

        List <Heritage> listOfHeritages = session.createQuery("from Heritage").list ();

        return listOfHeritages;
    }
}
