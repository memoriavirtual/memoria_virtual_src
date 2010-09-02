package org.mvirtual.persistence.entity.relation;

import org.mvirtual.persistence.entity.relation.embedded.HeritageSubjectId;

import org.mvirtual.persistence.entity.AbstractEntity;
import org.mvirtual.persistence.entity.Heritage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.FieldBridge;

import org.mvirtual.persistence.hibernate.search.bridge.HeritageSubjectIdBridge;

/**
 * HeritageSubject model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Entity
@Table(name = "`heritage_subject`")
public class HeritageSubject
	extends	AbstractEntity<HeritageSubjectId>
	implements Serializable
{
	private static final long serialVersionUID = 5371040799305845985L;

	private HeritageSubjectId id;
	private Heritage heritage;

	public HeritageSubject() {}

	public HeritageSubject(HeritageSubjectId id, Heritage heritage) {
		this.id = id;
		this.heritage = heritage;
	}

	@EmbeddedId
	@DocumentId
	@IndexedEmbedded(depth=1)
	@FieldBridge(impl=HeritageSubjectIdBridge.class)
        @Override
	public HeritageSubjectId getId() {
		return this.id;
	}

        @Override
	public void setId(HeritageSubjectId id) {
		this.id = id;
	}

	@ContainedIn
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`id_heritage`", nullable = false, insertable = false, updatable = false)
	public Heritage getHeritage() {
		return this.heritage;
	}

	public void setHeritage(Heritage heritage) {
		this.heritage = heritage;
	}

        @Override
	public String toString() {
		return this.getId().toString();
	}

	@Transient
        @Override
	public String getRepr() {
		return "HeritageSubject";
	}
}
