/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

/**
 * @author bigmac
 * 
 */
public class EditarAutorMB extends CadastrarAutorMB {

	@EJB
	private EditarAutorRemote editarAutorEJB;
	private String strDeBusca;
	private List<Autor> autores = new ArrayList<Autor>();
	private boolean etapa1 = true;
	private boolean etapa2 = false;

	/**
	 * Construtor
	 */
	public EditarAutorMB() {
		super();
	}

	/**
	 * M�todo � chamado enquanto as letras são inseridas no campo de busca.
	 */
	public void listarAutores(AjaxBehaviorEvent event) {
		
		
		this.listarAutores();
	}

	public String listarAutores() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		this.autores.clear();

	//	if (!this.strDeBusca.equals("")) {
			try {
				this.autores = this.editarAutorEJB
						.listarAutores(this.strDeBusca);
				Autor autor = new Autor();
				autor.setNome(bundle.getString("listarTodos"));
				this.autores.add(0, autor);
			} catch (ModeloException e) {
				e.printStackTrace();
			}

//			if (this.autores.isEmpty()) {
//				Autor aut = new Autor();
//				aut.setNome(bundle
//						.getString("excluirInstituicaoErrolistavazia"));
//				this.autores.add(aut);
//			}
	//	}
		return null;
	}

	public String selecionarAutor() {
		return null;
	}

	public String selecionarAutor(Autor autor) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (!autor.getNome().equals(
				bundle.getString("listarTodos"))) {
			this.autor = autor;
			this.etapa1 = false;
			this.etapa2 = true;
		}
		else{
			this.strDeBusca = "";
			this.listarAutores();
			this.autores.remove(0);
		}
		return null;
	}

	public String salvarEdicaoAutor() {

		if (this.validateNome() && this.validateSobrenome()
				&& this.validateNascimento() && this.validateObito()
				&& this.validateAtividade()) {

			try {
				this.editarAutorEJB.editarAutor(this.autor);
			} catch (ModeloException e) {
				e.printStackTrace();
				MensagensDeErro
						.getErrorMessage("editarAutorError", "resultado");
			}
			MensagensDeErro.getSucessMessage("editarAutorSucesso", "resultado");
			this.resetEditarAutor();
		}
		return null;
	}

	/**
	 * Volta os atributos a um estado original
	 */
	public String resetEditarAutor() {
		super.resetCadastrarAutor();
		this.strDeBusca = "";
		this.autores.clear();
		this.etapa1 = true;
		this.etapa2 = false;
		return null;
	}

	/**
	 * @return the strBusca
	 */
	public String getStrDeBusca() {
		return strDeBusca;
	}

	/**
	 * @param strBusca
	 *            the strBusca to set
	 */
	public void setStrDeBusca(String strBusca) {
		this.strDeBusca = strBusca;
	}

	/**
	 * @return the autores
	 */
	public List<Autor> getAutores() {
		return autores;
	}

	/**
	 * @return the etapa1
	 */
	public boolean isEtapa1() {
		return etapa1;
	}

	/**
	 * @param etapa1
	 *            the etapa1 to set
	 */
	public void setEtapa1(boolean etapa1) {
		this.etapa1 = etapa1;
	}

	/**
	 * @return the etapa2
	 */
	public boolean isEtapa2() {
		return etapa2;
	}

	/**
	 * @param etapa2
	 *            the etapa2 to set
	 */
	public void setEtapa2(boolean etapa2) {
		this.etapa2 = etapa2;
	}

}
