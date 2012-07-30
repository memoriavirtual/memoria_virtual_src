package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirAutorRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@SessionScoped
@ManagedBean(name = "excluirAutorMB")
public class ExcluirAutorMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1769494097935536965L;
	private String nome;
	private List<Autor> autores;
	@EJB
	private ExcluirAutorRemote excluirAutorEJB;
	@EJB
	private EditarAutorRemote editarAutorEJB;
	private Autor autor;

	public ExcluirAutorMB() {

	}

	public void listarAutores(AjaxBehaviorEvent e) {
		this.listarAutores();
	}

	public void listarAutores() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		try {
			this.autores = editarAutorEJB.listarAutores(this.nome);
			Autor autor = new Autor();
			autor.setNome(bundle.getString("listarTodos"));
			this.autores.add(0, autor);
		} catch (ModeloException m) {
			m.printStackTrace();
		}
	}

	public String selecionarAutor(Autor autor) {

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		this.listarAutores();

		if (!autor.getNome().equals(bundle.getString("listarTodos"))) {

			if ((this.autor == null) && (this.autores.size() > 1)) {
				this.autor = this.autores.get(1);
			} else {
				MensagensDeErro
						.getErrorMessage("excluirAutorErro", "resultado");
			}

		} else {
			this.nome = "";
			this.listarAutores();
			this.autores.remove(0);
			return null;
		}

		this.autor = autor;
		this.nome = this.autor.getNome();
		this.autores.clear();
		return null;
	}

	public String excluirAutor() {

		try {
			this.excluirAutorEJB.excluirAutor(this.autor);
		} catch (ModeloException m) {
			MensagensDeErro.getErrorMessage("excluirAutorErro", "resultado");
			m.printStackTrace();
		}

		this.autor = null;
		this.nome = null;
		MensagensDeErro.getSucessMessage("excluirAutorSucesso", "resultado");
		return null;

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

}
