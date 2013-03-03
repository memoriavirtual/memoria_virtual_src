package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "realizarBuscaSimplesMB")
@RequestScoped
public class RealizarBuscaSimplesMB {

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;
	private String busca;
	private List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();

	public RealizarBuscaSimplesMB() {

	}

	public String buscar() {
		try {
			this.bens = realizarBuscaEJB.buscar(this.busca);
		} catch (Exception e) {
			e.printStackTrace();
			MensagensDeErro.getErrorMessage("realizarBuscaErro", "resultado");
			return null;
		}
		return "sucesso";
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public List<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(List<BemPatrimonial> bens) {
		this.bens = bens;
	}

	public boolean isRendSair() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) context.getExternalContext()
				.getSessionMap().get("usuario");
		if (usuario != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRendLogin() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) context.getExternalContext()
				.getSessionMap().get("usuario");
		if (usuario != null) {
			return false;
		} else {
			return true;
		}
	}

	public String redirecionarLogin() {
		return "login";
	}

}
