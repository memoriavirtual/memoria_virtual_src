/**
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
public class Intervencao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 753537968848831284L;

	/**
	 * 
	 */
	public Intervencao() {
	}
	
	
	public Intervencao(String data, String responsavel, String descricao) {
		super();
		this.data = data;
		this.responsavel = responsavel;
		this.descricao = descricao;
	}
	
	
	private String data;
	private String responsavel;
	private String descricao;

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the responsavel
	 */
	public String getResponsavel() {
		return responsavel;
	}
	/**
	 * @param responsavel the responsavel to set
	 */
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
