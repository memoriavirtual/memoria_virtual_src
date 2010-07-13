/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Heritage;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;

/**
 *
 * @author Fabricio
 */
public class SaveProductionTab extends ActionSupport {

    private String heritageLocal;

    private String heritageDate;

    private String heritageEditionNumber;

    private String heritageReissueNumber;

    private String heritageOtherResponsibilities;

    private String to;

    public void setHeritageDate(String heritageDate) {
        this.heritageDate = heritageDate;
    }

    public void setHeritageEditionNumber(String heritageEditionNumber) {
        this.heritageEditionNumber = heritageEditionNumber;
    }

    public void setHeritageLocal(String heritageLocal) {
        this.heritageLocal = heritageLocal;
    }

    public void setHeritageOtherResponsibilities(String heritageOtherResponsibilities) {
        this.heritageOtherResponsibilities = heritageOtherResponsibilities;
    }

    public void setHeritageReissueNumber(String heritageReissueNumber) {
        this.heritageReissueNumber = heritageReissueNumber;
    }



    public void setTo(String to) {
        this.to = to;
    }


    @Override
    public String execute () {
        
        Map <String, Object> httpSession = ActionContext.getContext().getSession();

        Session dbSession = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);

        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);

        heritage.setLocal(heritageLocal);

        heritage.setDate (heritageDate);

        heritage.setEditionNumber(heritageEditionNumber);

        heritage.setReissueNumber(heritageReissueNumber);

        heritage.setOtherResponsibilities(heritageOtherResponsibilities);
        
        dbSession.flush ();



        return this.to;
    }

}
