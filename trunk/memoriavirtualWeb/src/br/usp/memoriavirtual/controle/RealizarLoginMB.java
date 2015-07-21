package br.usp.memoriavirtual.controle;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "realizarLoginMB")
@ViewScoped
public class RealizarLoginMB implements Serializable {

	private static final long serialVersionUID = -1005061522826383091L;

	@EJB
	private RealizarLoginRemote realizarLoginEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private String usuario = "";
	private String senha = "";
	private boolean captchaNeed = true;
	private String publicKey = "6LdnC_0SAAAAAILrDzvj4h10-WnTXHjM7EJ5HukP";
	private String privateKey = "6LdnC_0SAAAAANIlxFpnqZdp7IaYsNHwVqTaGhhg";

	public RealizarLoginMB() {
		super();
		this.redirecionarUsuarioLogado();
	}

	public String autenticarUsuario() {
		if (validaCaptcha()) {
			boolean autenticado = false;
	
			Usuario usuarioAutenticado = null;
			try {
				usuarioAutenticado = realizarLoginEJB.realizarLogin(
						this.getUsuario(), this.getSenha());
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
			Integer contadorCaptcha = (Integer) request.getSession().getAttribute("contadorCaptcha");
			
			if (contadorCaptcha == null)
				request.getSession().setAttribute("contadorCaptcha", 0);
	
			if (usuarioAutenticado != null) {
				autenticado = true;
	
				/*
				 * Coloca o usuario autenticado no sessao.
				 */
				request.getSession().setAttribute("usuario", usuarioAutenticado);
	
				/*
				 * Coloca a lista de acessos do usuario no sessao.
				 */
				List<Acesso> listaAcessos = memoriaVirtualEJB
						.listarAcessos(usuarioAutenticado);
				request.getSession().setAttribute("acessos", listaAcessos);
	
			} else {
				contadorCaptcha = (Integer) request.getSession().getAttribute("contadorCaptcha");
				
				contadorCaptcha++;
				
				request.getSession().setAttribute("contadorCaptcha", contadorCaptcha);
				
				if(contadorCaptcha >= 3) {
					setCaptchaNeed(true);
					FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				}	
			}
	
			if (!autenticado)
				MensagensDeErro.getErrorMessage("realizarLoginErro", "resultado");
	
			this.setSenha(null);
	
			return autenticado ? "sucesso" : "falha";
		} else {
			MensagensDeErro.getErrorMessage("realizarLoginCaptchaErro", "resultado");
			return "falha";
		}
	}

	public String redirecionarUsuarioLogado() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");

		if (u != null) {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				context.redirect("restrito/index.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;

	}
	
	public String getCodigoHtmlRecaptcha() {
		ReCaptcha r = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
		Properties options = new Properties();
        options.setProperty("theme", "blackglass");
        options.setProperty("lang", "pt");
		return r.createRecaptchaHtml(null, options);
	}
	
	private boolean validaCaptcha() {		
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String enderecoRemoto = req.getRemoteAddr();
		
		ReCaptchaImpl r = new ReCaptchaImpl();
		r.setPrivateKey(privateKey);
		
		String textoCriptografado = req.getParameter("recaptcha_challenge_field");
		String resposta = req.getParameter("recaptcha_response_field");
		
		ReCaptchaResponse reCaptchaResponse = r.checkAnswer(enderecoRemoto, textoCriptografado, resposta);
		
		if(resposta.isEmpty() || !reCaptchaResponse.isValid()){
			return false;
		} else {	
			return true;
		}
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha
	 *            the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean getCaptchaNeed() {
		return captchaNeed;
	}

	public void setCaptchaNeed(boolean captchaNeed) {
		this.captchaNeed = captchaNeed;
	}

}
