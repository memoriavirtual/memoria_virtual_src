package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "excluirBemPatrimonialMB")
@SessionScoped
public class ExcluirBemPatrimonialMB implements Serializable {

	private static final long serialVersionUID = -5120759550692482010L;
	private String nome = "";
	private BemPatrimonial bemPatrimonial = null;
	private List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
	@EJB
	private ExcluirBemPatrimonialRemote excluirBemPatrimonialEJB;

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;

	public void listarBemPatrimonial(AjaxBehaviorEvent event) {

		this.listarBemPatrimonial();

	}

	public String cancelar() {
		this.nome = "";
		this.bemPatrimonial = null;
		this.bens.clear();

		return "/restrito/index.jsf";
	}

	public String selecionarBem() {
		return null;
	}

	public void listarBemPatrimonialFocus() {

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		this.bens.clear();
		BemPatrimonial b = new BemPatrimonial();
		b.setTituloPrincipal(bundle.getString("listarTodos"));
		this.bens.add(b);
	}

	public String listarBemPatrimonial() {

		this.bens.clear();

		try {
			this.bens = realizarBuscaEJB.buscar(this.nome,1);

		} catch (ModeloException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String selecionarBem(BemPatrimonial bem) {

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		if (bem.getTituloPrincipal().equals(bundle.getString("listarTodos"))) {
			this.nome = "";
			this.listarBemPatrimonial();
			return null;
		}

		this.bemPatrimonial = bem;
		this.nome = bem.getTituloPrincipal();
		this.bens.clear();

		return "sucesso";

	}

	public String excluirBemPatrimonial() {

		try {
			this.bemPatrimonial = this.excluirBemPatrimonialEJB
					.recuperarDados(this.bemPatrimonial);
			this.excluirBemPatrimonialEJB.excluirBem(this.bemPatrimonial);
		} catch (ModeloException m) {
			MensagensDeErro.getErrorMessage(
					"excluirBemPatrimonialErroBemInexistente", "resultado");
			m.printStackTrace();
		}

		MensagensDeErro.getSucessMessage("excluirBemPatrimonialSucesso",
				"resultado");

		return "sucesso";

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

	public List<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(List<BemPatrimonial> bens) {
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
