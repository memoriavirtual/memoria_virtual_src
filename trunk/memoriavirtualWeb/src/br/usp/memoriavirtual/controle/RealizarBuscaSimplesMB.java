package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArqueologico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArquitetonico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemNatural;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "realizarBuscaSimplesMB")
@SessionScoped
public class RealizarBuscaSimplesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4130356176853401265L;
	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;
	private String busca;
	private List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
	private BemPatrimonial bem;
	private BemArqueologico bemArqueologico = null;
	private BemArquitetonico bemArquitetonico = null;
	private BemNatural bemNatural = null;
	private boolean arquitetonico;
	private boolean arqueologico;
	private boolean natural;

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

		return "resultados";
	}

	public String resultado(BemPatrimonial b) {
		this.bem = b;
		this.determinaTipo();

		if (this.bem.getTipoDoBemPatrimonial().equals("BemArqueologico")) {
			try {
				this.bemArqueologico = this.realizarBuscaEJB
						.buscarBemArqueologico(this.bem);
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		}

		if (this.bem.getTipoDoBemPatrimonial().equals("BemArquitetonico")) {
			try {
				this.bemArquitetonico = this.realizarBuscaEJB
						.buscarBemArquitetonico(this.bem);
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		}

		if (this.bem.getTipoDoBemPatrimonial().equals("BemNatural")) {
			try {
				this.bemNatural = this.realizarBuscaEJB
						.buscarBemNatural(this.bem);
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		}

		return "bempatrimonial";

	}

	public void determinaTipo() {

		if (this.bem.getTipoDoBemPatrimonial().equalsIgnoreCase("arqueologico")) {

			this.arqueologico = true;
			this.arquitetonico = false;
			this.natural = false;
			try {
				this.bemArqueologico = realizarBuscaEJB
						.buscarBemArqueologico(bem);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("erro", "resultado");
			}

		} else if (this.bem.getTipoDoBemPatrimonial().equalsIgnoreCase(
				"arquitetonico")) {

			this.arqueologico = false;
			this.arquitetonico = true;
			this.natural = false;
			try {
				this.bemArquitetonico = realizarBuscaEJB
						.buscarBemArquitetonico(bem);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("erro", "resultado");
			}

		} else if (this.bem.getTipoDoBemPatrimonial().equalsIgnoreCase(
				"natural")) {

			this.arqueologico = false;
			this.arquitetonico = false;
			this.natural = true;
			try {
				this.bemNatural = realizarBuscaEJB.buscarBemNatural(bem);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("erro", "resultado");
			}

		}

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

	public BemPatrimonial getBem() {
		return bem;
	}

	public void setBem(BemPatrimonial bem) {
		this.bem = bem;
	}

	public BemArqueologico getBemArqueologico() {
		return bemArqueologico;
	}

	public void setBemArqueologico(BemArqueologico bemArqueologico) {
		this.bemArqueologico = bemArqueologico;
	}

	public BemArquitetonico getBemArquitetonico() {
		return bemArquitetonico;
	}

	public void setBemArquitetonico(BemArquitetonico bemArquitetonico) {
		this.bemArquitetonico = bemArquitetonico;
	}

	public BemNatural getBemNatural() {
		return bemNatural;
	}

	public void setBemNatural(BemNatural bemNatural) {
		this.bemNatural = bemNatural;
	}

	public boolean isArquitetonico() {
		return arquitetonico;
	}

	public void setArquitetonico(boolean arquitetonico) {
		this.arquitetonico = arquitetonico;
	}

	public boolean isArqueologico() {
		return arqueologico;
	}

	public void setArqueologico(boolean arqueologico) {
		this.arqueologico = arqueologico;
	}

	public boolean isNatural() {
		return natural;
	}

	public void setNatural(boolean natural) {
		this.natural = natural;
	}

}
