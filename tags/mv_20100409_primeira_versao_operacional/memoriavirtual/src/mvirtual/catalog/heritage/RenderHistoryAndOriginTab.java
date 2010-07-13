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
public class RenderHistoryAndOriginTab extends ActionSupport {
    private Long acquisitionType;
    private String acquisitionOrigin;
    private String acquisitionValue;
    private String acquisitionDate;
    private String acquisitionCurrentOwner;
    private String historic;
    private String findingAid;

    public String getAcquisitionCurrentOwner() {
        return acquisitionCurrentOwner;
    }

    public String getAcquisitionDate() {
        return acquisitionDate;
    }

    public String getAcquisitionOrigin() {
        return acquisitionOrigin;
    }

    public Long getAcquisitionType() {
        return acquisitionType;
    }

    public String getAcquisitionValue() {
        return acquisitionValue;
    }

    public String getFindingAid() {
        return findingAid;
    }

    public String getHistoric() {
        return historic;
    }





    @Override
    public String execute () {
        Map <String, Object> session = ActionContext.getContext().getSession();

        Heritage heritage = (Heritage) session.get (SessionNames.HERITAGE);

        for (AcquisitionType at : AcquisitionType.values()) {
            if (heritage.getAcquisitionType() == null) break;

            if (heritage.getAcquisitionType ().equalsIgnoreCase(at.getName())) {
                this.acquisitionType = at.getIndex();
                break;
            }
        }

        this.acquisitionOrigin = heritage.getAcquisitionOrigin();
        this.acquisitionValue = heritage.getAcquisitionValue();
        this.acquisitionDate = heritage.getAcquisitionDate();
        this.acquisitionCurrentOwner = heritage.getAcquisitionCurrentOwner();
        this.historic = heritage.getHistoric();
        this.findingAid = heritage.getFindingAid();

        return SUCCESS;
    }
}
