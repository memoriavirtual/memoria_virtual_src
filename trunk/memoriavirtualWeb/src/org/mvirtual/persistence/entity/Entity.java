package org.mvirtual.persistence.entity;

import java.io.Serializable;

/**
 * Entity must have id accessors.
 * @author Fabricio
 * @param <ID>
 */
public interface Entity<ID extends Serializable>
{
	public ID getId();
	public void setId(ID id);

        @Override
	public String toString();
        
	public String getRepr();
	public String getIdAsString();
	public String getClassName();
}
