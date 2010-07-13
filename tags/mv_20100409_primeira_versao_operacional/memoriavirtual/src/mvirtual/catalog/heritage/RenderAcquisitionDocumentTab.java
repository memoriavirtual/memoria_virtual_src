/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;

import org.mvirtual.persistence.entity.Heritage;

/**
 *
 * @author Fabricio
 */
public class RenderAcquisitionDocumentTab extends ActionSupport {
    private Heritage heritage;

    @Override
    public String execute () {
        heritage = HeritageLoader.getSessionHeritage();

        return (heritage != null) ? SUCCESS: INPUT;
    }

    @Override
    public void validate () {

    }
}
