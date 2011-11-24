package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

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

		/*
		 * FacesContext.getCurrentInstance().addMessage( "resultado", new
		 * FacesMessage(FacesMessage.SEVERITY_INFO,
		 * "Cadastro concluido com sucesso.", null));
		 */

		this.validateId();
		this.validateEmail();
		this.validateNomeCompleto();
		this.validateTelefone();
		this.validateSenha();
		this.validateConfirmacaoSenha();

		Usuario usuario = new Usuario(this.id, this.email, this.nomeCompleto,
				this.telefone, this.senha);

		if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {

			try {
				cadastrarUsuarioEJB.cadastrarUsuario(usuario, validacao);
			} catch (ModeloException e) {
				usuario = null;
				MensagensDeErro
						.getErrorMessage("convite_invalido", "resultado");
			} catch (RuntimeException e) {
				usuario = null;
				MensagensDeErro.getErrorMessage("erro_cadastramento",
						"resultado");
			}
			if (usuario != null) {
				usuario = usuario.clone(); //Teste se isso nao altera o usuario ja persistido na banco
				HttpServletRequest request = (HttpServletRequest) FacesContext
						.getCurrentInstance().getExternalContext().getRequest();
				request.getSession().setAttribute("usuario", usuario);
			}

			this.id = "";
			this.email = "";
			this.nomeCompleto = "";
			this.telefone = "";
			this.senha = "";
			this.confirmacaoSenha = "";
			this.validacao = "";

			MensagensDeErro.getSucessMessage("cadastro_concluido", "resultado");

		}
		return "falhou";
	}

	public String getValidacao() {
		return validacao;
	}

	public void setValidacao(String validacao) {
		this.validacao = validacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void validateId(AjaxBehaviorEvent event) {
		this.validateId();
	}

	public void validateId() {

		if (this.id.equals("")) {
			String[] argumentos = { "id" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoId");
		} else if (this.id.length() < 4) {
			String[] argumentos = { "id", "id_minimo" };
			MensagensDeErro.getErrorMessage("tamanho_minimo", argumentos,
					"validacaoId");
		} else if (!memoriaVirtualEJB.verificarDisponibilidadeIdUsuario(this.id)) {
			String[] argumentos = { "id" };
			MensagensDeErro.getErrorMessage("ja_cadastrado", argumentos,
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
		this.validateEmail();
	}

	public void validateEmail() {

		if (this.email.equals("")) {
			String[] argumentos = { "email" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoEmail");
		} else if (!ValidacoesDeCampos.validarFormatoEmail(this.email)) {
			String[] argumentos = { "email" };
			MensagensDeErro.getErrorMessage("formato_invalido", argumentos,
					"validacaoEmail");
		} else if (!memoriaVirtualEJB.verificarDisponibilidadeEmail(this.email)) {
			String[] argumentos = { "email" };
			MensagensDeErro.getErrorMessage("ja_cadastrado", argumentos,
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
		this.validateSenha();
	}

	public void validateSenha() {
		if (this.senha.equals("")) {
			String[] argumentos = { "senha" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoSenha");
		} else if (this.senha.length() < 6) {
			String[] argumentos = { "senha", "senha_minima" };
			MensagensDeErro.getErrorMessage("tamanho_minimo", argumentos,
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
		this.validateNomeCompleto();
	}

	public void validateNomeCompleto() {
		if (this.nomeCompleto.equals("")) {
			String[] argumentos = { "nome_completo" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
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
		this.validateTelefone();
	}

	public void validateTelefone() {
		if (this.telefone.equals("")) {
			String[] argumentos = { "telefone" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoTelefone");
		} else if (!ValidacoesDeCampos.validarFormatoTelefone(this.telefone)) {
			String[] argumentos = { "telefone" };
			MensagensDeErro.getErrorMessage("formato_invalido", argumentos,
					"validacaoTelefone");
		}
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public void validateConfirmacaoSenha(AjaxBehaviorEvent event) {
		this.validateConfirmacaoSenha();
	}

	public void validateConfirmacaoSenha() {
		if (confirmacaoSenha.equals("")) {
			String[] argumentos = { "confirmacao_senha" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoConfirmacaoSenha");
		} else if (!this.confirmacaoSenha.equals(this.senha)) {
			String[] argumentos = { "confirmacao_senha", "senha" };
			MensagensDeErro.getErrorMessage("confirmacao_errado", argumentos,
					"validacaoConfirmacaoSenha");
		}
	}

}
