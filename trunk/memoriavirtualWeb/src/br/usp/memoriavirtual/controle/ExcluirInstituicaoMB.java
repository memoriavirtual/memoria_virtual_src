package br.usp.memoriavirtual.controle;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MVModeloAcao;
import br.usp.memoriavirtual.utils.MVModeloEmailParser;
import br.usp.memoriavirtual.utils.MVModeloEmailTemplates;
import br.usp.memoriavirtual.utils.MVModeloMapeamentoUrl;
import br.usp.memoriavirtual.utils.MVModeloParametrosEmail;
import br.usp.memoriavirtual.utils.MVModeloStatusAprovacao;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "excluirInstituicaoMB")
@ViewScoped
public class ExcluirInstituicaoMB extends EditarInstituicaoMB implements
		Serializable {

	private static final long serialVersionUID = 1147425267036231710L;

	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;

	private Instituicao instituicao = new Instituicao();
	private String validade = "1";
	private String justificativa = "";
	private Aprovacao aprovacao = null;
	private String analista;
	private MensagensMB mensagens;
	private Usuario solicitante = new Usuario();

	public ExcluirInstituicaoMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
	}

	@PostConstruct
	public void run(){
		if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")!=null){//aprovacao
			try {
				Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().get("usuario");
	
				String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
	
				if (!id.equals(null) && id != null && id.length() > 0) {
					Aprovacao aprovacao = memoriaVirtualEJB.getAprovacao(new Long(
							id));
					if ((aprovacao.getAnalista().getId() == usuario.getId())
							&& (aprovacao.getStatus() == MVModeloStatusAprovacao.aguardando)) {	
						this.setAprovacao(aprovacao);
					} else {
						System.out.println(aprovacao.getAnalista().getId() + " "+ usuario.getId());
						this.getMensagens().mensagemErro(this.traduzir("solitacaoNaoEhParaEsteUsuario"));
						FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
					}
				} else {
					System.out.println(id);
					this.getMensagens().mensagemErro(this.traduzir("acaoInvalida"));
					FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
				}
	
			} catch (Exception e) {
				e.printStackTrace();
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public String selecionarInstituicao() {
		if (this.id != -1) {
			try {
				this.instituicao = this.editarInstituicaoEJB
						.getInstituicao(this.id);
				return this.redirecionar("/restrito/excluirinstituicao.jsf", true);
			} catch (ModeloException m) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				m.printStackTrace();
				return null;
			}
		} else {
			String args[] = { this.traduzir("nome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-nome");
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return null;
		}
	}
	
	public List<SelectItem> getAnalistas() {
		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		try {
			Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("usuario");
			List<Usuario> analistas = this.excluirInstituicaoEJB
					.listarAnalistas(this.instituicao, usuario);

			for (Usuario u : analistas) {
				opcoes.add(new SelectItem(u.getId(), u.getNomeCompleto()));
			}
		} catch (ModeloException m) {
			m.printStackTrace();
		}

		return opcoes;
	}

	public String solicitarExclusao() {

		if (this.validar()) {
			try {
				int dias = new Integer(this.validade).intValue();
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(new Date());
				calendario.add(Calendar.DATE, dias);
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String expiraEm = dateFormat.format(calendario.getTime());
				Aprovacao aprovacao = new Aprovacao();
				aprovacao.setAcao(MVModeloAcao.excluir_instituicao);
				Usuario analista = memoriaVirtualEJB.getUsuario(this.analista);
				aprovacao.setAnalista(analista);
				aprovacao.setDados("id;" + this.instituicao.getId()
						+ ";justificativa;" + this.justificativa);
				aprovacao.setExpiraEm(calendario.getTime());
				Usuario solicitante = (Usuario) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSessionMap().get("usuario");
				aprovacao.setSolicitante(solicitante);

				Long id = this.excluirInstituicaoEJB.solicitarExclusao(
						this.instituicao, aprovacao);

				Map<String, String> tags = new HashMap<String, String>();
				tags.put(MVModeloParametrosEmail.ANALISTA000.toString(),
						analista.getNomeCompleto());
				tags.put(MVModeloParametrosEmail.EXPIRACAO000.toString(),
						expiraEm);
				tags.put(MVModeloParametrosEmail.INSTITUICAO000.toString(),
						this.instituicao.getNome());
				tags.put(MVModeloParametrosEmail.JUSTIFICATIVA000.toString(),
						this.justificativa);
				tags.put(MVModeloParametrosEmail.SOLICITANTE000.toString(),
						solicitante.getNomeCompleto());

				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put("id", id.toString());
				String url = this.memoriaVirtualEJB.getUrl(
						MVModeloMapeamentoUrl.excluirInstituicao, parametros);

				tags.put(MVModeloParametrosEmail.URL000.toString(), url);

				String email = new MVModeloEmailParser().getMensagem(tags,
						MVModeloEmailTemplates.excluirInstituicao);
				String assunto = this
						.traduzir("excluirInstituicaoAssuntoEmail");
				this.memoriaVirtualEJB.enviarEmail(analista.getEmail(),
						assunto, email);
				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
			} catch (Exception e) {
				e.printStackTrace();
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			}
		}
		return null;
	}

	public String aprovar() {
		try {
			this.aprovacao.setDados("instituicao;" + this.instituicao.getNome()
					+ ";justificativa;" + this.justificativa);
			this.excluirInstituicaoEJB
					.aprovar(this.instituicao, this.aprovacao);
			this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
			return this.redirecionar("/restrito/index.jsf", true);
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
			return null;
		}
	}

	public String negar() {
		try {
			this.aprovacao.setDados("instituicao;" + this.instituicao.getNome()
					+ ";justificativa;" + this.justificativa);
			this.excluirInstituicaoEJB.negar(this.instituicao, this.aprovacao);
			this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
			return this.redirecionar("/restrito/index.jsf", true);
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
			return null;
		}
	}

	public void carregar() {
		try {
			String dados = this.aprovacao.getDados();
			String[] tokens = dados.split(";");

			for (int i = 0; i < tokens.length; ++i) {
				if (tokens[i].equals("id")) {
					this.instituicao = editarInstituicaoEJB
							.getInstituicao(new Long(tokens[i + 1]));
				}
				if (tokens[i].equals("justificativa")) {
					this.justificativa = tokens[i + 1];
				}
			}

			this.solicitante = aprovacao.getSolicitante();
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
		}
	}

	@Override
	public boolean validar() {
		return this.validarJustificativa();
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

	// Getters e Setters
	public Aprovacao getAprovacao() {
		return aprovacao;
	}

	public void setAprovacao(Aprovacao aprovacao) {
		this.aprovacao = aprovacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String pnome) {
		nome = pnome;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getValidade() {
		return validade;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public String getAnalista() {
		return analista;
	}

	public void setAnalista(String analista) {
		this.analista = analista;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}
}