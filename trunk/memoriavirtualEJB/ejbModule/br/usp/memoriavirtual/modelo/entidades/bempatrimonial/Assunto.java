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
public class Assunto {

	/**
	 * 
	 */
	public Assunto() {}
	
	@Id
	private String assunto;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "assuntos")
	private Set<BemPatrimonial> bens;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Set<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(Set<BemPatrimonial> bens) {
		this.bens = bens;
	}

}
