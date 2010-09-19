package org.mvirtual.persistence.entity;

import java.io.Serializable;

/**
 * Abstract implementation for Entity.
 * @see Entity
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
public abstract class AbstractEntity<ID extends Serializable>
	implements Entity<ID>
{
        @Override
	public String getIdAsString() {
		return (getId() != null ? getId().toString() : "?");
	}

        @Override
	public String getClassName() {
		return getClass().getSimpleName();
	}

        @Override
	public String toString() {
		return getClassName() + " <" + getIdAsString() + ">";
	}
}
