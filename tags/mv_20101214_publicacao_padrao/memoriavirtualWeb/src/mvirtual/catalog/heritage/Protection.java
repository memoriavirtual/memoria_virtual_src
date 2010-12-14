/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

/**
 *
 * @author fabricio
 */
enum Protection {
    YES ("YES", 0L),
    PROCESSING ("PROCESSING", 1L),
    NO ("NO", 2L);

    private String name;

    private Long value;

    private Protection(String name, Long value) {
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
