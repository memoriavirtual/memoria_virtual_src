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
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginCaptchaRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "realizarLoginCaptchaMB")
@ViewScoped
public class RealizarLoginCaptchaMB implements Serializable {

	private static final long serialVersionUID = -1005061526726383091L;

	@EJB
	private RealizarLoginCaptchaRemote RealizarLoginCaptchaEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private String usuario = "";
	private String senha = "";
	private boolean captchaNeed = true;

	public RealizarLoginCaptchaMB() {
		super();
		this.redirecionarUsuarioLogado();
	}

	public String autenticarUsuario() {
		if (validaCaptcha()) {
			boolean autenticado = false;
	
			Usuario usuarioAutenticado = null;
			try {
				usuarioAutenticado = RealizarLoginCaptchaEJB.realizarLoginCaptcha(
						this.getUsuario(), this.getSenha());
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
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
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
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
		
		ReCaptcha r;
		try {
			r = ReCaptchaFactory.newReCaptcha(memoriaVirtualEJB.getCaptchaPublicKey(), memoriaVirtualEJB.getCaptchaPrivateKey()	, false);
			Properties options = new Properties();
	        options.setProperty("theme", "blackglass");
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
