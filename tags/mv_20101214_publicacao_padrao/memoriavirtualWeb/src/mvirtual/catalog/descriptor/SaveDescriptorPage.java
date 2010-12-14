/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.descriptor;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Descriptor;

import java.util.Map;

import mvirtual.catalog.SessionNames;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fabricio
 */
public class SaveDescriptorPage extends ActionSupport {
    private String descriptor;


    public void setDescriptor(String name) {
        this.descriptor = name;
    }

    @Override
    public String execute () {
        Map <String, Object> session = ActionContext.getContext().getSession ();

        Descriptor descriptorObject = (Descriptor) session.get (SessionNames.DESCRIPTOR);
        Session dbSession = (Session) session.get (SessionNames.DESCRIPTOR_DB_SESSION);
        Transaction dbTransaction = (Transaction) session.get (SessionNames.DESCRIPTOR_DB_TRANSACTION);
        String descriptorOperation = (String) session.get (SessionNames.DESCRIPTOR_OPERATION);

        if (descriptorOperation.equalsIgnoreCase("new")) {

            descriptorObject.setDescriptor(this.descriptor);

            dbTransaction.commit ();
            dbSession.close();

        }

        if (descriptorOperation.equalsIgnoreCase("edit")) {

            descriptorObject.setDescriptor(this.descriptor);

            dbTransaction.commit ();
            dbSession.close();
        }

        session.put (SessionNames.DESCRIPTOR, null);
        session.put (SessionNames.DESCRIPTOR_DB_SESSION, null);
        session.put (SessionNames.DESCRIPTOR_DB_TRANSACTION, null);
        session.put (SessionNames.DESCRIPTOR_OPERATION, null);
        return SUCCESS;
    }
}
