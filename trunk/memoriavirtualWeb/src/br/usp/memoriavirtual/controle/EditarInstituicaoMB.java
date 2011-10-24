package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;

@ManagedBean(name = "editarInstituicaoMB")
@SessionScoped
public class EditarInstituicaoMB implements Serializable {

	private static final long serialVersionUID = -2609697377310497761L;
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;
	private String velhoNome;
	private String novoNome;
	private String novoEmail;
	private String novoLocalizacao;
	private String novoEndereco;
	private String novoCidade;
	private String novoEstado;
	private String novoCep;
	private String novoTelefone;
	private List<Instituicao> instituicoes;

	public String editarInstituicao() {

		String resultado;

		resultado = this.editarInstituicaoEJB.editarInstituicao(
				this.velhoNome, this.novoNome, this.novoEmail,
				this.novoLocalizacao, this.novoEndereco, this.novoCidade,
				this.novoEstado, this.novoCep, this.novoTelefone);

		return resultado;

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
		this.novoEmail = instituicao.getEmail();
		this.novoEndereco = instituicao.getEndereco();
		this.novoEstado = instituicao.getEstado();
		this.novoLocalizacao = instituicao.getLocalizacao();
		this.novoTelefone = instituicao.getTelefone();
		this.instituicoes.clear();
		return null;
	}

	/**
	 * @return the velhoNome
	 */
	public String getVelhoNome() {
		return this.velhoNome;
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
	 * @return the novoEmail
	 */
	public String getNovoEmail() {
		return novoEmail;
	}

	/**
	 * @param novoEmail
	 *            the novoEmail to set
	 */
	public void setNovoEmail(String novoEmail) {
		this.novoEmail = novoEmail;
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