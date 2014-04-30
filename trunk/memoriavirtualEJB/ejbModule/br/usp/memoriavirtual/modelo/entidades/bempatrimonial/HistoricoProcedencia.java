package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class HistoricoProcedencia implements Serializable {

	public static enum TipoAquisicao {
		comprado, trocado, doacaoInstitucional, doacaoPessoal, emprestado
	}

	private static final long serialVersionUID = 8644876249752710680L;
	private TipoAquisicao tipoAquisicao = HistoricoProcedencia.TipoAquisicao.comprado;
	private String valorVenalTransacao = "";
	private String dadosDocTransacao = "";
	private String primeiroProprietario = "";
	private String historico = "";
	private String instrumentoPesquisa = "";
	private String dataAquisicao = "";

	public HistoricoProcedencia() {
	}

	public String getValorVenalTransacao() {
		return valorVenalTransacao;
	}

	public void setValorVenalTransacao(String valorVenalTransacao) {
		this.valorVenalTransacao = valorVenalTransacao;
	}

	public String getDadosDocTransacao() {
		return dadosDocTransacao;
	}

	public void setDadosDocTransacao(String dadosDocTransacao) {
		this.dadosDocTransacao = dadosDocTransacao;
	}

	public String getPrimeiroProprietario() {
		return primeiroProprietario;
	}

	public void setPrimeiroProprietario(String primeiroProprietario) {
		this.primeiroProprietario = primeiroProprietario;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getInstrumentoPesquisa() {
		return instrumentoPesquisa;
	}

	public void setInstrumentoPesquisa(String instrumentoPesquisa) {
		this.instrumentoPesquisa = instrumentoPesquisa;
	}

	public String getDataAquisicao() {
		return dataAquisicao;
	}

	public void setDataAquisicao(String dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}

	public TipoAquisicao getTipoAquisicao() {
		return tipoAquisicao;
	}

	public void setTipoAquisicao(TipoAquisicao tipoAquisicao) {
		this.tipoAquisicao = tipoAquisicao;
	}

}
