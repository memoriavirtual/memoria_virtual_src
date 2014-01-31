/**
 * 
 */
package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;
 
/**
 * @author bigmac
 *
 */
@Embeddable
public class Pesquisador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5418736654663671679L;

	/**
	 * 
	 */
	public Pesquisador() {
	}
	
	
	/**
	 * @param nome
	 * @param dataPesquisa
	 * @param notasPesquizador
	 */
	public Pesquisador(String nome, String dataPesquisa, String notasPesquizador) {
		super();
		this.nome = nome;
		this.dataPesquisa = dataPesquisa;
		this.notasPesquisador = notasPesquizador;
	}


	private String nome;
	private String dataPesquisa;
	private String notasPesquisador;

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the dataPesquisa
	 */
	public String getDataPesquisa() {
		return dataPesquisa;
	}
	/**
	 * @param dataPesquisa the dataPesquisa to set
	 */
	public void setDataPesquisa(String dataPesquisa) {
		this.dataPesquisa = dataPesquisa;
	}
	/**
	 * @return the notasPesquizador
	 */
	public String getNotasPesquisador() {
		return notasPesquisador;
	}
	/**
	 * @param notasPesquizador the notasPesquizador to set
	 */
	public void setNotasPesquisador(String notasPesquizador) {
		this.notasPesquisador = notasPesquizador;
	}
}
