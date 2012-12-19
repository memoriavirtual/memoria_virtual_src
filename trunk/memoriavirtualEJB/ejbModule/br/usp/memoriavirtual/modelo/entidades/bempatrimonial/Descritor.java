/**
 * 
 */
package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * @author mac
 *
 */
@Entity
public class Descritor {

	/**
	 * 
	 */
	public Descritor() {
		
	}
	@Id
	private String descritor;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "descritores")
	private Set<BemPatrimonial> bens;

	public String getDescritor() {
		return descritor;
	}

	public void setDescritor(String descritor) {
		this.descritor = descritor;
	}

	public Set<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(Set<BemPatrimonial> bens) {
		this.bens = bens;
	}
	
	
}
