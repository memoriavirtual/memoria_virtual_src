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

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "excluirBemPatrimonialMB")
@SessionScoped
public class ExcluirBemPatrimonialMB extends EditarBemPatrimonialMB implements Serializable {

	private static final long serialVersionUID = -5120759550692482010L;
	private String id = "";

	@EJB
	private ExcluirBemPatrimonialRemote excluirBemPatrimonialEJB;

	private MensagensMB mensagens;
	private Integer validade = 1;
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
		if(validar()){
			
			
		}
		return null;
	}

	@Override
	public boolean validar(){
		return validarValidade() && validarUsuario() && validarJustificativa();		
	}
	
	public boolean validarValidade(){		
		if(this.validade<1 || this.validade >30){
			MensagensDeErro.getErrorMessage("erroValidadeInvalida", "validacao-validade");
			return false;
		}
		return true;
	}
	
	public boolean validarUsuario(){
		if(usuarioAprovador.equals("disabled"))
			return false;
		else
			return true;
	}
	
	public boolean validarJustificativa(){
		if(justificativa.isEmpty())
			return false;
		else
			return true;
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
	
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}


	public String getUsuarioAprovador() {
		return usuarioAprovador;
	}


	public void setUsuarioAprovador(String usuarioAprovador) {
		this.usuarioAprovador = usuarioAprovador;
	}

	/**
	 * Retorna a lista de usuários que serão mostrados no campo "Select"
	 * 
	 */
	public List<SelectItem> getUsuariosAprovadores() {
		List<SelectItem> usuarios = new ArrayList<SelectItem>();

		Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

		try {
			List<Usuario> listaUsuarios = this.excluirBemPatrimonialEJB.listarUsuariosAprovadores(this.bemPatrimonial.getInstituicao(), usuario);
			for(Usuario u : listaUsuarios){
				usuarios.add(new SelectItem(u.getId(),u.getNomeCompleto()));
			}
		} catch (ModeloException e) {
			e.printStackTrace();
		}

		return usuarios;
	}

	public Integer getValidade() {
		return validade;
	}

	public void setValidade(Integer validade) {
		this.validade = validade;
	}
}
