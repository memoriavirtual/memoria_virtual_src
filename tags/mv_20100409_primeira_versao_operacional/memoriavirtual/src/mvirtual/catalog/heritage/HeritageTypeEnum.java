/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

/**
 *
 * @author fabricio
 */
public enum HeritageTypeEnum {
    
    BEM_IMATERIAL (0L, "Bem Imaterial"),
    CLASSE (1L, "Classe"),
    COLECAO_ACERVO (2L, "Coleção/Acervo"),
    CONJUNTO (3L, "Conjunto"),
    DOCUMENTO (4L, "Documento"),
    DOSSIE (5L, "Dossiê"),
    EDIFICACAO (6L, "Edificação"),
    ESPECIE (7L, "Espécie"),
    FAMILIA (8L, "Família"),
    FUNDO (9L, "Fundo"),
    GENERO (10L, "Gênero"),
    PARTE_DO_DOCUMENTO (11L, "Parte do Documento"),
    PROCESSO (12L, "Processo"),
    SUB_CLASSE (13L,"Sub-Classe"),
    SUB_CONJUNTO (14L,"Sub-Conjunto"),
    SUB_SERIE (15L, "Sub-Série");

    /**
     *
     */
    private Long index;

    /**
     *
     */
    private String description;

    /**
     *
     * @param index
     * @param description
     */
    HeritageTypeEnum (Long index, String description) {
        this.index = index;
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public Long getIndex() {
        return index;
    }
}
