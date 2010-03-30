/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.descriptor;

import com.opensymphony.xwork2.ActionSupport;

import org.mvirtual.persistence.entity.Descriptor;
import org.mvirtual.persistence.hibernate.IndustrialEstate;

import org.hibernate.Session;

import java.util.List;


/**
 *
 * @author Elisa Yumi Nakagawa
 */
public class RenderDescriptorMainPage extends ActionSupport {

    public List <Descriptor> getListOfDescriptors () {

        Session dbSession = null;

        List <Descriptor> listOfDescriptors = null;

        try {
            dbSession = IndustrialEstate.getSessionFactory ().openSession();

            listOfDescriptors = dbSession.createQuery("from Descriptor").list();
        }
        finally {
            dbSession.close ();
        }

        return listOfDescriptors;
    }

    public List <Descriptor> getDescriptorSelected () {
        return null;
    }

}