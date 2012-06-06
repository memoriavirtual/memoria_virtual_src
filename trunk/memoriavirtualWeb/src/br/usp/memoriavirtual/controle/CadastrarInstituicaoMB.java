package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;

import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

/**
 * Mananged Bean responsável pelo controle do cadaStro de uma nova instituição
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
	private String pais = "";
	private String cep = "";
	private String telefone = "";
	private String caixaPostal = "";
	private String email = "";
	private String URL = "";
	private String identificacaoProprietario = "";
	private String administradorPropriedade = "";
	private String latitude = "";
	private String longitude = "";
	/**
	 * Construtor padrão
	 */
	public CadastrarInstituicaoMB() {

	}

	public String cadastrarInstituicao() {
		if (this.validateNome() && this.validateLocalizacao()
				&& this.validateCep() && this.validateTelefone()) {
			Instituicao instituicao = new Instituicao(this.nome,
					this.localizacao, this.endereco, this.cidade, this.estado,
					this.cep, this.telefone);
			// Como n�o � necessario a aprova��o de nenhum outro
			// administrador
			// A validade do registro j� � setada como verdadeira.
			instituicao.setValidade(true);
			// Cadastra a instituicao no banco de dados
			cadastrarInstituicaoEJB.cadastrarInstituicao(instituicao);
			// Testa se a institui��o foi gravada
			if (!memoriaVirtualEJB
					.verificarDisponibilidadeNomeInstituicao(this.nome)) {
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
			}
		}
		return null;
	}

	public String resetCadastrarinstituicao() {
		this.nome = "";
		this.localizacao = "";
		this.endereco = "";
		this.cidade = "";
		this.estado = "";
		this.cep = "";
		this.telefone = "";
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

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		List<SelectItem> estados = new ArrayList<SelectItem>();

		estados.add(new SelectItem(null, bundle
				.getString("cadastrarInstituicaoEscolhaEstado")));
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
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @return the caixaPostal
	 */
	public String getCaixaPostal() {
		return caixaPostal;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	
	/**
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}
	
	
	/**
	 * @return the identificacaoProprietario
	 */
	public String getIdentificacaoProprietario() {
		return identificacaoProprietario;
	}

	
	/**
	 * @return the administradorPropriedade
	 */
	public String getAdministradorPropriedade() {
		return administradorPropriedade;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param administradorPropriedade the administradorPropriedade to set
	 */
	public void setAdministradorPropriedade(String administradorPropriedade) {
		this.administradorPropriedade = administradorPropriedade;
	}

	/**
	 * @param identificacaoProprietario the identificacaoProprietario to set
	 */
	public void setIdentificacaoProprietario(String identificacaoProprietario) {
		this.identificacaoProprietario = identificacaoProprietario;
	}

	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param caixaPostal
	 *            the caixaPostal to set
	 */
	public void setCaixaPostal(String caixaPostal) {
		this.caixaPostal = caixaPostal;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
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
		} else if (!memoriaVirtualEJB
				.verificarDisponibilidadeNomeInstituicao(this.nome)) {
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
			return true;
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
	 * Validacao do pais
	 */
	public void validatePais(AjaxBehaviorEvent event) {
		this.validatePais();
	}

	public boolean validatePais() {
		if (this.pais.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroPaisVazio", "validacaoPais");
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
			return true;
		} else if (!ValidacoesDeCampos.validarFormatoCep(this.cep)
				&& !this.cep.equals("")) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroCEPInvalido", "validacaoCep");
			return false;
		}
		return true;
	}
	
	/**
	 * Validacao do URL
	 */
	public void validateURL(AjaxBehaviorEvent event) {
		this.validateURL();
	}

	public boolean validateURL() {
		if (this.URL.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroURLVazio",
					"validacaoURL");
			return false;
		}
		return true;
	}

	/**
	 * Validacao do Caixa Postal
	 */
	public void validateCaixaPostal(AjaxBehaviorEvent event) {
		this.validateCaixaPostal();
	}

	public boolean validateCaixaPostal() {
		if (this.caixaPostal.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroCaixaPostalVazio",
					"validacaoCaixaPostal");
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
			return true;
		} else if (!ValidacoesDeCampos.validarFormatoTelefone(this.telefone)
				&& !this.telefone.equals("")) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroTelefoneInvalido",
					"validacaoTelefone");
			return false;
		}
		return true;
	}
	
	/**
	 * Validacao do IdentificacaoProprietario
	 */
	public void validateIdentificacaoProprietario(AjaxBehaviorEvent event) {
		this.validateIdentificacaoProprietario();
	}

	public boolean validateIdentificacaoProprietario() {
		if (this.identificacaoProprietario.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroIdentificacaoProprietarioVazio",
					"validacaoIdentificacaoProprietario");
			return false;
		}
		return true;
	}
	
	/**
	 * Validacao do AdministradorPropriedade
	 */
	public void validateAdministradorPropriedade(AjaxBehaviorEvent event) {
		this.validateAdministradorPropriedade();
	}

	public boolean validateAdministradorPropriedade() {
		if (this.administradorPropriedade.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroAdministradorPropriedadeVazio",
					"validacaoAdministradorPropriedade");
			return false;
		}
		return true;
	}
	
	/**
	 * Validacao do Latitude
	 */
	public void validateLatitude(AjaxBehaviorEvent event) {
		this.validateLatitude();
	}

	public boolean validateLatitude() {
		if (this.latitude.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroLatitudeVazio",
					"validacaoLatitude");
			return false;
		}
		return true;
	}
	
	/**
	 * Validacao do Longitude
	 */
	public void validateLongitude(AjaxBehaviorEvent event) {
		this.validateLongitude();
	}

	public boolean validateLongitude() {
		if (this.longitude.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroLongitudeVazio",
					"validacaoLongitude");
			return false;
		}
		return true;
	}

	/**
	 * Validacao do email
	 */
	public void validateEmail(AjaxBehaviorEvent event) {
		this.validateEmail();
	}

	public boolean validateEmail() {
		if (this.email.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroEmailVazio", "validacaoEmail");
			return false;
		} else {
			Pattern padrao = Pattern.compile(".+@.+\\.[a-z]+");
			Matcher pesquisa = padrao.matcher(this.email);
			if(!pesquisa.matches()){
				MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroEmailInvalido", "validacaoEmail");
			}
		}
		return true;
	}
}
