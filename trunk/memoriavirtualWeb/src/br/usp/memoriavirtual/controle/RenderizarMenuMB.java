package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.List;

import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;

@ManagedBean(name = "renderizarMenuMB")
@RequestScoped
public class RenderizarMenuMB implements Serializable {

	private static final long serialVersionUID = -3767548433370852588L;
	private boolean administrador = false;
	private boolean gerente = false;
	private boolean revisor = false;
	
	
	private CadastrarBemPatrimonialMB beanCadastroBem;
	
	@SuppressWarnings("unchecked")
	public void verificarAcessos(ComponentSystemEvent event) {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		List<Acesso> listaAcessos = (List<Acesso>) request.getSession()
				.getAttribute("acessos");

		Usuario usuario = (Usuario) request.getSession()
				.getAttribute("usuario");

		if (usuario.isAdministrador() && usuario.isAtivo()) {
			this.administrador = this.gerente = this.revisor = true;
		} else {

			for (Acesso acesso : listaAcessos) {
				if (acesso.getGrupo().getId().equalsIgnoreCase("GERENTE") && acesso.getValidade()) {
					this.gerente = true;
				}
				if (acesso.getGrupo().getId().equalsIgnoreCase("REVISOR") && acesso.getValidade()) {
					this.revisor = true;
				}
			}
		}
	}
	
	public String cadastrarBemAction(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.beanCadastroBem = (CadastrarBemPatrimonialMB) resolver.getValue(facesContext.getELContext(), null, "cadastrarBemPatrimonialMB");
		beanCadastroBem.limpar();
		return "cadastrarbempatrimonial.jsf";
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public boolean isGerente() {
		return gerente;
	}

	public void setGerente(boolean gerente) {
		this.gerente = gerente;
	}

	public boolean isRevisor() {
		return revisor;
	}

	public void setRevisor(boolean revisor) {
		this.revisor = revisor;
	}

}
