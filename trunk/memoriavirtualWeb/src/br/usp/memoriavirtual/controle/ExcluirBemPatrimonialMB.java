package br.usp.memoriavirtual.controle;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean
@RequestScoped
public class ExcluirBemPatrimonialMB {

	private String nome = "";
	private BemPatrimonial bemPatrimonial = new BemPatrimonial();
	private ArrayList<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
	@EJB
	private ExcluirBemPatrimonialRemote excluirBemPatrimonialEJB;

	public void listarBensPatrimoniais(AjaxBehaviorEvent e) {
		this.listarBensPatrimoniais();
	}

	public void listarBensPatrimoniais() {
		try {
			this.bens = (ArrayList<BemPatrimonial>) excluirBemPatrimonialEJB
					.listarBensPatrimoniais(this.nome);
		} catch (ModeloException m) {
			MensagensDeErro.getErrorMessage("excluirBemPatrimonialErroBemInexistente", "resultado");
			m.printStackTrace();
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	public void setBemPatrimonial(BemPatrimonial bemPatrimonial) {
		this.bemPatrimonial = bemPatrimonial;
	}

	public ArrayList<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(ArrayList<BemPatrimonial> bens) {
		this.bens = bens;
	}

	public ExcluirBemPatrimonialRemote getExcluirBemPatrimonialEJB() {
		return excluirBemPatrimonialEJB;
	}

	public void setExcluirBemPatrimonialEJB(
			ExcluirBemPatrimonialRemote excluirBemPatrimonialEJB) {
		this.excluirBemPatrimonialEJB = excluirBemPatrimonialEJB;
	}

}
