package br.usp.memoriavirtual.modelo.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;



@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@SequenceGenerator(name = "ENTIDADECOMMIDIA_ID", sequenceName = "ENTIDADECOMMIDIA_SEQ", allocationSize = 1)
public  class EntidadeComMidia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTIDADECOMMIDIA_ID")
	protected long id;  
	@OneToMany(mappedBy="entidadeComMidia", cascade=CascadeType.ALL )
   	protected List<Multimidia> multimidia = new ArrayList<Multimidia>();

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	} 


	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}


	/**
	 * @return the multimidia
	 */
	public List<Multimidia> getMultimidia() {
		return multimidia;
	}


	/**
	 * @param multimidia the multimidia to set
	 */
	public void setMultimidia(List<Multimidia> multimidia) {
		this.multimidia = multimidia;
	}



	
	
	public void addReferenciaMultimidia(Multimidia e){
		e.setEntidadeComMidia(this);
		this.multimidia.add(e);
	}
	
}	
