package br.usp.memoriavirtual.controle;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirAutorRemote;

@ManagedBean(name = "excluirAutorMB")
@SessionScoped
public class ExcluirAutorMB extends EditarAutorMB implements Serializable {

	private static final long serialVersionUID = -1769494097935536965L;

	@EJB
	private ExcluirAutorRemote excluirAutorEJB;
	@EJB
	private EditarAutorRemote editarAutorEJB;

	private MensagensMB mensagens;

	public ExcluirAutorMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(facesContext.getELContext(), null, "mensagensMB");
	}
	
	@Override
	public String selecionarAutor() {
		if(id.isEmpty()){
			this.getMensagens().mensagemErro(this.traduzir("mensagemErroExcluirAutorEmBranco"));
			return null;
		}
		try {
			this.autor = this.editarAutorEJB.getAutor(new Long(this.id));
			return "excluirautor.jsf";
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String voltar(){
		return "selecionarautorexclusao.jsf";
	}
	
	public String excluir() {
		try {
			this.excluirAutorEJB.excluirAutor(this.autor);
			this.getMensagens().mensagemSucesso(this.traduzir("autorExcluido"));
			return this.redirecionar("/restrito/index.jsf", true);
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}
}
