/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Heritage;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mvirtual.catalog.SessionNames;

import java.util.Map;

/**
 *
 * @author fabricio
 */
public class SaveHeritage extends ActionSupport {
    @Override
    public String execute () {
        Map <String, Object> httpSession = ActionContext.getContext().getSession();

        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);
        Session dbSession = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);
        Transaction dbTransaction = (Transaction) httpSession.get (SessionNames.HERITAGE_DB_TRANSACTION);

        dbSession.save (heritage);
        dbTransaction.commit ();

        dbSession.close ();

        httpSession.put (SessionNames.HERITAGE, null);
        httpSession.put (SessionNames.HERITAGE_DB_SESSION, null);
        httpSession.put (SessionNames.HERITAGE_DB_TRANSACTION, null);
        httpSession.put (SessionNames.HERITAGE_OPERATION, null);
        
        return SUCCESS;
    }
}
