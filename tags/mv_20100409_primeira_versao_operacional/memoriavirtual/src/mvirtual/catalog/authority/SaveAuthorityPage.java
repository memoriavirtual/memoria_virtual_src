/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.authority;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Authority;

import java.util.Map;

import mvirtual.catalog.SessionNames;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Fabricio
 */
public class SaveAuthorityPage extends ActionSupport {
    private String name;
    private String nickname;
    private String birthdate;
    private String deathdate;

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setDeathdate(String deathdate) {
        this.deathdate = deathdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String execute () {
        Map <String, Object> httpSession = ActionContext.getContext().getSession ();

        Authority authority = (Authority) httpSession.get (SessionNames.AUTHORITY);

        Session dbSession = (Session) httpSession.get (SessionNames.AUTHORITY_DB_SESSION);

        Transaction dbTransaction = (Transaction) httpSession.get (SessionNames.AUTHORITY_DB_TRANSACTION);
        
        String authorityOperation = (String) httpSession.get (SessionNames.AUTHORITY_OPERATION);

        if (authorityOperation.equalsIgnoreCase("new")) {

            authority.setName(this.name);
            authority.setNickname(this.nickname);
            authority.setBirthDate(this.birthdate);
            authority.setDeathDate(this.deathdate);

            dbTransaction.commit ();
            dbSession.close();

        }

        if (authorityOperation.equalsIgnoreCase("edit")) {
            
            authority.setName(this.name);
            authority.setNickname(this.nickname);
            authority.setBirthDate(this.birthdate);
            authority.setDeathDate(this.deathdate);

            dbTransaction.commit ();
            dbSession.close();
        }

        httpSession.put (SessionNames.AUTHORITY, null);
        httpSession.put (SessionNames.AUTHORITY_DB_SESSION, null);
        httpSession.put (SessionNames.AUTHORITY_DB_TRANSACTION, null);
        httpSession.put (SessionNames.AUTHORITY_OPERATION, null);
        return SUCCESS;
    }

}
