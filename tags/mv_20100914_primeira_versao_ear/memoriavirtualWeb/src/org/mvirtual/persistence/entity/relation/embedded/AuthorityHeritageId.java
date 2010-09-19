package org.mvirtual.persistence.entity.relation.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.ClassBridge;
//import org.hibernate.search.annotations.Index;
//import org.hibernate.search.annotations.Store;
//
import org.mvirtual.persistence.hibernate.search.bridge.AuthorityHeritageIdClassBridge;

/**
 * Embedded id class for AuthorityHeritage model bean.
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 */
@Embeddable
//@ClassBridge(name="authorityHeritageId", index=Index.TOKENIZED, store=Store.YES, impl = AuthorityHeritageIdClassBridge.class)
public class AuthorityHeritageId
	implements java.io.Serializable
{
	private static final long serialVersionUID = 2472233816646184755L;

        /**
         * Heritage table id.
         */
	private Long idHeritage;

        /**
         * Authority table id.
         */
	private Long idAuthority;

        /**
         * Authorship type.
         */
	private String authorShipType;

        /**
         * Default Constructor. A must for Hibernate.
         */
	public AuthorityHeritageId() {}

        /**
         * Constructor that loads Heritage id, Authority id and Authorship Type.
         * @param idHeritage Heritage id.
         * @param idAuthority Authority id.
         * @param authorshiptype Authorship Type.
         */
	public AuthorityHeritageId(Long idHeritage,
                                   Long idAuthority,
                                   String authorshiptype) {
		this.idHeritage = idHeritage;
		this.idAuthority = idAuthority;
		this.authorShipType = authorshiptype;
	}

	@Column(name = "`id_heritage`", nullable = false)
	public Long getIdHeritage() {
		return this.idHeritage;
	}

	public void setIdHeritage(Long idHeritage) {
		this.idHeritage = idHeritage;
	}

	@Column(name = "`id_authority`", nullable = false)
	public Long getIdAuthority() {
		return this.idAuthority;
	}

	public void setIdAuthority(Long idAuthority) {
		this.idAuthority = idAuthority;
	}

	@Column(name = "`authorshiptype`", nullable = false)
	@Field
	public String getAuthorShipType() {
		return this.authorShipType;
	}

	public void setAuthorShipType(String authorshiptype) {
		this.authorShipType = authorshiptype;
	}

        @Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AuthorityHeritageId))
			return false;

		AuthorityHeritageId castOther = (AuthorityHeritageId) other;

		return ( ((this.getIdHeritage() == castOther.getIdHeritage()) || (this.getIdHeritage() != null && castOther.getIdHeritage() != null && this.getIdHeritage().equals(castOther.getIdHeritage())))
			&& ((this.getIdAuthority() == castOther.getIdAuthority()) || (this.getIdAuthority() != null && castOther.getIdAuthority() != null && this.getIdAuthority().equals(castOther.getIdAuthority())))
			&& ((this.getAuthorShipType() == castOther.getAuthorShipType()) || (this.getAuthorShipType() != null && castOther.getAuthorShipType() != null && this.getAuthorShipType().equals(castOther.getAuthorShipType()))) );
	}

        @Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getIdHeritage() == null ? 0 : this.getIdHeritage().hashCode());
		result = 37 * result + (this.getIdAuthority() == null ? 0 : this.getIdAuthority().hashCode());
		result = 37 * result + (this.getAuthorShipType() == null ? 0 : this.getAuthorShipType().hashCode());
		return result;
	}
}
