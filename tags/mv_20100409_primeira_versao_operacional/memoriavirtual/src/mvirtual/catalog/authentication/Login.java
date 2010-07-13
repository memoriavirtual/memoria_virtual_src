/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.authentication;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;


import org.mvirtual.persistence.hibernate.IndustrialEstate;
import org.mvirtual.persistence.entity.Institution;

/**
 *
 * @author Fabricio
 */
public class Login extends ActionSupport {
    @Override
    public String execute () {
//        Session session = IndustrialEstate.getSessionFactory().openSession();
//
//        Transaction transaction = session.beginTransaction();

//        this.listOfInstitutions = session.createQuery("from Institution").list();

        ActionContext actionContext = ActionContext.getContext();

        Map <String, Object> sessionMap = actionContext.getSession();

        if (sessionMap.containsKey("username")) {
            return ActionSupport.LOGIN;
        } else {
            return ActionSupport.LOGIN;
        }
    }

    @Override
    public void validate () {
    }
//
//    private List <Institution> listOfInstitutions;
//
//    public List<Institution> getListOfInstitutions() {
//        return listOfInstitutions;
//    }
//
//    public void setListOfInstitutions(List<Institution> listOfInstitutions) {
//        this.listOfInstitutions = listOfInstitutions;
//    }
}
