package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroProprioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

public class EditarCadastroProprioMB {

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private EditarCadastroProprioRemote editarCadastroProprioEJB;
	private Usuario usuario;
	private String novoEmail;
	private String novoNomeCompleto;
	private String novoTelefone;
	private String novaSenha;
	private String confirmacaoNovaSenha;
	private String mudaSenha;
	private String habilitaAlteracao;
	private String senhaConfirmacao;
	private String id;
	private String antigaSenha;

	public EditarCadastroProprioMB() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		this.usuario = (Usuario) request.getSession().getAttribute("usuario");
		setId(this.usuario.getId());
		try {
			this.usuario = (Usuario) this.editarCadastroProprioEJB
					.recuperarDadosUsuario("mvirtual");
			setNovoNomeCompleto(this.usuario.getNomeCompleto());
			setNovoEmail(this.usuario.getEmail());
			setNovoTelefone(this.usuario.getTelefone());
			setAntigaSenha(this.usuario.getSenha());
		} catch (ModeloException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		setHabilitaAlteracao("true");
		setMudaSenha("0");

	}

	public void setAntigaSenha(String antigaSenha) {
		this.antigaSenha = antigaSenha;
	}

	public String getAntigaSenha() {
		return this.antigaSenha;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public String editarCadastroProprio() {
		if (this.novoEmail == null || this.novoNomeCompleto == null
				|| this.novaSenha == null)
			return "Incompleto";
		try {
			this.editarCadastroProprioEJB.atualizarDadosUsuario(getId(),
					getNovoEmail(), getNovoNomeCompleto(), getNovoTelefone(),
					getNovaSenha());
			MensagensDeErro.getSucessMessage("cadastro alterado com sucesso",
					"resultado");
			return "Sucesso";
			/** Atualizar os dados da sessão de usuario */
		} catch (Exception e) {
			return "Falha";
		}
	}

	public void setNovoEmail(String novoEmail) {
		this.novoEmail = novoEmail;
	}

	public void setNovoNomeCompleto(String novoNomeCompleto) {
		this.novoNomeCompleto = novoNomeCompleto;
	}

	public void setNovoTelefone(String novoTelefone) {
		this.novoTelefone = novoTelefone;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}

	public String getNovoNomeCompleto() {
		return this.novoNomeCompleto;
	}

	public String getNovoTelefone() {
		return this.novoTelefone;
	}

	public String getNovoEmail() {
		return this.novoEmail;
	}

	public String getNovaSenha() {
		return this.novaSenha;
	}

	public String getConfirmacaoNovaSenha() {
		return this.confirmacaoNovaSenha;
	}

	public void validateSenha(AjaxBehaviorEvent event) {
		this.validateSenha();
	}

	public void validateSenha() {
		if (this.novaSenha.equals("")) {
			String[] argumentos = { "senha" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoSenha");
		} else if (this.novaSenha.length() < 6) {
			String[] argumentos = { "senha", "senha_minima" };
			MensagensDeErro.getErrorMessage("tamanho_minimo", argumentos,
					"validacaoSenha");
		}
	}

	public void validateConfirmacaoSenha(AjaxBehaviorEvent event) {
		this.validateConfirmacaoSenha();
	}

	public void validateConfirmacaoSenha() {
		if (confirmacaoNovaSenha.equals("")) {
			String[] argumentos = { "confirmacao_senha" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoConfirmacaoSenha");
		} else if (!this.confirmacaoNovaSenha.equals(this.novaSenha)) {
			String[] argumentos = { "confirmacao_senha", "senha" };
			MensagensDeErro.getErrorMessage("confirmacao_errado", argumentos,
					"validacaoConfirmacaoSenha");
		}
	}

	public void setMudaSenha(String mudaSenha) {
		this.mudaSenha = mudaSenha;
	}

	public String getMudaSenha() {
		return this.mudaSenha;
	}

	public void setHabilitaAlteracao(String habilitaAlteracao) {
		this.habilitaAlteracao = habilitaAlteracao;
	}

	public String getHabilitaAlteracao() {
		return this.habilitaAlteracao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	public String getSenhaConfirmacao() {
		return this.senhaConfirmacao;
	}

	public void validateNomeCompleto(AjaxBehaviorEvent event) {
		this.validateNomeCompleto();
	}

	public void validateNomeCompleto() {
		if (this.novoNomeCompleto.equals("")) {
			String[] argumentos = { "nome_completo" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoNomeCompleto");
		}
	}

	public void validateEmail(AjaxBehaviorEvent event) {
		this.validateEmail();
	}

	public void validateEmail() {

		if (this.novoEmail.equals("")) {
			String[] argumentos = { "email" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoEmail");
		} else if (!ValidacoesDeCampos.validarFormatoEmail(this.novoEmail)) {
			String[] argumentos = { "email" };
			MensagensDeErro.getErrorMessage("formato_invalido", argumentos,
					"validacaoEmail");
		} else if (!memoriaVirtualEJB.disponibilidadeEmail(this.novoEmail)) {
			String[] argumentos = { "email" };
			MensagensDeErro.getErrorMessage("ja_cadastrado", argumentos,
					"validacaoEmail");
		}
	}

	public void validateTelefone(AjaxBehaviorEvent event) {
		this.validateTelefone();
	}

	public void validateTelefone() {
		if (this.novoTelefone.equals("")) {
			String[] argumentos = { "telefone" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoTelefone");
		} else if (!ValidacoesDeCampos
				.validarFormatoTelefone(this.novoTelefone)) {
			String[] argumentos = { "telefone" };
			MensagensDeErro.getErrorMessage("formato_invalido", argumentos,
					"validacaoTelefone");
		}
	}

	public String validateLiberaAlteracao() {
		if (senhaConfirmacao.equals("")) {
			String[] argumentos = { "confirmacao_senha" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoLiberaAlteracao");
			return "Falha";
		} else if (!this.senhaConfirmacao.equals(this.usuario.getSenha())) {
			String[] argumentos = { "confirmacao_senha", "senha" };
			MensagensDeErro.getErrorMessage("confirmacao_errado", argumentos,
					"validacaoLiberaAlteracao");
			return "Falha";
		}
		setHabilitaAlteracao("false");
		return "Sucesso";
	}

	public String vA() {
		if (senhaConfirmacao.equals("")) {
			String[] argumentos = { "confirmacao_senha" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoLiberaAlteracao");
			return "Falha";
		} else if (!this.senhaConfirmacao.equals(this.usuario.getSenha())) {
			String[] argumentos = { "confirmacao_senha", "senha" };
			MensagensDeErro.getErrorMessage("confirmacao_errado", argumentos,
					"validacaoLiberaAlteracao");
			return "Falha";
		}
		setHabilitaAlteracao("false");
		return "Sucesso";
	}

}
