package br.usp.memoriavirtual.controle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
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
	@EJB
	private EditarCadastroProprioRemote editarCadastroProprioEJB;
	private Usuario usuario;
	private String novoEmail;
	private String novoNomeCompleto;
	private String novoTelefone;
	private String novaSenha;
	private String confirmacaoNovaSenha;
	private String mudaSenha = "true";
	private String habilitaAlteracao = "true";
	private String senhaConfirmacao;
	private String id;
	private String antigaSenha;
	private Boolean atualiza = false;
	private Boolean flag = false;
	private int tipoMensagem = 0;

	public EditarCadastroProprioMB() {
		

	}

	@PostConstruct
	public void carregarDados() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		this.usuario = (Usuario) request.getSession().getAttribute("usuario");
		setId(this.usuario.getId());
		try {
			this.usuario = this.editarCadastroProprioEJB
					.recuperarDadosUsuario(getId());
			setAntigaSenha(this.usuario.getSenha());
		} catch (ModeloException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		setNovoNomeCompleto(this.usuario.getNomeCompleto());
		setNovoEmail(this.usuario.getEmail());
		setNovoTelefone(this.usuario.getTelefone());
		setHabilitaAlteracao("true");
		setMudaSenha("true");
	}

	/* Método que é chamado toda vez que a página é carregada */
	/*public void carregarDados2(ComponentSystemEvent event) {
		if (atualiza == true) {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			this.usuario = (Usuario) request.getSession().getAttribute(
					"usuario");
			setId(this.usuario.getId());
			try {
				this.usuario = this.editarCadastroProprioEJB
						.recuperarDadosUsuario(getId());
				setAntigaSenha(this.usuario.getSenha());
			} catch (ModeloException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			setNovoNomeCompleto(this.usuario.getNomeCompleto());
			setNovoEmail(this.usuario.getEmail());
			setNovoTelefone(this.usuario.getTelefone());
			setHabilitaAlteracao("true");
			setMudaSenha("true");
			atualiza = false;
		} else {
			if (flag == true) {
				setHabilitaAlteracao("false");
				flag = false;
				atualiza = true;
			}
		}
		switch (tipoMensagem) {
		case 1:
			MensagensDeErro.getErrorMessage("editarCadastroProprioFaltaCampos",
					"resultado");
			break;
		case 2:
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioSenhasDiferentes", "resultado");
			break;
		case 3:
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioFormatoSenha", "resultado");
			break;
		case 4:
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioFormatoEmail", "resultado");
			break;
		case 5:
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioDisponibilidadeEmail", "resultado");
			break;
		case 6:
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioFormatoTelefone", "resultado");
			break;
		case 7:
			MensagensDeErro.getSucessMessage("editarCadastroProprioSucesso",
					"resultado");
			break;
		case 8:
			MensagensDeErro.getErrorMessage("editarCadastroProprioErro",
					"resultado");
			break;
		case 9:
			MensagensDeErro.getErrorMessage("editarCadastroProprioSenhaVazia",
					"resultado");
			break;
		case 10:
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioSenhaInvalida", "resultado");
			break;
		}
		tipoMensagem = 0;
	}*/
	
	public void descartar2(AjaxBehaviorEvent event){
		this.descartar2();
	}

	public void descartar2(){
		carregarDados();
	}
	
	public void teste(AjaxBehaviorEvent event){
		this.teste();
	}
	
	public void teste(){
		carregarDados();
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

	public String editarCadastroProprio() {
		Boolean erro = false;
		if (this.mudaSenha.matches("false")) {
			if (this.novoEmail.matches("") || this.novoNomeCompleto.matches("")
					|| this.novoTelefone.matches("")
					|| this.novaSenha.matches("")
					|| this.confirmacaoNovaSenha.matches("")) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroProprioFaltaCampos", "resultado");
				tipoMensagem = 1;
				atualiza = false;
				flag = false;
				erro = true;
			}
			if (!this.novaSenha.matches(this.confirmacaoNovaSenha)) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroProprioSenhasDiferentes", "resultado");
				tipoMensagem = 2;
				atualiza = false;
				flag = false;
				erro = true;
			}
			if (this.novaSenha.length() < 6) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroProprioFormatoSenha", "resultado");
				tipoMensagem = 3;
				atualiza = false;
				flag = false;
				erro = true;
			}
		} else {
			if (this.novoEmail.matches("") || this.novoNomeCompleto.matches("")
					|| this.novoTelefone.matches("")) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroProprioFaltaCampos", "resultado");
				tipoMensagem = 1;
				atualiza = false;
				flag = false;
				erro = true;
			}
		}
		if (!ValidacoesDeCampos.validarFormatoEmail(this.novoEmail)) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioFormatoEmail", "resultado");
			tipoMensagem = 4;
			atualiza = false;
			flag = false;
			erro = true;
		}
		/*
		 * if (!memoriaVirtualEJB.verificarDisponibilidadeEmail(this.novoEmail))
		 * { MensagensDeErro.getErrorMessage(
		 * "editarCadastroProprioDisponibilidadeEmail", "resultado");
		 * tipoMensagem = 5; erro = true; }
		 */
		if (!ValidacoesDeCampos.validarFormatoTelefone(this.novoTelefone)) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioFormatoTelefone", "resultado");
			tipoMensagem = 6;
			atualiza = false;
			flag = false;
			erro = true;
		}
		if (erro == false) {
			try {
				if (this.mudaSenha.matches("false"))
					this.editarCadastroProprioEJB.atualizarDadosUsuario(
							getId(), getNovoEmail(), getNovoNomeCompleto(),
							getNovoTelefone(), getNovaSenha());
				else
					this.editarCadastroProprioEJB.atualizarDadosUsuario(
							getId(), getNovoEmail(), getNovoNomeCompleto(),
							getNovoTelefone());
				MensagensDeErro.getSucessMessage(
						"editarCadastroProprioSucesso", "resultado");
				tipoMensagem = 7;
				/* Salva na sessão */
				HttpServletRequest request = (HttpServletRequest) FacesContext
						.getCurrentInstance().getExternalContext().getRequest();
				request.getSession().setAttribute("usuario",
						this.usuario.clone());
			} catch (Exception e) {
				MensagensDeErro.getErrorMessage("editarCadastroProprioErro",
						"resultado");
				tipoMensagem = 8;
			}
			carregarDados();
		}
		return "Sucesso";
	}

	public void validateMudaSenha(AjaxBehaviorEvent event) {
		this.validateMudaSenha();
	}

	public void validateMudaSenha() {

	}

	public void validateNomeCompleto(AjaxBehaviorEvent event) {
		this.validateNomeCompleto();
	}

	public void validateNomeCompleto() {
		if (this.novoNomeCompleto.matches("")) {
			String[] argumentos = { "nome_completo" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoNomeCompleto");
		}
	}

	public void validateEmail(AjaxBehaviorEvent event) {
		this.validateEmail();
	}

	public void validateEmail() {

		if (this.novoEmail.matches("")) {
			String[] argumentos = { "email" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoEmail");
		} else if (!ValidacoesDeCampos.validarFormatoEmail(this.novoEmail)) {
			String[] argumentos = { "email" };
			MensagensDeErro.getErrorMessage("formato_invalido", argumentos,
					"validacaoEmail");
		} else if (!memoriaVirtualEJB
				.verificarDisponibilidadeEmail(this.novoEmail)) {
			String[] argumentos = { "email" };
			MensagensDeErro.getErrorMessage("ja_cadastrado", argumentos,
					"validacaoEmail");
		}
	}

	public void validateTelefone(AjaxBehaviorEvent event) {
		this.validateTelefone();
	}

	public void validateTelefone() {
		if (this.novoTelefone.matches("")) {
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

	public void validateSenha(AjaxBehaviorEvent event) {
		this.validateSenha();
	}

	public void validateSenha() {
		if (this.mudaSenha.matches("false")) {
			if (this.novaSenha.matches("")) {
				String[] argumentos = { "senha" };
				MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
						"validacaoSenha");
			} else if (this.novaSenha.length() < 6) {
				String[] argumentos = { "senha", "senha_minima" };
				MensagensDeErro.getErrorMessage("tamanho_minimo", argumentos,
						"validacaoSenha");
			}
		}
	}

	public void validateConfirmacaoSenha(AjaxBehaviorEvent event) {
		this.validateConfirmacaoSenha();
	}

	public void validateConfirmacaoSenha() {
		if (this.mudaSenha.matches("false")) {
			if (confirmacaoNovaSenha.matches("")) {
				String[] argumentos = { "confirmacao_senha" };
				MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
						"validacaoConfirmacaoSenha");
			} else if (!this.confirmacaoNovaSenha.matches(this.novaSenha)) {
				String[] argumentos = { "confirmacao_senha", "senha" };
				MensagensDeErro.getErrorMessage("confirmacao_errado",
						argumentos, "validacaoConfirmacaoSenha");
			}
		}
	}
	
	public void validarAlteracao(AjaxBehaviorEvent event){
		this.validarAlteracao();
	}

	public void validarAlteracao() {
		Boolean erro = false;
		if (this.senhaConfirmacao.matches("")) {
			MensagensDeErro.getErrorMessage("editarCadastroProprioSenhaVazia",
					"resultado");
			erro = true;
		} else if (!Usuario.gerarHash(this.senhaConfirmacao).matches(
				getAntigaSenha())) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioSenhaInvalida", "resultado");
			erro = true;
		}
		if (erro == false) {
			setHabilitaAlteracao("false");
		}
	}
	
	/*public String validaAlteracao() {
		Boolean erro = false;
		if (this.senhaConfirmacao.matches("")) {
			MensagensDeErro.getErrorMessage("editarCadastroProprioSenhaVazia",
					"resultado");
			tipoMensagem = 9;
			erro = true;
		} else if (!Usuario.gerarHash(this.senhaConfirmacao).matches(
				getAntigaSenha())) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroProprioSenhaInvalida", "resultado");
			tipoMensagem = 10;
			erro = true;
		}
		if (erro == false) {
			flag = true;
			setHabilitaAlteracao("false");
		}
		return null;
	}*/

}
