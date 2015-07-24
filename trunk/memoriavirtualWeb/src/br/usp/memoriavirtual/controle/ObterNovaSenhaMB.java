package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ObterNovaSenhaRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "obterNovaSenhaMB")
@ViewScoped
public class ObterNovaSenhaMB implements Serializable {

	private static final long serialVersionUID = -5890869630608586063L;

	@EJB
	private ObterNovaSenhaRemote obterNovaSenhaEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	private String email;
	private String token;
	private String novaSenha;
	private boolean captchaNeed = true;

	public ObterNovaSenhaMB() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext()
				.getRequest();

		String token = req.getParameter("validacao");
		String email = req.getParameter("email");

		if (token != null) {
			this.token = token;
		}
		if (email != null) {
			this.email = email;
		}

	}

	public String obterNovaSenha() {
		if (validaCaptcha()) {
			try {
				this.obterNovaSenhaEJB.obterNovaSenha(this.email);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("obterNovaSenhaErro", "resultado");
				e.printStackTrace();
				return "falha";
			}
			MensagensDeErro.getSucessMessage("obterNovaSenhaSucesso", "resultado");
			return "sucesso";
		} else {
			MensagensDeErro.getErrorMessage("obterNovaSenhaCaptchaErro", "resultado");
			return "falha";
		}
	}

	public String cadastrarNovaSenha() {
		try {
			this.obterNovaSenhaEJB.cadastrarNovaSenha(this.email, this.token,
					this.novaSenha);
		} catch (ModeloException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return "falha";
		}
		return "sucesso";
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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	
	public boolean getCaptchaNeed() {
		return captchaNeed;
	}

	public void setCaptchaNeed(boolean captchaNeed) {
		this.captchaNeed = captchaNeed;
	}

}
