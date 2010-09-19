/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.user;

import com.opensymphony.xwork2.ActionSupport;

import org.hibernate.Session;

import org.mvirtual.persistence.entity.User;
import org.mvirtual.persistence.hibernate.IndustrialEstate;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Elisa
 */
public class RenderUserMainPage extends ActionSupport {

    public List <User> getListOfUsers () {

        Session dbSession = null;

        List <User> listOfUsers = null;

        try {
            dbSession = IndustrialEstate.getSessionFactory ().openSession();

            listOfUsers = dbSession.createQuery("from User").list();
        }
        finally {
            dbSession.close ();
        }

        if (listOfUsers == null)
            listOfUsers = new ArrayList <User> ();

        return listOfUsers;
    }

    public List <User> getUserSelected () {
        return null;
    }
}