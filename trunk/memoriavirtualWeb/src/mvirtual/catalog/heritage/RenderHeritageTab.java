/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import mvirtual.catalog.SessionNames;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import org.mvirtual.persistence.entity.Heritage;
import org.mvirtual.persistence.entity.HeritageType;
import org.mvirtual.persistence.entity.Institution;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.mvirtual.persistence.hibernate.IndustrialEstate;

import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fabricio
 */
public class RenderHeritageTab extends ActionSupport {

    private Set <Long> selectedHeritageTypes = new HashSet <Long> ();
   
    private String heritageControlNumber;
    
    private String heritageTitle;
    
    private String heritageOriginalTitle;
    
    private String heritageAlternativeTitle;
    
    private String heritageCollection;
    
    private String heritagePhysicalLocation;
    
    private String heritageComplementTitle;
    
    private Long heritageSituation;
    
    private String from;

    public void setFrom(String from) {
        this.from = from;
    }

    public String getHeritageAlternativeTitle() {
        return heritageAlternativeTitle;
    }

    public String getHeritageCollection() {
        return heritageCollection;
    }

    public String getHeritageComplementTitle() {
        return heritageComplementTitle;
    }

    public String getHeritageControlNumber() {
        return heritageControlNumber;
    }

    public String getHeritageOriginalTitle() {
        return heritageOriginalTitle;
    }

    public String getHeritagePhysicalLocation() {
        return heritagePhysicalLocation;
    }

    public Long getHeritageSituation() {
        return heritageSituation;
    }

    public String getHeritageTitle() {
        return heritageTitle;
    }

    /**
     * Bem patrimonial selecionado.
     */
    private Long selectedHeritage;



    /**
     * A página consulta através deste método tipos de bens patrimoniais selecionados.
     * @return �?ndices dos bens selecionados.
     */
    public Set <String> getSelectedHeritageTypes () {

        Set <String> setOfSelectedHeritageNames = new HashSet ();

        Heritage tempHeritage = HeritageLoader.getSessionHeritage();

        for (HeritageType heritageType : tempHeritage.getHeritageTypes()) {
            setOfSelectedHeritageNames.add (heritageType.getHeritageType());
        }

        return setOfSelectedHeritageNames;
    }

    /**
     * A página obtém uma lista de tipos de bens patrimoniais para serem escolhidos
     * pelo usuário.
     * @return Lista de tipos de bens patrimoniais.
     */
    public Map <String, String> getHeritageTypes () {

        Map <String, String> heritageTypes = new HashMap <String, String> ();

        heritageTypes.put ("BEM_IMATERIAL", "Bem Imaterial");
        heritageTypes.put ("CLASSE", "Classe");
        heritageTypes.put ("COLECAO_ACERVO", "Coleção/Acervo");
        heritageTypes.put ("CONJUNTO", "Conjunto");
        heritageTypes.put ("DOCUMENTO", "Documento");
        heritageTypes.put ("DOSSIE", "Dossiê");
        heritageTypes.put ("EDIFICACAO", "Edificação");
        heritageTypes.put ("ESPECIE", "Espécie");
        heritageTypes.put ("FAMILIA", "Família");
        heritageTypes.put ("FUNDO", "Fundo");
        heritageTypes.put ("GENERO", "Gênero");
        heritageTypes.put ("PARTE_DO_DOCUMENTO", "Parte do Documento");
        heritageTypes.put ("PROCESSO", "Processo");
        heritageTypes.put ("SUB_CLASSE", "Sub-Classe");
        heritageTypes.put ("SUB_CONJUNTO", "Sub-Conjunto");
        heritageTypes.put ("SUB_SERIE", "Sub-Série");

        return heritageTypes;
    }

    /**
     * Fornece à página uma lista de situações do bem patrimonial.
     * @return Situações do bem patrimonial.
     */
    public Map <Long, String> getSituations () {
        Map <Long, String> situations = new HashMap <Long, String> ();

        situations.put (0L, "NAO_SE_APLICA");
        situations.put (1L, "ACERVO");
        situations.put (2L, "BAIXA_DE_PATRIMONIO");
        situations.put (3L, "EVENTO");
        situations.put (4L, "MANUTENCAO");

        return situations;
    }
    

    /**
     * Através deste método, a página obtém a situação corrente do bem patrimonial.
     * @return
     */
    public Long getSituation () {
        Heritage tempHeritage = HeritageLoader.getSessionHeritage();

        Long situation = tempHeritage.getSituation();

        if (situation == null) situation = 0L;

        return situation;
    }

    /**
     * A página anterior chama este método e carrega o bem patrimonial escolhido.
     * @param heritageSelected Bem patrimonial escolhido para edição ou remoção.
     */
    public void setSelectedHeritage (Long heritageSelected) {
        this.selectedHeritage = heritageSelected;
    }

    private String button;

    public void setButton (String button) {
        this.button = button;
    }

    public String getButton () {
        return this.button;
    }

    /**
     * Dado o botão selecionado e o bem patrimonial escolhido, toma uma ação.
     * Se o botão New foi pressionado, gera um bem patrimonial vazio.
     * Se o botão Edit foi pressionado, pega o bem patrimonial selecionado da
     * página anterior e fornece para a próxima página para edição.
     * Se o botão Remove foi pressionado, remove o bem patrimonial selecionado.
     * @return
     */
    @Override
    public String execute () {

        Map <String, Object> httpSession = ActionContext.getContext().getSession();
        
        Session dbSession = (Session) httpSession.get (SessionNames.HERITAGE_DB_SESSION);
        
        if (dbSession == null) {
            dbSession = IndustrialEstate.getSessionFactory().openSession();
        }

        Transaction dbTransaction = (Transaction) httpSession.get (SessionNames.HERITAGE_DB_TRANSACTION);        
        
        if (dbTransaction == null) {
            dbTransaction =  dbSession.beginTransaction();
        }
                
//        HeritageType ht;
//
//        for (HeritageTypeEnum hte : HeritageTypeEnum.values()) {
//            ht = (HeritageType) dbSession.createQuery ("select heritageType from HeritageType ht where ht.heritageType = 'BEM_PATRIMONIAL'").uniqueResult();
//
//            if (ht == null) {
//                HeritageType tempHeritageType = new HeritageType (hte.getDescription());
//
//                dbSession.save (tempHeritageType);
//            }
//
//        }
//
//        dbTransaction.commit ();
//
//        dbTransaction = dbSession.beginTransaction();
//
                
                
                
                
                
                
                
                
                
                
                
                
                

        Heritage heritage = null;

        //
        if (from != null && from.equalsIgnoreCase("Outside")) {

            // Verifica se o botão pressionado foi o de novo bem patrimonial.
            if (this.button.equalsIgnoreCase("new")) {

                httpSession.put(SessionNames.HERITAGE_OPERATION, "new");

                // Cria um novo objeto bem patrimonial.
                heritage = new Heritage();

                Institution institution = new Institution ();

                institution.setAcronym("");
                institution.setAddress("");
                institution.setCity("");
                institution.setCountry("");
                institution.setEmail("");
                institution.setFax("");
                institution.setLocality("");
                institution.setName("");
                institution.setPostOfficeBox("");
                institution.setPostalCode("");
                institution.setRegistryNumber("");
                institution.setState("");
                institution.getInstitutionTelephones().clear();
                institution.setUnit("");
                institution.setUnitResponsible("");
                institution.setUnitResponsibleFunction("");
                institution.setUrl("");

                heritage.setTitle("");

                dbSession.saveOrUpdate(institution);

                heritage.setInstitution(institution);

                dbSession.save (heritage);

                // Adiciona este bem patrimonial para edição.
                httpSession.put (SessionNames.HERITAGE, heritage);
                httpSession.put (SessionNames.HERITAGE_DB_SESSION, dbSession);
                httpSession.put (SessionNames.HERITAGE_DB_TRANSACTION, dbTransaction);

                heritageControlNumber = "";
                heritageOriginalTitle = "";
                heritageAlternativeTitle = "";
                heritageTitle = "";
                heritageCollection = "";
                heritagePhysicalLocation = "";
                heritageComplementTitle = "";
                heritageSituation = 0l;

                // Retorna "new" para indicar que objeto novo foi criado.
                return "new";
            }

            // Verifica se o botão pressionado foi o de edição de bem patrimonial.
            if (this.button.equalsIgnoreCase("edit")) {
                ActionContext.getContext().getSession().put(SessionNames.HERITAGE_OPERATION, "edit");

                // Obtém bem patrimonial selecionado do banco de dados.
                heritage = (Heritage) dbSession.get(Heritage.class, selectedHeritage);

                // Adiciona bem patrimonial para a sessão.
                httpSession.put (SessionNames.HERITAGE, heritage);
                httpSession.put (SessionNames.HERITAGE_DB_SESSION, dbSession);
                httpSession.put (SessionNames.HERITAGE_DB_TRANSACTION, dbTransaction);

                heritageControlNumber = heritage.getControlNumber();
                heritageOriginalTitle = heritage.getOriginalTitle();
                heritageAlternativeTitle = heritage.getAlternativeTitle();
                heritageTitle = heritage.getTitle ();
                heritageCollection = heritage.getCollection();
                heritagePhysicalLocation = heritage.getPhysicalLocation();
                heritageComplementTitle = heritage.getComplementTitle();
                heritageSituation = heritage.getSituation();

                
                
                
                
                
                
                Set <HeritageType> setOfHeritageTypes = heritage.getHeritageTypes();

                for (HeritageType heritageType : setOfHeritageTypes) {
                    Long index = null;

                    try {
                        index = HeritageTypeEnum.valueOf(heritageType.getHeritageType()).getIndex();
                    }
                    catch (IllegalArgumentException e) {

                    }

                    if (index != null) {
                        this.selectedHeritageTypes.add (index);
                    }
                }



                // Retorna sinalizando que há bem patrimonial para edição.
                return "edit";
            }

            // Verifica se o botão pressionado foi o de remoção de bem patrimonial.
            if (this.button.equalsIgnoreCase("remove")) {

                // Carrega bem patrimonial dado o id do bem.
                heritage = (Heritage) dbSession.get(Heritage.class, selectedHeritage);

                // Deleta bem patrimonial do banco de dados.
                dbSession.delete(heritage);

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
            heritage = (Heritage) httpSession.get (SessionNames.HERITAGE);

            heritageControlNumber = heritage.getControlNumber();
            heritageOriginalTitle = heritage.getOriginalTitle();
            heritageAlternativeTitle = heritage.getAlternativeTitle();
            heritageTitle = heritage.getTitle ();
            heritageCollection = heritage.getCollection();
            heritagePhysicalLocation = heritage.getPhysicalLocation();
            heritageComplementTitle = heritage.getComplementTitle();
            heritageSituation = heritage.getSituation();

            return "edit";
        }

        return ERROR;
    }
}
