/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Intervention;
import org.mvirtual.persistence.entity.Heritage;

import mvirtual.catalog.SessionNames;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Fabricio
 */
public class SaveInterventionTab extends ActionSupport {

    private String to;

    private String [] results;

    public void setTo(String to) {
        this.to = to;
    }
    

    public String [] getResult() {
        return results;
    }

    public void setResult(String [] results) {
        if (results == null)
            this.results = new String [0];
        else
            this.results = results;
    }

    @Override
    public String execute () {

        if (results == null) return this.to;

        Map <String, Object> httpSession =
                ActionContext.getContext().getSession();

        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);

        Session dbSession = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);

        Transaction dbTransaction = (Transaction) httpSession.get (SessionNames.HERITAGE_DB_TRANSACTION);

        Intervention tempIntervention = null;

        Set <Intervention> receivedInterventions = new HashSet ();

        for (String interventionFields : results) {
            String [] fields = interventionFields.split("&;&");

            Long id;

            String crudeId     = (fields [0] != null) ? fields [0] : "";
            String status      = (fields [1] != null) ? fields [1] : "";
            String date        = (fields [2] != null) ? fields [2] : "";
            String responsible = (fields [3] != null) ? fields [3] : "";
            String description = (fields [4] != null) ? fields [4] : "";

            // Se o id é nulo ou o comprimento é menor que 2 (que inclui a primeira
            // letra do Id + o número do id, por exemplo, I2.
            if (crudeId == null || crudeId.length() < 2) continue;

                id = null;

                tempIntervention = new Intervention (responsible,
                                                     description,
                                                     date);

                heritage.getInterventions().add (tempIntervention);
        }

        dbSession.saveOrUpdate (heritage);

        return this.to;
    }
}
