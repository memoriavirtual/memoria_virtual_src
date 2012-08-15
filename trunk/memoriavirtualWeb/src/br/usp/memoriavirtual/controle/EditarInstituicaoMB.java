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

import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;

@ManagedBean(name = "editarInstituicaoMB")
@SessionScoped
public class EditarInstituicaoMB extends CadastrarInstituicaoMB implements
		Serializable {

	private static final long serialVersionUID = -2609697377310497761L;
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;
	private List<Instituicao> instituicoes = new ArrayList<Instituicao>();
	private Instituicao instituicao;
	private long id;

	public String editarInstituicao() {

		if (this.validateNome() && this.validateLocalizacao()) {

			Instituicao instituicao = new Instituicao(this.id, this.nome,
					this.localizacao, this.endereco, this.cidade, this.estado,
					this.pais, this.cep, this.telefone, this.caixaPostal,
					this.email, this.URL, this.identificacaoProprietario,
					this.administradorPropriedade, this.latitude,
					this.longitude, this.altitude, this.tipoPropriedade,
					this.protecaoExistente, this.legislacao,
					this.sinteseHistorica);

			FacesContext context = FacesContext.getCurrentInstance();
			String bundleName = "mensagens";
			ResourceBundle bundle = context.getApplication().getResourceBundle(
					context, bundleName);

			if (bundle.getString("cadastrarInstituicaoEscolhaEstado").equals(
					instituicao.getEstado()))
				instituicao.setEstado("");
			if (bundle.getString("cadastrarInstituicaoEscolhaTipoPropriedade")
					.equals(instituicao.getTipoPropriedade()))
				instituicao.setTipoPropriedade("");
			if (bundle
					.getString("cadastrarInstituicaoEscolhaProtecaoExistente")
					.equals(instituicao.getProtecaoExistente()))
				instituicao.setProtecaoExistente("");
			try {
				this.editarInstituicaoEJB.editarInstituicao(instituicao);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage(
						"editarInstituicaoErroEditarFalha", "resutado");
			} catch (RuntimeException e) {
				MensagensDeErro.getErrorMessage(
						"editarInstituicaoErroEditarFalha", "resultado");
			}

			this.resetCadastrarinstituicao();

			MensagensDeErro.getSucessMessage("editarInstituicaoSucessoEditar",
					"resultado");
		}
		return "falha";

	}

	public String cancelar() {
		this.resetCadastrarinstituicao();
		return "cancelar";
	}
	public void listarTodos(AjaxBehaviorEvent event) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		this.instituicoes.clear();
		Instituicao ins = new Instituicao();
		ins.setNome(bundle.getString("listarTodos"));
		this.instituicoes.add(0, ins);

	}
	public void instituicoesSugeridas(AjaxBehaviorEvent event) {

		this.listarInstituicoes();

	}

	public void listarInstituicoes() {

		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) context.getExternalContext()
				.getSessionMap().get("usuario");
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		// Lista de instituicoes que o usuario pertence
		List<Instituicao> instituicoesUsuario = new ArrayList<Instituicao>();

		if (usuario.isAdministrador()) {
			try {
				instituicoesUsuario = this.editarInstituicaoEJB
						.listarInstituicoes(this.nome);
				Instituicao ins = new Instituicao();
				ins.setNome(bundle.getString("listarTodos"));
				instituicoesUsuario.add(0, ins);
			} catch (ModeloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Grupo grupo = new Grupo("Gerente");
			try {
				instituicoesUsuario = this.editarInstituicaoEJB
						.listarInstituicoes(this.nome, grupo, usuario);
				Instituicao ins = new Instituicao();
				ins.setNome(bundle.getString("listarTodos"));
				instituicoesUsuario.add(0, ins);
			} catch (ModeloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.instituicoes = instituicoesUsuario;
	}

	public String selecionarInstituicao(Instituicao instituicao) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		if (instituicao.getNome().equals(bundle.getString("listarTodos"))) {
			this.nome = "";
			this.listarInstituicoes();
			this.instituicoes.remove(0);
			return null;
		}
		this.id = instituicao.getId();
		this.nome = instituicao.getNome();
		this.cep = instituicao.getCep();
		this.cidade = instituicao.getCidade();
		this.endereco = instituicao.getEndereco();
		this.estado = instituicao.getEstado();
		this.localizacao = instituicao.getLocalidade();
		this.telefone = instituicao.getTelefone();

		this.administradorPropriedade = instituicao
				.getAdministradorPropriedade();
		this.altitude = instituicao.getAltitude();
		this.caixaPostal = instituicao.getCaixaPostal();
		this.email = instituicao.getEmail();
		this.identificacaoProprietario = instituicao
				.getIdentificacaoProprietario();
		this.latitude = instituicao.getLatitude();
		this.longitude = instituicao.getLongitude();
		this.protecaoExistente = instituicao.getProtecaoExistente();
		this.tipoPropriedade = instituicao.getTipoPropriedade();
		this.URL = instituicao.getUrl();
		this.legislacao = instituicao.getLegislacaoExistente();
		this.instituicao = instituicao;
		this.instituicoes.clear();
		return "sucesso";
	}

	public String selecionarInstituicao() {

		if (this.validateNome()) {
			Instituicao instituicao;
			Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("usuario");

			if (usuario.isAdministrador()) {
				try {
					instituicao = editarInstituicaoEJB
							.getInstituicao(this.nome);

					this.cep = instituicao.getCep();
					this.cidade = instituicao.getCidade();
					this.endereco = instituicao.getEndereco();
					this.estado = instituicao.getEstado();
					this.localizacao = instituicao.getLocalidade();
					this.telefone = instituicao.getTelefone();

					this.administradorPropriedade = instituicao
							.getAdministradorPropriedade();
					this.altitude = instituicao.getAltitude();
					this.caixaPostal = instituicao.getCaixaPostal();
					this.email = instituicao.getEmail();
					this.identificacaoProprietario = instituicao
							.getIdentificacaoProprietario();
					this.latitude = instituicao.getLatitude();
					this.longitude = instituicao.getLongitude();
					this.protecaoExistente = instituicao.getProtecaoExistente();
					this.tipoPropriedade = instituicao.getTipoPropriedade();
					this.URL = instituicao.getUrl();
					this.legislacao = instituicao.getLegislacaoExistente();
					this.instituicao = instituicao;
					this.instituicoes.clear();
					return "sucesso";
				} catch (ModeloException e) {
					e.printStackTrace();
				}
			} else {
				Grupo grupo = new Grupo("Gerente");
				try {
					instituicao = editarInstituicaoEJB.getInstituicao(
							this.nome, grupo, usuario);

					this.cep = instituicao.getCep();
					this.cidade = instituicao.getCidade();
					this.endereco = instituicao.getEndereco();
					this.estado = instituicao.getEstado();
					this.localizacao = instituicao.getLocalidade();
					this.telefone = instituicao.getTelefone();

					this.administradorPropriedade = instituicao
							.getAdministradorPropriedade();
					this.altitude = instituicao.getAltitude();
					this.caixaPostal = instituicao.getCaixaPostal();
					this.email = instituicao.getEmail();
					this.identificacaoProprietario = instituicao
							.getIdentificacaoProprietario();
					this.latitude = instituicao.getLatitude();
					this.longitude = instituicao.getLongitude();
					this.protecaoExistente = instituicao.getProtecaoExistente();
					this.tipoPropriedade = instituicao.getTipoPropriedade();
					this.URL = instituicao.getUrl();
					this.legislacao = instituicao.getLegislacaoExistente();
					this.instituicao = instituicao;
					this.instituicoes.clear();
					return "sucesso";
				} catch (ModeloException e) {
					e.printStackTrace();
				}
			}
		}
		return "erro";

	}

	public void validateNome(AjaxBehaviorEvent event) {
		this.validateNome();
	}

	public boolean validateNome() {
		if ((this.memoriaVirtualEJB
				.verificarDisponibilidadeNomeInstituicao(this.nome))) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroNomeExistente", "validacaoNome");
			return false;
		} else if (this.nome.length() < 4) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroNomeCurto",
					"validacaoNome");
			return false;
		}
		return true;
	}

	public List<SelectItem> getEstadoSigla() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		List<SelectItem> estados = new ArrayList<SelectItem>();

		estados.add(new SelectItem(this.estado, this.estado));
		estados.add(new SelectItem(bundle
				.getString("cadastrarInstituicaoEscolhaEstado"), bundle
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

		tiposPropriedade.add(new SelectItem(this.tipoPropriedade,
				this.tipoPropriedade));
		tiposPropriedade
				.add(new SelectItem(
						bundle.getString("cadastrarInstituicaoEscolhaTipoPropriedade"),
						bundle.getString("cadastrarInstituicaoEscolhaTipoPropriedade")));
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

		protecaoExistentes.add(new SelectItem(this.protecaoExistente,
				this.protecaoExistente));
		protecaoExistentes
				.add(new SelectItem(
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistente"),
						bundle.getString("cadastrarInstituicaoEscolhaProtecaoExistente")));
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

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	/**
	 * @return the novoNome
	 */

	public String getNome() {
		return this.nome;
	}

	/**
	 * @param novoNome
	 *            the novoNome to set
	 */
	public void setNome(String novoNome) {
		this.nome = novoNome;
	}

	/**
	 * @return the localizacao
	 */
	public String getlocalizacao() {
		return localizacao;
	}

	/**
	 * @param localizacao
	 *            the localizacao to set
	 */
	public void setlocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	/**
	 * @return the endereco
	 */
	public String getendereco() {
		return endereco;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setendereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the cidade
	 */
	public String getcidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            the cidade to set
	 */
	public void setcidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the estado
	 */
	public String getestado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setestado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the cep
	 */
	public String getcep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setcep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the telefone
	 */
	public String gettelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            the telefone to set
	 */
	public void settelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the instituicoes
	 */
	public List<Instituicao> getInstituicoes() {
		return this.instituicoes;
	}

	/**
	 * @param telefone
	 *            the instituicoes to set
	 */
	public void setInstituicoes(List<Instituicao> novoInstituicoes) {
		this.instituicoes = novoInstituicoes;
	}

}