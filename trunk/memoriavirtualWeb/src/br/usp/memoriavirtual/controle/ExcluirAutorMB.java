package br.usp.memoriavirtual.controle;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirAutorRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@SessionScoped
@ManagedBean(name = "excluirAutorMB")
public class ExcluirAutorMB {

	private String nome;
	private List<Autor> autores;
	@EJB
	private ExcluirAutorRemote excluirAutorEJB;
	private Autor autor;

	public ExcluirAutorMB() {

	}

	public void listarAutores(AjaxBehaviorEvent e) {
		this.listarAutores();
	}

	public void listarAutores() {
		try {
			this.autores = excluirAutorEJB.listarAutores(this.nome);
		} catch (ModeloException m) {
			m.printStackTrace();
		}
	}

	public String selecionarAutor(Autor autor) {
		this.autor = autor;
		this.nome = this.autor.getNome();
		return null;
	}

	public String excluirAutor() {

		this.listarAutores();

		if (this.autores.isEmpty())
			MensagensDeErro.getErrorMessage("excluirAutorNomeIncorreto",
					"resultado");

		if (this.autores.size() == 1) {
			this.autor = autores.get(0);
		}

		try {
			this.excluirAutorEJB.excluirAutor(this.autor);
		} catch (ModeloException m) {
			MensagensDeErro.getErrorMessage("excluirAutorErro", "resultado");
			m.printStackTrace();
		}

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
