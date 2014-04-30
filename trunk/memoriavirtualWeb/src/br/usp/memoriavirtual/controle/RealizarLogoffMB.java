package br.usp.memoriavirtual.controle;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "realizarLogoffMB")
@RequestScoped
public class RealizarLogoffMB implements Serializable {

	private static final long serialVersionUID = 6409587570712439232L;

	public RealizarLogoffMB() {

	}

	public String realizarLogoff() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		request.getSession().invalidate();
		return "logout";
	}

	public String getInstance() {
		return System.getProperty("com.sun.aas.instanceName");
	}
}
