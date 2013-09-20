/**
 * 
 */
package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author mac
 *
 */
@Entity
public class Descritor implements Comparable<Descritor> , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4048084487550749341L;

	/**
	 * 
	 */
	public Descritor() {
		
	}
	@Id
	private String descritor;
	
	@XmlTransient
	@ManyToMany( fetch = FetchType.EAGER, mappedBy = "descritores", cascade = CascadeType.MERGE  )
	private Set<BemPatrimonial> bens;

	public String getDescritor() {
		return descritor;
	}

	public void setDescritor(String descritor) {
		this.descritor = descritor;
	}

	@XmlTransient
	public Set<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(Set<BemPatrimonial> bens) {
		this.bens = bens;
	}

	@Override
	public int compareTo(Descritor o) {
		
		return this.descritor.compareTo(o.descritor);
	}
	
	
}
