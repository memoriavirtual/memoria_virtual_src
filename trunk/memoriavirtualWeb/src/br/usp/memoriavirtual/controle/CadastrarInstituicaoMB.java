package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MVControleTiposDePropriedade;
import br.usp.memoriavirtual.utils.MVControleTiposDeProtecaoExistente;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "cadastrarInstituicaoMB")
@SessionScoped
public class CadastrarInstituicaoMB extends BeanContainerDeMidia implements
		BeanDeSessao, BeanMemoriaVirtual, Serializable {

	private static final long serialVersionUID = -6620103410985404517L;
	@EJB
	protected MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private CadastrarInstituicaoRemote cadastrarInstituicaoEJB;
	protected String nome = "";
	protected String localidade = "";
	protected String endereco = "";
	protected String cidade = "";
	protected String estado = "";
	protected String pais = "";
	protected String cep = "";
	protected String telefone = "";
	protected String caixaPostal = "";
	protected String email = "";
	protected String url = "";
	protected String identificacaoProprietario = "";
	protected String administradorPropriedade = "";
	protected String latitude = "";
	protected String longitude = "";
	protected String altitude = "";
	protected String tipoPropriedade = "";
	protected String protecaoExistente = "";
	protected String legislacao = "";
	protected String sinteseHistorica = "";
	protected String legislacaoExistente = "";
	protected String legislacaoIncidente = "";

	public CadastrarInstituicaoMB() {

	}

	public String cadastrarInstituicao() {

		if (this.validar()) {

			Instituicao instituicao = new Instituicao();
			instituicao.setNome(this.nome);
			instituicao.setEndereco(this.endereco);
			instituicao
					.setAdministradorPropriedade(this.administradorPropriedade);
			instituicao.setAltitude(this.altitude);
			instituicao.setCaixaPostal(this.caixaPostal);
			instituicao.setCep(this.cep);
			instituicao.setCidade(this.cidade);
			instituicao.setEmail(this.email);
			instituicao.setEndereco(this.endereco);
			instituicao.setEstado(this.estado);
			instituicao
					.setIdentificacaoProprietario(this.identificacaoProprietario);
			instituicao.setLatitude(this.latitude);
			instituicao.setLegislacaoExistente(this.legislacaoExistente);
			instituicao.setLegislacaoIncidente(this.legislacaoIncidente);
			instituicao.setLocalidade(this.localidade);
			instituicao.setLongitude(this.longitude);
			instituicao.setNome(this.nome);
			instituicao.setPais(this.pais);
			instituicao.setProtecaoExistente(this.protecaoExistente);
			instituicao.setSinteseHistorica(this.sinteseHistorica);
			instituicao.setTelefone(this.telefone);
			instituicao.setTipoPropriedade(this.tipoPropriedade);
			instituicao.setUrl(this.url);
			instituicao.setValidade(true);

			cadastrarInstituicaoEJB.cadastrarInstituicao(instituicao);
			this.limpar();
		}

		return null;
	}

	public String getNome() {
		return this.nome;
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

	public String getPais() {
		return pais;
	}

	public String getCaixaPostal() {
		return caixaPostal;
	}

	public String getEmail() {
		return email;
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

		ResourceBundle bundle = getResourceBundle();
		List<SelectItem> tiposPropriedade = new ArrayList<SelectItem>();

		for (MVControleTiposDePropriedade t : MVControleTiposDePropriedade
				.values()) {
			tiposPropriedade.add(new SelectItem(t.toString(), bundle
					.getString(t.toString())));
		}

		return tiposPropriedade;
	}

	public List<SelectItem> getProtecaoExistenteLista() {

		ResourceBundle bundle = this.getResourceBundle();

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (MVControleTiposDeProtecaoExistente t : MVControleTiposDeProtecaoExistente
				.values()) {
			opcoes.add(new SelectItem(t.toString(), bundle.getString(t
					.toString())));
		}

		return opcoes;
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

	public boolean validarNome() {

		if (this.nome == null || this.nome.equals("")) {
			ResourceBundle bundle = this.getResourceBundle();
			String args[] = { bundle.getString("nomeDoCampoNome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacaoNome");
			return false;
		} else if (!memoriaVirtualEJB
				.verificarDisponibilidadeNomeInstituicao(this.nome)) {
			MensagensDeErro.getErrorMessage("erroNomeJaCadastrado",
					"validacaoNome");
			return false;
		}
		return true;
	}

	public String cancelar() {

		this.limpar();
		return "/restrito/index.jsf";

	}

	public String getSinteseHistorica() {
		return sinteseHistorica;
	}

	public void setSinteseHistorica(String sinteseHistorica) {
		this.sinteseHistorica = sinteseHistorica;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLegislacaoExistente() {
		return legislacaoExistente;
	}

	public void setLegislacaoExistente(String legislacaoExistente) {
		this.legislacaoExistente = legislacaoExistente;
	}

	public String getLegislacaoIncidente() {
		return legislacaoIncidente;
	}

	public void setLegislacaoIncidente(String legislacaoIncidente) {
		this.legislacaoIncidente = legislacaoIncidente;
	}

	@Override
	public ResourceBundle getResourceBundle() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context,
				MVControleMemoriaVirtual.bundleName);
	}

	@Override
	public String limpar() {
		this.nome = "";
		this.localidade = "";
		this.endereco = "";
		this.cidade = "";
		this.estado = "";
		this.pais = "";
		this.cep = "";
		this.telefone = "";
		this.caixaPostal = "";
		this.email = "";
		this.url = "";
		this.identificacaoProprietario = "";
		this.administradorPropriedade = "";
		this.latitude = "";
		this.longitude = "";
		this.altitude = "";
		this.tipoPropriedade = "";
		this.protecaoExistente = "";
		this.legislacao = "";
		this.sinteseHistorica = "";
		this.legislacaoExistente = "";
		this.legislacaoIncidente = "";
		return null;
	}

	@Override
	public boolean validar() {
		return this.validarNome();
	}
}
