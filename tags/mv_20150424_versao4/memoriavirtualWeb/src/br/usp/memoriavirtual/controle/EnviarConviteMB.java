package br.usp.memoriavirtual.controle;

import java.io.Serializable;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
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

	public EnviarConviteMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");

		this.usuario = (Usuario) facesContext.getExternalContext()
				.getSessionMap().get("usuario");
	}

	public String enviarConvite() {

		if (this.validar()) {
			try {
				String[] emails = this.emails.split("\\s+");

				Calendar calendario = Calendar.getInstance();
				calendario.setTime(new Date());
				calendario.add(Calendar.DATE, new Integer(this.validade));
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String expiraEm = dateFormat.format(calendario.getTime());

				long id = this.enviarConviteEJB.enviarConvite(emails,
						this.mensagem, calendario.getTime(),
						this.administrador, this.acessos);

				Map<String, String> tags = new HashMap<String, String>();

				Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
						.getExternalContext().getSessionMap().get("usuario");

				tags.put(MVModeloParametrosEmail.SOLICITANTE000.toString(),
						usuario.getNomeCompleto());

				tags.put(MVModeloParametrosEmail.MENSAGEM000.toString(),
						this.mensagem);
				tags.put(MVModeloParametrosEmail.EXPIRACAO000.toString(),
						expiraEm);

				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put("id", this.memoriaVirtualEJB
						.embaralhar(new Long(id).toString()));
				String url = this.memoriaVirtualEJB.getUrl(
						MVModeloMapeamentoUrl.cadastrarUsuario, parametros);

				tags.put(MVModeloParametrosEmail.URL000.toString(), url);

				String email = new MVModeloEmailParser().getMensagem(tags,
						MVModeloEmailTemplates.enviarConvite);

				for (String s : emails) {
					this.memoriaVirtualEJB.enviarEmail(s,
							this.traduzir("enviarConviteAssunto"), email);
				}

				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));

				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "O código digitado está incorreto!",null));
				
				return this.redirecionar("/restrito/index.jsf", true);
			} catch (Exception e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
				return null;
			}
		}
		return null;
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
	public boolean validar() {
		boolean a, b;
		a = this.validarEmail();
		b = this.validarAcessos();

		return (a && b);
	}

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

	@Override
	public void validarCampo(String nomeCampoMensagem, String nomeCampo,String campo) {
		if(ValidacoesDeCampos.validarComprimento(campo, 255)){
			String args[] = {"255"};
			MensagensDeErro.getWarningMessage("erroMaximoCaracteres", args, nomeCampoMensagem);
		}		
	}
}
