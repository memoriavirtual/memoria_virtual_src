package org.mvirtual.persistence.entity;

import org.mvirtual.persistence.entity.relation.AuthorityHeritage;
import org.mvirtual.persistence.entity.relation.embedded.AuthorityHeritageId;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;
//import org.hibernate.annotations.Type;

//import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
//import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.DocumentId;
/**
 * Authority model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 */
@Entity
@Table(name = "`authority`")
public class Authority
	extends AbstractPersistentObject
	implements java.io.Serializable
{
	private static final long serialVersionUID = 7009755448890932878L;

        /**
         * Author's name.
         */
	private String name;

        /**
         * That's it. Only nickname.
         */
	private String nickName;

        /**
         * The birthDate is String because birthDate may be unprecise.
         */
	private String birthDate;

        /**
         * The deathDate is String because deathDate may be unprecise.
         */
	private String deathDate;

        /**
         *
         */
	private Set<AuthorityHeritage> authorityHeritages = new HashSet<AuthorityHeritage>(0);

        /**
         * Constructor without parameter. A Must for Hibernate.
         */
	public Authority() {}

        /**
         * Coonstructor with no relational attributes.
         * @param name Author's name.
         * @param nickname His nickname.
         * @param birthdate Author's birthdate. The birthDate is String because birthDate may be unprecise.
         * @param deathdate Author's deathdate. The deathDate is String because birthDate may be unprecise.
         */
	public Authority(String name, 
                         String nickname,
                         String birthdate,
			 String deathdate) {
		this.name = name;
		this.nickName = nickname;
		this.birthDate = birthdate;
		this.deathDate = deathdate;
	}

        /**
         * Coonstructor with no relational attributes.
         * @param name Author's name.
         * @param nickname His nickname.
         * @param birthdate Author's birthdate. The birthDate is String because birthDate may be unprecise.
         * @param deathdate Author's deathdate. The deathDate is String because birthDate may be unprecise.
         * @param authorityHeritages Relation with heritage. Indicates which heritages have this author.
         */
	public Authority(String name, 
                         String nickname,
                         String birthdate,
                         String deathdate,
                         Set<AuthorityHeritage> authorityHeritages) {
		this.name = name;
		this.nickName = nickname;
		this.birthDate = birthdate;
		this.deathDate = deathdate;
		this.authorityHeritages = authorityHeritages;
	}

	/**
         * Id of this register in database.
         * @return id.
         */
	@Transient
	@DocumentId
        @Override
	public Long getId() {
		return super.getId();
	}
	
        /**
         * Gets author's name.
         * @return Author's name.
         */
	@NaturalId
	@Column(name = "`name`")
	@Fields( {
		@Field(index = Index.TOKENIZED),
		@Field(name = "authorityName_forSort", index = Index.UN_TOKENIZED, store = Store.YES)
	} )
	public String getName() {
		return this.name;
	}

        /**
         * Sets author's name.
         * @param name Author's name.
         */
	public void setName(String name) {
		this.name = name;
	}

        /**
         * Gets author's nick.
         * @return Author's nickname.
         */
	@Column(name = "`nickname`")
	@Field
	public String getNickname() {
		return this.nickName;
	}

        /**
         * Sets author's nick.
         * @param nickname Author's nickname.
         */
	public void setNickname(String nickname) {
		this.nickName = nickname;
	}

        /**
         * Gets author's birthdate.
         * @return Author's birthdate.
         */
	@Column(name = "`birthdate`")
	@Field
	public String getBirthDate() {
		return this.birthDate;
	}

        /**
         * Sets author's birthdate.
         * @param birthdate Author's birthdate.
         */
	public void setBirthDate(String birthdate) {
		this.birthDate = birthdate;
	}

        /**
         * Gets author's deathdate.
         * @return Author's deathdate.
         */
	@Column(name = "`deathdate`")
	@Field
	public String getDeathDate() {
		return this.deathDate;
	}

        /**
         * Sets author's deathdate.
         * @param deathdate Author's deathdate.
         */
	public void setDeathDate(String deathdate) {
		this.deathDate = deathdate;
	}

        /**
         * Gets the set that implements relation between Authority and Heritage.
         * @return A set of AuthorityHeritage relation.
         */
	@ContainedIn
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "authority")
	public Set<AuthorityHeritage> getAuthorityHeritages() {
		return this.authorityHeritages;
	}

        /**
         * Sets the set that implements relation between Authority and Heritage.
         * @param authorityHeritages A set of AuthorityHeritage relation.
         */
	public void setAuthorityHeritages(Set<AuthorityHeritage> authorityHeritages) {
		this.authorityHeritages = authorityHeritages;
	}
        
        public void addHeritage (Heritage heritage, 
                                 String authorshipType,
                                 String function) {


            AuthorityHeritageId authorityHeritageId =
                    new AuthorityHeritageId (heritage.getId(),
                                             this.getId (),
                                             authorshipType);
            
            AuthorityHeritage authorityHeritage =
                    new AuthorityHeritage(authorityHeritageId,
                                          heritage,
                                          this,
                                          function);


            this.authorityHeritages.add (authorityHeritage);


        }

        /**
         * Prints the author's name.
         * @return String with author's name.
         */
	@Transient
        @Override
	public String getRepr() {
		return String.format("%s", name);
	}
}
