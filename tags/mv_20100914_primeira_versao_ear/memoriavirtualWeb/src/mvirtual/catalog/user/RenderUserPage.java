/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.user;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mvirtual.catalog.SessionNames;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.entity.User;
import org.mvirtual.persistence.entity.relation.UserInstitution;
import org.mvirtual.persistence.entity.relation.embedded.UserInstitutionId;
import org.mvirtual.persistence.hibernate.IndustrialEstate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author fabricio
 */
public class RenderUserPage extends ActionSupport {

    private String button;

    private Long userSelected;

    private String from;

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

//    private Set<UserHeritageReview> userHeritageReviews = new HashSet<UserHeritageReview>(0);
//    private Set<UserHeritageCatalogue> userHeritageCatalogues = new HashSet<UserHeritageCatalogue>(0);
    private Set<UserInstitution> userInstitutions = new HashSet<UserInstitution>(0);

    public Integer getUserType () {
        return this.userType;
    }

    public Long getInstitutionSelected () {
        return this.institutionSelected;
    }

    public void setFrom (String from) {
        this.from = from;
    }

    public void setEditButton(String button) {
        this.button = "edit";
    }

    public void setNewButton(String button) {
        this.button = "new";
    }

    public void setRemoveButton(String button) {
        this.button = "remove";
    }

    public void setUserSelected(Long userSelected) {
        this.userSelected = userSelected;
    }

    public Boolean getActive() {
        return active;
    }

    public String getAddress() {
        return address;
    }

    public String getButton() {
        return button;
    }

    public String getDismissDate() {
        return dismissDate;
    }

    public String getEmail() {
        return email;
    }

    public String getFrom() {
        return from;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getUserIdentificationNumber() {
        return userIdentificationNumber;
    }

    public Long getUserSelected() {
        return userSelected;
    }

    public List<Institution> getInstitutions () {
        Session dbSession = IndustrialEstate.getSessionFactory().openSession();

        List <Institution> institutions = dbSession.createQuery ("from Institution").list();

        dbSession.close ();

        return institutions;

    }

    @Override
    public String execute () {
        // Cria sessão para o usuário.
        Session dbSession = IndustrialEstate.getSessionFactory().openSession();

        // Inicia transação.
        Transaction dbTransaction = dbSession.beginTransaction();

        // Obtém sessão.
        Map <String, Object> httpSession = ActionContext.getContext().getSession();

        User user = null;

        //
        if (from != null && from.equalsIgnoreCase("UserMainPage")) {

            // Verifica se o botão pressionado foi o de novo bem patrimonial.
            if (this.button.equalsIgnoreCase("new")) {

                httpSession.put(SessionNames.USER_OPERATION, "new");

                // Cria um novo objeto bem patrimonial.
                user = new User();

                user.setLogin("");
                user.setPassword("");
                user.setName("");
                user.setUserIdentificationNumber("");
                user.setAddress("");
                user.setActive(false);
                user.setRegistryDate("");
                user.setDismissDate("");
                user.setTelephone("");
                user.setEmail("");

                dbSession.save (user);

                // Adiciona este bem patrimonial para edição.
                httpSession.put (SessionNames.USER, user);
                httpSession.put (SessionNames.USER_DB_SESSION, dbSession);
                httpSession.put (SessionNames.USER_DB_TRANSACTION, dbTransaction);


                this.login = "";
                this.password = "";
                this.name = "";
                this.userIdentificationNumber = "";
                this.address = "";
                this.active = false;
                this.registryDate = "";
                this.dismissDate = "";
                this.telephone = "";
                this.email = "";

                // Retorna "new" para indicar que objeto novo foi criado.
                return "new";
            }

            // Verifica se o botão pressionado foi o de edição de bem patrimonial.
            if (this.button.equalsIgnoreCase("edit")) {
                ActionContext.getContext().getSession().put(SessionNames.USER_OPERATION, "edit");

                // Obtém bem patrimonial selecionado do banco de dados.
                user = (User) dbSession.get(User.class, this.userSelected);

                // Adiciona bem patrimonial para a sessão.
                httpSession.put (SessionNames.USER, user);
                httpSession.put (SessionNames.USER_DB_SESSION, dbSession);
                httpSession.put (SessionNames.USER_DB_TRANSACTION, dbTransaction);

                this.login = user.getLogin();
                this.password = user.getPassword();
                this.name = user.getName();
                this.userIdentificationNumber = user.getUserIdentificationNumber();
                this.address = user.getAddress();
                this.active = user.isActive();
                this.registryDate = user.getRegistryDate();
                this.dismissDate = user.getDismissDate();
                this.telephone = user.getTelephone();
                this.email = user.getEmail();
                
                UserInstitution ui = user.getUserInstitutions().iterator().next ();

                UserInstitutionId uii = ui.getId();

                this.userType = uii.getUsertype();

                this.institutionSelected = uii.getIdInstitution();

                // Retorna sinalizando que há bem patrimonial para edição.
                return "edit";
            }

            // Verifica se o botão pressionado foi o de remoção de usuário.
            if (this.button.equalsIgnoreCase("remove")) {

                // Carrega bem patrimonial dado o id do bem.
                user = (User) dbSession.get(User.class, this.userSelected);

                // Deleta usuário do banco de dados.
                dbSession.delete(user);

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
            user = (User) httpSession.get (SessionNames.USER);

            this.login = user.getLogin();
            this.password = user.getPassword();
            this.name = user.getName();
            this.userIdentificationNumber = user.getUserIdentificationNumber();
            this.address = user.getAddress();
            this.active = user.isActive();
            this.registryDate = user.getRegistryDate();
            this.dismissDate = user.getDismissDate();
            this.telephone = user.getTelephone();
            this.email = user.getEmail();

                UserInstitution ui = user.getUserInstitutions().iterator().next ();

                UserInstitutionId uii = ui.getId();

                this.userType = uii.getUsertype();

                this.institutionSelected = uii.getIdInstitution();

            return "edit";
        }

        return ERROR;
    }
}
