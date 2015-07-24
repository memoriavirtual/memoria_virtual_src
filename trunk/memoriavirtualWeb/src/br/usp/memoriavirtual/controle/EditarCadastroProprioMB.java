package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroProprioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

@ManagedBean
@ViewScoped
public class EditarCadastroProprioMB implements Serializable,
		BeanMemoriaVirtual {

	private static final long serialVersionUID = -8062688859796560352L;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private EditarCadastroProprioRemote editarCadastroProprioEJB;

	@EJB
	private RealizarLoginRemote realizarLoginEJB;

	private Usuario usuario;
	private String senha = "";
	private String novaSenha = "";
	private String confirmarSenha = "";
	private boolean permissao = true;
	private boolean alterarSenha = false;
	private MensagensMB mensagens;
	private boolean captchaNeed = true;


	public EditarCadastroProprioMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
		this.usuario = (Usuario) facesContext.getExternalContext()
				.getSessionMap().get("usuario");
	}

	public String editarCadastroProprio() {
		if (validaCaptcha()) {
			if (this.validar() && !this.permissao) {
				try {
					this.usuario.setSenha(this.novaSenha);
					this.editarCadastroProprioEJB.editar(this.usuario);
					this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
	
					HttpServletRequest request = (HttpServletRequest) FacesContext
							.getCurrentInstance().getExternalContext().getRequest();
					request.getSession().setAttribute("usuario",
							this.usuario.clone());
					
					this.limpar();
					return this.redirecionar("/restrito/index.jsf", true);
				} catch (Exception e) {
					this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
					e.printStackTrace();
					return null;
				}
			} else if (this.permissao) {
				this.getMensagens().mensagemErro(this.traduzir("erroLogin"));
				return null;
			}
			return null;
		} else {
			this.getMensagens().mensagemErro(this.traduzir("erroCaptcha"));
			return null;
		}
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
					|| this.usuario.getEmail().equals("")) {
				String args[] = { this.traduzir("email") };
				MensagensDeErro.getErrorMessage("erroCampoVazio", args,
						"validacao-email");
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				return false;
			} else if (!ValidacoesDeCampos.validarFormatoEmail(this.usuario
					.getEmail())) {
				MensagensDeErro.getErrorMessage("erroEmailInvalido",
						"validacao-email");
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				return false;
			} else if (!memoriaVirtualEJB.verificarDisponibilidadeEmail(
					this.usuario.getEmail(), this.usuario)) {
				MensagensDeErro.getErrorMessage("erroEmailIndisponivel",
						"validacao-email");
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				return false;
			}
			return true;
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
			return false;
		}
	}

	public boolean validarSenha() {
		if (this.alterarSenha) {
			if (this.novaSenha == null || this.novaSenha.equals("")) {
				String args[] = { this.traduzir("senha") };
				MensagensDeErro.getErrorMessage("erroCampoVazio", args,
						"validacao-senha");
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				return false;
			} else if (this.novaSenha.length() < 6) {
				MensagensDeErro.getErrorMessage("erroComprimentoSenha",
						"validacao-senha");
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				return false;
			} else if (!this.novaSenha.equals(this.confirmarSenha)) {
				MensagensDeErro.getErrorMessage("erroConfirmacaoSenha",
						"validacao-senha");
				MensagensDeErro.getErrorMessage("erroConfirmacaoSenha",
						"validacao-confirmacao-senha");
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				return false;
			}
		} else {
			novaSenha = getSenha();
			return true;
		}
		return true;
	}

	public String autenticar() {
		try {
			Usuario usuario = this.realizarLoginEJB.realizarLogin(this.usuario.getIdentificacao(), this.senha);
			if (usuario != null) {
				this.permissao = false;
				return null;
			} else {
				this.getMensagens().mensagemErro(this.traduzir("erroLogin"));
				return null;
			}
		} catch (NoResultException n) {
			this.getMensagens().mensagemErro(this.traduzir("erroLogin"));
			n.printStackTrace();
			return null;
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
			return null;
		}
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

	@Override
	public boolean validar() {
		boolean a, b, c;
		
		a = this.validarNome();
		b = this.validarEmail();
		c = this.validarSenha();

		return (a && b && c);
	}

	@Override
	public String cancelar() {
		this.limpar();
		return this.redirecionar("/restrito/index.jsf", true);
	}

	public void limpar() {
		this.permissao = true;
		this.usuario = new Usuario();
		this.senha = "";
		this.novaSenha = "";
		this.confirmarSenha = "";
	}

	public String getCodigoHtmlRecaptcha() {
		
		ReCaptcha r;
		try {
			r = ReCaptchaFactory.newReCaptcha(memoriaVirtualEJB.getCaptchaPublicKey(), memoriaVirtualEJB.getCaptchaPrivateKey()	, false);
			Properties options = new Properties();
	        options.setProperty("theme", "white");
	        options.setProperty("lang", "pt");
	        
			return r.createRecaptchaHtml(null, options);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean validaCaptcha() {		
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String enderecoRemoto = req.getRemoteAddr();
		
		ReCaptchaImpl r = new ReCaptchaImpl();
		try {
			r.setPrivateKey(memoriaVirtualEJB.getCaptchaPrivateKey());
			String textoCriptografado = req.getParameter("recaptcha_challenge_field");
			String resposta = req.getParameter("recaptcha_response_field");
			
			ReCaptchaResponse reCaptchaResponse = r.checkAnswer(enderecoRemoto, textoCriptografado, resposta);
			
			if(resposta.isEmpty() || !reCaptchaResponse.isValid()){
				return false;
			} else {	
				return true;
			}
		} catch (ModeloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// getters e setters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public boolean isPermissao() {
		return permissao;
	}

	public void setPermissao(boolean permissao) {
		this.permissao = permissao;
	}

	public boolean isAlterarSenha() {
		return alterarSenha;
	}

	public void setAlterarSenha(boolean alterarSenha) {
		this.alterarSenha = alterarSenha;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	
	public boolean getCaptchaNeed() {
		return captchaNeed;
	}

	public void setCaptchaNeed(boolean captchaNeed) {
		this.captchaNeed = captchaNeed;
	}

}
