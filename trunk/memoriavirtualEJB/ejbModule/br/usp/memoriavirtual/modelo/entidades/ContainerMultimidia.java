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
public class ContainerMultimidia implements Serializable {

	private static final long serialVersionUID = 7661055811957115846L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTAINERMULTIMIDIA_ID")
	protected long id;

	@OneToMany(mappedBy = "containerMultimidia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	protected List<Multimidia> multimidia = new ArrayList<Multimidia>();

	public ContainerMultimidia() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Multimidia> getMultimidia() {
		return multimidia;
	}

	public void setMultimidia(List<Multimidia> multimidia) {
		this.multimidia = multimidia;
	}
}
