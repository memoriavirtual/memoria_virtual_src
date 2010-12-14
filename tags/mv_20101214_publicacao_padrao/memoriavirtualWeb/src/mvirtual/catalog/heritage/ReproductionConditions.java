/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

/**
 *
 * @author fabricio
 */
enum ReproductionConditions {
    YES ("YES", 0L),
    UNDER_CONSULT ("UNDER_CONSULT", 1L),
    NO ("NO", 2L);

    private String name;
    private Long value;

    private ReproductionConditions(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Long getValue() {
        return value;
    }
}
