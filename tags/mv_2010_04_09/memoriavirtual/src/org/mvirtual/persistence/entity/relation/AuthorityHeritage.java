package org.mvirtual.persistence.entity.relation;

import org.mvirtual.persistence.entity.relation.embedded.AuthorityHeritageId;

import org.mvirtual.persistence.entity.AbstractEntity;
import org.mvirtual.persistence.entity.Authority;
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
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.FieldBridge;

import org.mvirtual.persistence.hibernate.search.bridge.AuthorityHeritageIdBridge;

/**
 * AuthorityHeritage model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Entity
@Table(name = "`authority_heritage`")
public class AuthorityHeritage
	extends	AbstractEntity<AuthorityHeritageId>
	implements Serializable
{
	private static final long serialVersionUID = -3643467925484089813L;

	private AuthorityHeritageId id;
	private Heritage heritage;
	private Authority authority;
	private String function;

        /**
         * Default constructor. Necessary to hibernate.
         */
	public AuthorityHeritage() {}

        /**
         * Constructor with all attributes.
         * @param id Relationship between Heritage and Authority.
         * @param heritage FK to Heritage Id.
         * @param authority FK to Authority Id.
         * @param function Function, as painter, writer...
         */
	public AuthorityHeritage(AuthorityHeritageId id,
                                 Heritage heritage,
			         Authority authority,
                                 String function) {
		this.id = id;
		this.heritage = heritage;
		this.authority = authority;
		this.function = function;
	}

        /**
         * Gets PK id.
         * @return Id.
         */
        @Override
	@EmbeddedId
	@DocumentId
	@IndexedEmbedded(depth=1)
	@FieldBridge(impl = AuthorityHeritageIdBridge.class)
	public AuthorityHeritageId getId() {
		return this.id;
	}

        /**
         * Sets PK id.
         * @param id Id.
         */
        @Override
	public void setId(AuthorityHeritageId id) {
		this.id = id;
	}

        /**
         * Gets the heritage.
         * @return Heritage.
         */
	@ContainedIn
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`id_heritage`", nullable = false, insertable = false, updatable = false)
	public Heritage getHeritage() {
		return this.heritage;
	}

        /**
         * Sets the heritage.
         * @param heritage Heritage.
         */
	public void setHeritage(Heritage heritage) {
		this.heritage = heritage;
	}

        /**
         * Gets the Authority.
         * @return Authority object.
         */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`id_authority`", nullable = false, insertable = false, updatable = false)
	@IndexedEmbedded(depth=1)
	public Authority getAuthority() {
		return this.authority;
	}

        /**
         * Sets the Authority.
         * @param authority Authority object.
         */
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

        /**
         * Gets the function of this authority into the heritage context.
         * @return Function of this authority.
         */
	@Column(name = "`function`", nullable = false)
	@Field
	public String getFunction() {
		return this.function;
	}

        /**
         * Sets the function of this authority into the heritage context.
         * @param function
         */
	public void setFunction(String function) {
		this.function = function;
	}

        /**
         * Returns a string with the authority function.
         * @return String with the authority function.
         */
        @Override
	public String toString() {
		return this.getFunction();
	}

        /**
         * Object name.
         * @return
         */
        @Override
	@Transient
	public String getRepr() {
		return "AuthorityHeritage";
	}
}
