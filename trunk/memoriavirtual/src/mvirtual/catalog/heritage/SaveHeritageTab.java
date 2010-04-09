/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.Query;

import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.entity.HeritageType;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author fabricio
 */
public class SaveHeritageTab extends ActionSupport {

    private String button;

    /**
     * Cria um conjunto para carregar os bens patrimoniais selecionados.
     *
     */
    private Set <String> selectedHeritageTypes = new HashSet <String> ();
   
    private String heritageControlNumber;
    
    private String heritageTitle;
    
    private String heritageOriginalTitle;
    
    private String heritageAlternativeTitle;
    
    private String heritageCollection;
    
    private String heritagePhysicalLocation;
    
    private String heritageComplementTitle;
    
    private Long heritageSituation;
    
    private boolean SaveHeritageButton;
    
    private String to;

    private String nextAction;

    public void setButton(String button) {
        this.button = button;
    }
    public Set<String> getSelectedHeritageTypes() {
        return selectedHeritageTypes;
    }

    public void setSelectedHeritageTypes(Set<String> selectedHeritageTypes) {
        this.selectedHeritageTypes = selectedHeritageTypes;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setHeritageAlternativeTitle(String heritageAlternativeTitle) {
        this.heritageAlternativeTitle = heritageAlternativeTitle;
    }

    public void setHeritageCollection(String heritageCollection) {
        this.heritageCollection = heritageCollection;
    }

    public void setHeritageComplementTitle(String heritageComplementTitle) {
        this.heritageComplementTitle = heritageComplementTitle;
    }

    public void setHeritageControlNumber(String heritageControlNumber) {
        this.heritageControlNumber = heritageControlNumber;
    }

    public void setHeritageOriginalTitle(String heritageOriginalTitle) {
        this.heritageOriginalTitle = heritageOriginalTitle;
    }

    public void setHeritagePhysicalLocation(String heritagePhysicalLocation) {
        this.heritagePhysicalLocation = heritagePhysicalLocation;
    }

    public void setHeritageSituation(Long heritageSituation) {
        this.heritageSituation = heritageSituation;
    }

    public void setHeritageTitle(String heritageTitle) {
        this.heritageTitle = heritageTitle;
    }
    
    @Override
    public String execute () {

        Map<String, Object> session = ActionContext.getContext().getSession();

        Session dbSession = (Session) session.get(SessionNames.HERITAGE_DB_SESSION);

        Heritage heritage = (Heritage) session.get(SessionNames.HERITAGE);

        String heritageOperation = (String) session.get(SessionNames.HERITAGE_OPERATION);

        if (heritageOperation != null && heritageOperation.equalsIgnoreCase("new")) {

            heritage.setComplementTitle(heritageComplementTitle);
            heritage.setControlNumber(heritageControlNumber);
            heritage.setTitle(heritageTitle);
            heritage.setOriginalTitle(heritageOriginalTitle);
            heritage.setAlternativeTitle(heritageAlternativeTitle);
            heritage.setCollection(heritageCollection);
            heritage.setPhysicalLocation(heritagePhysicalLocation);
            heritage.setSituation(heritageSituation);

            for (String index : selectedHeritageTypes) {

                HeritageTypeEnum heritageTypeEnum = HeritageTypeEnum.valueOf(index);

                String heritageTypeName = heritageTypeEnum.getDescription();

                Long id = (Long) dbSession.createQuery
                        ("select id from HeritageType ht where ht.heritageType = :heritageName")
                        .setParameter("heritageName", heritageTypeName)
                        .uniqueResult();

                HeritageType heritageTypeToBeAdded = null;

                // Verifica se há o HeritageType na tabela.
                if (id == null) {
                    // Não há HeritageType na tabela. Tem que criar um.
                    heritageTypeToBeAdded = new HeritageType(heritageTypeEnum.getDescription());
                }
                else
                {
                    // Há HeritageType na tabela.
                    heritageTypeToBeAdded = (HeritageType) dbSession.get (HeritageType.class, id);
                }

                heritage.getHeritageTypes().add(heritageTypeToBeAdded);
            }
        }

//        if (heritageOperation.equalsIgnoreCase("edit"))
//        {
            heritage.setComplementTitle(heritageComplementTitle);
            heritage.setControlNumber(heritageControlNumber);
            heritage.setTitle(heritageTitle);
            heritage.setOriginalTitle(heritageOriginalTitle);
            heritage.setAlternativeTitle(heritageAlternativeTitle);
            heritage.setCollection(heritageCollection);
            heritage.setPhysicalLocation(heritagePhysicalLocation);
            heritage.setSituation(heritageSituation);

            for (String index : selectedHeritageTypes) {

                HeritageTypeEnum heritageTypeEnum = HeritageTypeEnum.valueOf(index);

                String heritageTypeName = heritageTypeEnum.name();

                Long id = (Long) dbSession.createQuery
                        ("select id from HeritageType ht where ht.heritageType = :heritageName")
                        .setParameter("heritageName", heritageTypeName)
                        .uniqueResult();

                HeritageType heritageTypeToBeAdded = null;

                // Verifica se há o HeritageType na tabela.
                if (id == null) {
                    // Não há HeritageType na tabela. Tem que criar um.
                    heritageTypeToBeAdded = new HeritageType(heritageTypeEnum.name());
                    
                    heritage.getHeritageTypes().add(heritageTypeToBeAdded);
                }
                else
                {
                    // Há HeritageType na tabela.
//                    heritageTypeToBeAdded = (HeritageType) dbSession.get (HeritageType.class, id);

                    // Verifica se heritage já tem heritageType.
                    heritageTypeToBeAdded = (HeritageType) dbSession.createQuery
                            ("select ht from Heritage h ,HeritageType ht where ht.id = :htid and h.id = :hid")
                            .setParameter("htid",id).setParameter("hid", heritage.getId())
                            .uniqueResult();
                    
                    if (heritageTypeToBeAdded == null) {
                        heritage.getHeritageTypes().add(heritageTypeToBeAdded);
                    }
                    
                    

                }

                

            }

//        }

//        if (this.button != null && this.button.equalsIgnoreCase("SAVE")) {
//            return "SaveHeritage";
//        }

        return to;
    }

}
