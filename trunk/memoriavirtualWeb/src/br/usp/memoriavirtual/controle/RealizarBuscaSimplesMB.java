package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.util.ArrayList;
import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name="realizarBuscaSimples")
@RequestScoped
public class RealizarBuscaSimplesMB {
	
	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;
	private String busca;
	private List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
	
	public RealizarBuscaSimplesMB(){
		
	}
	
	public String buscar(){
		try{
			this.bens = realizarBuscaEJB.buscar(this.busca);
		}
		catch(Exception e){
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
	
	

}
