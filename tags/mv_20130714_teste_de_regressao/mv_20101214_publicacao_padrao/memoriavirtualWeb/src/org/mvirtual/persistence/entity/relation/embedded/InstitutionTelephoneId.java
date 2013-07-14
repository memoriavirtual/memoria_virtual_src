package org.mvirtual.persistence.entity.relation.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embedded id class for InstitutionTelephone model bean.
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Embeddable
public class InstitutionTelephoneId
	implements java.io.Serializable
{
	private static final long serialVersionUID = 4279111661162619359L;

	private Long idInstitution;
	private String telephone;

	public InstitutionTelephoneId() {}

	public InstitutionTelephoneId(Long idInstitution, String telephone) {
		this.idInstitution = idInstitution;
		this.telephone = telephone;
	}

	@Column(name = "`id_institution`", nullable = false)
	public Long getIdInstitution() {
		return this.idInstitution;
	}

	public void setIdInstitution(Long idInstitution) {
		this.idInstitution = idInstitution;
	}

	@Column(name = "`telephone`", nullable = false)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof InstitutionTelephoneId))
			return false;

		InstitutionTelephoneId castOther = (InstitutionTelephoneId) other;

		return ( ((this.getIdInstitution() == castOther.getIdInstitution()) || (this.getIdInstitution() != null && castOther.getIdInstitution() != null && this.getIdInstitution().equals(castOther.getIdInstitution())))
			&& ((this.getTelephone() == castOther.getTelephone()) || (this.getTelephone() != null && castOther.getTelephone() != null && this.getTelephone().equals(castOther.getTelephone()))) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getIdInstitution() == null ? 0 : this.getIdInstitution().hashCode());
		result = 37 * result + (this.getTelephone() == null ? 0 : this.getTelephone().hashCode());
		return result;
	}
}
