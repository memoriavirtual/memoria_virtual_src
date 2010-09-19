package org.mvirtual.persistence.entity;

import java.io.Serializable;

public interface PersistentObject<ID extends Serializable> extends Entity<ID>
{
    /**
     * Obtém versão do banco de dados.
     * @return
     */
    public Integer getVersion();

    /**
     * Define versão do banco de dados.
     * @param version
     */
    public void setVersion(Integer version);

    /**
     * Compara objetos.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o);

    /**
     * Cria hash code.
     * @return
     */
    @Override
    public int hashCode();
}
