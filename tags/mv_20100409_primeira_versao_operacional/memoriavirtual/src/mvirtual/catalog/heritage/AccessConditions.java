/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

/**
 *
 * @author fabricio
 */
enum AccessConditions {
    FREE ("FREE", 0L),
    UNDER_CONSULT ("UNDER_CONSULT", 1L),
    NO_ACCESS ("NO_ACCESS", 2L),
    COPY_ACCESS ("COPY_ACCESS", 3L);
    
    private String name;
    private Long value;

    private AccessConditions (String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Long getValue() {
        return this.value;
    }


}
