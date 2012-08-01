/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import com.sun.faces.renderkit.html_basic.AjaxBehaviorRenderer;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;

/**
 * @author bigmac
 * 
 */

public class CadastrarBemPatrimonialMB implements BeanComMidia, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7413170360811077491L;
	protected boolean externo;
	protected String naturezaBem;
	protected String tipoDoBemPatrimonial;
	protected String numeroRegistro;
	protected List<Titulo> titulos = new ArrayList<Titulo>();
	protected String inputEscondido = new String();

	/**
	 * 
	 */
	public CadastrarBemPatrimonialMB() {
		super();
		this.titulos.add(new Titulo());
		
	}

	@Override
	public List<Multimidia> recuperaColecaoMidia() {
		return null;
	}

	@Override
	public void adicionarMidia(Multimidia midia) {

	}

	@Override
	public String removeMidia(Multimidia midia) {
		return null;
	}

	public List<SelectItem> getTiposTitulo() {
		List<SelectItem> tipos = new ArrayList<SelectItem>();
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		tipos.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTituloSelecione")));

		tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoTitulo0")));
		tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoTitulo1")));
		tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoTitulo2")));
		tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoTitulo3")));
		tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoTitulo4")));
		tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoTitulo5")));
		tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoTitulo6")));
		tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoTitulo7")));

		return tipos;

	}
	
	public void adicionaTitulo(AjaxBehaviorEvent event){
		this.titulos.add(new Titulo());	
	}
	
	public void removeTitulo(AjaxBehaviorEvent event){
		FacesContext context = FacesContext.getCurrentInstance();
		
		this.titulos.remove(this.titulos.size()-1);
		System.out.println(event.getComponent().getClientId());
	}
	
	
	public String getTextoBotao(Titulo o) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (this.titulos.indexOf(o) > 0) {
			return bundle.getString("cadastrarBemTituloRemover");
		} else
			return bundle.getString("cadastrarBemTituloAdicionar");
	}
	

	public List<SelectItem> getTiposBem() {
		List<SelectItem> tipos = new ArrayList<SelectItem>();
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		tipos.add(new SelectItem(bundle
				.getString("cadastrarBemTipoListaSelecione")));
		if (this.naturezaBem == null) {
			tipos.clear();
			return tipos;
		}
		if (this.naturezaBem.equalsIgnoreCase(bundle
				.getString("cadastrarBemMaterial"))) {
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista0")));
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista1")));
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista2")));
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista3")));
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista4")));
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista5")));
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista6")));
		} else {
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista7")));
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista8")));
			tipos.add(new SelectItem(bundle.getString("cadastrarBemTipoLista9")));
			tipos.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista10")));
		}
		return tipos;

	}

	public String removerTitulo(String a) {
		return null;
	}

	public String removerTitulo(Titulo t) {
		this.titulos.remove(t);
		return null;
	}

	/**
	 * @return the naturezaBem
	 */
	public String getNaturezaBem() {
		return naturezaBem;
	}

	/**
	 * @param naturezaBem
	 *            the naturezaBem to set
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
	 * @param externo
	 *            the externo to set
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
	 * @param tipoDoBemPatrimonial
	 *            the tipoDoBemPatrimonial to set
	 */
	public void setTipoDoBemPatrimonial(String tipoDoBemPatrimonial) {
		this.tipoDoBemPatrimonial = tipoDoBemPatrimonial;
	}

	/**
	 * @return the numeroRegistro
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * @param numeroRegistro
	 *            the numeroRegistro to set
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * @return the titulos
	 */
	public List<Titulo> getTitulos() {
		return titulos;
	}

	/**
	 * @param titulos
	 *            the titulos to set
	 */
	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}

	

	

}
