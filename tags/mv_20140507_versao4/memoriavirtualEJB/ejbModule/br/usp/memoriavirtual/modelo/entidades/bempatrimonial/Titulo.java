package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name = "BEMPATRIMONIAL_TITULOS")
@SequenceGenerator(name = "TITULO_ID", sequenceName = "TITULO_SEQ", allocationSize = 1)
public class Titulo implements Serializable {

	public enum TipoTitulo {
		equivalente, atribuido, subtitulo, anterior, posterior, suplemento, correlato
	};

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TITULO_ID")
	private long id;

	@XmlTransient
	@ManyToOne(fetch = FetchType.EAGER)
	private BemPatrimonial bemPatrimonial;
	private TipoTitulo tipo = Titulo.TipoTitulo.anterior;
	private String valor = "";
	@Transient
	private boolean select = false;

	public Titulo() {

	}

	@XmlTransient
	public BemPatrimonial getBempatrimonial() {
		return bemPatrimonial;
	}

	public void setBempatrimonial(BemPatrimonial bempatrimonial) {
		this.bemPatrimonial = bempatrimonial;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	public void setBemPatrimonial(BemPatrimonial bemPatrimonial) {
		this.bemPatrimonial = bemPatrimonial;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public TipoTitulo getTipo() {
		return tipo;
	}

	public void setTipo(TipoTitulo tipo) {
		this.tipo = tipo;
	}

}
