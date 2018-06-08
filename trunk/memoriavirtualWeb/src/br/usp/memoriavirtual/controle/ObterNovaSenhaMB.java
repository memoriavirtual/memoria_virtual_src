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

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;

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
	private String siteKeyRecaptcha;

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
	
	/* **********************************************************************************
	 * 
	 * 
	 * 									GETTERS E SETTERS
	 * 
	 * 
	 * **********************************************************************************
	 */

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
