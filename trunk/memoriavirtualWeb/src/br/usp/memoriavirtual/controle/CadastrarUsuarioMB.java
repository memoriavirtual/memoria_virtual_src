package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
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

		/*FacesContext.getCurrentInstance().addMessage(
				"resultado",
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Cadastro concluido com sucesso.", null));
*/
		FacesContext.getCurrentInstance().getMessages();
		MensagensErro.getSucessMessage("cadastro_concluido", "resultado");
		

		/*
		 * Realiza o cadastro do usuario no banco de dados e retorna uma cópia
		 * do usuário sem conter a senha para colocar na seção.
		 */
		
		/*
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
*/
		return "falhou";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void validateId(AjaxBehaviorEvent event) {
		System.out.println(event.getComponent().toString());
		System.out.println(event.getBehavior().toString());
		
		if (this.id.equals("")) {
			String[] argumentos = { "id" };
			MensagensErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoId");
		} else if (this.id.length() < 4) {
			String[] argumentos = { "id", "id_minimo" };
			MensagensErro.getErrorMessage("tamanho_minimo", argumentos,
					"validacaoId");
		} else if (!memoriaVirtualEJB.disponibilidadeId(this.id)) {
			String[] argumentos = { "id" };
			MensagensErro.getErrorMessage("ja_cadastrado", argumentos,
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

		if (this.email.equals("")) {
			String[] argumentos = { "email" };
			MensagensErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoEmail");
		} else if (!memoriaVirtualEJB.validarEmail(this.email)) {
			String[] argumentos = { "email" };
			MensagensErro.getErrorMessage("formato_invalido", argumentos,
					"validacaoEmail");
		} else if (!memoriaVirtualEJB.disponibilidadeEmail(this.email)) {
			String[] argumentos = { "email" };
			MensagensErro.getErrorMessage("ja_cadastrado", argumentos,
					"validacaoEmail");
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
			String[] argumentos = { "senha" };
			MensagensErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoSenha");
		} else if (this.senha.length() < 6) {
			String[] argumentos = { "senha", "senha_minima" };
			MensagensErro.getErrorMessage("tamanho_minimo", argumentos,
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
		if (this.nomeCompleto.equals("")) {
			String[] argumentos = { "nome_completo" };
			MensagensErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoNomeCompleto");
		}
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void validateTelefone(AjaxBehaviorEvent event) {
		String formato = "\\([0-9]{2}?\\)[0-9]{4}?\\-[0-9]{4}?";

		if (this.telefone.equals("")) {
			String[] argumentos = { "telefone" };
			MensagensErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoTelefone");
		} else if (!this.telefone.matches(formato)) {
			String[] argumentos = { "telefone" };
			MensagensErro.getErrorMessage("formato_invalido", argumentos,
					"validacaoTelefone");
		}

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
			String[] argumentos = { "confirmacao_senha" };
			MensagensErro.getErrorMessage("campo_vazio", argumentos,
			"validacaoConfirmacaoSenha");
		} else if (!this.confirmacaoSenha.equals(this.senha)) {
			String[] argumentos = { "confirmacao_senha", "senha" };
			MensagensErro.getErrorMessage("confirmacao_errado", argumentos,
			"validacaoConfirmacaoSenha");
		} 
	}

}
