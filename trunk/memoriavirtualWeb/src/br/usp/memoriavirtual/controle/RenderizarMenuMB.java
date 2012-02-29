package br.usp.memoriavirtual.controle;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;

public class RenderizarMenuMB {

	public boolean administrador = false;
	public boolean gerente = false;

	@SuppressWarnings("unchecked")
	public void verificarAcessos(ComponentSystemEvent event) {

		/*
		 * Pega a lista de acessos do usuario na sessao.
		 */
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		List<Acesso> listaAcessos = (List<Acesso>) request.getSession()
				.getAttribute("acessos");

		Usuario usuario = (Usuario) request.getSession()
				.getAttribute("usuario");

		if (usuario.isAdministrador() && usuario.isAtivo()) {
			this.administrador = this.gerente = true;
		} else {

			for (Acesso acesso : listaAcessos) {
				if (acesso.getGrupo().getId().equalsIgnoreCase("Gerente")
						&& acesso.getValidade()) {
					this.gerente = true;
				}
			}
		}
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

}
