/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang.NotImplementedException;

/**
 *
 * @author Fabricio
 */
public class SaveAcquisitionDocumentTab extends ActionSupport {

    private String to;

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String execute () {
        return this.to;
    }
}
