/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Heritage;

import java.util.Map;



/**
 *
 * @author Fabricio
 */
public class RenderProductionTab extends ActionSupport {

    private String heritageLocal;

    private String heritageDate;

    private String heritageEditionNumber;

    private String heritageReissueNumber;

    private String heritageOtherResponsibilities;

    public String getHeritageDate() {
        return heritageDate;
    }

    public String getHeritageEditionNumber() {
        return heritageEditionNumber;
    }

    public String getHeritageLocal() {
        return heritageLocal;
    }

    public String getHeritageOtherResponsibilities() {
        return heritageOtherResponsibilities;
    }

    public String getHeritageReissueNumber() {
        return heritageReissueNumber;
    }



    @Override
    public String execute () {

        Map <String, Object> session = ActionContext.getContext().getSession();

        Heritage heritage = (Heritage) session.get (SessionNames.HERITAGE);
        
        this.heritageLocal = heritage.getLocal();
    
        this.heritageDate = heritage.getDate();
    
        this.heritageEditionNumber = heritage.getEditionNumber();
    
        this.heritageReissueNumber = heritage.getReissueNumber();
    
        this.heritageOtherResponsibilities = heritage.getOtherResponsibilities();

        return SUCCESS;
    }

    @Override
    public void validate () {

    }
}
