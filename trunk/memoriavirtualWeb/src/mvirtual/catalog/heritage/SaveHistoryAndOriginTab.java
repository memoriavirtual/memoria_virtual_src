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
public class SaveHistoryAndOriginTab extends ActionSupport {

    private Long acquisitionType;
    private String acquisitionOrigin;
    private String acquisitionValue;
    private String acquisitionDate;
    private String acquisitionCurrentOwner;
    private String historic;
    private String findingAid;

    private String to;

    public void setAcquisitionCurrentOwner(String acquisitionCurrentOwner) {
        this.acquisitionCurrentOwner = acquisitionCurrentOwner;
    }

    public void setAcquisitionDate(String acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public void setAcquisitionOrigin(String acquisitionOrigin) {
        this.acquisitionOrigin = acquisitionOrigin;
    }

    public void setAcquisitionType(Long acquisitionType) {
        this.acquisitionType = acquisitionType;
    }

    public void setAcquisitionValue(String acquisitionValue) {
        this.acquisitionValue = acquisitionValue;
    }

    public void setFindingAid(String findingAid) {
        this.findingAid = findingAid;
    }

    public void setHistoric(String historic) {
        this.historic = historic;
    }



    public void setTo(String to) {
        this.to = to;
    }

    
    /**
     *
     * @return
     */
    @Override
    public String execute () {

        Map <String, Object> session = ActionContext.getContext().getSession();

        Session dbSession = (Session) session.get (SessionNames.HERITAGE_DB_SESSION);

        Heritage heritage = (Heritage) session.get (SessionNames.HERITAGE);

        if (acquisitionType != null) {
            for (AcquisitionType at : AcquisitionType.values()) {
                if (at.getIndex().equals(acquisitionType)) {
                    heritage.setAcquisitionType (at.getName());
                    break;
                }
            }
        }

        heritage.setAcquisitionOrigin(this.acquisitionOrigin);
        heritage.setAcquisitionValue (acquisitionValue);
        heritage.setAcquisitionDate(acquisitionDate);
        heritage.setAcquisitionCurrentOwner(acquisitionCurrentOwner);
        heritage.setHistoric(historic);
        heritage.setFindingAid(findingAid);

        dbSession.flush ();
        return this.to;
    }

}
