package org.mvirtual.persistence.entity;

import javax.persistence.*;
import org.hibernate.search.annotations.*;

/**
 * Abstract implementation for PersistentObject.
 * @see PersistentObject
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@MappedSuperclass
@Indexed
public abstract class AbstractPersistentObject
	implements PersistentObject<Long>
{
        private String status;

	protected Long id;
	private Integer version;

        private String temporaryId;

        @Transient
        public String getTemporaryId() {
            return temporaryId;
        }

        public void setTemporaryId(String temporaryId) {
            this.temporaryId = temporaryId;
        }

        @Transient
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status.toUpperCase();
        }


        @Override
	@DocumentId
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`id`", unique = true, nullable = false, insertable = false, updatable = false)
	public Long getId() { return id; }

        @Override
	public void setId(Long i) { id = i; }

        @Override
	@Version
	@Column(name = "`version`", unique = false, nullable = false)
	public Integer getVersion() { return version; }

        @Override
	public void setVersion(Integer v) { version = v; }

        @Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

        @Override
	public int hashCode() {
		return super.hashCode();
	}

        @Override
	public String toString() {
		return getClassName() + " <" + getIdAsString() + ">";
	}

        @Override
	@Transient
	public String getIdAsString() {
		return (id != null ? id.toString() : "0");
	}

        @Override
	@Transient
	public String getClassName() {
		return getClass().getSimpleName();
	}
}
