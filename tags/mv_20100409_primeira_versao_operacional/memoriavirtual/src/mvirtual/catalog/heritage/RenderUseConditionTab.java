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

import org.mvirtual.persistence.entity.Heritage;

import java.util.Map;

/**
 *
 * @author Fabricio
 */
public class RenderUseConditionTab extends ActionSupport {

    private Long accessConditions;
    private Long reproductionConditions;
    private String usage;
    private Long protection;
    private String heritageProtectionInstitution;
    private String legislation;

    public Long getAccessConditions() {
        return accessConditions;
    }

    public String getHeritageProtectionInstitution() {
        return heritageProtectionInstitution;
    }

    public String getLegislation() {
        return legislation;
    }

    public Long getProtection() {
        return protection;
    }

    public Long getReproductionConditions() {
        return reproductionConditions;
    }

    public String getUsage() {
        return usage;
    }
    
    @Override
    public String execute () {

        Map <String, Object> httpSession = ActionContext.getContext().getSession ();

        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);

        Session session = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);

        if (heritage.getAccessConditions() != null && heritage.getAccessConditions().equalsIgnoreCase(AccessConditions.FREE.getName())) {
            this.accessConditions = AccessConditions.FREE.getValue();
        }

        if (heritage.getAccessConditions() != null && heritage.getAccessConditions().equalsIgnoreCase(AccessConditions.UNDER_CONSULT.getName())) {
            this.accessConditions = AccessConditions.UNDER_CONSULT.getValue();
        }

        if (heritage.getAccessConditions() != null && heritage.getAccessConditions().equalsIgnoreCase(AccessConditions.NO_ACCESS.getName())) {
            this.accessConditions = AccessConditions.NO_ACCESS.getValue();
        }

        if (heritage.getAccessConditions() != null && heritage.getAccessConditions().equalsIgnoreCase(AccessConditions.COPY_ACCESS.getName())) {
            this.accessConditions = AccessConditions.COPY_ACCESS.getValue();
        }


        if (heritage.getReproductionConditions() != null && heritage.getReproductionConditions().equalsIgnoreCase(ReproductionConditions.YES.getName())) {
            this.reproductionConditions = ReproductionConditions.YES.getValue();
        }

        if (heritage.getReproductionConditions() != null && heritage.getReproductionConditions().equalsIgnoreCase(ReproductionConditions.UNDER_CONSULT.getName())) {
            this.reproductionConditions = ReproductionConditions.UNDER_CONSULT.getValue ();
        }

        if (heritage.getReproductionConditions () != null && heritage.getReproductionConditions().equalsIgnoreCase(ReproductionConditions.NO.getName())) {
            this.reproductionConditions = ReproductionConditions.NO.getValue();
        }


        if (heritage.getProtection() != null && heritage.getProtection().equalsIgnoreCase(Protection.YES.getName())) {
            this.protection = Protection.YES.getValue();
        }

        if (heritage.getProtection() != null && heritage.getProtection().equalsIgnoreCase(Protection.PROCESSING.getName())) {
            this.protection = Protection.PROCESSING.getValue();
        }

        if (heritage.getProtection() != null && heritage.getProtection().equalsIgnoreCase (Protection.NO.getName())) {
            this.protection = Protection.NO.getValue();
        }
        
        this.usage = heritage.getUsage();

        this.heritageProtectionInstitution = heritage.getHeritageProtectionInstitution();

        this.legislation = heritage.getLegislation();

        return SUCCESS;
    }
}
