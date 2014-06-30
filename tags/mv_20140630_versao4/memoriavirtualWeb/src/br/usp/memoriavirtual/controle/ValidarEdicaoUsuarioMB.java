package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
			if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")!=null){
				String id = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
				this.aprovacao = this.editarCadastroUsuarioEJB.getAprovacao(id);
	
				Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	
				Date hoje = new Date();
				if (hoje.after(this.aprovacao.getExpiraEm())) {
					this.getMensagens().mensagemErro(FacesUtil.getMessageFromBundle("linkExpirado"));
					FacesUtil.redirecionar("index.jsf");
				} else if (usuario.getId() != this.aprovacao.getAnalista().getId()) {
					this.getMensagens().mensagemErro(FacesUtil.getMessageFromBundle("solitacaoNaoEhParaEsteUsuario"));
					FacesUtil.redirecionar("index.jsf");
				} else if(this.aprovacao.getStatus() != MVModeloStatusAprovacao.aguardando
						|| this.aprovacao.getAcao() != MVModeloAcao.editar_cadastro_usuario){
					this.getMensagens().mensagemErro(FacesUtil.getMessageFromBundle("acaoInvalida"));
					FacesUtil.redirecionar("index.jsf");
				} else{
					this.carregarAprovacao(this.aprovacao);
				}
			}else{
				this.getMensagens().mensagemErro(FacesUtil.getMessageFromBundle("acaoInvalida"));
				FacesUtil.redirecionar("index.jsf");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getMensagens().mensagemErro(FacesUtil.getMessageFromBundle("erroInterno"));
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
