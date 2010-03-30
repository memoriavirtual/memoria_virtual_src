/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.hibernate.Session;

import org.mvirtual.persistence.entity.Heritage;

import java.util.Map;

/**
 *
 * @author Fabricio
 */
public class SaveUseConditionTab extends ActionSupport {

    private Long accessConditions;
    private Long reproductionConditions;
    private String usage;
    private Long protection;
    private String heritageProtectionInstitution;
    private String legislation;
    private String to;

    public void setAccessConditions(Long accessConditions) {
        this.accessConditions = accessConditions;
    }

    public void setHeritageProtectionInstitution(String heritageProtectionInstitution) {
        this.heritageProtectionInstitution = heritageProtectionInstitution;
    }

    public void setLegislation(String legislation) {
        this.legislation = legislation;
    }

    public void setProtection(Long protection) {
        this.protection = protection;
    }

    public void setReproductionConditions(Long reproductionConditions) {
        this.reproductionConditions = reproductionConditions;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setTo(String to) {
        this.to = to;
    }

    


    @Override
    public String execute () {

        Map <String, Object> httpSession = ActionContext.getContext().getSession();

        Session dbSession = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);

        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);

        for (AccessConditions ac : AccessConditions.values()) {
            if (this.accessConditions.equals(ac.getValue ())) {
                heritage.setAccessConditions(ac.getName());
                break;
            }
        }

        for (ReproductionConditions rc : ReproductionConditions.values()) {
            if (this.reproductionConditions.equals(rc.getValue ())) {
                heritage.setReproductionConditions(rc.getName());
                break;
            }
        }

        heritage.setUsage(this.usage);

        for (Protection prot : Protection.values()) {
            if (this.protection.equals(prot.getValue ())) {
                heritage.setProtection(prot.getName());
                break;
            }
        }

        heritage.setHeritageProtectionInstitution(this.heritageProtectionInstitution);

        heritage.setLegislation(this.legislation);

        return this.to;
    }

}
