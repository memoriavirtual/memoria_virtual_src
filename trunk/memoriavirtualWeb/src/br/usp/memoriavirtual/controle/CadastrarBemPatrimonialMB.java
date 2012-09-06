/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;

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
		this.apresentaAutorias.add(new ApresentaAutoria());
		this.autorias.add(new Autoria());

	}

	@EJB
	private EditarAutorRemote editarAutorEJB;

	private static final long serialVersionUID = 7413170360811077491L;
	private SerialHtmlDataTable dataTableTitulos = new SerialHtmlDataTable();
	private SerialHtmlDataTable dataTableAutoria = new SerialHtmlDataTable();
	private boolean cadastrarAutor = false;
	protected boolean botaRemoverTitulo = false;
	protected List<Autor> autores = new ArrayList<Autor>();
	protected List<CadastrarBemPatrimonialMB.ApresentaAutoria> apresentaAutorias = new ArrayList<CadastrarBemPatrimonialMB.ApresentaAutoria>();

	private BemPatrimonial bemPatrimonial = new BemPatrimonial();
	protected boolean externo;
	protected String naturezaBem;
	protected String tipoDoBemPatrimonial;
	protected String numeroRegistro;
	protected String colecao;
	protected String complemento;
	protected String latitude;
	protected String longitude;
	protected List<Titulo> titulos = new ArrayList<Titulo>();
	protected List<Autoria> autorias = new ArrayList<Autoria>();
	protected String producaoLocal;
	protected String producaoAno;
	protected String producaoEdicao;
	protected String producaoOutrasRes;

	/**
	 * 
	 */
	public String cadastrarBemPatrimonial(){
		return null;
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

	public List<SelectItem> getTiposAutoria() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		List<SelectItem> tipos = new ArrayList<SelectItem>();

		for (int i = 0; i < 19; i++) {
			tipos.add(new SelectItem(bundle
					.getString("cadastrarAutorListaTipoAutoria" + i)));
		}

		return tipos;
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

	public void mostrarCadastrarAutor(AjaxBehaviorEvent event) {
		this.cadastrarAutor = true;
	}

	public void adicionarTitulo(AjaxBehaviorEvent event) {
		if (this.titulos.size() < this.getTiposTitulo().size() - 1) {
			this.titulos.add(new Titulo());
		}

	}

	public void adicionarAutoria(AjaxBehaviorEvent event) {
		if (this.autorias.size() < Autoria.TipoAutoria.values().length - 1) {
			this.autorias.add(new Autoria());
			this.apresentaAutorias.add(new ApresentaAutoria());
		}
	}

	public void listarSugestoesAutoresFocus(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		String[] list = event.getComponent().getClientId().split(":");
		Integer index = new Integer(list[2]);
		this.autores.clear();
		if (this.apresentaAutorias.get((int) index).getNomeAutor().equals("")) {
			Autor a = new Autor();
			a.setNome(bundle.getString("listarTodos"));
			this.autores.add(a);

		}
		this.dataTableAutoria.processUpdates(context);

	}

	public void listarSugestoesAutores(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		String[] list = event.getComponent().getClientId().split(":");
		Integer index = new Integer(list[2]);
		if (!this.apresentaAutorias.get((int) index).getNomeAutor().equals("")) {
			try {
				this.autores = this.editarAutorEJB
						.listarAutores(this.apresentaAutorias.get((int) index)
								.getNomeAutor());

				this.dataTableAutoria.processUpdates(context);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
		} else {
			this.listarSugestoesAutoresFocus(event);
		}
	}

	public void excluirTitulo(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		String[] list = event.getComponent().getClientId().split(":");
		Integer index = new Integer(list[2]);
		if (this.titulos.size() > 1) {
			this.titulos.remove((int) index);
		}
		this.dataTableTitulos.processUpdates(context);
	}

	public void selecionarAutoria(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		String[] list = event.getComponent().getClientId().split(":");
		Integer indexAutoria = new Integer(list[2]);
		Integer indexAutor = new Integer(list[4]);
		System.out.println(event.getComponent().getClientId() + " "
				+ indexAutor + " " + indexAutoria);
		if (!this.autores.get(indexAutor).getNome()
				.equals(bundle.getString("listarTodos"))) {

			this.autorias.get(indexAutoria).setAutor(
					(this.autores.get(indexAutor)));
			this.apresentaAutorias
					.get(indexAutoria)
					.setNomeAutor(
							(this.autores.get(indexAutor).getNome() + " " + this.autores
									.get(indexAutor).getSobrenome()));

			this.autores.clear();
		} else {
			try {
				this.autores = this.editarAutorEJB.listarAutores("");
			} catch (ModeloException e) {
				e.printStackTrace();
			}
		}
		this.dataTableAutoria.processUpdates(context);
	}

	public void excluirAutoria(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		String[] list = event.getComponent().getClientId().split(":");
		Integer index = new Integer(list[2]);
		if (this.apresentaAutorias.size() > 1) {
			this.apresentaAutorias.remove((int) index);
		}
		this.dataTableAutoria.processUpdates(context);
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

	public SerialHtmlDataTable getDataTableTitulos() {
		return dataTableTitulos;
	}

	public void setDataTableTitulos(SerialHtmlDataTable dataTableTitulos) {
		this.dataTableTitulos = dataTableTitulos;
	}

	public SerialHtmlDataTable getDataTableAutoria() {
		return dataTableAutoria;
	}

	public void setDataTableAutoria(SerialHtmlDataTable datatableAutoria) {
		this.dataTableAutoria = datatableAutoria;
	}

	public List<Autoria> getAutorias() {
		return autorias;
	}

	public void setAutorias(List<Autoria> autorias) {
		this.autorias = autorias;
	}

	public List<CadastrarBemPatrimonialMB.ApresentaAutoria> getApresentaAutorias() {
		return apresentaAutorias;
	}

	public void setApresentaAutorias(
			List<CadastrarBemPatrimonialMB.ApresentaAutoria> apresentaAutorias) {
		this.apresentaAutorias = apresentaAutorias;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public boolean isCadastrarAutor() {
		return cadastrarAutor;
	}

	public void setCadastrarAutor(boolean cadastrarAutor) {
		this.cadastrarAutor = cadastrarAutor;
	}

	public String getProducaoLocal() {
		return producaoLocal;
	}

	public void setProducaoLocal(String producaoLocal) {
		this.producaoLocal = producaoLocal;
	}

	public String getProducaoAno() {
		return producaoAno;
	}

	public void setProducaoAno(String producaoAno) {
		this.producaoAno = producaoAno;
	}

	public String getProducaoEdicao() {
		return producaoEdicao;
	}

	public void setProducaoEdicao(String producaoEdicao) {
		this.producaoEdicao = producaoEdicao;
	}

	public String getProducaoOutrasRes() {
		return producaoOutrasRes;
	}

	public void setProducaoOutrasRes(String producaoOutrasRes) {
		this.producaoOutrasRes = producaoOutrasRes;
	}

	public class ApresentaAutoria implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String nomeAutor = "";
		private String tipoAutoria = "";

		public String getNomeAutor() {
			return nomeAutor;
		}

		public void setNomeAutor(String nomeAutor) {
			this.nomeAutor = nomeAutor;
		}

		public String getTipoAutoria() {
			return tipoAutoria;
		}

		public void setTipoAutoria(String tipoAutoria) {
			this.tipoAutoria = tipoAutoria;
		}

	}

	public class SerialHtmlDataTable extends HtmlDataTable implements
			Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

}
