package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;

public class CadastrarUsuarioMB {

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

		boolean erro = false;

		// Validar ID

		/* Verifico se o email é valido e se ainda não está cadastrado */
		String erroEmail = cadastrarUsuarioEJB.validarEmail(email);
		if (erroEmail != null) {
			erro = true;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(erroEmail));
		}

		// Faltando validar o campo telefone

		/*
		 * Verifica se as senhas digitadas nos dois campos de senha são iguais
		 * para evitar erro de digitação.
		 */
		if (!senha.equals(confirmacaoSenha)) {
			erro = true;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Confirmacao de senha não confere."));
		}
		if (erro)
			return "erro nos dados";

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
			return "sucesso";
		} else {
			return "falhou";
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

}
