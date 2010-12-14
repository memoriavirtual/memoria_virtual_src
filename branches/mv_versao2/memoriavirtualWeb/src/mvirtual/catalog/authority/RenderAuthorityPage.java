/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.authority;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;

import mvirtual.catalog.SessionNames;

import org.mvirtual.persistence.entity.Authority;
import org.mvirtual.persistence.hibernate.IndustrialEstate;


/**
 *
 * @author Fabricio
 */
public class RenderAuthorityPage extends ActionSupport {

    private String button;

    private String from;

    private String name;
    private String nickname;
    private String birthdate;
    private String deathdate;

    private Long authoritySelected;

    public void setAuthoritySelected(Long authoritySelected) {
        this.authoritySelected = authoritySelected;
    }
    

    public void setNewButton (String button) {
        this.button = "new";

    }

    public void setEditButton (String button) {
        this.button = "edit";

    }

    public void setRemoveButton (String button) {
        this.button = "remove";

    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getDeathdate() {
        return deathdate;
    }

    public String getname() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String execute () {
        // Cria sessão para o usuário.
        Session dbSession = IndustrialEstate.getSessionFactory().openSession();

        // Inicia transação.
        Transaction dbTransaction = dbSession.beginTransaction();

        // Obtém sessão.
        Map <String, Object> httpSession = ActionContext.getContext().getSession();

        Authority authority = null;

        //
        if (from != null && from.equalsIgnoreCase("AuthorityMainPage")) {

            // Verifica se o botão pressionado foi o de novo bem patrimonial.
            if (this.button.equalsIgnoreCase("new")) {

                httpSession.put(SessionNames.AUTHORITY_OPERATION, "new");

                // Cria um novo objeto bem patrimonial.
                authority = new Authority();

                authority.setName("");
                authority.setNickname("");
                authority.setBirthDate("");
                authority.setDeathDate("");

                dbSession.save (authority);

                // Adiciona este bem patrimonial para edição.
                httpSession.put (SessionNames.AUTHORITY, authority);
                httpSession.put (SessionNames.AUTHORITY_DB_SESSION, dbSession);
                httpSession.put (SessionNames.AUTHORITY_DB_TRANSACTION, dbTransaction);

                this.name = "";
                this.nickname = "";
                this.birthdate = "";
                this.deathdate = "";

                // Retorna "new" para indicar que objeto novo foi criado.
                return "new";
            }

            // Verifica se o botão pressionado foi o de edição de bem patrimonial.
            if (this.button.equalsIgnoreCase("edit")) {
                ActionContext.getContext().getSession().put(SessionNames.AUTHORITY_OPERATION, "edit");

                // Obtém bem patrimonial selecionado do banco de dados.
                authority = (Authority) dbSession.get(Authority.class, this.authoritySelected);

                // Adiciona bem patrimonial para a sessão.
                httpSession.put (SessionNames.AUTHORITY, authority);
                httpSession.put (SessionNames.AUTHORITY_DB_SESSION, dbSession);
                httpSession.put (SessionNames.AUTHORITY_DB_TRANSACTION, dbTransaction);

                this.name = authority.getName ();
                this.nickname = authority.getNickname();
                this.birthdate = authority.getBirthDate();
                this.deathdate = authority.getDeathDate();

                // Retorna sinalizando que há bem patrimonial para edição.
                return "edit";
            }

            // Verifica se o botão pressionado foi o de remoção de usuário.
            if (this.button.equalsIgnoreCase("remove")) {

                // Carrega bem patrimonial dado o id do bem.
                authority = (Authority) dbSession.get(Authority.class, this.authoritySelected);

                // Deleta usuário do banco de dados.
                dbSession.delete(authority);

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
            authority = (Authority) httpSession.get (SessionNames.AUTHORITY);

            this.name = authority.getName ();
            this.nickname = authority.getNickname();
            this.birthdate = authority.getBirthDate();
            this.deathdate = authority.getDeathDate();

            return "edit";
        }

        return ERROR;
    }

}
