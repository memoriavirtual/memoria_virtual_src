package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.FacesUtil;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MVModeloAcao;
import br.usp.memoriavirtual.utils.MVModeloStatusAprovacao;

@ManagedBean(name="validarExclusaoBemMB")
@ViewScoped
public class validarExclusaoBem implements Serializable{

	private static final long serialVersionUID = 3565639588547927353L;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	
	@EJB
	private CadastrarBemPatrimonialRemote cadastrarBemEJB;
	
	@EJB
	private ExcluirBemPatrimonialRemote excluirBemEJB;
	
	private Aprovacao aprovacao;
	private MensagensMB mensagens;
	private BemPatrimonial bem;
	private Usuario solicitante;
	private String justificativa;
	
	public validarExclusaoBem(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(facesContext.getELContext(), null, "mensagensMB");
	}
	
	@PostConstruct
	public void run(){
		try {
			if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id") != null) {// aprovacao
				Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
						.getExternalContext().getSessionMap().get("usuario");

				String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

				if (!id.equals(null) && id != null && id.length() > 0) {
					
					this.aprovacao = memoriaVirtualEJB.getAprovacao(new Long(id));
					if(aprovacao == null){
						this.getMensagens().mensagemErro(this.traduzir("aprovacaoInvalida"));
						FacesUtil.redirecionar("index.jsf");
					}else{
						if(aprovacao.getAcao() != MVModeloAcao.excluir_bem){
							this.getMensagens().mensagemErro(this.traduzir("acaoInvalida"));
							FacesUtil.redirecionar("index.jsf");
						}
						if(aprovacao.getStatus() == MVModeloStatusAprovacao.expirada){
							this.getMensagens().mensagemErro(this.traduzir("aprovacaoExpirada"));
							FacesUtil.redirecionar("index.jsf");
						}
						else if(aprovacao.getStatus() == MVModeloStatusAprovacao.aprovada){
							this.getMensagens().mensagemErro(this.traduzir("aprovacaoJaAprovada"));
							FacesUtil.redirecionar("index.jsf");
						}else if(aprovacao.getStatus() == MVModeloStatusAprovacao.negada){
							this.getMensagens().mensagemErro(this.traduzir("aprovacaoNegada"));
							FacesUtil.redirecionar("index.jsf");
						}
						else if(aprovacao.getStatus() == MVModeloStatusAprovacao.aguardando){
							if (aprovacao.getAnalista().getId() == usuario.getId()) {
								this.setAprovacao(aprovacao);
							} else {
								this.getMensagens().mensagemErro(this.traduzir("solitacaoNaoEhParaEsteUsuario"));
								FacesUtil.redirecionar("index.jsf");
							}
						}else{
							this.getMensagens().mensagemErro(this.traduzir("aprovacaoInvalida"));
							FacesUtil.redirecionar("index.jsf");
						}
						carregarAprovacao(aprovacao);
					}
				} else {
					this.getMensagens().mensagemErro(this.traduzir("acaoInvalida"));
					FacesUtil.redirecionar("index.jsf");
				}
			} else {
				this.getMensagens().mensagemErro(this.traduzir("cliqueNovamenteExclusao"));
				FacesUtil.redirecionar("index.jsf");
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			FacesUtil.redirecionar("index.jsf");
		}
	}
	
	public void carregarAprovacao(Aprovacao aprovacao) {
		try {
			String dados = aprovacao.getDados();
			String[] tokens = dados.split(";");

			for (int i = 0; i < tokens.length; ++i) {
				if (tokens[i].equals("id")) {
					this.bem = cadastrarBemEJB.getBemPatrimonial(new Long(tokens[i + 1]));
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

	public void aprovar() {
		try {
			this.aprovacao.setDados("registro;" + this.bem.getNumeroRegistro()+ ";justificativa;" + this.justificativa);
			this.excluirBemEJB.aprovarExclusao(this.bem, this.aprovacao);
			this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
			FacesUtil.redirecionar("index.jsf");
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
		}
	}

	public void negar() {
		try {
			this.aprovacao.setDados("registro;" + this.bem.getNumeroRegistro()
					+ ";justificativa;" + this.justificativa);
			this.excluirBemEJB.negarExclusao(this.bem, this.aprovacao);
			this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
			FacesUtil.redirecionar("index.jsf");
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
		}
	}
	
	public void cancelar() {
		FacesUtil.redirecionar("index.jsf");
	}
	
	public String traduzir(String chave) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, MVControleMemoriaVirtual.bundleName);
		return bundle.getString(chave);
	}

	public Aprovacao getAprovacao() {
		return aprovacao;
	}

	public void setAprovacao(Aprovacao aprovacao) {
		this.aprovacao = aprovacao;
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

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public BemPatrimonial getBem() {
		return bem;
	}

	public void setBem(BemPatrimonial bem) {
		this.bem = bem;
	}
	
}
