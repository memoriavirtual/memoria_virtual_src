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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MVModeloAcao;
import br.usp.memoriavirtual.utils.MVModeloEmailParser;
import br.usp.memoriavirtual.utils.MVModeloEmailTemplates;
import br.usp.memoriavirtual.utils.MVModeloMapeamentoUrl;
import br.usp.memoriavirtual.utils.MVModeloParametrosEmail;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "editarCadastroUsuarioMB")
@SessionScoped
public class EditarCadastroUsuarioMB implements BeanMemoriaVirtual,
		Serializable {

	private static final long serialVersionUID = -2609697377310497761L;

	@EJB
	private EditarCadastroUsuarioRemote editarCadastroUsuarioEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;

	protected Usuario usuario;
	protected String justificativa;
	protected String id;
	protected String analista;
	protected Usuario solicitante;
	protected Aprovacao aprovacao;
	private MensagensMB mensagens;

	public EditarCadastroUsuarioMB() {
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(facesContext.getELContext(), null, "mensagensMB");
		this.solicitante = (Usuario) facesContext.getExternalContext().getSessionMap().get("usuario");
	}
	
	

	public String solicitarAprovacao() {

		if (this.validar()) {
			try {
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(new Date());
				calendario.add(Calendar.DATE, 30);
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String expiraEm = dateFormat.format(calendario.getTime());
				Aprovacao aprovacao = new Aprovacao();
				aprovacao.setAcao(MVModeloAcao.editar_cadastro_usuario);
				Usuario analista = memoriaVirtualEJB.getUsuario(this.analista);
				aprovacao.setAnalista(analista);
				aprovacao.setExpiraEm(calendario.getTime());
				aprovacao.setSolicitante(this.solicitante);
				String dados = "id;"
						+ new Long(this.usuario.getId()).toString();

				if (this.usuario.isAdministrador()) {
					dados += ";administrador";
				} else {
					for (Acesso a : this.usuario.getAcessos()) {
						dados += ";grupo;" + a.getGrupo().getId()
								+ ";instituicao;" + a.getInstituicao().getId();
					}
				}
				dados += ";justificativa;" + this.justificativa;

				aprovacao.setDados(dados);

				long id = this.editarCadastroUsuarioEJB
						.solicitarEdicao(aprovacao);

				Map<String, String> tags = new HashMap<String, String>();

				tags.put(MVModeloParametrosEmail.ANALISTA000.toString(),
						analista.getNomeCompleto());
				tags.put(MVModeloParametrosEmail.EXPIRACAO000.toString(),
						expiraEm);
				tags.put(MVModeloParametrosEmail.JUSTIFICATIVA000.toString(),
						this.justificativa);
				tags.put(MVModeloParametrosEmail.SOLICITANTE000.toString(),
						this.solicitante.getNomeCompleto());
				tags.put(MVModeloParametrosEmail.USUARIO000.toString(),
						this.usuario.getNomeCompleto());

				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put("id", new Long(id).toString());
				String url = this.memoriaVirtualEJB
						.getUrl(MVModeloMapeamentoUrl.editarCadastroUsuario,
								parametros);

				tags.put(MVModeloParametrosEmail.URL000.toString(), url);

				String email = new MVModeloEmailParser().getMensagem(tags,
						MVModeloEmailTemplates.editarCadastroUsuario);
				String assunto = this
						.traduzir("editarCadastroUsuarioAssuntoEmail");
				this.memoriaVirtualEJB.enviarEmail(analista.getEmail(),
						assunto, email);
				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
				return this.redirecionar("/restrito/index.jsf", true);

			} catch (Exception e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public List<SelectItem> getAnalistas() {
		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		try {
			List<Usuario> analistas = this.editarCadastroUsuarioEJB
					.listarAprovadores(this.solicitante, this.usuario);

			for (Usuario u : analistas) {
				opcoes.add(new SelectItem(u.getId(), u.getNomeCompleto()));
			}
		} catch (ModeloException m) {
			m.printStackTrace();
		}
		return opcoes;
	}

	public String cancelar() {
		this.limpar();
		return "cancelar";
	}

	public String selecionarUsuario() {
		try {
			this.usuario = this.memoriaVirtualEJB.getUsuario(this.id);
			return this.redirecionar("/restrito/editarcadastrousuario.jsf",
					true);
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	public String removerAcesso(Acesso acesso) {
		if (this.usuario.getAcessos().contains(acesso)) {
			this.usuario.getAcessos().remove(acesso);
		}
		return null;
	}

	public String adicionarAcesso() {

		Instituicao instituicao = new Instituicao();
		Grupo grupo = new Grupo();

		Acesso acesso = new Acesso();
		acesso.setGrupo(grupo);
		acesso.setInstituicao(instituicao);
		acesso.setUsuario(this.usuario);

		this.usuario.getAcessos().add(acesso);
		return null;
	}

	public List<SelectItem> getInstituicoes() {

		List<Instituicao> instituicoes = new ArrayList<Instituicao>();
		List<SelectItem> opcoes = new ArrayList<SelectItem>();
		try {
			Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("usuario");

			if (usuario.isAdministrador()) {
				instituicoes = editarInstituicaoEJB.listarInstituicoes("");
			} else {
				List<Instituicao> parcial;
				for (Acesso a : usuario.getAcessos()) {
					parcial = editarInstituicaoEJB.listarInstituicoes("",
							a.getGrupo(), usuario);
					instituicoes.addAll(parcial);
				}
			}

			instituicoes = editarCadastroUsuarioEJB.listarInstituicoes("");

			for (Instituicao i : instituicoes) {
				opcoes.add(new SelectItem(i.getId(), i.getNome()));
			}
			return opcoes;

		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return opcoes;
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

	public void limpar() {
		this.usuario = new Usuario();
		this.justificativa = null;
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
	public boolean validar() {
		boolean a, b;
		a = this.validarJustificativa();
		b = this.validarAcessos();
		return (a && b);
	}

	public boolean validarJustificativa() {
		if (!this.justificativa.equals(null) && this.justificativa != null
				&& this.justificativa.length() > 0) {
			return true;
		} else {
			String args[] = { this.traduzir("justificativa") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-justificativa");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return false;
		}
	}

	public boolean validarAcessos() {
		if (!this.usuario.isAdministrador()
				&& this.usuario.getAcessos().isEmpty()) {
			this.getMensagens().mensagemErro(this.traduzir("erroAcessosVazio"));
			return false;
		}
		return true;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnalista() {
		return analista;
	}

	public void setAnalista(String analista) {
		this.analista = analista;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public Aprovacao getAprovacao() {
		return aprovacao;
	}

	public void setAprovacao(Aprovacao aprovacao) {
		this.aprovacao = aprovacao;
	}

}