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
public class RenderInformationSourceTab extends ActionSupport {
    private Heritage heritage;

    @Override
    public String execute () {
        Map <String, Object> session = ActionContext.getContext().getSession ();

        Heritage heritage = (Heritage) session.get (SessionNames.HERITAGE);

        

        return SUCCESS;
    }

}
