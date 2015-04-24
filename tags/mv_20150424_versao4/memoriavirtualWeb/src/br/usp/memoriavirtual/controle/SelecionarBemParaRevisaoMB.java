package br.usp.memoriavirtual.controle;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "selecionarBemParaRevisaoMB")
@ViewScoped
public class SelecionarBemParaRevisaoMB implements Serializable {

	private static final long serialVersionUID = -3007846877991463524L;
	
	private String id = "";
	
	public String selecionar(){
		return "revisarbempatrimonial";
	}
	
	public String cancelar(){
		return "index";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
