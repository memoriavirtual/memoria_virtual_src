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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

/**
 * Multimedia model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 */
@Entity
@Table(name = "`multimedia`")
@Indexed
public class Multimedia
	extends AbstractPersistentObject
	implements java.io.Serializable
{
	private static final long serialVersionUID = 1721730577372374347L;

	private String name;
	private String title;
	private String type;
	private String description;

	private Set<Heritage> heritages = new HashSet<Heritage>(0);

	public Multimedia() {}

	public Multimedia(String name, String title, String type) {
		this.name = name;
		this.title = title;
		this.type = type;
	}

	public Multimedia(String name, String title, String type, String description) {
		this.name = name;
		this.title = title;
		this.type = type;
		this.description = description;
	}

	public Multimedia(String name, String title, String type,
			String description, Set<Heritage> heritages) {
		this.name = name;
		this.title = title;
		this.type = type;
		this.description = description;
		this.heritages = heritages;
	}

	/*
	@Transient
	@DocumentId
	public Long getID() {
		return super.getId();
	}
	*/

	@NaturalId
	@Column(name = "`name`", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "`title`", nullable = false)
	@Type(type = "text")
	@Field
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "`type`", nullable = false)
	@Field
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "`description`")
	@Type(type = "text")
	@Field
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`multimedia_heritage`", joinColumns = { @JoinColumn(name = "`id_multimedia`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) })
	@IndexedEmbedded(depth = 1)
	public Set<Heritage> getHeritages() {
		return this.heritages;
	}

	public void setHeritages(Set<Heritage> heritages) {
		this.heritages = heritages;
	}

	@Transient
	public String getRepr() {
		return String.format("%s", title);
	}
}
