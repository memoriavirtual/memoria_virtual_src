package br.usp.memoriavirtual.controle;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class RealizarLogoffMB {

    public RealizarLogoffMB() {

    }

    public String realizarLogoff() {
	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
		.getRequest();
	
	request.getSession().invalidate();
	return "logout";
    }

}
