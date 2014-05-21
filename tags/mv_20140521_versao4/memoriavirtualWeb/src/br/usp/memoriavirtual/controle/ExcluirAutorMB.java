package br.usp.memoriavirtual.controle;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.entidades.Autor;
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
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
	}

	public String excluir() {
		try {
			Autor autor = this.editarAutorEJB.getAutor(new Long(this.id));
			this.excluirAutorEJB.excluirAutor(autor);
			this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
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
