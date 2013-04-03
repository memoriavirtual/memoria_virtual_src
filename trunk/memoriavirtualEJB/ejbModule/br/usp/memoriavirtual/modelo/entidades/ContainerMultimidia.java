package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "CONTAINERMULTIMIDIA_ID", sequenceName = "CONTAINERMULTIMIDIA_SEQ", allocationSize = 1)
public class ContainerMultimidia implements Serializable{

	public ContainerMultimidia() {
		super();
		multimidia = new ArrayList<Multimidia>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7661055811957115846L;

	@OneToMany(mappedBy = "containerMultimidia", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	protected List<Multimidia> multimidia = new ArrayList<Multimidia>();

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTAINERMULTIMIDIA_ID")
	protected long id;

	
	
	
	
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param multimidia
	 *            the multimidia to set
	 */
	public void setMultimidia(List<Multimidia> multimidia) {
		this.multimidia = multimidia;
	}

	public void addMultimidia(Multimidia e) {
		e.setEntidadeComMidia(this);
		this.multimidia.add(e);
	}

}
