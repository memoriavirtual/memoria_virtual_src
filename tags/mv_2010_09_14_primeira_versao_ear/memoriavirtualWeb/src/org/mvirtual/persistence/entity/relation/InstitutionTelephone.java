package org.mvirtual.persistence.entity.relation;

import org.mvirtual.persistence.entity.relation.embedded.InstitutionTelephoneId;

import org.mvirtual.persistence.entity.AbstractEntity;
import org.mvirtual.persistence.entity.Institution;

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
 * InstitutionTelephone model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Entity
@Table(name = "`institution_telephone`")
public class InstitutionTelephone
	extends	AbstractEntity<InstitutionTelephoneId>
	implements Serializable
{
	private static final long serialVersionUID = -1336424054270273749L;

	private InstitutionTelephoneId id;
	private Institution institution;

	public InstitutionTelephone() {}

	public InstitutionTelephone(InstitutionTelephoneId id,
			Institution institution) {
		this.id = id;
		this.institution = institution;
	}

        @Override
	@EmbeddedId
	public InstitutionTelephoneId getId() {
		return this.id;
	}

        @Override
	public void setId(InstitutionTelephoneId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`id_institution`", nullable = false, insertable = false, updatable = false)
	public Institution getInstitution() {
		return this.institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

        @Override
	public String toString() {
		return this.getId().toString();
	}

        @Override
	@Transient
	public String getRepr() {
		return "InstitutionTelephone";
	}
}
