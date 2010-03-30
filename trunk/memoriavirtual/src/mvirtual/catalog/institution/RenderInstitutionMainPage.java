/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.institution;

import com.opensymphony.xwork2.ActionSupport;

import org.mvirtual.persistence.entity.Institution;

import org.mvirtual.persistence.hibernate.IndustrialEstate;

import org.hibernate.Session;

import java.util.List;

/**
 *
 * @author Fabricio
 */
public class RenderInstitutionMainPage extends ActionSupport {

    public List <Institution> getListOfInstitutions () {
        Session dbSession = null;

        List<Institution> listOfInstitutions = null;
        
        try {
            
            dbSession = IndustrialEstate.getSessionFactory().openSession();

            listOfInstitutions = dbSession.createQuery("from Institution").list();

        }
        finally {
            dbSession.close ();
        }

        return listOfInstitutions;
    }

    public Long getSelectedInstitution () {
        return null;
    }

}
