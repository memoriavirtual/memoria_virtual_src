package org.mvirtual.persistence.entity.relation.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embedded id class for UserHeritageCatalogue model bean.
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Embeddable
public class UserHeritageCatalogueId
	implements java.io.Serializable
{
	private static final long serialVersionUID = -3751243924889464704L;

	private Long idHeritage;
	private Long idUser;

	public UserHeritageCatalogueId() {}

	public UserHeritageCatalogueId(Long idHeritage, Long idUser) {
		this.idHeritage = idHeritage;
		this.idUser = idUser;
	}

	@Column(name = "`id_heritage`", nullable = false)
	public Long getIdHeritage() {
		return this.idHeritage;
	}

	public void setIdHeritage(Long idHeritage) {
		this.idHeritage = idHeritage;
	}

	@Column(name = "`id_user`", nullable = false)
	public Long getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserHeritageCatalogueId))
			return false;

		UserHeritageCatalogueId castOther = (UserHeritageCatalogueId) other;

		return ( ((this.getIdHeritage() == castOther.getIdHeritage()) || (this.getIdHeritage() != null && castOther.getIdHeritage() != null && this.getIdHeritage().equals(castOther.getIdHeritage())))
			&& ((this.getIdUser() == castOther.getIdUser()) || (this.getIdUser() != null && castOther.getIdUser() != null && this.getIdUser().equals(castOther.getIdUser()))) );
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getIdHeritage() == null ? 0 : this.getIdHeritage().hashCode());
		result = 37 * result + (this.getIdUser() == null ? 0 : this.getIdUser().hashCode());
		return result;
	}
}
