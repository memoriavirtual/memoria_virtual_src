/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;

/**
 * @author mac
 *
 */
@ManagedBean(name = "editarBemPatrimonialMB")
@SessionScoped
public class EditarBemPatrimonialMB extends CadastrarBemPatrimonialMB implements BeanComMidia, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2482974978856128676L;
	
	
	private String strDeBusca;
	private List<BemPatrimonial> bemPatrimoniais = new ArrayList<BemPatrimonial>();
	private boolean etapa1 = true;
	private boolean etapa2 = false;
	private boolean listarTodos = false;
	
	/**
	 * 
	 */
	public EditarBemPatrimonialMB() {
		
	}
	
	public void listarBemPatrimonial(AjaxBehaviorEvent event) {

		this.listarBemPatrimonial();

	}

	public String selecionarBemPatrimonial() {
		return null;
	}

	
	public String listarBemPatrimonial() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		
		return null;
	}

	
	public String selecionarBemPatrimonial(BemPatrimonial bemPatrimonial) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getStrDeBusca() {
		return strDeBusca;
	}




	public void setStrDeBusca(String strDeBusca) {
		this.strDeBusca = strDeBusca;
	}




	public List<BemPatrimonial> getBemPatrimoniais() {
		return bemPatrimoniais;
	}




	public void setBemPatrimoniais(List<BemPatrimonial> autores) {
		this.bemPatrimoniais = autores;
	}

	
	
	
	

	public boolean isEtapa1() {
		return etapa1;
	}

	public void setEtapa1(boolean etapa1) {
		this.etapa1 = etapa1;
	}

	public boolean isEtapa2() {
		return etapa2;
	}

	public void setEtapa2(boolean etapa2) {
		this.etapa2 = etapa2;
	}

	public boolean isListarTodos() {
		return listarTodos;
	}

	public void setListarTodos(boolean listarTodos) {
		this.listarTodos = listarTodos;
	}

}
