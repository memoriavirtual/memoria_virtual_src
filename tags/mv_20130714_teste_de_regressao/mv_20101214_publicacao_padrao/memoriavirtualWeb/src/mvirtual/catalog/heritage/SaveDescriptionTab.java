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
 * @author fabricio
 */
public class SaveDescriptionTab extends ActionSupport {

    private String to;

    private String heritageContent;

    private String heritageNote;

    private String heritageDimensions;

    private String heritagePhysicalFeatures;

    private String heritageSupport;

    private String heritageCondition;

    public void setTo(String to) {
        this.to = to;
    }

    public void setHeritageCondition(String heritageCondition) {
        this.heritageCondition = heritageCondition;
    }

    public void setHeritageContent(String heritageContent) {
        this.heritageContent = heritageContent;
    }

    public void setHeritageDimensions(String heritageDimensions) {
        this.heritageDimensions = heritageDimensions;
    }

    public void setHeritageNote(String heritageNote) {
        this.heritageNote = heritageNote;
    }

    public void setHeritagePhysicalFeatures(String heritagePhysicalFeatures) {
        this.heritagePhysicalFeatures = heritagePhysicalFeatures;
    }

    public void setHeritageSupport(String heritageSupport) {
        this.heritageSupport = heritageSupport;
    }



    @Override
    public String execute () {

        Map <String, Object> httpSession = ActionContext.getContext().getSession();

        Session dbSession = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);

        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);

        heritage.setContent(this.heritageContent);

        heritage.setNote(this.heritageNote);

        heritage.setDimensions(this.heritageDimensions);

        heritage.setPhysicalFeatures(this.heritagePhysicalFeatures);

        heritage.setSupport(this.heritageSupport);

        heritage.setCondition(this.heritageCondition);

        dbSession.flush();

        return this.to;
        
    }
}
