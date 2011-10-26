package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

/**
 * Mananged Bean responsável pelo controle do cadatro de uma nova instituição
 */

public class CadastrarInstituicaoMB {

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private CadastrarInstituicaoRemote cadastrarInstituicaoEJB;
	private String nome = "";
	private String localizacao = "";
	private String endereco = "";
	private String cidade = "";
	private String estado = "";
	private String cep = "";
	private String telefone = "";

	/**
	 * Construtor padrão
	 */
	public CadastrarInstituicaoMB() {

	}

	public String cadastrarInstituicao() {

		/*
		 * FacesContext.getCurrentInstance().addMessage( "resultado", new
		 * FacesMessage(FacesMessage.SEVERITY_INFO,
		 * "Cadastro concluido com sucesso.", null));
		 */

		if (this.validateNome() && this.validateLocalizacao()
				&& this.validateCep() && this.validateTelefone()) {
			Instituicao instituicao = new Instituicao(this.nome,
					this.localizacao, this.endereco, this.cidade, this.estado,
					this.cep, this.telefone);

			cadastrarInstituicaoEJB.cadastrarInstituicao(instituicao);
			if (!memoriaVirtualEJB.disponibilidadeNomeInstituicao(this.nome)) {

				this.nome = "";
				this.localizacao = "";
				this.endereco = "";
				this.cidade = "";
				this.estado = "";
				this.cep = "";
				this.telefone = "";

				MensagensDeErro
						.getSucessMessage(
								"cadastrarInstituicaoSucessocadastramento",
								"resultado");
				return null;
			}
		}
		MensagensDeErro.getErrorMessage(
				"cadastrarInstituicaoErrocadastramento", "resultado");
		return null;
	}
	
	
	public String resetCadastrarinstituicao(){
		return "reset";
	}
	/**
	 * Getter do nome
	 * 
	 * @return (String) Nome da instituicao
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Getter da localização
	 * 
	 * @return (String) Localização da instituicao
	 */
	public String getLocalizacao() {
		return this.localizacao;
	}

	/**
	 * Getter do endereco
	 * 
	 * @return (String) Endereco da instituicao
	 */
	public String getEndereco() {
		return this.endereco;
	}

	/**
	 * Getter da cidade
	 * 
	 * @return (String) Cidade da instituicao
	 */
	public String getCidade() {
		return this.cidade;
	}

	/**
	 * Getter do estado(unidade da federação)
	 * 
	 * @return (String) Estado da instituicao
	 */
	public String getEstado() {
		return this.estado;
	}

	/**
	 * Getter do CEP
	 * 
	 * @return (String) CEP da instituicao
	 */
	public String getCep() {
		return this.cep;
	}

	/**
	 * Getter do telefone
	 * 
	 * @return (String) telefone da instituicao
	 */
	public String getTelefone() {
		return this.telefone;
	}

	/**
	 * Getter dos estados (Usado no form de cadastro)
	 * 
	 * @return (List<String>) todos os estados do Brasil (somente estados)
	 */
	public List<SelectItem> getEstadoSigla() {

		List<SelectItem> estados = new ArrayList<SelectItem>();

		estados.add(new SelectItem(null, "--Escolha o estado--"));
		estados.add(new SelectItem("AL", "AL"));
		estados.add(new SelectItem("AM", "AM"));
		estados.add(new SelectItem("AP", "AP"));
		estados.add(new SelectItem("BA", "BA"));
		estados.add(new SelectItem("CE", "CE"));
		estados.add(new SelectItem("DF", "DF"));
		estados.add(new SelectItem("ES", "ES"));
		estados.add(new SelectItem("GO", "GO"));
		estados.add(new SelectItem("MA", "MA"));
		estados.add(new SelectItem("MG", "MG"));
		estados.add(new SelectItem("MS", "MS"));
		estados.add(new SelectItem("MT", "MT"));
		estados.add(new SelectItem("PA", "PA"));
		estados.add(new SelectItem("PB", "PB"));
		estados.add(new SelectItem("PE", "PE"));
		estados.add(new SelectItem("PI", "PI"));
		estados.add(new SelectItem("PR", "PR"));
		estados.add(new SelectItem("RJ", "RJ"));
		estados.add(new SelectItem("RN", "RN"));
		estados.add(new SelectItem("RO", "RO"));
		estados.add(new SelectItem("RR", "RR"));
		estados.add(new SelectItem("RS", "RS"));
		estados.add(new SelectItem("SC", "SC"));
		estados.add(new SelectItem("SP", "SP"));
		estados.add(new SelectItem("SE", "SE"));
		estados.add(new SelectItem("TO", "TO"));

		return estados;
	}

	/**
	 * Setter do nome da instituição
	 */
	public void setNome(String pNome) {
		this.nome = pNome;
	}

	/**
	 * Setter da localização da instituicao
	 */
	public void setLocalizacao(String pLocalizacao) {
		this.localizacao = pLocalizacao;
	}

	/**
	 * Setter do endereco da instituicao
	 */
	public void setEndereco(String pEndereco) {
		this.endereco = pEndereco;
	}

	/**
	 * Setter da cidade da instituicao
	 */
	public void setCidade(String pCidade) {
		this.cidade = pCidade;
	}

	/**
	 * Setter do estado (UF) da instituicao
	 */
	public void setEstado(String pEstado) {
		this.estado = pEstado;
	}

	/**
	 * Setter do CEP da instituicao
	 */
	public void setCep(String pCep) {
		this.cep = pCep;
	}

	/**
	 * Setter do telefone da instituicao
	 */
	public void setTelefone(String pTelefone) {
		this.telefone = pTelefone;
	}

	/**
	 * Validacao do nome
	 */
	public void validateNome(AjaxBehaviorEvent event) {
		this.validateNome();
	}

	public boolean validateNome() {

		if (this.nome.equals("")) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroNomeVazio", "validacaoNome");
			return false;
		} else if (!memoriaVirtualEJB.disponibilidadeNomeInstituicao(this.nome)) {
			MensagensDeErro
					.getErrorMessage(
							"cadastrarInstituicaoErroNomeJaCadastrado",
							"validacaoNome");
			return false;
		}
		return true;
	}

	/**
	 * Validacao do Localizacao
	 */
	public void validateLocalizacao(AjaxBehaviorEvent event) {
		this.validateLocalizacao();
	}

	public boolean validateLocalizacao() {
		if (this.localizacao.equals("")) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroLocalizacaoVazio",
					"validacaoLocalizacao");
			return false;
		}/*
		 * else if (!ValidacoesDeCampos.validarFormatoCep(this.cep)) {
		 * MensagensDeErro
		 * .getErrorMessage("cadastrarInstituicaoErroLocalizacaoInvalido",
		 * "validacaoLocalizacao"); return false; }
		 */
		return true;
	}

	/**
	 * Validacao do endereco
	 */
	public void validateEndereco(AjaxBehaviorEvent event) {
		this.validateEndereco();
	}

	public boolean validateEndereco() {
		if (this.endereco.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroEnderecoVazio",
					"validacaoEndereco");
			return false;
		}
		return true;
	}

	/**
	 * Validacao do cidade
	 */
	public void validateCidade(AjaxBehaviorEvent event) {
		this.validateCidade();
	}

	public boolean validateCidade() {
		if (this.cidade.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroCidadeVazio", "validacaoCidade");
			return false;
		}
		return true;
	}

	/**
	 * Validacao do estado
	 */
	public void validateEstado(AjaxBehaviorEvent event) {
		this.validateEstado();
	}

	public boolean validateEstado() {
		if (this.estado.equals("--Escolha o estado--")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroEstadoVazio", "validacaoEstado");
			return false;
		}
		return false;
	}

	/**
	 * Validacao do Cep
	 */
	public void validateCep(AjaxBehaviorEvent event) {
		this.validateCep();
	}

	public boolean validateCep() {
		if (this.cep.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroCEPVazio", "validacaoCep");
			return false;
		} else if (!ValidacoesDeCampos.validarFormatoCep(this.cep)
				&& !this.cep.equals("")) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroCEPInvalido", "validacaoCep");
			return false;
		}
		return true;
	}

	/**
	 * Validacao do Telefone
	 */
	public void validateTelefone(AjaxBehaviorEvent event) {
		this.validateTelefone();
	}

	public boolean validateTelefone() {
		if (this.telefone.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroTelefoneVazio",
					"validacaoTelefone");
			return false;
		} else if (!ValidacoesDeCampos.validarFormatoTelefone(this.telefone)
				&& !this.telefone.equals("")) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroTelefoneInvalido",
					"validacaoTelefone");
			return false;
		}
		return true;
	}
}
