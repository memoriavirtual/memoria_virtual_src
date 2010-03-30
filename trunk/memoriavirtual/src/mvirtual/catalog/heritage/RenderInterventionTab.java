/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.entity.Intervention;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mvirtual.catalog.SessionNames;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

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

//    public Map <Long, String> getFormattedInterventions () {
//        Map <String, Object> httpSession = ActionContext.getContext().getSession();
//
//        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);
//
//        Set <Intervention> setOfInterventions = heritage.getInterventions();
//
//        Map <Long, String> formattedInterventions = new HashMap ();
//
//        for (Intervention intervention : setOfInterventions) {
//            String temp = intervention.getDate() + " - "
//                    + intervention.getResponsible() + " - "
//                    + intervention.getDescription();
//
//            formattedInterventions.put (intervention.getId(), temp);
//        }
//
//        return formattedInterventions;
//    }





    /**
     *
     * @return
     */
//    public Set<String> getInterventionDescriptions() {
//        Map <String, Object> httpSession = ActionContext.getContext().getSession();
//
//        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);
//
//        Set <Intervention> setOfInterventions = heritage.getInterventions();
//
//        Set <String> setOfInterventionDescriptions = new HashSet ();
//
//        for (Intervention intervention : setOfInterventions) {
//            String temp = intervention.getDate () + " - "
//                    + intervention.getResponsible () + " - "
//                    + intervention.getDescription();
//
//            setOfInterventionDescriptions.add (temp);
//        }
//
//        return setOfInterventionDescriptions;
//    }
}
