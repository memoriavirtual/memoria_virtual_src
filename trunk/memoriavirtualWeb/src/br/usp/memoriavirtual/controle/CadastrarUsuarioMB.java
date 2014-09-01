package br.usp.memoriavirtual.controle;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

@ManagedBean(name = "cadastrarUsuarioMB")
@ViewScoped
public class CadastrarUsuarioMB implements Serializable, BeanMemoriaVirtual {

	private static final long serialVersionUID = 2202578392589624271L;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private CadastrarUsuarioRemote cadastrarUsuarioEJB;
	private String senha = "";
	private String confirmacaoSenha = "";
	private Usuario usuario;
	private MensagensMB mensagens;
	
	private boolean podeCadastrar = true;

	public CadastrarUsuarioMB() {
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(facesContext.getELContext(), null, "mensagensMB");
	}

	@PostConstruct
	public void run() {
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");
		try {
			if (usuarioLogado != null) {
				this.getMensagens().mensagemErro(this.traduzir("erroUsuarioLogado"));
				FacesContext.getCurrentInstance().getExternalContext().redirect("restrito/index.jsf");
			} else {
				String validacao = (String) FacesContext.getCurrentInstance()
						.getExternalContext().getRequestParameterMap()
						.get("id");
				validacao = memoriaVirtualEJB.embaralhar(validacao);

				usuario = null;

				usuario = cadastrarUsuarioEJB.verificarConvite(validacao);
				if (usuario == null) {
					this.getMensagens().mensagemErro(this.traduzir("conviteInexistente"));
					podeCadastrar = false;
				} else if (usuario.isAtivo()) {
					this.getMensagens().mensagemErro(this.traduzir("conviteJaUtilizado"));
					podeCadastrar = false;
				} else {
					usuario.setIdentificacao("");
					usuario.setNomeCompleto("");
					usuario.setTelefone("");
				}
			}
		} catch (ModeloException e) {
			e.printStackTrace();
			this.getMensagens().mensagemErro(this.traduzir("conviteInexistente"));
			podeCadastrar = false;
		} catch (IOException e) {
			e.printStackTrace();
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			podeCadastrar = false;
		}
	}

	public String cadastrar() {
		if(podeCadastrar){
			if (this.validar()) {
				try {
					this.cadastrarUsuarioEJB.cadastrarUsuario(usuario, this.senha);
					this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
					return this.redirecionar("/login.jsf", true);
				} catch (Exception e) {
					this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
					e.printStackTrace();
					return null;
				}
	
			}
		}
		return null;
	}

	@Override
	public String traduzir(String chave) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, MVControleMemoriaVirtual.bundleName);
		return bundle.getString(chave);
	}

	@Override
	public String redirecionar(String pagina, boolean redirect) {
		return redirect ? pagina + "?faces-redirect=true" : pagina;
	}

	@Override
	public String cancelar() {
		return null;
	}

	@Override
	public boolean validar() {
		boolean a, b, c, d;
		a = this.validarNome();
		b = this.validarEmail();
		c = this.validarIdentificacao();
		d = this.validarSenha();
		return (a && b && c && d);
	}

	public boolean validarNome() {
		if (this.usuario.getNomeCompleto() == null
				|| this.usuario.getNomeCompleto().equals("")) {
			String args[] = { this.traduzir("nome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-nome");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return false;
		}
		return true;
	}

	public boolean validarEmail() {
		try {
			if (this.usuario.getEmail() == null
					|| this.usuario.getEmail().length() == 0) {
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				String[] args = { this.traduzir("email") };
				MensagensDeErro.getErrorMessage("erroCampoVazio", args,
						"validacao-email");
				return false;
			}
			boolean erroFormato = false;
			boolean erroInsdisponivel = false;
			if (!ValidacoesDeCampos
					.validarFormatoEmail(this.usuario.getEmail())) {
				erroFormato = true;
			} else if (!memoriaVirtualEJB.verificarDisponibilidadeEmail(
					this.usuario.getEmail(), this.usuario)) {
				erroInsdisponivel = true;
			}
			if (erroFormato) {
				MensagensDeErro.getErrorMessage("erroEmailInvalido",
						"validacao-email");
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				return false;
			}
			if (erroInsdisponivel) {
				MensagensDeErro.getErrorMessage("erroEmailIndisponivel",
						"validacao-email");
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				return false;
			}
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean validarSenha() {
		if (this.senha == null || this.senha.equals("")) {
			String args[] = { this.traduzir("senha") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-senha");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return false;
		} else if (this.senha.length() < 6) {
			MensagensDeErro.getErrorMessage("erroComprimentoSenha",
					"validacao-senha");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return false;
		} else if (!this.senha.equals(this.confirmacaoSenha)) {
			MensagensDeErro.getErrorMessage("erroConfirmacaoSenha",
					"validacao-senha");
			MensagensDeErro.getErrorMessage("erroConfirmacaoSenha",
					"validacao-confirmacao-senha");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return false;
		}
		return true;
	}

	public boolean validarIdentificacao() {
		if (this.usuario.getIdentificacao() == null
				|| this.usuario.getIdentificacao().equals("")) {
			String args[] = { this.traduzir("identificacao") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-identificacao");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return false;
		} else if (this.usuario.getIdentificacao().length() < 6) {
			MensagensDeErro.getErrorMessage("erroComprimentoIdentificacao",
					"validacao-identificacao");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return false;
		} else if (!this.memoriaVirtualEJB
				.verificarDisponibilidadeIdUsuario(this.usuario
						.getIdentificacao())) {
			MensagensDeErro.getErrorMessage("erroIdentificacaoIndisponivel",
					"validacao-identificacao");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return false;
		}
		return true;
	}

	// getters e setters

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isPodeCadastrar() {
		return podeCadastrar;
	}

	public void setPodeCadastrar(boolean podeCadastrar) {
		this.podeCadastrar = podeCadastrar;
	}

	@Override
	public void validarCampo(String nomeCampoMensagem, String nomeCampo,String campo) {
		if(ValidacoesDeCampos.validarComprimento(campo, 30)){
			String args[] = {"30"};
			MensagensDeErro.getWarningMessage("erroMaximoCaracteres", args, nomeCampoMensagem);
		}		
	}
	
	public void validarEmail(String nomeCampoMensagem, String nomeCampo,String campo) {
		if(ValidacoesDeCampos.validarComprimento(campo, 100)){
			String args[] = {"100"};
			MensagensDeErro.getWarningMessage("erroMaximoCaracteres", args, nomeCampoMensagem);
		}		
	}
	
	public void limpar(){
		usuario.setNomeCompleto("");
		usuario.setTelefone("");
		usuario.setIdentificacao("");
	}
}
