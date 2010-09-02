package org.mvirtual.persistence.entity.relation;

import org.mvirtual.persistence.entity.relation.embedded.UserInstitutionId;

import org.mvirtual.persistence.entity.AbstractEntity;
import org.mvirtual.persistence.entity.Institution;
import org.mvirtual.persistence.entity.User;

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
 * UserInstitution model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Entity
@Table(name = "`user_institution`")
public class UserInstitution
	extends	AbstractEntity<UserInstitutionId>
	implements Serializable
{
	private static final long serialVersionUID = -380430861632109611L;

        // PK FK BIGINT
	private UserInstitutionId id;

        // PK INTEGER
	private Institution institution;

        // PK FK BIGINT
	private User user;

	public UserInstitution() {}

        /**
         * Constructor of UserInstitution.
         * @param id Key of the table.
         * @param institution Institution 
         * @param user
         */
	public UserInstitution(UserInstitutionId id,
                               Institution institution,
			       User user) {
		this.id = id;
		this.institution = institution;
		this.user = user;
	}

        @Override
	@EmbeddedId
	public UserInstitutionId getId() {
		return this.id;
	}

        @Override
	public void setId(UserInstitutionId id) {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`id_user`", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String toString() {
		return this.getId().toString();
	}

	@Transient
	public String getRepr() {
		return "UserInstitution";
	}
}
