/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.mvirtual.persistence.entity.Authority;
import org.mvirtual.persistence.entity.relation.AuthorityHeritage;
import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.hibernate.IndustrialEstate;

import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * Acquire data to render Authority page.
 * @author Fabricio
 */
public class RenderAuthorityTab extends ActionSupport {
    /**
     *
     */
    private Heritage heritage;

    /**
     *
     */
    private Set <Authority> authorities
            = new HashSet <Authority> ();

    private Set <Authority> authoritiesSelected
            = new HashSet <Authority> ();

    private Set <Authority> coAuthoritiesSelected
            = new HashSet <Authority> ();

    /**
     * Gets the logger.
     */
    private static Logger logger = Logger.getLogger (RenderAuthorityTab.class);

    /**
     *
     * @return
     */
    public Heritage getHeritage() {
        return heritage;
    }

    /**
     *
     * @param heritage
     */
    public void setHeritage(Heritage heritage) {
        this.heritage = heritage;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public Set<Authority> getAuthoritiesSelected() {
        return authoritiesSelected;
    }

    public Set<Authority> getCoAuthoritiesSelected() {
        return coAuthoritiesSelected;
    }

    /**
     *
     * @return
     */
    @Override
    public String execute () {

        Map <String, Object> session = ActionContext.getContext().getSession ();

        Session dbSession = (Session) session.get (SessionNames.HERITAGE_DB_SESSION);

        Transaction dbTransaction = (Transaction) session.get (SessionNames.HERITAGE_DB_TRANSACTION);

        // Carrega heritage.
        this.heritage = (Heritage) session.get (SessionNames.HERITAGE);

        List <Authority> listOfAuthorities =
                dbSession.createQuery ("from Authority").list ();

        // Obtém conjunto de relacionamentos Authority-Heritages.
        Set <AuthorityHeritage> setOfAuthorityHeritages =
                this.heritage.getAuthorityHeritages();

        if (setOfAuthorityHeritages != null) {
            // Carrega o conjunto de autoridades com os autores.
            for (AuthorityHeritage tempAuthorityHeritage : setOfAuthorityHeritages) {

                if (tempAuthorityHeritage.getId().getAuthorShipType().equals("AUTHOR")) {

                    this.authoritiesSelected.add(tempAuthorityHeritage.getAuthority());
                    listOfAuthorities.remove(tempAuthorityHeritage.getAuthority());
                } else {
                    this.coAuthoritiesSelected.add(tempAuthorityHeritage.getAuthority());
                    listOfAuthorities.remove (tempAuthorityHeritage.getAuthority());
                }
            }
        }

        this.authorities = new HashSet <Authority> (listOfAuthorities);

        return SUCCESS;
    }
}
