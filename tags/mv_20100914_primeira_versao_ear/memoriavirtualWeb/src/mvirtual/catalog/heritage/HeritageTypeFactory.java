/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import org.mvirtual.persistence.entity.HeritageType;

/**
 *
 * @author fabricio
 */
public class HeritageTypeFactory {
    public static HeritageType createHeritageType (Long index) {

        if (index.equals(HeritageTypeEnum.BEM_IMATERIAL.getIndex())) {
            return new HeritageType (HeritageTypeEnum.BEM_IMATERIAL.getDescription());
        }

        if (index.equals(HeritageTypeEnum.CLASSE.getIndex())) {
            return new HeritageType(HeritageTypeEnum.CLASSE.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.COLECAO_ACERVO.getIndex())) {
            return new HeritageType(HeritageTypeEnum.COLECAO_ACERVO.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.CONJUNTO.getIndex())) {
            return new HeritageType(HeritageTypeEnum.CONJUNTO.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.DOCUMENTO.getIndex())) {
            return new HeritageType(HeritageTypeEnum.DOCUMENTO.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.DOSSIE.getIndex())) {
            return new HeritageType(HeritageTypeEnum.DOSSIE.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.EDIFICACAO.getIndex())) {
            return new HeritageType(HeritageTypeEnum.EDIFICACAO.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.ESPECIE.getIndex())) {
            return new HeritageType(HeritageTypeEnum.ESPECIE.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.FAMILIA.getIndex())) {
            return new HeritageType(HeritageTypeEnum.FAMILIA.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.FUNDO.getIndex())) {
            return new HeritageType(HeritageTypeEnum.FUNDO.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.GENERO.getIndex())) {
            return new HeritageType(HeritageTypeEnum.GENERO.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.PARTE_DO_DOCUMENTO.getIndex())) {
            return new HeritageType(HeritageTypeEnum.PARTE_DO_DOCUMENTO.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.PROCESSO.getIndex())) {
            return new HeritageType(HeritageTypeEnum.PROCESSO.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.SUB_CLASSE.getIndex())) {
            return new HeritageType(HeritageTypeEnum.SUB_CLASSE.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.SUB_CONJUNTO.getIndex())) {
            return new HeritageType(HeritageTypeEnum.SUB_CONJUNTO.getDescription());
        }
        
        if (index.equals(HeritageTypeEnum.SUB_SERIE.getIndex())) {
            return new HeritageType(HeritageTypeEnum.SUB_SERIE.getDescription());
        }
        
        return null;

    }
}
