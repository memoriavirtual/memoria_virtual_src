package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class HistoricoProcedencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8644876249752710680L;

	public HistoricoProcedencia() {
	}
	private String tipoAquisicao;
	private String valorVenalTransacao;
	private String dadosDocTransacao;
	private String primeiroProprietario;
	private String historico;
	private String instrumentoPesquisa;

	public HistoricoProcedencia(String tipoAquisicao,
			String valorVenalTransacao, String dadosDocTransacao,
			String primeiroProprietario, String historico,
			String instrumentoPesquisa) {
		super();
		this.tipoAquisicao = tipoAquisicao;
		this.valorVenalTransacao = valorVenalTransacao;
		this.dadosDocTransacao = dadosDocTransacao;
		this.primeiroProprietario = primeiroProprietario;
		this.historico = historico;
		this.instrumentoPesquisa = instrumentoPesquisa;
	}
	/**
	 * @return the tipoAquisicao
	 */
	public String getTipoAquisicao() {
		return tipoAquisicao;
	}
	/**
	 * @param tipoAquisicao the tipoAquisicao to set
	 */
	public void setTipoAquisicao(String tipoAquisicao) {
		this.tipoAquisicao = tipoAquisicao;
	}
	/**
	 * @return the valorVenalTransacao
	 */
	public String getValorVenalTransacao() {
		return valorVenalTransacao;
	}
	/**
	 * @param valorVenalTransacao the valorVenalTransacao to set
	 */
	public void setValorVenalTransacao(String valorVenalTransacao) {
		this.valorVenalTransacao = valorVenalTransacao;
	}
	/**
	 * @return the dadosDocTransacao
	 */
	public String getDadosDocTransacao() {
		return dadosDocTransacao;
	}
	/**
	 * @param dadosDocTransacao the dadosDocTransacao to set
	 */
	public void setDadosDocTransacao(String dadosDocTransacao) {
		this.dadosDocTransacao = dadosDocTransacao;
	}
	/**
	 * @return the primeiroProprietario
	 */
	public String getPrimeiroProprietario() {
		return primeiroProprietario;
	}
	/**
	 * @param primeiroProprietario the primeiroProprietario to set
	 */
	public void setPrimeiroProprietario(String primeiroProprietario) {
		this.primeiroProprietario = primeiroProprietario;
	}
	/**
	 * @return the historico
	 */
	public String getHistorico() {
		return historico;
	}
	/**
	 * @param historico the historico to set
	 */
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	/**
	 * @return the instrumentoPesquisa
	 */
	public String getInstrumentoPesquisa() {
		return instrumentoPesquisa;
	}
	/**
	 * @param instrumentoPesquisa the instrumentoPesquisa to set
	 */
	public void setInstrumentoPesquisa(String instrumentoPesquisa) {
		this.instrumentoPesquisa = instrumentoPesquisa;
	}

}
