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

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.ContainedIn;

/**
 * Descriptor model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 */
@Entity
@Table(name = "`descriptor`")
public class Descriptor
	extends AbstractPersistentObject
	implements java.io.Serializable
{
	private static final long serialVersionUID = -135233366110723119L;

	private String descriptor;
	
	private Set<Heritage> heritages = new HashSet<Heritage>(0);

	public Descriptor() {}

	public Descriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public Descriptor(String descriptor, Set<Heritage> heritages) {
		this.descriptor = descriptor;
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
	@Column(name = "`descriptor`", nullable = false)
	@Field
	@Type(type = "text")
	public String getDescriptor() {
		return this.descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	@ContainedIn
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`descriptor_heritage`", 
                   joinColumns = { @JoinColumn(name = "`id_descriptor`",
                                               nullable = false,
                                               updatable = false) },
                   inverseJoinColumns = { @JoinColumn(name = "`id_heritage`",
                                          nullable = false,
                                          updatable = false) })
	public Set<Heritage> getHeritages() {
		return this.heritages;
	}

	public void setHeritages(Set<Heritage> heritages) {
		this.heritages = heritages;
	}

	@Transient
    @Override
	public String getRepr() {
		return String.format("%s", descriptor);
	}
}
