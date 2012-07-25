/**
 * 
 */
package br.usp.memoriavirtual.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author bigmac
 *
 */
@ManagedBean(name = "containerExpansivoMB")
@RequestScoped
public class ContainerExpansivoMB {
 
	/**
	 * 
	 */
	public ContainerExpansivoMB() {
		super();
		this.setEstadoAtual("plift");
	}
	
	/**
	 * @return the estadoAtual
	 */
	public String getEstadoAtual() {
		return estadoAtual;
	}

	/**
	 * @param estadoAtual the estadoAtual to set
	 */
	public void setEstadoAtual(String estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	private String estadoAtual;

	

}
