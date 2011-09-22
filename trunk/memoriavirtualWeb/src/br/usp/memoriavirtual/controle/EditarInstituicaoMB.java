package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;

public class EditarInstituicaoMB {

	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;
	private Instituicao instituicao;
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

	public List<SelectItem> getInstituicoesPermitidas() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		// Lista de SelectItem que sera exibida na pagina
		List<SelectItem> listaInstituicoes = new ArrayList<SelectItem>();

		// Lista de instituicoes que o usuario pertence
		List<Instituicao> instituicoesUsuario = new ArrayList<Instituicao>();

		if (usuario.isAdministrador()) {
			instituicoesUsuario = this.editarInstituicaoEJB.getInstituicoes();
		} else {
			Grupo grupo = new Grupo("Gerente");
			instituicoesUsuario = this.editarInstituicaoEJB.getInstituicoes(
					grupo, usuario);
		}
		for (Instituicao instituicao : instituicoesUsuario) {
			listaInstituicoes.add(new SelectItem(instituicao.getNome(),
					instituicao.getNome()));
		}
		return listaInstituicoes;
	}

	public String editarInstituicao() {

		String resultado;
		// Checagem no servidor de completeza dos dados
		if ((this.novoNome == null) || (this.novoEmail == null)
				|| (this.novoLocalizacao == null)
				|| (this.novoEndereco == null) || (this.novoCidade == null)
				|| (this.novoEstado == null) || (this.novoCep == null)
				|| (this.novoTelefone == null))
			return "Incompleto";

		resultado = this.editarInstituicaoEJB.editarInstituicao(
				this.instituicao.getNome(), this.novoNome, this.novoEmail,
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
			instituicoesUsuario = this.editarInstituicaoEJB.getInstituicoes();
		} else {
			Grupo grupo = new Grupo("Gerente");
			instituicoesUsuario = this.editarInstituicaoEJB.getInstituicoes(
					grupo, usuario);
		}

		this.instituicoes = instituicoesUsuario;

	}

	// Metodos usados para preencher o form com os dados antigos da instiuicao
	public String getNome() {
		return this.instituicao.getNome();
	}

	public String getEmail() {
		return this.instituicao.getEmail();
	}

	public String getLocalizacao() {
		return this.instituicao.getLocalizacao();
	}

	public String getEndereco() {
		return this.instituicao.getEndereco();
	}

	public String getCidade() {
		return this.instituicao.getCidade();
	}

	public String getEstado() {
		return this.instituicao.getEstado();
	}

	public String getCep() {
		return this.instituicao.getCep();
	}

	public String getTelefone() {
		return this.instituicao.getTelefone();
	}

	/**
	 * @return the velhoNome
	 */
	public String getVelhoNome() {
		return velhoNome;
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