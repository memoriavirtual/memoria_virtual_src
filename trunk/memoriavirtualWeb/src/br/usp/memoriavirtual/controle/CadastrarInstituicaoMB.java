package br.usp.memoriavirtual.controle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

/**
 * Mananged Bean responsável pelo controle do cadaStro de uma nova instituição
 */

@ManagedBean(name = "cadastrarInstituicaoMB")
@SessionScoped
public class CadastrarInstituicaoMB implements Serializable, BeanComMidia {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6620103410985404517L;
	@EJB
	protected MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private CadastrarInstituicaoRemote cadastrarInstituicaoEJB;
	protected String slot = "arquivo0";

	protected ArrayList<Multimidia> midias = new ArrayList<Multimidia>();
	protected ArrayList<Integer> ApresentaMidias = new ArrayList<Integer>();

	protected String nome = "";
	protected String localizacao = "";
	protected String endereco = "";
	protected String cidade = "";
	protected String estado = "";
	protected String pais = "";
	protected String cep = "";
	protected String telefone = "";
	protected String caixaPostal = "";
	protected String email = "";
	protected String URL = "";
	protected String identificacaoProprietario = "";
	protected String administradorPropriedade = "";
	protected String latitude = "";
	protected String longitude = "";
	protected String altitude = "";
	protected String tipoPropriedade = "";
	protected String protecaoExistente = "";
	protected String legislacao = "";
	protected String sinteseHistorica = "";
	protected ContainerMultimidia containerMultimidia = new ContainerMultimidia();
	protected Part part;

	public CadastrarInstituicaoMB() {

	}

	public String cadastrarInstituicao() {
		if (this.validateNome() && this.validateCep()
				&& this.validateTelefone()) {
			Instituicao instituicao = new Instituicao(this.nome,
					this.localizacao, this.endereco, this.cidade, this.estado,
					this.pais, this.cep, this.telefone, this.caixaPostal,
					this.email, this.URL, this.identificacaoProprietario,
					this.administradorPropriedade, this.latitude,
					this.longitude, this.altitude, this.tipoPropriedade,
					this.protecaoExistente, this.legislacao,
					this.sinteseHistorica);

			instituicao.setValidade(true);

			FacesContext context = FacesContext.getCurrentInstance();
			String bundleName = "mensagens";
			ResourceBundle bundle = context.getApplication().getResourceBundle(
					context, bundleName);

			if (instituicao.getEstado().equals(
					bundle.getString("cadastrarInstituicaoEscolhaEstado")))
				instituicao.setEstado("");
			if (instituicao
					.getTipoPropriedade()
					.equals(bundle
							.getString("cadastrarInstituicaoEscolhaTipoPropriedade")))
				instituicao.setTipoPropriedade("");
			if (instituicao
					.getProtecaoExistente()
					.equals(bundle
							.getString("cadastrarInstituicaoEscolhaProtecaoExistente")))
				instituicao.setProtecaoExistente("");

			for (Multimidia i : midias) {
				this.containerMultimidia.addMultimidia(i);
			}

			instituicao.setContainerMultimidia(containerMultimidia);

			cadastrarInstituicaoEJB.cadastrarInstituicao(instituicao);

			if (!memoriaVirtualEJB
					.verificarDisponibilidadeNomeInstituicao(this.nome)) {
				this.resetCadastrarinstituicao();
				MensagensDeErro
						.getSucessMessage(
								"cadastrarInstituicaoSucessocadastramento",
								"resultado");
			}
		}
		this.resetCadastrarinstituicao();
		return null;
	}

	/**
	 * @return the midias
	 */
	public ArrayList<Multimidia> getMidias() {
		return midias;
	}

	/**
	 * @param midias
	 *            the midias to set
	 */
	public void setMidias(ArrayList<Multimidia> midias) {
		this.midias = midias;
	}

	public void adicionarMidia(Multimidia midia) {
		this.midias.add(midia);

		if ((this.midias.size() % 4) == 1) {
			Integer mult = this.midias.size() - 1;
			this.ApresentaMidias.add(mult);

		}
	}

	public String resetCadastrarinstituicao() {
		this.nome = "";
		this.localizacao = "";
		this.endereco = "";
		this.cidade = "";
		this.estado = "";
		this.pais = "";
		this.cep = "";
		this.telefone = "";
		this.caixaPostal = "";
		this.email = "";
		this.URL = "";
		this.identificacaoProprietario = "";
		this.administradorPropriedade = "";
		this.latitude = "";
		this.longitude = "";
		this.altitude = "";
		this.tipoPropriedade = "";
		this.protecaoExistente = "";
		this.legislacao = "";
		this.sinteseHistorica = "";
		this.midias.clear();
		this.ApresentaMidias.clear();
		this.midias.clear();
		return "reset";

	}

	public String removerMidia(Multimidia m) {
		this.midias.remove(m);
		return null;
	}

	public String uploadFile() throws IOException {

		if (part.getSize() > 0) {
			String fileName = getFileName(part);

			InputStream inputStream = null;
			ByteArrayOutputStream out = null;
			try {
				inputStream = part.getInputStream();
				out = new ByteArrayOutputStream();

				int read = 0;
				final byte[] bytes = new byte[128];
				while ((read = inputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				out.toByteArray();
				Multimidia m = new Multimidia();
				m.setContentType(part.getContentType());
				m.setNome(fileName);
				m.setContent(out.toByteArray());
				m.setContainerMultimidia(this.containerMultimidia);
				this.midias.add(m);
				this.ApresentaMidias.add(this.ApresentaMidias.size());

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null)
					out.close();
				if (inputStream != null) {
					inputStream.close();
				}
			}

		} else {
			MensagensDeErro.getErrorMessage("cadastrarInstituicaoErro",
					"resultado");
		}

		return null;
	}

	protected String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

	public String imageDisplay(Multimidia m) {
		return m.isImagem() ? "block" : "none";
	}

	public String videoDisplay(Multimidia m) {
		return m.isVideo() ? "block" : "none";
	}

	public String midiaDisplay(Multimidia m) {
		return (!m.isImagem() && !m.isVideo()) ? "block" : "none";
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
	 * @return the altitude
	 */
	public String getAltitude() {
		return altitude;
	}

	/**
	 * Getter dos tipos de Propriedade (Usado no form de cadastro)
	 * 
	 * @return (List<String>) tipos de Propriedade
	 */
	public List<SelectItem> getTipoPropriedadeLista() {

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		List<SelectItem> tiposPropriedade = new ArrayList<SelectItem>();

		tiposPropriedade
				.add(new SelectItem(
						"Publica",
						bundle.getString("cadastrarInstituicaoEscolhaTipoPropriedadePublica")));
		tiposPropriedade
				.add(new SelectItem(
						"Privada",
						bundle.getString("cadastrarInstituicaoEscolhaTipoPropriedadePrivada")));
		tiposPropriedade.add(new SelectItem("Mista", bundle
				.getString("cadastrarInstituicaoEscolhaTipoPropriedadeMista")));
		tiposPropriedade.add(new SelectItem("Outra", bundle
				.getString("cadastrarInstituicaoEscolhaTipoPropriedadeOutra")));

		return tiposPropriedade;
	}

	/**
	 * Getter dos tipos de protecaoExistente (Usado no form de cadastro)
	 * 
	 * @return (List<String>) protecao existente
	 */
	public List<SelectItem> getProtecaoExistenteLista() {

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		List<SelectItem> protecaoExistentes = new ArrayList<SelectItem>();

		protecaoExistentes
				.add(new SelectItem(
						"Publica",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteMundial")));
		protecaoExistentes
				.add(new SelectItem(
						"Privada",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteFederalI")));
		protecaoExistentes
				.add(new SelectItem(
						"Mista",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteFederalC")));
		protecaoExistentes
				.add(new SelectItem(
						"Outra",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteEstadualI")));
		protecaoExistentes
				.add(new SelectItem(
						"Mista",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteEstadualC")));
		protecaoExistentes
				.add(new SelectItem(
						"Outra",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteMunicipalI")));
		protecaoExistentes
				.add(new SelectItem(
						"Mista",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteMunicipalC")));
		protecaoExistentes
				.add(new SelectItem(
						"Mista",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteDecreto")));
		protecaoExistentes
				.add(new SelectItem(
						"Mista",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteEntorno")));
		protecaoExistentes
				.add(new SelectItem(
						"Mista",
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistenteNenhuma")));

		return protecaoExistentes;
	}

	/**
	 * @param altitude
	 *            the altitude to set
	 */
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param administradorPropriedade
	 *            the administradorPropriedade to set
	 */
	public void setAdministradorPropriedade(String administradorPropriedade) {
		this.administradorPropriedade = administradorPropriedade;
	}

	/**
	 * @param identificacaoProprietario
	 *            the identificacaoProprietario to set
	 */
	public void setIdentificacaoProprietario(String identificacaoProprietario) {
		this.identificacaoProprietario = identificacaoProprietario;
	}

	/**
	 * @param uRL
	 *            the uRL to set
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
	 * @return the protecaoExistente
	 */
	public String getProtecaoExistente() {
		return protecaoExistente;
	}

	/**
	 * @param protecaoExistente
	 *            the protecaoExistente to set
	 */
	public void setProtecaoExistente(String protecaoExistente) {
		this.protecaoExistente = protecaoExistente;
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
	 * @return the tipoPropriedade
	 */
	public String getTipoPropriedade() {
		return tipoPropriedade;
	}

	/**
	 * @param tipoPropriedade
	 *            the tipoPropriedade to set
	 */
	public void setTipoPropriedade(String tipoPropriedade) {
		this.tipoPropriedade = tipoPropriedade;
	}

	public String getLegislacao() {
		return legislacao;
	}

	public void setLegislacao(String legislacao) {
		this.legislacao = legislacao;
	}

	/**
	 * Validacao do nome
	 */
	public void validateNome(AjaxBehaviorEvent event) {
		this.validateNome();
	}

	public boolean validateNome() {

		if (this.nome.equals("")) {

			String args[] = { "nome" };
			MensagensDeErro.getErrorMessage("campo_vazio", args,
					"validacaoNome");
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
			MensagensDeErro.getWarningMessage(
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
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (this.estado.equals(bundle
				.getString("cadastrarInstituicaoEscolhaEstado"))) {
			this.estado = "";
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
					"cadastrarInstituicaoErroURLVazio", "validacaoURL");
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
		} else if (!ValidacoesDeCampos
				.validaCoordenadaGeografica(this.latitude)) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroLatitudeInvalido",
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
		} else if (!ValidacoesDeCampos
				.validaCoordenadaGeografica(this.longitude)) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroLongitudeInvalido",
					"validacaoLongitude");
			return false;
		}
		return true;
	}

	/**
	 * Validacao do Altitude
	 */
	public void validateAltitude(AjaxBehaviorEvent event) {
		this.validateAltitude();
	}

	public boolean validateAltitude() {
		if (this.altitude.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroAltitudeVazio",
					"validacaoAltitude");
			return false;
		} else if (!ValidacoesDeCampos.validarAltitude(this.altitude)) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroAltitudeInvalido",
					"validacaoAltitude");
			return false;
		}
		return true;
	}

	/**
	 * Validacao do TipoPropriedade
	 */
	public void validateTipoPropriedade(AjaxBehaviorEvent event) {
		this.validateTipoPropriedade();
	}

	public boolean validateTipoPropriedade() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (this.tipoPropriedade.equals(bundle
				.getString("cadastrarInstituicaoEscolhaTipoPropriedade"))) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroTipoPropriedadeVazio",
					"validacaoTipoPropriedade");
			return false;
		}
		return false;
	}

	/**
	 * Validacao do ProtecaoExistente
	 */
	public void validateProtecaoExistente(AjaxBehaviorEvent event) {
		this.validateProtecaoExistente();
	}

	public boolean validateProtecaoExistente() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (this.protecaoExistente.equals(bundle
				.getString("cadastrarInstituicaoEscolhaProtecaoExistente"))) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroProtecaoVazio",
					"validacaoProtecaoExistente");
			return false;
		}
		return false;
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
			if (!pesquisa.matches()) {
				MensagensDeErro.getErrorMessage(
						"cadastrarInstituicaoErroEmailInvalido",
						"validacaoEmail");
			}
		}
		return true;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	/**
	 * Validacao do Legislacao
	 */
	public void validateLegislacao(AjaxBehaviorEvent event) {
		this.validateLegislacao();
	}

	public boolean validateLegislacao() {
		if (this.legislacao.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroLegislacaoVazio",
					"validacaoLegislacao");
			return false;
		}
		return true;
	}

	/**
	 * Validacao do Legislacao
	 */
	public void validateSinteseHistorica(AjaxBehaviorEvent event) {
		this.validateSinteseHistorica();
	}

	public boolean validateSinteseHistorica() {
		if (this.sinteseHistorica.equals("")) {
			MensagensDeErro.getWarningMessage(
					"cadastrarInstituicaoErroSinteseHistorica",
					"validacaoSinteseHistorica");
			return false;
		}
		return true;
	}

	@Override
	public List<Multimidia> recuperaColecaoMidia() {
		return this.midias;
	}

	public String removeMidia(String midia) {
		return null;
	}

	@Override
	public String removeMidia(Multimidia midia) {
		this.midias.remove(midia);
		return null;
	}

	/**
	 * @return the sinteseHistorica
	 */
	public String getSinteseHistorica() {
		return sinteseHistorica;
	}

	/**
	 * @param sinteseHistorica
	 *            the sinteseHistorica to set
	 */
	public void setSinteseHistorica(String sinteseHistorica) {
		this.sinteseHistorica = sinteseHistorica;
	}

	@Override
	public String removeMidia(int index) {
		this.midias.remove(index);

		if (this.midias.size() % 4 == 0) {
			this.ApresentaMidias.remove(this.ApresentaMidias.size() - 1);
		}
		return null;
	}

	public ArrayList<Integer> getApresentaMidias() {
		return ApresentaMidias;
	}

	public void setApresentaMidias(ArrayList<Integer> apresentaMidias) {
		ApresentaMidias = apresentaMidias;
	}

	public boolean isRenderCell(int index) {
		if (this.midias.size() > index) {
			return true;
		}
		return false;
	}

	public String cancelar() {

		this.resetCadastrarinstituicao();
		return "/restrito/index.jsf";

	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}
}
