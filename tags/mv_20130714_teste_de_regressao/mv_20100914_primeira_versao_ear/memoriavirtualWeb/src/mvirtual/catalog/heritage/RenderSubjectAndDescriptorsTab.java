/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;

import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.entity.Descriptor;

/**
 *
 * @author Fabricio
 */
public class RenderSubjectAndDescriptorsTab extends ActionSupport {
    private Heritage heritage;

    @Override
    public String execute () {
        Map <String, Object> httpSession = ActionContext.getContext ().getSession();

        Session dbSession = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);

        Transaction dbTransaction = (Transaction) httpSession.get (SessionNames.HERITAGE_DB_TRANSACTION);

        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);





        return SUCCESS;
    }

    @Override
    public void validate () {

    }
}
