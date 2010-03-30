package org.mvirtual.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
//import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import org.hibernate.search.annotations.Field;
//import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.ContainedIn;

//import javax.persistence.*;
import org.hibernate.search.annotations.DocumentId;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


/**
 * HeritageType model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 */
@Entity
@Table(name = "`heritagetype`")
public class HeritageType
	extends AbstractPersistentObject
	implements java.io.Serializable
{
	private static final long serialVersionUID = -7926838336351685205L;

	private String heritageType;

	private Set<Heritage> heritages = new HashSet<Heritage>(0);

	public HeritageType() {}

	public HeritageType(String heritagetype) {
		this.heritageType = heritagetype;
	}

	public HeritageType(String heritagetype, Set<Heritage> heritages) {
		this.heritageType = heritagetype;
		this.heritages = heritages;
	}

//        @Override
//	@DocumentId
//	@Id
//	@Column(name = "`id`", unique = true, nullable = false, insertable = false, updatable = false)
//	public Long getId() { return id; }

	@NaturalId
	@Column(name = "`heritagetype`", unique = true, nullable = false)
	@Type(type = "text")
	@Field
	public String getHeritageType() {
		return this.heritageType;
	}

	public void setHeritageType(String heritagetype) {
		this.heritageType = heritagetype;
	}

	@ContainedIn
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`heritagetype_heritage`", 
                   joinColumns = { @JoinColumn(name = "`id_heritagetype`", nullable = false, updatable = false) },
                   inverseJoinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) })
	public Set<Heritage> getHeritages() {
		return this.heritages;
	}

	public void setHeritages(Set<Heritage> heritages) {
		this.heritages = heritages;
	}

        @Override
	@Transient
	public String getRepr() {
		return String.format("%s", heritageType);
	}
}
