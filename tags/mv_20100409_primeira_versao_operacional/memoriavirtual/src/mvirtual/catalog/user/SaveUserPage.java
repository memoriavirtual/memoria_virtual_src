/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.user;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mvirtual.catalog.SessionNames;

import org.mvirtual.persistence.entity.User;
import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.entity.relation.UserInstitution;
import org.mvirtual.persistence.entity.relation.embedded.UserInstitutionId;

import java.util.Map;

/**
 *
 * @author fabricio
 */
public class SaveUserPage extends ActionSupport {

    private String login;
    private String password;
    private String name;
    private String userIdentificationNumber;
    private String address;
    private Boolean active;
    private String registryDate;
    private String dismissDate;
    private String telephone;
    private String email;
    private Long institutionSelected;
    private Integer userType;

    public void setUserType (Integer userType) {
        this.userType = userType;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDismissDate(String dismissDate) {
        this.dismissDate = dismissDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegistryDate(String registryDate) {
        this.registryDate = registryDate;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUserIdentificationNumber(String userIdentificationNumber) {
        this.userIdentificationNumber = userIdentificationNumber;
    }

    public void setInstitutionSelected (Long institutionSelected) {
        this.institutionSelected = institutionSelected;

    }
    
    @Override
    public String execute () {
        Map <String, Object> session = ActionContext.getContext().getSession ();

        User user = (User) session.get (SessionNames.USER);
        Session dbSession = (Session) session.get (SessionNames.USER_DB_SESSION);
        Transaction dbTransaction = (Transaction) session.get (SessionNames.USER_DB_TRANSACTION);
        String userOperation = (String) session.get (SessionNames.USER_OPERATION);

        if (userOperation.equalsIgnoreCase("new")) {
            user.setLogin (this.login);
            user.setPassword (this.password);
            user.setName (this.name);
            user.setUserIdentificationNumber (this.userIdentificationNumber);
            user.setAddress (this.address);
            user.setActive (this.active);
            user.setRegistryDate (this.registryDate);
            user.setDismissDate (this.dismissDate);
            user.setTelephone (this.telephone);
            user.setEmail (this.email);

            Institution institution = (Institution) dbSession.get(Institution.class, this.institutionSelected);

            UserInstitutionId uii = new UserInstitutionId ();

            uii.setIdInstitution(this.institutionSelected);
            uii.setIdUser (user.getId());
            uii.setUsertype(this.userType);

    //        uii.setIdUser(this.userIdentificationNumber);
    //        uii.setUserType(this.userType);

            UserInstitution ui = new UserInstitution ();

            ui.setId(uii);

            user.getUserInstitutions().add (ui);

            dbTransaction.commit ();
            dbSession.close();

        }

        if (userOperation.equalsIgnoreCase("edit")) {
            user.setLogin (this.login);
            user.setPassword (this.password);
            user.setName (this.name);
            user.setUserIdentificationNumber (this.userIdentificationNumber);
            user.setAddress (this.address);
            user.setActive (this.active);
            user.setRegistryDate (this.registryDate);
            user.setDismissDate (this.dismissDate);
            user.setTelephone (this.telephone);
            user.setEmail (this.email);

            Institution institution = (Institution) dbSession.get(Institution.class, this.institutionSelected);

            UserInstitutionId uii = new UserInstitutionId ();

            uii.setIdInstitution(this.institutionSelected);
            uii.setIdUser (user.getId());
            uii.setUsertype(this.userType);

    //        uii.setIdUser(this.userIdentificationNumber);
    //        uii.setUserType(this.userType);

            UserInstitution ui = new UserInstitution ();

            ui.setId(uii);


            while (user.getUserInstitutions().iterator().hasNext()) {
                UserInstitution temp = user.getUserInstitutions().iterator().next ();


                user.getUserInstitutions().remove(temp);
                dbSession.delete(temp);
            }

            dbSession.flush();

            user.getUserInstitutions().add (ui);

            dbTransaction.commit ();
            dbSession.close();
        }


        session.put (SessionNames.USER, null);
        session.put (SessionNames.USER_DB_SESSION, null);
        session.put (SessionNames.USER_DB_TRANSACTION, null);
        session.put (SessionNames.USER_OPERATION, null);
        return SUCCESS;
    }
}
