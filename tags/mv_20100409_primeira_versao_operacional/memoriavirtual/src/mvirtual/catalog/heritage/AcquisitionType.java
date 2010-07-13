/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

/**
 *
 * @author fabricio
 */
enum AcquisitionType {
    // Compra.
    BUY (0L, "BUY"),

    // Permuta.
    EXCHANGE (1L, "EXCHANGE"),

    // Doação.
    DONATION (2L, "DONATION"),

    // Comodato.
    LENDING (3L, "LENDING"),

    // Posse.
    OWNERSHIP (4L, "OWNERSHIP");

    private Long index;
    private String name;

    private AcquisitionType (Long index, String name) {
        this.index = index;
        this.name = name;
    }

    public Long getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
