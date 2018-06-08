package br.usp.memoriavirtual.controle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

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
	private String siteKeyRecaptcha;


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
	
	/**
	 * Método que monta a URL contendo a requisição POST para a API reCaptcha v2 do Google,
	 * obtém a resposta (sendo ela um objeto JSON) e verifica-se a checagem do reCaptcha (se o
	 * usuário é um robô ou não)
	 * @return O usuário é um humano (true) ou um robô (false)
	 */
	private boolean validaCaptcha() {
		//Objeto que armazena os dados de uma requisição HTTP de um Servlet Java
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		//Site de verificação do recaptcha
		String response = req.getParameter("g-recaptcha-response");
		String verify_url = "https://www.google.com/recaptcha/api/siteverify?";
		
		//
		try{
			//Aqui, busca-se a Secret Key e monta-se a URL (nome da URL + Secret Key + 
			//parâmetro da requisição POST quando o usuário envia o formulário do site)
			String secret_key = memoriaVirtualEJB.getCaptchaSecretKey();	
			verify_url = verify_url + "secret=" + secret_key + "&response=" + response;
			
			//Abre-se a conexão e obtém-se a resposta do método POST que foi enviado à Google
			InputStream res = new URL(verify_url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));
			
			//Montador de String, com base no Buffered reader 'rd' criado para obter a resposta
			//da requisição do reCaptcha V2
			StringBuilder sb = new StringBuilder();
			int captcha_response;
			while ((captcha_response = rd.read()) != -1){
				sb.append((char) captcha_response);
			}
			
			//A resposta do Google da requisição POST contendo a verificação do usuário (se é
			//um robô ou não) é um objeto JSON
			String jsonText = sb.toString();
			res.close();
			
			//Obtém a resposta JSON e retorna se a requisição foi um sucesso (ou não)
			JsonReader jsonReader = Json.createReader(new StringReader(jsonText));
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();
			
			return jsonObject.getBoolean("success");
			
		} catch (ModeloException | MalformedURLException e){
			e.printStackTrace();
			
		} catch (IOException e) {
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
	
	/**
	 * Getter de um booleano de verificação da necessidade do captcha
	 * @return
	 */
	public boolean getCaptchaNeed() {
		return captchaNeed;
	}

	/**
	 * Setter de um booleano de verificação da necessidade do captcha
	 * @param captchaNeed
	 */
	public void setCaptchaNeed(boolean captchaNeed) {
		this.captchaNeed = captchaNeed;
	}

	/**
	 * Método que funciona como getter do atributo "Site Key". Porém,
	 * busca esse valor a partir do objeto "memoriaVirtualEJB", em um método
	 * que busca a Site Key do Captcha no JNDI do servidor.
	 * @return
	 */
	public String getSiteKeyRecaptcha() {
		try {
			//Busca do objeto "memoriaVirtualEJB" a propriedade "Public Key", armazenada
			//em um JNDI do servidor Payara
			this.setSiteKeyRecaptcha(memoriaVirtualEJB.getCaptchaSiteKey());
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		return siteKeyRecaptcha;
	}

	/**
	 * Setter do atributo siteKeyReCaptcha
	 * @param siteKeyRecaptcha
	 */
	public void setSiteKeyRecaptcha(String siteKeyRecaptcha) {
		this.siteKeyRecaptcha = siteKeyRecaptcha;
	}
}

