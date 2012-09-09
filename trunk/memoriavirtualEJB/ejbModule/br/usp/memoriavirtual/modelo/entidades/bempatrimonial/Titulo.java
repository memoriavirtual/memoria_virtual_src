package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Titulo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TITULO_ID")
	private long id;
	@ManyToOne
	private BemPatrimonial bemPatrimonial;
	private String tipo;
	private String valor;
	@Transient
	private boolean select;

	/**
	 * @param tipo
	 * @param valor
	 * @param complemento
	 */
	public Titulo(String tipo, String valor, String complemento) {
		super();
		this.tipo = tipo;
		this.valor = valor;
	}

	public Titulo() {
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the select
	 */
	public boolean getSelect() {
		return select;
	}

	/**
	 * @param select
	 *            the select to set
	 */
	public void setSelect(boolean select) {
		this.select = select;
	}

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
}
