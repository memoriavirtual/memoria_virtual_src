package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.utils.ValidacoesDeCampos;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@ManagedBean(name = "editarInstituicaoMB")
@SessionScoped
public class EditarInstituicaoMB implements Serializable {

	private static final long serialVersionUID = -2609697377310497761L;
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private String velhoNome;
	private String novoNome;
	private String novoLocalizacao;
	private String novoEndereco;
	private String novoCidade;
	private String novoEstado;
	private String novoCep;
	private String novoTelefone;
	private List<Instituicao> instituicoes;

	public String editarInstituicao() {

		if (this.validateCep() && this.validateCidade()
				&& this.validateEndereco() && this.validateId()
				&& this.validateLocalizacao() && this.validateNome()
				&& this.validateTelefone()) {

			try {

				this.editarInstituicaoEJB.editarInstituicao(this.velhoNome,
						this.novoNome, this.novoLocalizacao, this.novoEndereco,
						this.novoCidade, this.novoEstado, this.novoCep,
						this.novoTelefone);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage(
						"editarInstituicaoErroEditarFalha", "resutado");
			} catch (RuntimeException e) {
				MensagensDeErro.getErrorMessage(
						"editarInstituicaoErroEditarFalha", "resultado");
			}
			this.velhoNome = "";
			this.novoCep = "";
			this.novoCidade = "";
			this.novoEndereco = "";
			this.novoEstado = "";
			this.novoLocalizacao = "";
			this.novoNome = "";
			this.novoTelefone = "";

			MensagensDeErro.getSucessMessage("editarInstituicaoSucessoEditar",
					"resultado");
		}
		return "falha";

	}
	
	public String cancelar(){
		return "cancelar";
	}

	public void instituicoesSugeridas(AjaxBehaviorEvent event) {

		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		// Lista de instituicoes que o usuario pertence
		List<Instituicao> instituicoesUsuario = new ArrayList<Instituicao>();

		if (usuario.isAdministrador()) {
			instituicoesUsuario = this.editarInstituicaoEJB
					.getInstituicoesSugeridas(this.velhoNome);
		} else {
			Grupo grupo = new Grupo("Gerente");
			instituicoesUsuario = this.editarInstituicaoEJB
					.getInstituicoesSugeridas("a", grupo, usuario);
		}

		this.instituicoes = instituicoesUsuario;
	}

	public String selecionarInstituicao(Instituicao instituicao) {

		this.velhoNome = instituicao.getNome();
		this.novoNome = instituicao.getNome();
		this.novoCep = instituicao.getCep();
		this.novoCidade = instituicao.getCidade();
		this.novoEndereco = instituicao.getEndereco();
		this.novoEstado = instituicao.getEstado();
		this.novoLocalizacao = instituicao.getLocalizacao();
		this.novoTelefone = instituicao.getTelefone();
		this.instituicoes.clear();
		return "sucesso";
	}

	public void validateId(AjaxBehaviorEvent event) {
		this.validateId();
	}

	public boolean validateId() {

		if (this.velhoNome.equals("")) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroIdVazio",
					"validacaoId");
			return false;
		} else if (memoriaVirtualEJB.disponibilidadeNomeInstituicao(velhoNome)) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroIdInexistente", "validacaoId");
			return false;
		}
		return true;
	}

	public void validateNome(AjaxBehaviorEvent event) {
		this.validateNome();
	}

	public boolean validateNome() {
		if (!(this.novoNome.equals(this.velhoNome) || this.memoriaVirtualEJB
				.disponibilidadeNomeInstituicao(this.novoNome))) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroNomeExistente", "validacaoNome");
			return false;
		} else if (this.novoNome.length() < 4) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroNomeCurto",
					"validacaoNome");
			return false;
		}
		return true;
	}

	public void validateLocaliacao(AjaxBehaviorEvent event) {
		this.validateLocalizacao();
	}

	public boolean validateLocalizacao() {
		if (this.novoLocalizacao.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroLocalizacaoVazio",
					"validacaoLocalizacao");
			return false;
		} else if (!ValidacoesDeCampos
				.validarFormatoLocalizacao(this.novoLocalizacao)) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroLocalizacaoInvalido",
					"validacaoLocalizacao");
		}
		return true;
	}

	public void validateEndereco(AjaxBehaviorEvent event) {
		this.validateEndereco();
	}

	public boolean validateEndereco() {
		if (this.novoEndereco.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroEnderecoVazio", "validacaoEndereco");
			return false;
		}
		return true;
	}

	public void validateCidade(AjaxBehaviorEvent event) {
		this.validateCidade();
	}

	public boolean validateCidade() {
		if (this.novoCidade.equals("")) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroCidadeVazio",
					"validacaoCidade");
			return false;
		}
		return true;
	}

	public void validateCep(AjaxBehaviorEvent event) {
		this.validateCep();
	}

	public boolean validateCep() {
		if (this.novoCep.equals("")) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroCepVazio",
					"validacaoCep");
			return false;
		} else if (!ValidacoesDeCampos.validarFormatoCep(this.novoCep)) {
			MensagensDeErro.getErrorMessage("editarInstituicaoErroCepInvalido",
					"validacaoCep");
			return false;
		}
		return true;
	}

	public void validateTelefone(AjaxBehaviorEvent event) {
		this.validateCep();
	}

	public boolean validateTelefone() {
		if (this.novoTelefone.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroTelefoneVazio", "validacaoTelefone");
			return false;
		} else if (!ValidacoesDeCampos
				.validarFormatoTelefone(this.novoTelefone)) {
			MensagensDeErro.getErrorMessage(
					"editarInstituicaoErroTelefoneInvalido",
					"validacaoTelefone");
			return false;
		}
		return true;
	}

	/**
	 * @return the velhoNome
	 */
	public String getVelhoNome() {
		return this.velhoNome;
	}

	public SelectItem getNovoEstadoSigla() {
		SelectItem novoEstado = new SelectItem(this.novoEstado, this.novoEstado);
		return novoEstado;
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

	/**
	 * @param velhoNome
	 *            the velhoNome to set
	 */
	public void setVelhoNome(String velhoNome) {
		this.velhoNome = velhoNome;
	}

	/**
	 * @return the novoNome
	 */

	public void getNovoNome(AjaxBehaviorEvent event) {
		this.novoNome = "lol";
	}

	public String getNovoNome() {
		return novoNome;
	}

	/**
	 * @param novoNome
	 *            the novoNome to set
	 */
	public void setNovoNome(String novoNome) {
		this.novoNome = novoNome;
	}

	/**
	 * @return the novoLocalizacao
	 */
	public String getNovoLocalizacao() {
		return novoLocalizacao;
	}

	/**
	 * @param novoLocalizacao
	 *            the novoLocalizacao to set
	 */
	public void setNovoLocalizacao(String novoLocalizacao) {
		this.novoLocalizacao = novoLocalizacao;
	}

	/**
	 * @return the novoEndereco
	 */
	public String getNovoEndereco() {
		return novoEndereco;
	}

	/**
	 * @param novoEndereco
	 *            the novoEndereco to set
	 */
	public void setNovoEndereco(String novoEndereco) {
		this.novoEndereco = novoEndereco;
	}

	/**
	 * @return the novoCidade
	 */
	public String getNovoCidade() {
		return novoCidade;
	}

	/**
	 * @param novoCidade
	 *            the novoCidade to set
	 */
	public void setNovoCidade(String novoCidade) {
		this.novoCidade = novoCidade;
	}

	/**
	 * @return the novoEstado
	 */
	public String getNovoEstado() {
		return novoEstado;
	}

	/**
	 * @param novoEstado
	 *            the novoEstado to set
	 */
	public void setNovoEstado(String novoEstado) {
		this.novoEstado = novoEstado;
	}

	/**
	 * @return the novoCep
	 */
	public String getNovoCep() {
		return novoCep;
	}

	/**
	 * @param novoCep
	 *            the novoCep to set
	 */
	public void setNovoCep(String novoCep) {
		this.novoCep = novoCep;
	}

	/**
	 * @return the novoTelefone
	 */
	public String getNovoTelefone() {
		return novoTelefone;
	}

	/**
	 * @param novoTelefone
	 *            the novoTelefone to set
	 */
	public void setNovoTelefone(String novoTelefone) {
		this.novoTelefone = novoTelefone;
	}

	/**
	 * @return the instituicoes
	 */
	public List<Instituicao> getInstituicoes() {
		return this.instituicoes;
	}

	/**
	 * @param novoTelefone
	 *            the instituicoes to set
	 */
	public void setInstituicoes(List<Instituicao> novoInstituicoes) {
		this.instituicoes = novoInstituicoes;
	}

}