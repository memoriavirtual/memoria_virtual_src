/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Intervention;
import org.mvirtual.persistence.entity.Heritage;

import mvirtual.catalog.SessionNames;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Fabricio
 */
public class SaveInterventionTab extends ActionSupport {

    private String to;

    private String [] results;

    public void setTo(String to) {
        this.to = to;
    }
    

    public String [] getResults() {
        return results;
    }

    public void setResult(String [] results) {
        this.results = results;
    }

    @Override
    public String execute () {

        Map <String, Object> httpSession =
                ActionContext.getContext().getSession();

        Heritage heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);

        Session dbSession = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);

        Transaction dbTransaction = (Transaction) httpSession.get (SessionNames.HERITAGE_DB_TRANSACTION);

        Intervention tempIntervention = null;

        Set <Intervention> receivedInterventions = new HashSet ();

        for (String interventionFields : results) {
            String [] fields = interventionFields.split("&;&");

            Long id;

            String crudeId     = (fields [0] != null) ? fields [0] : "";
            String status      = (fields [1] != null) ? fields [1] : "";
            String date        = (fields [2] != null) ? fields [2] : "";
            String responsible = (fields [3] != null) ? fields [3] : "";
            String description = (fields [4] != null) ? fields [4] : "";

            // Se o id é nulo ou o comprimento é menor que 2 (que inclui a primeira
            // letra do Id + o número do id, por exemplo, I2.
            if (crudeId == null || crudeId.length() < 2) continue;

//
//                // Novo registro. Já cadastra.
                id = null;

                tempIntervention = new Intervention (responsible,
                                                     description,
                                                     date);

                heritage.getInterventions().add (tempIntervention);

//                tempIntervention.setId(null);

//                tempIntervention.setTemporaryId(crudeId);

//                tempIntervention.setStatus ("SESSION");

//                tempIntervention.getHeritages().add (HeritageLoader.getSessionHeritage());

//                receivedInterventions.add (tempIntervention);

        }

        dbSession.saveOrUpdate (heritage);

        return this.to;













//        Set <Intervention> receivedInterventions = new HashSet ();
//
//        Set <Intervention> sessionInterventions = heritage.getInterventions();
//
//        Intervention tempIntervention = null;
//
//        for (String interventionFields : results) {
//            String [] fields = interventionFields.split("&");
//
//            Long id;
//
//            String crudeId     = (fields [0] != null) ? fields [0] : "";
//            String status      = (fields [1] != null) ? fields [1] : "";
//            String date        = (fields [2] != null) ? fields [2] : "";
//            String responsible = (fields [3] != null) ? fields [3] : "";
//            String description = (fields [4] != null) ? fields [4] : "";
//
//            // Se o id é nulo ou o comprimento é menor que 2 (que inclui a primeira
//            // letra do Id + o número do id, por exemplo, I2.
//            if (crudeId == null || crudeId.length() < 2) continue;
//
//            // Verifica se registro novo.
//            if (status.equalsIgnoreCase("NEW")) {
//
//                // Novo registro. Já cadastra.
//                id = null;
//
//                tempIntervention = new Intervention (responsible,
//                                                     description,
//                                                     date);
//
//                tempIntervention.setId(null);
//
//                tempIntervention.setTemporaryId(crudeId);
//
//                tempIntervention.setStatus ("SESSION");
//
//                tempIntervention.getHeritages().add (HeritageLoader.getSessionHeritage());
//
//                sessionInterventions.add (tempIntervention);
//            }
//
//            // Verifica se registro do banco de dados não editado.
//            if (status.equalsIgnoreCase("DB")) {
//
//                id = Long.parseLong(crudeId.substring(1, crudeId.length()));
//
//                // Procurando pelo mesmo id.
//                for (Intervention sessionIntervention : sessionInterventions) {
//                    if (id == sessionIntervention.getId()) {
//                        sessionInterventions.remove (sessionIntervention);
//
//                        tempIntervention = new  Intervention (responsible,
//                                                              description,
//                                                              date);
//
//                        tempIntervention.setId (id);
//
//                        tempIntervention.setTemporaryId(crudeId);
//
//                        tempIntervention.setStatus(status);
//
//                        sessionInterventions.add (tempIntervention);
//                    }
//                }
//
//            }
//
//            // Verifica se registro do banco de dados editado.
//            if (status.equalsIgnoreCase("DB_MODIFIED")) {
//                id = Long.parseLong(crudeId.substring(1, crudeId.length()));
//
//                // Procurando pelo mesmo id.
//                for (Intervention sessionIntervention : sessionInterventions) {
//                    if (id == sessionIntervention.getId()) {
//                        sessionInterventions.remove (sessionIntervention);
//
//                        tempIntervention = new  Intervention (responsible,
//                                                              description,
//                                                              date);
//
//                        tempIntervention.setId (id);
//
//                        tempIntervention.setTemporaryId(crudeId);
//
//                        tempIntervention.setStatus(status);
//
//                        sessionInterventions.add (tempIntervention);
//                    }
//                }
//            }

//        }


//            return SUCCESS;
//    }
        

//        Intervention temporaryIntervention = null;
//
//        Set <Heritage> setOfHeritages = null;
//
//        // Obtém heritage da sessão.
//        Heritage heritage = HeritageLoader.getSessionHeritage();
//
//        // Obtém o conjunto de intervenções.
//        Set <Intervention> setOfHeritageInterventions = heritage.getInterventions();
//
//        Set <Intervention> temporarySetOfInterventions = new HashSet ();
//
//        // Pega os dados de cada intervenção inserida.
//        for (String receivedInterventionData : results) {
//
//            // Separa os campos da intervenção.
//            String [] temp = receivedInterventionData.split ("&");
//
//            // Carrega id da intervenção entrada.
//            String receivedInterventionId = temp[0];
//
//            Long receivedInterventionIdNumber;
//
//            // Compara as intervenções que já estão carregadas com as intervenções
//            // entradas.
//            for (Intervention currentIntervention : setOfHeritageInterventions) {
//
//                // Carrega o id da intervenção.
//                Long id = currentIntervention.getId ();
//
//                try {
//                    receivedInterventionIdNumber = Long.parseLong (receivedInterventionId);
//                }
//                catch (NumberFormatException e) {
//
//                    if (receivedInterventionId.substring(0,3).equals ("New")) {
//                        temporaryIntervention = new Intervention (temp [2],
//                                                                  temp [3],
//                                                                  temp [1],
//                                                                  null);
//
//                        temporarySetOfInterventions.add (currentIntervention);
//
//
//                    }
////
////                        receivedInterventionId = receivedInterventionId.substring(2, 2 + receivedInterventionId.length() - 3);
////                    }
////
////                    try {
////                        receivedInterventionIdNumber = Long.parseLong(receivedInterventionId);
////
////                    }
////                    catch (NumberFormatException ex) {
////                        continue;
////                    }
//                }
//
//                // Se o id carregado for igual ao
//                if (id == Long.parseLong(receivedInterventionId)) {
//
//                }
//
//            }
//
//
//            temporaryIntervention = new Intervention (temp [2],
//                            temp [3],
//                            temp [1],
//                            null);
//
//
//        }


    }
}
