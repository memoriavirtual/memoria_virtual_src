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

/**
 * InformationSource model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 */
@Entity
@Table(name = "`informationsource`")
public class InformationSource
	extends AbstractPersistentObject
	implements java.io.Serializable
{
	private static final long serialVersionUID = 2496751473283314897L;

	private String bibliography;
	private String bibliographytype;

	private Set<Heritage> heritages = new HashSet<Heritage>(0);

	public InformationSource() {}

	public InformationSource(String bibliography) {
		this.bibliography = bibliography;
	}

	public InformationSource(String bibliography,
			String bibliographytype, Set<Heritage> heritages) {
		this.bibliography = bibliography;
		this.bibliographytype = bibliographytype;
		this.heritages = heritages;
	}

	@NaturalId
	@Column(name = "`bibliography`", unique = true, nullable = false)
	@Type(type = "text")
	public String getBibliography() {
		return this.bibliography;
	}

	public void setBibliography(String bibliography) {
		this.bibliography = bibliography;
	}

	@Column(name = "`bibliographytype`")
	@Type(type = "text")
	public String getBibliographytype() {
		return this.bibliographytype;
	}

	public void setBibliographytype(String bibliographytype) {
		this.bibliographytype = bibliographytype;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`informationsource_heritage`", joinColumns = { @JoinColumn(name = "`id_informationsource`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) })
	public Set<Heritage> getHeritages() {
		return this.heritages;
	}

	public void setHeritages(Set<Heritage> heritages) {
		this.heritages = heritages;
	}

	@Transient
	public String getRepr() {
		return String.format("%s", bibliography);
	}
}
