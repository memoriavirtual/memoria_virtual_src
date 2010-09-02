package org.mvirtual.persistence.entity.relation.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embedded id class for HeritageResearcherresponsible model bean.
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Embeddable
public class HeritageResearcherResponsibleId
	implements java.io.Serializable
{
	private static final long serialVersionUID = 3374085067646222442L;

	private Long idHeritage;
	private String responsiblename;
	private String researchdate;

	public HeritageResearcherResponsibleId() {}

	public HeritageResearcherResponsibleId(Long idHeritage,
			String responsiblename, String researchdate) {
		this.idHeritage = idHeritage;
		this.responsiblename = responsiblename;
		this.researchdate = researchdate;
	}

	@Column(name = "`id_heritage`", nullable = false)
	public Long getIdHeritage() {
		return this.idHeritage;
	}

	public void setIdHeritage(Long idHeritage) {
		this.idHeritage = idHeritage;
	}

	@Column(name = "`responsiblename`", nullable = false)
	public String getResponsiblename() {
		return this.responsiblename;
	}

	public void setResponsiblename(String responsiblename) {
		this.responsiblename = responsiblename;
	}

	@Column(name = "`researchdate`", nullable = false)
	public String getResearchdate() {
		return this.researchdate;
	}

	public void setResearchdate(String researchdate) {
		this.researchdate = researchdate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HeritageResearcherResponsibleId))
			return false;

		HeritageResearcherResponsibleId castOther = (HeritageResearcherResponsibleId) other;

		return ( ((this.getIdHeritage() == castOther.getIdHeritage()) || (this.getIdHeritage() != null && castOther.getIdHeritage() != null && this.getIdHeritage().equals(castOther.getIdHeritage())))
			&& ((this.getResponsiblename() == castOther.getResponsiblename()) || (this.getResponsiblename() != null && castOther.getResponsiblename() != null && this.getResponsiblename().equals(castOther.getResponsiblename())))
			&& ((this.getResearchdate() == castOther.getResearchdate()) || (this.getResearchdate() != null && castOther.getResearchdate() != null && this.getResearchdate().equals(castOther.getResearchdate()))) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getIdHeritage() == null ? 0 : this.getIdHeritage().hashCode());
		result = 37 * result + (this.getResponsiblename() == null ? 0 : this.getResponsiblename().hashCode());
		result = 37 * result + (this.getResearchdate() == null ? 0 : this.getResearchdate().hashCode());
		return result;
	}
}
