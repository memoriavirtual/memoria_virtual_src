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

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;

/**
 * Intervention model bean
 * @author Kiyoshi de Brito Murata <kbmurata@gmail.com>
 * @author Gabriel de Faria Andery <gfandery@gmail.com>
 */
@Entity
@Table(name = "`intervention`")
public class Intervention
	extends AbstractPersistentObject
	implements java.io.Serializable
{
	private static final long serialVersionUID = -2587995598369197991L;

	private String responsible;
	private String description;
	private String date;

	private Set<Heritage> heritages = new HashSet<Heritage>(0);

	public Intervention() {}

	public Intervention(String responsible,
                            String description,
                            String date) {
		this.responsible = responsible;
		this.description = description;
		this.date = date;
	}

	public Intervention(String responsible,
                            String description,
                            String date,
                            Set<Heritage> heritages) {
		this.responsible = responsible;
		this.description = description;
		this.date = date;
		this.heritages = heritages;
	}

	@Column(name = "`responsible`")
	@Field
	public String getResponsible() {
		return this.responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
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

	@Column(name = "`date`")
	@Field
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@ContainedIn
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "`intervention_heritage`", joinColumns = { @JoinColumn(name = "`id_intervention`", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "`id_heritage`", nullable = false, updatable = false) })
	public Set<Heritage> getHeritages() {
		return this.heritages;
	}

	public void setHeritages(Set<Heritage> heritages) {
		this.heritages = heritages;
	}

        @Override
	@Transient
	public String getRepr() {
		return String.format("%s", description);
	}
}
