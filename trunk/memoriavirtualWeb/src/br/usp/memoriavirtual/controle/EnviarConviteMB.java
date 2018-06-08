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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.Email;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MVModeloEmailParser;
import br.usp.memoriavirtual.utils.MVModeloEmailTemplates;
import br.usp.memoriavirtual.utils.MVModeloMapeamentoUrl;
import br.usp.memoriavirtual.utils.MVModeloParametrosEmail;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

@ManagedBean(name = "enviarConviteMB")
@ViewScoped
public class EnviarConviteMB implements Serializable, BeanMemoriaVirtual {

	private static final long serialVersionUID = 4054888655663667241L;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	@EJB
	private EnviarConviteRemote enviarConviteEJB;
	private String mensagem = "";
	private String validade = "1";
	private List<Acesso> acessos = new ArrayList<Acesso>();
	private List<Email> listaEmails = new ArrayList<Email>();
	private boolean administrador = false;
	private String emails = "";
	private Usuario usuario;
	private MensagensMB mensagens;
	private boolean captchaNeed = true;
	private String siteKeyRecaptcha;

	public EnviarConviteMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");

		this.usuario = (Usuario) facesContext.getExternalContext()
				.getSessionMap().get("usuario");
	}

	public String enviarConvite() {
		//valida o captcha para confirmação dos novos usuários a serem cadastrados
		if (validaCaptcha()) {
			if (this.validar()) {
				try {
					//obtém o(s) email(s) do(s) candidato(s) a usuário(s)
					String[] emails = this.emails.split("\\s+");
					
					//obtem a data atual para determinar a data de expiracao
					//do convite (um dia apos o envio)
					Calendar calendario = Calendar.getInstance();
					calendario.setTime(new Date());
					calendario.add(Calendar.DATE, new Integer(this.validade));
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String expiraEm = dateFormat.format(calendario.getTime());
	
					//persiste o pré-cadastro dos candidatos e obtem o id de 
					//cada um deles
					long id = this.enviarConviteEJB.enviarConvite(emails,
							this.mensagem, calendario.getTime(),
							this.administrador, this.acessos);
	
					//hashmap criado para armazenar as tags de solicitante 
					//da mensagem e da data de expiracao dos convites
					
					Map<String, String> tags = new HashMap<String, String>();
	
					Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
							.getExternalContext().getSessionMap().get("usuario");
	
					tags.put(MVModeloParametrosEmail.SOLICITANTE000.toString(),
							usuario.getNomeCompleto());
	
					tags.put(MVModeloParametrosEmail.MENSAGEM000.toString(),
							this.mensagem);
					tags.put(MVModeloParametrosEmail.EXPIRACAO000.toString(),
							expiraEm);
	
					//obtem a URL da pagina de confirmacao do cadastro dos candidatos,
					//com os respectivos parametros do link (no caso, o id do
					//candidato embaralhado)
					Map<String, String> parametros = new HashMap<String, String>();
					parametros.put("id", this.memoriaVirtualEJB
							.embaralhar(new Long(id).toString()));
					String url = "http://" + this.memoriaVirtualEJB.getUrl(
							MVModeloMapeamentoUrl.cadastrarUsuario, parametros);
	
					tags.put(MVModeloParametrosEmail.URL000.toString(), url);
					
					//modelo do corpo do email a ser enviado, com suas respectivas
					//tags
					String email = new MVModeloEmailParser().getMensagem(tags,
							MVModeloEmailTemplates.enviarConvite);
					//envio do email
					for (String s : emails) {
						this.memoriaVirtualEJB.enviarEmail(s,
								this.traduzir("enviarConviteAssunto"), email);
					}
				
					this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
	
					return this.redirecionar("/restrito/index.jsf", true);
				} catch (Exception e) {
					this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
					e.printStackTrace();
					return null;
				}
			}
			return null;
		} else {
			
			this.getMensagens().mensagemErro(this.traduzir("erroCaptcha"));
			return null;
		}
	}

	public List<SelectItem> getNiveisAcesso() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		if (usuario.isAdministrador()) {
			opcoes.add(new SelectItem("GERENTE", this
					.traduzir(Grupo.Grupos.gerente.toString())));
			opcoes.add(new SelectItem("CATALOGADOR", this
					.traduzir(Grupo.Grupos.catalogador.toString())));
			opcoes.add(new SelectItem("REVISOR", this
					.traduzir(Grupo.Grupos.revisor.toString())));

		} else {
			opcoes.add(new SelectItem("CATALOGADOR", this
					.traduzir(Grupo.Grupos.catalogador.toString())));
			opcoes.add(new SelectItem("REVISOR", this
					.traduzir(Grupo.Grupos.revisor.toString())));
		}

		return opcoes;
	}

	public List<SelectItem> getInstituicoes() {

		List<SelectItem> instituicoes = new ArrayList<SelectItem>();

		try {
			List<Instituicao> instituicoesUsuario = new ArrayList<Instituicao>();
			instituicoesUsuario = this.enviarConviteEJB
					.listarInstituicoes(usuario);

			for (Instituicao instituicao : instituicoesUsuario) {
				instituicoes.add(new SelectItem(instituicao.getId(),
						instituicao.getNome()));
			}

			return instituicoes;
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
			return instituicoes;
		}
	}

	public boolean validarAcessos() {
		System.out.println("teste:"+administrador);
		if (!this.administrador && this.acessos.isEmpty()) {
			this.getMensagens().mensagemErro(this.traduzir("erroAcessosVazio"));
			return false;
		}
		return true;
	}

	public boolean validarEmail() {
		try {
			if (this.emails == null || this.emails.length() == 0) {
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				String[] args = { this.traduzir("emails") };
				MensagensDeErro.getErrorMessage("erroCampoVazio", args,
						"validacao-emails");
				return false;
			}
			boolean erroFormato = false;
			boolean erroInsdisponivel = false;
			String[] emailArray = this.emails.split("\\s+");
			for (String email : emailArray) {
				if (!ValidacoesDeCampos.validarFormatoEmail(email)) {
					erroFormato = true;
				} else if (!memoriaVirtualEJB.verificarDisponibilidadeEmail(
						email, null)) {
					erroInsdisponivel = true;
				}
			}
			if (erroFormato) {
				MensagensDeErro.getErrorMessage("erroEmailInvalido",
						"validacao-emails");
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				return false;
			}
			if (erroInsdisponivel) {
				MensagensDeErro.getErrorMessage("erroEmailIndisponivel",
						"validacao-emails");
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

	public String removerAcesso(Acesso a) {
		this.acessos.remove(a);
		return null;
	}

	public String adicionarAcesso() {
		if (!this.administrador) {
			Instituicao i = new Instituicao();
			Usuario u = new Usuario();
			Grupo g = new Grupo();
			Acesso a = new Acesso();
			a.setInstituicao(i);
			a.setGrupo(g);
			a.setUsuario(u);
			a.setValidade(true);
			this.acessos.add(a);
		}
		return null;
	}

	public List<Acesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<Acesso> acessos) {
		this.acessos = acessos;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
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

	public String limpar() {
		this.acessos = new ArrayList<Acesso>();
		this.administrador = false;
		this.mensagem = "";
		this.validade = "1";
		this.emails = "";
		return null;
	}

	@Override
	public String cancelar() {
		this.limpar();
		return this.redirecionar("/restrito/index.jsf", true);
	}
	
	@Override
	public void validarCampo(String nomeCampoMensagem, String nomeCampo,String campo) {
		if(ValidacoesDeCampos.validarComprimento(campo, 255)){
			String args[] = {"255"};
			MensagensDeErro.getWarningMessage("erroMaximoCaracteres", args, nomeCampoMensagem);
		}		
	}

	@Override
	public boolean validar() {
		boolean a, b;
		a = this.validarEmail();
		b = this.validarAcessos();

		return (a && b);
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


	public List<Email> getListaEmails() {
		return listaEmails;
	}

	public void setListaEmails(List<Email> listaEmails) {
		this.listaEmails = listaEmails;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
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
