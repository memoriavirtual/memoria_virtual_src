/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.entity.Intervention;

import mvirtual.catalog.SessionNames;

import java.util.Set;
import java.util.Map;

/**
 *
 * @author Fabricio
 */
public class RenderInterventionTab extends ActionSupport {

    public Set <Intervention> getInterventions () {
        Map <String, Object> httpSession = ActionContext.getContext().getSession();
        
        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);

        return heritage.getInterventions();
    }
}
