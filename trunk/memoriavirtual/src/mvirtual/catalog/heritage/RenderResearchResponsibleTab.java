/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.entity.relation.HeritageResearcherResponsible;
import org.mvirtual.persistence.entity.relation.embedded.HeritageResearcherResponsibleId;

import mvirtual.catalog.SessionNames;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import java.util.Set;
import java.util.List;


/**
 *
 * @author Fabricio
 */
public class RenderResearchResponsibleTab extends ActionSupport {

    private String researcherNotes;
    private String responsibleName;
    private String researchDate;



    public List <HeritageResearcherResponsible> getResearcherResponsible () {
        return null;
    }



    @Override
    public String execute () {
        Map <String, Object> httpSession = ActionContext.getContext().getSession ();

        Session dbSession = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);

        Transaction dbTransaction = (Transaction) httpSession.get (SessionNames.HERITAGE_DB_TRANSACTION);

        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);

        Set <HeritageResearcherResponsible> setOfResearcherResponsible
                = heritage.getHeritageResearcherResponsibles();

        String key = "";

        for (HeritageResearcherResponsible hrr: setOfResearcherResponsible) {
            HeritageResearcherResponsibleId hrri = hrr.getId();

            key = hrri.getResponsiblename();
            this.researchDate = hrri.getResearchdate();
            this.researcherNotes = hrr.getResearchernotes();
            this.responsibleName = hrri.getResponsiblename();

        }

        return SUCCESS;
    }

}
