package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.FacesUtil;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MVModeloAcao;
import br.usp.memoriavirtual.utils.MVModeloStatusAprovacao;

@ManagedBean(name = "validarEdicaoUsuarioMB")
@ViewScoped
public class ValidarEdicaoUsuarioMB implements Serializable{

	private static final long serialVersionUID = 2339766005474944552L;
	
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	
	@EJB
	private EditarCadastroUsuarioRemote editarCadastroUsuarioEJB;
	
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;
	
	private MensagensMB mensagens;
	private Aprovacao aprovacao;
	private Usuario solicitante;
	private Usuario usuario;
	private String justificativa;
	
	public ValidarEdicaoUsuarioMB(){
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
						if(aprovacao.getAcao() != MVModeloAcao.editar_cadastro_usuario){
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
		this.aprovacao = aprovacao;
		try {
			String[] dados = aprovacao.getDados().split(";");
			List<Acesso> acessos = new ArrayList<Acesso>();

			for (int i = 0; i < dados.length; ++i) {
				if (dados[i].equals("id")) {
					this.usuario = this.memoriaVirtualEJB
							.getUsuario(dados[i + 1]);
				}
				if (dados[i].equals("administrador")) {
					this.usuario.setAdministrador(true);
				}
				if (dados[i].equals("grupo")
						&& dados[i + 2].equals("instituicao")) {
					this.usuario.setAdministrador(false);
					Grupo grupo = new Grupo(dados[i + 1]);
					Instituicao instituicao = this.editarInstituicaoEJB
							.getInstituicao(new Long(dados[i + 3]).longValue());
					Acesso a = new Acesso();
					a.setUsuario(this.usuario);
					a.setGrupo(grupo);
					a.setInstituicao(instituicao);
					acessos.add(a);
				}
				if (dados[i].equals("justificativa")) {
					this.justificativa = dados[i + 1];
				}
			}
			if (!this.usuario.isAdministrador()) {
				this.usuario.setAcessos(acessos);
			}
		} catch (Exception e) {
			this.getMensagens().mensagemErro(FacesUtil.getMessageFromBundle("erroInterno"));
			e.printStackTrace();
		}
	}

	public void aprovar() {
		try {
			this.aprovacao.setStatus(MVModeloStatusAprovacao.aprovada);
			this.aprovacao.setAlteradaEm(new Date());
			String dados = "nome;" + this.usuario.getNomeCompleto();

			if (this.usuario.isAdministrador()) {
				dados += ";administrador";
			} else {
				for (Acesso a : this.usuario.getAcessos()) {
					dados += ";grupo;" + a.getGrupo().getId() + ";instituicao;"
							+ a.getInstituicao().getNome();
				}
			}
			dados += ";justificativa;" + this.justificativa;
			aprovacao.setDados(dados);
			this.editarCadastroUsuarioEJB.aprovar(this.usuario, this.aprovacao);
			this.getMensagens().mensagemSucesso(FacesUtil.getMessageFromBundle("sucesso"));
			FacesUtil.redirecionar("index.jsf");
		} catch (Exception e) {
			this.getMensagens().mensagemErro(FacesUtil.getMessageFromBundle("erroInterno"));
			e.printStackTrace();
		}
	}

	public void negar() {
		try {
			this.aprovacao.setStatus(MVModeloStatusAprovacao.negada);
			this.aprovacao.setAlteradaEm(new Date());
			String dados = "nome;" + this.usuario.getNomeCompleto();

			if (this.usuario.isAdministrador()) {
				dados += ";administrador";
			} else {
				for (Acesso a : this.usuario.getAcessos()) {
					dados += ";grupo;" + a.getGrupo().getId() + ";instituicao;"
							+ a.getInstituicao().getNome();
				}
			}
			dados += ";justificativa;" + this.justificativa;
			aprovacao.setDados(dados);
			this.editarCadastroUsuarioEJB.negar(this.aprovacao);
			this.getMensagens().mensagemSucesso(FacesUtil.getMessageFromBundle("sucesso"));
			FacesUtil.redirecionar("index.jsf");
		} catch (Exception e) {
			this.getMensagens().mensagemErro(FacesUtil.getMessageFromBundle("erroInterno"));
			e.printStackTrace();
		}
	}
	
	public String traduzir(String chave) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, MVControleMemoriaVirtual.bundleName);
		return bundle.getString(chave);
	}
	
	public void cancelar() {
		FacesUtil.redirecionar("index.jsf");
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

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
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
}
