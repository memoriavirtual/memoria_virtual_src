package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;

@ManagedBean(name = "excluirBemPatrimonialMB")
@SessionScoped
public class ExcluirBemPatrimonialMB extends EditarBemPatrimonialMB implements Serializable {

	private static final long serialVersionUID = -5120759550692482010L;
	private String id = "";

	@EJB
	private ExcluirBemPatrimonialRemote excluirBemPatrimonialEJB;

	private MensagensMB mensagens;
	private String validade = "1";
	private String justificativa = "";
	private String usuarioAprovador = "";

	public ExcluirBemPatrimonialMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(facesContext.getELContext(), null, "mensagensMB");
	}

	public String selecionar() {
		return this.redirecionar("/restrito/excluirbempatrimonial.jsf", true);

	}

	public String solicitarExclusao() {

		return null;
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

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}

	/**
	 * @param justificativa
	 *           the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	/**
	 * @return the usuarioAprovador
	 */
	public String getUsuarioAprovador() {
		return usuarioAprovador;
	}

	/**
	 * @param usuarioAprovador
	 *           the usuarioAprovador to set
	 */
	public void setUsuarioAprovador(String usuarioAprovador) {
		this.usuarioAprovador = usuarioAprovador;
	}

	/**
	 * Retorna a lista de usuários que serão mostrados no campo "Select"
	 * 
	 * @return
	 */
	public List<SelectItem> getUsuariosAprovadores() {
		List<SelectItem> usuarios = new ArrayList<SelectItem>();

		return usuarios;
	}
}
