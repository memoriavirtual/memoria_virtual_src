package br.usp.memoriavirtual.modelo.entidades.bempatrimonial;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class DisponibilidadeUsoProtecao implements Serializable {

	public static enum CondicoesAcesso {
		livre, sobConsulta
	}

	public static enum CondicoesReproducao {
		livre, nao
	}

	public static enum Disponibilidade {
		acervo, evento, exposicaoPermanente, emprestimo, restauro, baixa
	}

	public static enum Protecao {
		sim, nao, emProgresso
	}

	private static final long serialVersionUID = 8365500506415705562L;

	private Disponibilidade disponibilidade = DisponibilidadeUsoProtecao.Disponibilidade.acervo;
	private CondicoesAcesso condicoesAcesso = DisponibilidadeUsoProtecao.CondicoesAcesso.livre;
	private CondicoesReproducao condicoesReproducao = DisponibilidadeUsoProtecao.CondicoesReproducao.livre;
	private String dataRetorno = "";
	@Lob
	private String notasUsoAproveitamento = "";
	private Protecao protecao = DisponibilidadeUsoProtecao.Protecao.nao;
	private String legislacao = "";
	private String protetoraInstituicao = "";

	public DisponibilidadeUsoProtecao() {
	}

	public String getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(String dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public String getNotasUsoAproveitamento() {
		return notasUsoAproveitamento;
	}

	public void setNotasUsoAproveitamento(String notasUsoAproveitamento) {
		this.notasUsoAproveitamento = notasUsoAproveitamento;
	}

	public String getLegislacao() {
		return legislacao;
	}

	public void setLegislacao(String legislacao) {
		this.legislacao = legislacao;
	}

	public String getProtetoraInstituicao() {
		return protetoraInstituicao;
	}

	public void setProtetoraInstituicao(String protetoraInstituicao) {
		this.protetoraInstituicao = protetoraInstituicao;
	}

	public Disponibilidade getDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(Disponibilidade disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	public CondicoesAcesso getCondicoesAcesso() {
		return condicoesAcesso;
	}

	public void setCondicoesAcesso(CondicoesAcesso condicoesAcesso) {
		this.condicoesAcesso = condicoesAcesso;
	}

	public CondicoesReproducao getCondicoesReproducao() {
		return condicoesReproducao;
	}

	public void setCondicoesReproducao(CondicoesReproducao condicoesReproducao) {
		this.condicoesReproducao = condicoesReproducao;
	}

	public void setProtecao(Protecao protecao) {
		this.protecao = protecao;
	}

	public Protecao getProtecao() {
		return protecao;
	}

}
