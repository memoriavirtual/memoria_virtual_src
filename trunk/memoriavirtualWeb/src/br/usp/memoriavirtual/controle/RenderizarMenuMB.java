package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Acesso;

public class RenderizarMenuMB {
	
	public boolean administrador = false;
	public boolean catalogador = false;

	
	public void verificarAcessos(ComponentSystemEvent event){
		
		/*
		 * Pega a lista de acessos do usuario na sessao.
		 */
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		List lista = (List) request.getSession().getAttribute("acesso");

		/*
		 * Pega o Id de todos os grupos que o usuario pertence e
		 * coloca em uma lista de strings para facilitar a verificação
		 * dos niveis que o usuario pode acessar.
		 */
		ArrayList<String> listaNome = new ArrayList<String>();
		for(Object o:lista){
			Acesso acesso = (Acesso)o;
			listaNome.add(acesso.getGrupo().getId());
		}
		
		/*
		 * Verifica quais niveis de acesso o usuario possui em pelo menos uma
		 * instituicao e seta a respectiva variavel usada no momento de renderizar
		 * o menu.
		 */
		if(listaNome.contains("Administrador")){
			this.administrador = true;
		}
		
		if(listaNome.contains("Catalogador")){
			this.catalogador = true;
		}

	
	  }	

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public boolean isCatalogador() {
		return catalogador;
	}

	public void setCatalogador(boolean catalogador) {
		this.catalogador = catalogador;
	}
	
	
	
	
}
