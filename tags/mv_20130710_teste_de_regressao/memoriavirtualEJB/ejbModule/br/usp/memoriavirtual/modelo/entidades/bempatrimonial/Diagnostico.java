package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class Diagnostico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2256116542157135280L;
	
	
	public Diagnostico() {
		super();
	}
	
	public Diagnostico(String estPreservacao,
			String notaEstConservacao) {
		super();
		this.estPreservacao = estPreservacao;
		this.estConservacao = "";
		this.notaEstConservacao = notaEstConservacao;
	}
	public Diagnostico(String estPreservacao, String estConservacao,
			String notaEstConservacao) {
		super();
		this.estPreservacao = estPreservacao;
		this.estConservacao = estConservacao;
		this.notaEstConservacao = notaEstConservacao;
	}
	private String estPreservacao;
	private String estConservacao;
	private String notaEstConservacao;
	/**
	 * @return the estPreservacao
	 */
	public String getEstPreservacao() {
		return estPreservacao;
	}
	/**
	 * @param estPreservacao the estPreservacao to set
	 */
	public void setEstPreservacao(String estPreservacao) {
		this.estPreservacao = estPreservacao;
	}
	/**
	 * @return the estConservacao
	 */
	public String getEstConservacao() {
		return estConservacao;
	}
	/**
	 * @param estConservacao the estConservacao to set
	 */
	public void setEstConservacao(String estConservacao) {
		this.estConservacao = estConservacao;
	}
	/**
	 * @return the notaEstConservacao
	 */
	public String getNotaEstConservacao() {
		return notaEstConservacao;
	}
	/**
	 * @param notaEstConservacao the notaEstConservacao to set
	 */
	public void setNotaEstConservacao(String notaEstConservacao) {
		this.notaEstConservacao = notaEstConservacao;
	}
}
