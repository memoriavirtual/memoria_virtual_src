package org.mvirtual.persistence.entity.relation;

import org.mvirtual.persistence.entity.relation.embedded.HeritageResearcherResponsibleId;

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

/**
 * HeritageResearcherResponsible model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Entity
@Table(name = "`heritage_researcherresponsible`")
public class HeritageResearcherResponsible
	extends	AbstractEntity<HeritageResearcherResponsibleId>
	implements Serializable
{
	private static final long serialVersionUID = 6853114302793409375L;

	private HeritageResearcherResponsibleId id;
	private Heritage heritage;
	private String researchernotes;

	public HeritageResearcherResponsible() {}

	public HeritageResearcherResponsible(HeritageResearcherResponsibleId id,
			Heritage heritage) {
		this.id = id;
		this.heritage = heritage;
	}

	public HeritageResearcherResponsible(HeritageResearcherResponsibleId id,
			Heritage heritage, String researchernotes) {
		this.id = id;
		this.heritage = heritage;
		this.researchernotes = researchernotes;
	}

	@EmbeddedId
	public HeritageResearcherResponsibleId getId() {
		return this.id;
	}

        @Override
	public void setId(HeritageResearcherResponsibleId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`id_heritage`", nullable = false, insertable = false, updatable = false)
	public Heritage getHeritage() {
		return this.heritage;
	}

	public void setHeritage(Heritage heritage) {
		this.heritage = heritage;
	}

	@Column(name = "`researchernotes`")
	public String getResearchernotes() {
		return this.researchernotes;
	}

	public void setResearchernotes(String researchernotes) {
		this.researchernotes = researchernotes;
	}

        @Override
	public String toString() {
		return this.getResearchernotes();
	}

	@Transient
	public String getRepr() {
		return "HeritageResearcherresponsible";
	}
}
