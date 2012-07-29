package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;


@Embeddable
public class Titulo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param tipo
	 * @param valor
	 * @param complemento
	 */
	public Titulo(String tipo, String valor, String complemento) {
		super();
		this.tipo = tipo;
		this.valor = valor;
		this.complemento = complemento;
	}
	public Titulo() {
	}
	@Transient
	private String select;
	private String tipo;	
	private String valor;	
	private String complemento;
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
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
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}
	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	/**
	 * @return the select
	 */
	public String getSelect() {
		return select;
	}
	/**
	 * @param select the select to set
	 */
	public void setSelect(String select) {
		this.select = select;
	}
}
