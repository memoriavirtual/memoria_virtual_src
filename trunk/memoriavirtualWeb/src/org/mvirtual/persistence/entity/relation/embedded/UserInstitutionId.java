package org.mvirtual.persistence.entity.relation.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embedded id class for UserInstitution model bean.
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Embeddable
public class UserInstitutionId
	implements java.io.Serializable
{
	private static final long serialVersionUID = -1324966279473973475L;

	private Long idInstitution;
	private Long idUser;
	private Integer usertype;

	public UserInstitutionId() {}

	public UserInstitutionId(Long idInstitution, Long idUser, Integer usertype) {
		this.idInstitution = idInstitution;
		this.idUser = idUser;
		this.usertype = usertype;
	}

	@Column(name = "`id_institution`", nullable = false)
	public Long getIdInstitution() {
		return this.idInstitution;
	}

	public void setIdInstitution(Long idInstitution) {
		this.idInstitution = idInstitution;
	}

	@Column(name = "`id_user`", nullable = false)
	public Long getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	@Column(name = "`usertype`", nullable = false)
	public Integer getUsertype() {
		return this.usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserInstitutionId))
			return false;

		UserInstitutionId castOther = (UserInstitutionId) other;

		return ( ((this.getIdInstitution() == castOther.getIdInstitution()) || (this.getIdInstitution() != null && castOther.getIdInstitution() != null && this.getIdInstitution().equals(castOther.getIdInstitution())))
			&& ((this.getIdUser() == castOther.getIdUser()) || (this.getIdUser() != null && castOther.getIdUser() != null && this.getIdUser().equals(castOther.getIdUser())))
			&& ((this.getUsertype() == castOther.getUsertype()) || (this.getUsertype() != null && castOther.getUsertype() != null && this.getUsertype().equals(castOther.getUsertype()))) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getIdInstitution() == null ? 0 : this.getIdInstitution().hashCode());
		result = 37 * result + (this.getIdUser() == null ? 0 : this.getIdUser().hashCode());
		result = 37 * result + (this.getUsertype() == null ? 0 : this.getUsertype().hashCode());
		return result;
	}

	public String toString() {
		String s1 = (idInstitution != null ? idInstitution.toString() : "unbound");
		String s2 = (idUser != null ? idUser.toString() : "unbound");
		String s3 = (usertype != null ? usertype.toString() : "unbound");
		return String.format("i: %s, u: %s, type: %s", s1, s2, s3);
	}
}
