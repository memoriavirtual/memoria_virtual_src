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
import java.util.HashSet;
import java.util.HashMap;

import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.entity.relation.InstitutionTelephone;
import org.mvirtual.persistence.entity.relation.UserInstitution;
import org.mvirtual.persistence.hibernate.IndustrialEstate;
import org.mvirtual.persistence.entity.Heritage;

import java.util.Set;

/**
 *
 * @author Fabricio
 */
public class RenderInstitutionPage extends ActionSupport{

    private String from;

    private String button;

    private Long selectedInstitution;

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

    /**
     * Set of phone numbers of this institution.
     */
//    private Set<InstitutionTelephone> institutionTelephones = new HashSet<InstitutionTelephone>(0);

    private String telephone;

    public String getTelephone() {
        return telephone;
    }

    /**
     * Set of phone numbers of this institution.
     */
//    private Set<InstitutionTelephone> institutionTelephones;
//
//    /**
//     * Set of users of this institution.
//     */
//    private Set<UserInstitution> userInstitutions;
//
//    /**
//     * Set of heritages that addresses this institution.
//     */
//    private Set<Heritage> heritages;

    public String getAcronym() {
        return acronym;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getFax() {
        return fax;
    }

    public String getLocality() {
        return locality;
    }

    public String getName() {
        return name;
    }

    public String getPostOfficeBox() {
        return postOfficeBox;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getState() {
        return state;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitResponsible() {
        return unitResponsible;
    }

    public String getUnitResponsibleFunction() {
        return unitResponsibleFunction;
    }

    public String getUrl() {
        return url;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setNewInstitutionButton(String newInstitutionButton) {
        this.button = "new";
    }

    public void setEditInstitutionButton(String editInstitutionButton) {
        this.button = "edit";
    }

    public void setRemoveInstitutionButton(String removeInstitutionButton) {
        this.button = "remove";
    }

    public void setSelectedInstitution(Long selectedInstitution) {
        this.selectedInstitution = selectedInstitution;
    }

    @Override
    public String execute () {
        Session dbSession = IndustrialEstate.getSessionFactory().openSession();

        Transaction dbTransaction = dbSession.beginTransaction();

        Map <String, Object> httpSession = ActionContext.getContext().getSession();

        Institution institution = null;

        //
        if (from != null && from.equalsIgnoreCase("InstitutionMainPage")) {

            // Verifica se o botão pressionado foi o de novo bem patrimonial.
            if (this.button.equalsIgnoreCase("new")) {

                httpSession.put("heritageOperation", "new");

                // Cria um novo objeto bem patrimonial.
                institution = new Institution();

                institution.setAcronym("");
                institution.setAddress("");
                institution.setCity("");
                institution.setCountry("");
                institution.setEmail("");
                institution.setFax("");
                institution.setLocality("");
                institution.setName("");
                institution.setPostOfficeBox("");
                institution.setPostalCode("");
                institution.setRegistryNumber("");
                institution.setState("");
                institution.getInstitutionTelephones().clear();
                institution.setUnit("");
                institution.setUnitResponsible("");
                institution.setUnitResponsibleFunction("");
                institution.setUrl("");

                dbSession.save (institution);

                // Adiciona este bem patrimonial para edição.
                httpSession.put (SessionNames.INSTITUTION, institution);
                httpSession.put (SessionNames.INSTITUTION_DB_SESSION, dbSession);
                httpSession.put (SessionNames.INSTITUTION_DB_TRANSACTION, dbTransaction);

                this.name = "";
                this.acronym = "";
                this.unit = "";
                this.country = "";
                this.address = "";
                this.postalCode = "";
                this.city = "";
                this.postOfficeBox = "";
                this.state = "";
                this.telephone = "";
                this.fax = "";
                this.locality = "";
                this.email = "";
                this.unitResponsible = "";
                this.unitResponsibleFunction = "";
                this.url = "";


                // Retorna "new" para indicar que objeto novo foi criado.
                return "new";
            }

            // Verifica se o botão pressionado foi o de edição de bem patrimonial.
            if (this.button.equalsIgnoreCase("edit")) {
                ActionContext.getContext().getSession().put("institutionOperation", "edit");

                // Obtém bem patrimonial selecionado do banco de dados.
                institution = (Institution) dbSession.get(Institution.class, selectedInstitution);

                // Adiciona bem patrimonial para a sessão.
                httpSession.put (SessionNames.INSTITUTION, institution);
                httpSession.put (SessionNames.INSTITUTION_DB_SESSION, dbSession);
                httpSession.put (SessionNames.INSTITUTION_DB_TRANSACTION, dbTransaction);

                this.name = institution.getName ();
                this.acronym = institution.getAcronym();
                this.unit = institution.getUnit ();
                this.country = institution.getCountry();
                this.address = institution.getAddress();
                this.postalCode = institution.getPostalCode();
                this.city = institution.getCity();
                this.postOfficeBox = institution.getPostOfficeBox();
                this.state = institution.getState();

                
                if (institution.getInstitutionTelephones() != null)
                {
                    if (!institution.getInstitutionTelephones().isEmpty())
                    this.telephone =  ((InstitutionTelephone) institution.getInstitutionTelephones().toArray () [0]).getId().getTelephone();
                }
                
                this.fax = institution.getFax();
                this.locality = institution.getLocality();
                this.email = institution.getEmail();
                this.unitResponsible = institution.getUnitResponsible();
                this.unitResponsibleFunction = institution.getUnitResponsibleFunction();
                this.url = institution.getUrl();

                // Retorna sinalizando que há bem patrimonial para edição.
                return "edit";
            }

            // Verifica se o botão pressionado foi o de remoção de bem patrimonial.
            if (this.button.equalsIgnoreCase("remove")) {

                // Carrega bem patrimonial dado o id do bem.
                institution = (Institution) dbSession.get(Institution.class, selectedInstitution);

                // Deleta bem patrimonial do banco de dados.
                dbSession.delete(institution);

                // Salva transação.
                dbTransaction.commit();

                dbSession.close();

                // Retorna que bem patrimonial foi deletado.
                return "remove";
            }

            dbTransaction.rollback();
            dbSession.close();
        }
        // Veio já com o Heritage na seção (de uma aba, por exemplo).
        else {
            institution = (Institution) httpSession.get (SessionNames.INSTITUTION);

            this.name = institution.getName ();
            this.acronym = institution.getAcronym();
            this.unit = institution.getUnit ();
            this.country = institution.getCountry();
            this.address = institution.getAddress();
            this.postalCode = institution.getPostalCode();
            this.city = institution.getCity();
            this.postOfficeBox = institution.getPostOfficeBox();
            this.state = institution.getState();

            if (institution.getInstitutionTelephones() != null && !institution.getInstitutionTelephones().isEmpty())
                this.telephone =  (String) institution.getInstitutionTelephones().toArray () [0];

            this.fax = institution.getFax();
            this.locality = institution.getLocality();
            this.email = institution.getEmail();
            this.unitResponsible = institution.getUnitResponsible();
            this.unitResponsibleFunction = institution.getUnitResponsibleFunction();
            this.url = institution.getUrl();

            return "edit";
        }

        return ERROR;
    }



    
    
}
