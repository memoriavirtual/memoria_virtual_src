/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.authority;

import com.opensymphony.xwork2.ActionSupport;

import org.hibernate.Session;

import org.mvirtual.persistence.entity.Authority;
import org.mvirtual.persistence.hibernate.IndustrialEstate;

import java.util.List;


/**
 *
 * @author Elisa
 */
public class RenderAuthorityMainPage extends ActionSupport {

    public List <Authority> getListOfAuthorities () {

        Session dbSession = null;

        List <Authority> listOfAuthorities = null;

        try {
            dbSession = IndustrialEstate.getSessionFactory ().openSession();

            listOfAuthorities = dbSession.createQuery("from Authority").list();
        }
        finally {
            dbSession.close ();
        }

        return listOfAuthorities;
    }

    public List <Authority> getAuthoritySelected () {
        return null;
    }
}
