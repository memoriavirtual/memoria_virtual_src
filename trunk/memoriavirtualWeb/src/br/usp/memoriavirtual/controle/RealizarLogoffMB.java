package br.usp.memoriavirtual.controle;

import javax.faces.context.FacesContext;

public class RealizarLogoffMB {
	
	public RealizarLogoffMB(){
		
	}
	
	public String realizarLogoff(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "sucesso";
	}

}
