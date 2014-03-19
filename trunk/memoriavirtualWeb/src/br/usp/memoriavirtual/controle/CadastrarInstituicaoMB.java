package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "cadastrarInstituicaoMB")
@SessionScoped
public class CadastrarInstituicaoMB extends MidiaContainer implements
		Serializable{

	private static final long serialVersionUID = -6620103410985404517L;
	@EJB
	protected MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private CadastrarInstituicaoRemote cadastrarInstituicaoEJB;
	protected String slot = "arquivo0"; 
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

	public CadastrarInstituicaoMB() {

	}

	public String cadastrarInstituicao() {

		if (this.validarNome()) {

			Instituicao instituicao = new Instituicao(this.nome,
					this.localizacao, this.endereco, this.cidade, this.estado,
					this.pais, this.cep, this.telefone, this.caixaPostal,
					this.email, this.URL, this.identificacaoProprietario,
					this.administradorPropriedade, this.latitude,
					this.longitude, this.altitude, this.tipoPropriedade,
					this.protecaoExistente, this.legislacao,
					this.sinteseHistorica);

			instituicao.setValidade(true);

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
		return "reset";

	}

	public String getNome() {
		return this.nome;
	}

	public String getLocalizacao() {
		return this.localizacao;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public String getCidade() {
		return this.cidade;
	}

	public String getEstado() {
		return this.estado;
	}

	public String getCep() {
		return this.cep;
	}

	public String getTelefone() {
		return this.telefone;
	}

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

	public String getPais() {
		return pais;
	}

	public String getCaixaPostal() {
		return caixaPostal;
	}

	public String getEmail() {
		return email;
	}

	public String getURL() {
		return URL;
	}

	public String getIdentificacaoProprietario() {
		return identificacaoProprietario;
	}

	public String getAdministradorPropriedade() {
		return administradorPropriedade;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getAltitude() {
		return altitude;
	}

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

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setAdministradorPropriedade(String administradorPropriedade) {
		this.administradorPropriedade = administradorPropriedade;
	}

	public void setIdentificacaoProprietario(String identificacaoProprietario) {
		this.identificacaoProprietario = identificacaoProprietario;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCaixaPostal(String caixaPostal) {
		this.caixaPostal = caixaPostal;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getProtecaoExistente() {
		return protecaoExistente;
	}

	public void setProtecaoExistente(String protecaoExistente) {
		this.protecaoExistente = protecaoExistente;
	}

	public void setNome(String pNome) {
		this.nome = pNome;
	}

	public void setLocalizacao(String pLocalizacao) {
		this.localizacao = pLocalizacao;
	}

	public void setEndereco(String pEndereco) {
		this.endereco = pEndereco;
	}

	public void setCidade(String pCidade) {
		this.cidade = pCidade;
	}

	public void setEstado(String pEstado) {
		this.estado = pEstado;
	}

	public void setCep(String pCep) {
		this.cep = pCep;
	}

	public void setTelefone(String pTelefone) {
		this.telefone = pTelefone;
	}

	public String getTipoPropriedade() {
		return tipoPropriedade;
	}

	public void setTipoPropriedade(String tipoPropriedade) {
		this.tipoPropriedade = tipoPropriedade;
	}

	public String getLegislacao() {
		return legislacao;
	}

	public void setLegislacao(String legislacao) {
		this.legislacao = legislacao;
	}

	public void validarNome(AjaxBehaviorEvent event) {
		this.validarNome();
	}

	public boolean validarNome() {

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

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
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

	public String getSinteseHistorica() {
		return sinteseHistorica;
	}

	public void setSinteseHistorica(String sinteseHistorica) {
		this.sinteseHistorica = sinteseHistorica;
	}
}
