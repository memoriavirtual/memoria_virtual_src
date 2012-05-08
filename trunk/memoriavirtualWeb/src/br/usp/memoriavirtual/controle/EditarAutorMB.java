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

/**
 * @author bigmac
 *
 */
public class EditarAutorMB {
	
	@EJB
	private EditarAutorRemote editarAutorEJB;
	
	private Autor autor;
	
	private String strDeBusca;
	
	private List<Autor> autores = new ArrayList<Autor>(); 
	
	

	private boolean etapa1 = true;
	private boolean etapa2= false;
	
	/**
	 * Construtor 
	 */
	public EditarAutorMB(){
		super();
	}
	/**
	 * MÈtodo È chamado enquanto as letras s√£o inseridas
	 * no campo de busca.
	 */
	public void listarAutores (AjaxBehaviorEvent event){
		
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, bundleName);
		
		this.autores.clear();
		
		
		try {
			this.autores = this.editarAutorEJB.listarAutores(this.strDeBusca);
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		
		
		if(this.autores.isEmpty()){
			Autor aut = new Autor();
			aut.setNome(bundle.getString("excluirInstituicaoErrolistavazia"));
			this.autores.add(aut);
		}
		return;
	}
	
	public String selecionarAutor(String string){
		
		return null;		
	}
	
	
	/**
	 * @return the strBusca
	 */
	public String getStrDeBusca() {
		return strDeBusca;
	}

	/**
	 * @param strBusca the strBusca to set
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
	 * @return the nome
	 */
	public String getNome() {
		return autor.getNome();
	}

	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return autor.getSobrenome();
	}

	/**
	 * @return the codinome
	 */
	public String getCodinome() {
		return autor.getCodinome();
	}

	/**
	 * @return the nascimento
	 */
	public String getNascimento() {
		return autor.getNascimento();
	}

	/**
	 * @return the obito
	 */
	public String getObito() {
		return autor.getObito();
	}

	/**
	 * @return the atividade
	 */
	public String getAtividade() {
		return autor.getAtividade();
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.autor.setNome(nome);
	}

	/**
	 * @param sobrenome
	 *            the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.autor.setSobrenome(sobrenome);
	}

	/**
	 * @param codinome
	 *            the codinome to set
	 */
	public void setCodinome(String codinome) {
		this.autor.setCodinome(codinome);
	}

	/**
	 * @param nascimento
	 *            the nascimento to set
	 */
	public void setNascimento(String nascimento) {
		this.autor.setNascimento(nascimento);
	}

	/**
	 * @param obito
	 *            the obito to set
	 */
	public void setObito(String obito) {
		this.autor.setObito(obito);
	}

	/**
	 * @param atividade
	 *            the atividade to set
	 */
	public void setAtividade(String atividade) {
		this.autor.setAtividade(atividade);
	}
	
	
	public boolean isEtapa1() {
		return etapa1;
	}

	public boolean isEtapa2() {
		return etapa2;
	}


}
