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
	
	
	public Diagnostico(String estPresercacao, String estConservacao,
			String notaEstConservacao) {
		super();
		this.estPresercacao = estPresercacao;
		this.estConservacao = estConservacao;
		this.notaEstConservacao = notaEstConservacao;
	}
	private String estPresercacao;
	private String estConservacao;
	private String notaEstConservacao;
	/**
	 * @return the estPresercacao
	 */
	public String getEstPresercacao() {
		return estPresercacao;
	}
	/**
	 * @param estPresercacao the estPresercacao to set
	 */
	public void setEstPresercacao(String estPresercacao) {
		this.estPresercacao = estPresercacao;
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
