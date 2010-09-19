/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;



import java.util.Map;

import org.mvirtual.persistence.entity.Heritage;

/**
 *
 * @author Fabricio
 */
public class RenderDescriptionTab extends ActionSupport {

    private String heritageContent;

    private String heritageNote;

    private String heritageDimensions;

    private String heritagePhysicalFeatures;

    private String heritageSupport;

    private String heritageCondition;

    public String getHeritageCondition() {
        return heritageCondition;
    }

    public String getHeritageContent() {
        return heritageContent;
    }

    public String getHeritageDimensions() {
        return heritageDimensions;
    }

    public String getHeritageNote() {
        return heritageNote;
    }

    public String getHeritagePhysicalFeatures() {
        return heritagePhysicalFeatures;
    }

    public String getHeritageSupport() {
        return heritageSupport;
    }



    @Override
    public String execute () {
        Map <String, Object> session = ActionContext.getContext().getSession ();

        Heritage heritage = (Heritage) session.get (SessionNames.HERITAGE);

        this.heritageContent = heritage.getContent();

        this.heritageNote = heritage.getNote();

        this.heritageDimensions = heritage.getDimensions();

        this.heritagePhysicalFeatures = heritage.getPhysicalFeatures();

        this.heritageSupport = heritage.getSupport();

        this.heritageCondition = heritage.getCondition();

        return SUCCESS;
    }
}
