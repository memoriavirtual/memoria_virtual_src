package br.usp.memoriavirtual.controle;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;

@ManagedBean(name = "excluirBemPatrimonialMB")
@ViewScoped
public class ExcluirBemPatrimonialMB extends EditarBemPatrimonialMB implements
		Serializable {

	private static final long serialVersionUID = -5120759550692482010L;
	private String id = "";
	
	@EJB
	private ExcluirBemPatrimonialRemote excluirBemPatrimonialEJB;

	private MensagensMB mensagens;

	public ExcluirBemPatrimonialMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
	}

	public String excluir() {

		try {
			this.excluirBemPatrimonialEJB.excluir(new Long(this.id).longValue());
			this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
			return this.redirecionar("/restrito/index.jsf", true);
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String nome) {
		this.id = nome;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}
}
