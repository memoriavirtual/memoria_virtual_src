/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;

import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.entity.Authority;
import org.mvirtual.persistence.entity.relation.AuthorityHeritage;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import org.hibernate.Session;

/**
 *
 * @author Fabricio
 */
public class SaveAuthorityTab extends ActionSupport {

    private String to;

    private Set <Long> authoritiesSelected
            = new HashSet <Long> (0);

    private Set <Long> coAuthoritiesSelected
            = new HashSet <Long> (0);

    @Override
    public String execute () {

        Map <String, Object> session = ActionContext.getContext().getSession();

        Heritage heritage = (Heritage) session.get (SessionNames.HERITAGE);

        Session dbSession = (Session) session.get (SessionNames.HERITAGE_DB_SESSION);

        Set <AuthorityHeritage> setOfAuthorityHeritages = Collections.synchronizedSet(heritage.getAuthorityHeritages());

        while (setOfAuthorityHeritages.iterator ().hasNext()) {
            AuthorityHeritage ah = setOfAuthorityHeritages.iterator().next();
            
            setOfAuthorityHeritages.remove (ah);
            dbSession.delete(ah);
        }

        for (Long authorityId : authoritiesSelected) {
            Authority tempAuthority
                    = (Authority) dbSession.get (Authority.class, authorityId);

            heritage.addAuthority(tempAuthority, "AUTHOR", "");
        }

        for (Long authorityId : coAuthoritiesSelected) {
            Authority tempAuthority
                    = (Authority) dbSession.get (Authority.class, authorityId);

            heritage.addAuthority (tempAuthority, "CO-AUTHOR","");
        }
        dbSession.flush();
        
        return this.to;
        
    }

    public Set<Long> getAuthoritiesSelected() {
        return authoritiesSelected;
    }

    public void setAuthoritiesSelected(Set<Long> authoritiesSelected) {
        this.authoritiesSelected = authoritiesSelected;
    }

    public Set<Long> getCoAuthoritiesSelected() {
        return coAuthoritiesSelected;
    }

    public void setCoAuthoritiesSelected(Set<Long> coAuthoritiesSelected) {
        this.coAuthoritiesSelected = coAuthoritiesSelected;
    }


    public void setTo(String to) {
        this.to = to;
    }
}
