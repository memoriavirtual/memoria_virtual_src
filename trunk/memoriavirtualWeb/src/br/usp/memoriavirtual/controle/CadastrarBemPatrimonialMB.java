/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

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
	public CadastrarBemPatrimonialMB() {
		super();
		this.titulos.add(new Titulo());

	}

	private static final long serialVersionUID = 7413170360811077491L;
	//private HtmlDataTable dataTable = new HtmlDataTable();

	protected boolean externo;
	protected String naturezaBem;
	protected String tipoDoBemPatrimonial;
	protected String numeroRegistro;
	protected String colecao;
	protected String complemento;
	protected String latitude;
	protected String longitude;
	protected List<Titulo> titulos = new ArrayList<Titulo>();
	protected boolean botaRemoverTitulo = false;

	/**
	 * 
	 */

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
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		List<SelectItem> tiposTitulo = new ArrayList<SelectItem>();

		tiposTitulo.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTituloSelecione")));
		tiposTitulo.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTitulo0")));
		tiposTitulo.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTitulo1")));
		tiposTitulo.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTitulo2")));
		tiposTitulo.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTitulo3")));
		tiposTitulo.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTitulo4")));
		tiposTitulo.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTitulo5")));
		tiposTitulo.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTitulo6")));
		tiposTitulo.add(new SelectItem(bundle
				.getString("cadastrarBemTipoTitulo7")));

		return tiposTitulo;
	}

	public void adicionarTitulo(AjaxBehaviorEvent event) {
		if (this.titulos.size() < this.getTiposTitulo().size() - 1) {
			this.titulos.add(new Titulo());
		}
	}

	public void setTituloSelect(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		String[] list = event.getComponent().getClientId().split(":");
		Integer index = new Integer(list[2]);
		System.out.println(index);
		if (this.titulos.size() > 1) {
			this.titulos.remove((int) index);
		}
		//this.dataTable.processUpdates(context);
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
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		List<SelectItem> tiposBem = new ArrayList<SelectItem>();
		tiposBem.add(new SelectItem(bundle
				.getString("cadastrarBemTipoListaSelecione")));
		if (this.naturezaBem == null) {
			tiposBem.clear();
			return tiposBem;
		}
		if (this.naturezaBem.equalsIgnoreCase(bundle
				.getString("cadastrarBemMaterial"))) {
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista0")));
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista1")));
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista2")));
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista3")));
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista4")));
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista5")));
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista6")));
		} else {
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista7")));
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista8")));
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista9")));
			tiposBem.add(new SelectItem(bundle
					.getString("cadastrarBemTipoLista10")));
		}
		return tiposBem;

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

	public boolean isBotaRemoverTitulo() {
		return botaRemoverTitulo;
	}

	public void setBotaRemoverTitulo(boolean botaRemoverTitulo) {
		this.botaRemoverTitulo = botaRemoverTitulo;
	}

	public String getColecao() {
		return colecao;
	}

	public void setColecao(String colecao) {
		this.colecao = colecao;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

//	public HtmlDataTable getDataTable() {
//		return dataTable;
//	}
//
//	public void setDataTable(HtmlDataTable dataTable) {
//		this.dataTable = dataTable;
//	}

}
