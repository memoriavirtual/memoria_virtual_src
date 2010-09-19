package org.mvirtual.persistence.entity.relation.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import org.mvirtual.persistence.hibernate.search.bridge.HeritageSubjectIdClassBridge;

/**
 * Embedded id class for HeritageSubject model bean.
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 */
@Embeddable
//@ClassBridge(name="heritageSubjectId", index=Index.TOKENIZED, store=Store.YES, impl = HeritageSubjectIdClassBridge.class)
public class HeritageSubjectId
	implements java.io.Serializable
{
	private static final long serialVersionUID = 1390465190467476195L;

	private Long idHeritage;
	private String subject;

	public HeritageSubjectId() {}

	public HeritageSubjectId(Long idHeritage, String subject) {
		this.idHeritage = idHeritage;
		this.subject = subject;
	}

	@Column(name = "`id_heritage`", nullable = false)
	public Long getIdHeritage() {
		return this.idHeritage;
	}

	public void setIdHeritage(Long idHeritage) {
		this.idHeritage = idHeritage;
	}

	@Column(name = "`subject`", nullable = false)
	@Field
	@Type(type = "text")
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

        @Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HeritageSubjectId))
			return false;

		HeritageSubjectId castOther = (HeritageSubjectId) other;

		return ( ((this.getIdHeritage() == castOther.getIdHeritage()) || (this.getIdHeritage() != null && castOther.getIdHeritage() != null && this.getIdHeritage().equals(castOther.getIdHeritage())))
			&& ((this.getSubject() == castOther.getSubject()) || (this.getSubject() != null && castOther.getSubject() != null && this.getSubject().equals(castOther.getSubject()))) );
	}

        @Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getIdHeritage() == null ? 0 : this.getIdHeritage().hashCode());
		result = 37 * result + (this.getSubject() == null ? 0 : this.getSubject().hashCode());
		return result;
	}
}
