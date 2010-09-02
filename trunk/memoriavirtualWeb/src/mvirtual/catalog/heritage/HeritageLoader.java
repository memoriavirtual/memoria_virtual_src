/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;
import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.entity.Authority;
import org.mvirtual.persistence.entity.relation.AuthorityHeritage;
import org.mvirtual.persistence.entity.relation.embedded.AuthorityHeritageId;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author Fabricio
 */
public class HeritageLoader{

    /**
     * This method gets the session and verifies if has "heritage" object.
     * If has, returns it. Otherwise creates a new "heritage" object and maps it
     * into the session.
     * @return Bem patrimonial.
     */
    public static Heritage getSessionHeritage () {

        Heritage heritage;

       // Gets a session.
        Map <String, Object> session = ActionContext.getContext().getSession();

        // If the session already has the Heritage object, ignores.
        if (!session.containsKey(SessionNames.HERITAGE))
        {
            // Otherwise... Creates a new object.
            heritage = new Heritage ();

            // Add the Heritage object to the session.
            session.put (SessionNames.HERITAGE, heritage);
        }
        else
        {
            heritage = (Heritage) session.get (SessionNames.HERITAGE);
        }

        return heritage;
    }

    /**
     * Adiciona um objeto Heritage para a sessão.
     * @param heritage Bem patrimonial.
     */
    public static void addHeritageToSession (Heritage heritage) {
        ActionContext.getContext().getSession ().put (SessionNames.HERITAGE, heritage);
    }

    public static void removeHeritageFromSession () {
        Heritage heritage = (Heritage) ActionContext.getContext().getSession ().get (SessionNames.HERITAGE);

        ActionContext.getContext().getSession().remove (heritage);
    }

//    public static Heritage createNewHeritage () {
//        Heritage heritage = new Heritage ();
//
//        AuthorityHeritage ah = new AuthorityHeritage ();
//
//
//
//        Set <AuthorityHeritage> authorityHeritage = heritage.getAuthorityHeritages();
//
//
//    }
}
