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
public class DisponibilidadeUsoProtecao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8365500506415705562L;

	/**
	 * 
	 */
	public DisponibilidadeUsoProtecao() {
	}
	
	
	private String disponibilidade;
	private String condicoesAcesso;
	private String condicoesReproducao;
	private String dataRetorno;
	private String notasUsoAproveitamento;
	private String protecao;
	private String legislacao;
	private String protetoraInstituicao;

	public DisponibilidadeUsoProtecao(String disponibilidade,
			String condicoesAcesso, String condicoesReproducao,
			String dataRetorno, String notasUsoAproveitamento, String protecao,
			String legislacao, String instituicaoProtetora) {
		super();
		this.disponibilidade = disponibilidade;
		this.condicoesAcesso = condicoesAcesso;
		this.condicoesReproducao = condicoesReproducao;
		this.dataRetorno = dataRetorno;
		this.notasUsoAproveitamento = notasUsoAproveitamento;
		this.protecao = protecao;
		this.legislacao = legislacao;
		this.protetoraInstituicao = instituicaoProtetora;
	}
	
	/**
	 * @return the disponibilidade
	 */
	public String getDisponibilidade() {
		return disponibilidade;
	}
	/**
	 * @param disponibilidade the disponibilidade to set
	 */
	public void setDisponibilidade(String disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	/**
	 * @return the condicoesAcesso
	 */
	public String getCondicoesAcesso() {
		return condicoesAcesso;
	}
	/**
	 * @param condicoesAcesso the condicoesAcesso to set
	 */
	public void setCondicoesAcesso(String condicoesAcesso) {
		this.condicoesAcesso = condicoesAcesso;
	}
	/**
	 * @return the condicoesReproducao
	 */
	public String getCondicoesReproducao() {
		return condicoesReproducao;
	}
	/**
	 * @param condicoesReproducao the condicoesReproducao to set
	 */
	public void setCondicoesReproducao(String condicoesReproducao) {
		this.condicoesReproducao = condicoesReproducao;
	}
	/**
	 * @return the dataRetorno
	 */
	public String getDataRetorno() {
		return dataRetorno;
	}
	/**
	 * @param dataRetorno the dataRetorno to set
	 */
	public void setDataRetorno(String dataRetorno) {
		this.dataRetorno = dataRetorno;
	}
	/**
	 * @return the notasUsoAproveitamento
	 */
	public String getNotasUsoAproveitamento() {
		return notasUsoAproveitamento;
	}
	/**
	 * @param notasUsoAproveitamento the notasUsoAproveitamento to set
	 */
	public void setNotasUsoAproveitamento(String notasUsoAproveitamento) {
		this.notasUsoAproveitamento = notasUsoAproveitamento;
	}
	/**
	 * @return the protecao
	 */
	public String getProtecao() {
		return protecao;
	}
	/**
	 * @param protecao the protecao to set
	 */
	public void setProtecao(String protecao) {
		this.protecao = protecao;
	}
	/**
	 * @return the legislacao
	 */
	public String getLegislacao() {
		return legislacao;
	}
	/**
	 * @param legislacao the legislacao to set
	 */
	public void setLegislacao(String legislacao) {
		this.legislacao = legislacao;
	}
	/**
	 * @return the instituicao
	 */
	public String getProtetoraInstituicao() {
		return protetoraInstituicao;
	}
	/**
	 * @param instituicao the instituicao to set
	 */
	public void setProtetoraInstituicao(String instituicao) {
		this.protetoraInstituicao = instituicao;
	}
	
}
