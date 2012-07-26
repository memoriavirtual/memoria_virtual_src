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
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;

/**
 * @author bigmac
 *
 */
@ManagedBean(name = "cadastrarBemPatrimonialMB")
@SessionScoped
public class CadastrarBemPatrimonialMB implements BeanComMidia, Serializable {

	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7413170360811077491L;
	
	
	
	
	
	
	
	protected boolean externo;
	protected String naturezaBem;
	protected String tipoDoBemPatrimonial;
	
	
	

	

	@Override
	public List<Multimidia> recuperaColecaoMidia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void adicionarMidia(Multimidia midia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String removeMidia(Multimidia midia) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SelectItem> getTiposBem(){
		List<SelectItem> tipos = new ArrayList<SelectItem>();
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade0")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade1")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade2")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade3")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade4")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade5")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade6")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade7")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade8")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade9")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade10")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade11")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade12")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade13")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade14")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade15")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade16")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade17")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade18")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade19")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade20")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade21")));
		tipos.add(new SelectItem(bundle
				.getString("cadastrarAutorListaAtividade22")));

		return tipos;
		
		
	}
	
	/**
	 * @return the naturezaBem
	 */
	public String getNaturezaBem() {
		return naturezaBem;
	}

	/**
	 * @param naturezaBem the naturezaBem to set
	 */
	public void setNaturezaBem(String naturezaBem) {
		this.naturezaBem = naturezaBem;
	}
	/**
	 * @return the externo
	 */
	public boolean isExterno() {
		return externo;
	}

	/**
	 * @param externo the externo to set
	 */
	public void setExterno(boolean externo) {
		this.externo = externo;
	}

	/**
	 * @return the tipoDoBemPatrimonial
	 */
	public String getTipoDoBemPatrimonial() {
		return tipoDoBemPatrimonial;
	}

	/**
	 * @param tipoDoBemPatrimonial the tipoDoBemPatrimonial to set
	 */
	public void setTipoDoBemPatrimonial(String tipoDoBemPatrimonial) {
		this.tipoDoBemPatrimonial = tipoDoBemPatrimonial;
	}

		
	
}
