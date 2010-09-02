/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.institution;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mvirtual.catalog.SessionNames;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.entity.relation.InstitutionTelephone;
import org.mvirtual.persistence.entity.relation.embedded.InstitutionTelephoneId;

/**
 *
 * @author Fabricio
 */
public class SaveInstitutionPage extends ActionSupport {

//    private Long selectedInstitution;

   /**
     * Name of the institution.
     */
    private String name;

    /**
     * Nickname of the institution.
     */
    private String acronym;

    /**
     * Unit of the institution.
     */
    private String unit;

    /**
     * Address of the institution.
     */
    private String address;

    /**
     * Postal Code of the institution.
     */
    private String postalCode;

    /**
     * Post Office Box of the institution.
     */
    private String postOfficeBox;

    private String telephone;

    /**
     * City of the institution.
     */
    private String city;

    /**
     * State of the institution.
     */
    private String state;

    /**
     * Country of the institution.
     */
    private String country;

    /**
     * Locality of the institution.
     */
    private String locality;

    /**
     * Fax of the institution.
     */
    private String fax;

    /**
     * Url of the institution.
     */
    private String url;

    /**
     * E-mail for contact.
     */
    private String email;

    /**
     * Responsible for the unit of institution.
     */
    private String unitResponsible;

    /**
     * Function of the responsible of the unit of institution.
     */
    private String unitResponsibleFunction;

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostOfficeBox(String postOfficeBox) {
        this.postOfficeBox = postOfficeBox;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

//    public void setSelectedInstitution(Long selectedInstitution) {
//        this.selectedInstitution = selectedInstitution;
//    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setUnitResponsible(String unitResponsible) {
        this.unitResponsible = unitResponsible;
    }

    public void setUnitResponsibleFunction(String unitResponsibleFunction) {
        this.unitResponsibleFunction = unitResponsibleFunction;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTelephone (String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String execute () {
        Map <String, Object> session = ActionContext.getContext().getSession ();

        Institution institution = (Institution) session.get (SessionNames.INSTITUTION);
        Session dbSession = (Session) session.get (SessionNames.INSTITUTION_DB_SESSION);
        Transaction dbTransaction = (Transaction) session.get (SessionNames.INSTITUTION_DB_TRANSACTION);

        institution.setName (this.name);
        institution.setAcronym(this.acronym);
        institution.setUnit(this.unit);
        institution.setCountry(this.country);
        institution.setAddress(this.address);
        institution.setPostalCode(this.postalCode);
        institution.setCity(this.city);
        institution.setPostOfficeBox(this.postOfficeBox);
        institution.setState(this.state);
        
        Set <InstitutionTelephone> setOfTelephones = institution.getInstitutionTelephones();

        while (setOfTelephones.iterator().hasNext()) {
            InstitutionTelephone a =  setOfTelephones.iterator ().next();
            setOfTelephones.remove (a);
            dbSession.delete (a);
        }

        dbSession.flush();

        InstitutionTelephoneId iti = new InstitutionTelephoneId (institution.getId(),this.telephone);

        InstitutionTelephone it = new InstitutionTelephone (iti, institution);

        institution.getInstitutionTelephones().add (it);

        institution.setFax(this.fax);
        institution.setLocality(this.locality);
        institution.setEmail(this.email);
        institution.setUnitResponsible(this.unitResponsible);
        institution.setUnitResponsibleFunction(this.unitResponsibleFunction);
        institution.setUrl(this.url);

        dbTransaction.commit ();
        dbSession.close();

        session.put (SessionNames.INSTITUTION, null);
        session.put (SessionNames.INSTITUTION_DB_SESSION, null);
        session.put (SessionNames.INSTITUTION_DB_TRANSACTION, null);
        return SUCCESS;
    }

}
