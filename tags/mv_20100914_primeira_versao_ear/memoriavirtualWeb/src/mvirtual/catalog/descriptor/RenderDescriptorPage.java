/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.descriptor;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;

import mvirtual.catalog.SessionNames;

import org.mvirtual.persistence.entity.Descriptor;
import org.mvirtual.persistence.hibernate.IndustrialEstate;

/**
 *
 * @author fabricio
 */
public class RenderDescriptorPage extends ActionSupport {
    private String button;

    private String from;

    private String descriptor;

    private Long descriptorSelected;

    public void setDescriptorSelected(Long descriptorSelected) {
        this.descriptorSelected = descriptorSelected;
    }


    public void setNewButton (String button) {
        this.button = "new";

    }

    public void setEditButton (String button) {
        this.button = "edit";

    }

    public void setRemoveButton (String button) {
        this.button = "remove";

    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDescriptor() {
        return descriptor;
    }



    @Override
    public String execute () {
        // Cria sessão para o usuário.
        Session dbSession = IndustrialEstate.getSessionFactory().openSession();

        // Inicia transação.
        Transaction dbTransaction = dbSession.beginTransaction();

        // Obtém sessão.
        Map <String, Object> httpSession = ActionContext.getContext().getSession();

        Descriptor descriptorObject = null;

        //
        if (from != null && from.equalsIgnoreCase("DescriptorMainPage")) {

            // Verifica se o botão pressionado foi o de novo bem patrimonial.
            if (this.button.equalsIgnoreCase("new")) {

                httpSession.put(SessionNames.DESCRIPTOR_OPERATION, "new");

                // Cria um novo objeto bem patrimonial.
                descriptorObject = new Descriptor();

                descriptorObject.setDescriptor("");

                dbSession.save (descriptorObject);

                // Adiciona este bem patrimonial para edição.
                httpSession.put (SessionNames.DESCRIPTOR, descriptorObject);
                httpSession.put (SessionNames.DESCRIPTOR_DB_SESSION, dbSession);
                httpSession.put (SessionNames.DESCRIPTOR_DB_TRANSACTION, dbTransaction);

                this.descriptor = "";

                // Retorna "new" para indicar que objeto novo foi criado.
                return "new";
            }

            // Verifica se o botão pressionado foi o de edição de bem patrimonial.
            if (this.button.equalsIgnoreCase("edit")) {
                ActionContext.getContext().getSession().put(SessionNames.DESCRIPTOR_OPERATION, "edit");

                // Obtém bem patrimonial selecionado do banco de dados.
                descriptorObject = (Descriptor) dbSession.get(Descriptor.class, this.descriptorSelected);

                // Adiciona bem patrimonial para a sessão.
                httpSession.put (SessionNames.DESCRIPTOR, descriptorObject);
                httpSession.put (SessionNames.DESCRIPTOR_DB_SESSION, dbSession);
                httpSession.put (SessionNames.DESCRIPTOR_DB_TRANSACTION, dbTransaction);

                this.descriptor = descriptorObject.getDescriptor();

                // Retorna sinalizando que há bem patrimonial para edição.
                return "edit";
            }

            // Verifica se o botão pressionado foi o de remoção de usuário.
            if (this.button.equalsIgnoreCase("remove")) {

                // Carrega bem patrimonial dado o id do bem.
                descriptorObject = (Descriptor) dbSession.get(Descriptor.class, this.descriptorSelected);

                // Deleta usuário do banco de dados.
                dbSession.delete(descriptorObject);

                // Salva transação.
                dbTransaction.commit();

                dbSession.close();

                // Retorna que bem patrimonial foi deletado.
                return "remove";
            }

            dbTransaction.rollback();
            dbSession.close();
        }
        // Veio já com o Heritage na seção (de uma aba, por exemplo).
        else {
            descriptorObject = (Descriptor) httpSession.get (SessionNames.DESCRIPTOR);

            this.descriptor = descriptorObject.getDescriptor();

            return "edit";
        }

        return ERROR;

    }
}
