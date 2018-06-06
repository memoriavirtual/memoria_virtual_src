package br.usp.memoriavirtual.controle;

//
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.List;

//
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

//
import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarLoginCaptchaRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

//
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.json.Json; 
import javax.json.JsonObject;
import javax.json.JsonReader;


@ManagedBean(name = "realizarLoginCaptchaMB")
@ViewScoped
public class RealizarLoginCaptchaMB implements Serializable {

	private static final long serialVersionUID = -1005061526726383091L;

	@EJB
	private RealizarLoginCaptchaRemote realizarLoginCaptchaEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private String usuario = "";
	private String senha = "";
	private String siteKeyRecaptcha = "";


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
				usuarioAutenticado = realizarLoginCaptchaEJB.realizarLoginCaptcha(
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

	/**
	 * Getter da String de usuário
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Setter da String de usuário
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Getter da senha do usuário
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Setter da senha do usuário
	 * @param senha
	 *            the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
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
