package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensErro;

public class CadastrarUsuarioMB {

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private CadastrarUsuarioRemote cadastrarUsuarioEJB;
	private String id = "";
	private String email = "";
	private String nomeCompleto = "";
	private String telefone = "";
	private String senha = "";
	private String confirmacaoSenha = "";
	private String validacao = "";

	public CadastrarUsuarioMB() {

	}

	public String completarCadastro() {

		FacesContext.getCurrentInstance().addMessage(
				"resultado",
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Cadastro concluido com sucesso.", null));
		/*
		 * Realiza o cadastro do usuario no banco de dados e retorna uma cópia
		 * do usuário sem conter a senha para colocar na seção.
		 */
		Usuario usuario = cadastrarUsuarioEJB.completarCadastro(id, email,
				nomeCompleto, telefone, senha, validacao);
		if (usuario != null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			request.getSession().setAttribute("usuario", usuario);

			FacesContext.getCurrentInstance().addMessage(
					"cadastroConcluido",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Cadastro concluido com sucesso.", null));
		} else {
			return "falhou";
		}

		return "falhou";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void validateId(AjaxBehaviorEvent event) {

		if (this.id.equals("")) {
			MensagensErro.getErrorMessage("cadastrarUsuarioErroIdVazio",
					"validacaoId");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void validateEmail(AjaxBehaviorEvent event) {

		// MensagensErro messageManager = new MensagensErro();

		if (this.email.equals("")) {
			MensagensErro.getErrorMessage("cadastrarUsuarioErroEmailVazio",
					"validacaoEmail");
		} else if (!memoriaVirtualEJB.validarEmail(this.email)) {
			MensagensErro.getErrorMessage("cadastrarUsuarioErroEmailInvalido",
					"validacaoEmail");
		} else if (!cadastrarUsuarioEJB.disponibilidadeEmail(this.email)) {
			MensagensErro.getErrorMessage(
					"cadastrarUsuarioErroEmailJaCadastrado", "validacaoEmail");
		}
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void validateSenha(AjaxBehaviorEvent event) {
		if (this.senha.equals("")) {
			MensagensErro.getErrorMessage("cadastrarUsuarioErroSenhaVazia",
					"validacaoSenha");
		} else if (this.senha.length() < 6) {
			MensagensErro
					.getErrorMessage("cadastrarUsuarioErroSenhaDigitosMinimos",
							"validacaoSenha");
		} else if (!this.senha.contains("a")) {
			MensagensErro.getWarningMessage("cadastrarUsuarioErroSenhaFraca",
					"validacaoSenha");
		}
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public void validateNomeCompleto(AjaxBehaviorEvent event) {

	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void validateTelefone(AjaxBehaviorEvent event) {

	}

	public String getValidacao() {
		return validacao;
	}

	public void setValidacao(String validacao) {
		this.validacao = validacao;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public void validateConfirmacaoSenha(AjaxBehaviorEvent event) {
		if (confirmacaoSenha.equals("")) {
			FacesContext.getCurrentInstance().addMessage(
					"validacaoConfirmacaoSenha",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Confirmacao de senha deve ser preenchida.", null));
		} else if (!this.confirmacaoSenha.equals(this.senha)) {
			FacesContext.getCurrentInstance().addMessage(
					"validacaoConfirmacaoSenha",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Confirmacao de senha não confere com a senha.",
							null));
		} else {

		}
	}

}
