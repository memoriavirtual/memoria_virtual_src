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
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Intervencao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

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
		this.geralTitulos.add(new Titulo());
		this.apresentaAutorias.add(new ApresentaAutoria());
		this.autorias.add(new Autoria());

	}

	@EJB
	private EditarAutorRemote editarAutorEJB;
	@EJB
	private CadastrarBemPatrimonialRemote cadastrarBemPatrimonialEJB;
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;
	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;

	private static final long serialVersionUID = 7413170360811077491L;

	private SerialHtmlDataTable dataTableAutoria = new SerialHtmlDataTable();
	private SerialHtmlDataTable dataTableIntervencao = new SerialHtmlDataTable();
	private boolean cadastrarAutor = false;
	private boolean botaRemoverTitulo = false;
	protected List<Autor> autores = new ArrayList<Autor>();
	protected List<Intervencao> intervencoes = new ArrayList<Intervencao>();
	protected List<CadastrarBemPatrimonialMB.ApresentaAutoria> apresentaAutorias = new ArrayList<CadastrarBemPatrimonialMB.ApresentaAutoria>();

	private BemPatrimonial bemPatrimonial = new BemPatrimonial();

	/*******************************************************************************************************
	 * 
	 * Bloco Informações Gerais
	 * 
	 *******************************************************************************************************/

	private SerialHtmlDataTable dataTableTitulos = new SerialHtmlDataTable();
	protected boolean geralExterno;
	protected String geralNomeInstituicao;
	protected String geralNaturezaBem;
	protected String geralTipoDoBemPatrimonial = "";
	protected String geralNumeroRegistro;
	protected String geralColecao;
	protected String geralComplemento;
	protected String geralLatitude;
	protected String geralLongitude;
	protected List<Titulo> geralTitulos = new ArrayList<Titulo>();

	public boolean isGeralExterno() {
		return geralExterno;
	}

	public void setGeralExterno(boolean externo) {
		this.geralExterno = externo;
	}

	public String getGeralNomeInstituicao() {
		return geralNomeInstituicao;
	}

	public void setGeralNomeInstituicao(String nomeInstituicao) {
		this.geralNomeInstituicao = nomeInstituicao;
	}

	public void setGeralTipoDoBemPatrimonial(String tipoDoBemPatrimonial) {
		this.geralTipoDoBemPatrimonial = tipoDoBemPatrimonial;
	}
	
	public String getGeralTipoDoBemPatrimonial() {
		return geralTipoDoBemPatrimonial;
	}

	public void setGeralNaturezaBem(String geralNaturezaBem) {
		this.geralNaturezaBem = geralNaturezaBem;
	}
	
	public String getGeralNaturezaBem() {
		return geralNaturezaBem;
	}
	
	public void setGeralNumeroRegistro(String numeroRegistro) {
		this.geralNumeroRegistro = numeroRegistro;
	}
	
	public String getGeralNumeroRegistro() {
		return geralNumeroRegistro;
	}


	public String getGeralColecao() {
		return geralColecao;
	}

	public void setGeralColecao(String geralColecao) {
		this.geralColecao = geralColecao;
	}
	
	public String getGeralComplemento() {
		return geralComplemento;
	}

	public void setGeralComplemento(String complemento) {
		this.geralComplemento = complemento;
	}
	
	public String getGeralLongitude() {
		return geralLongitude;
	}

	public void setGeralLongitude(String longitude) {
		this.geralLongitude = longitude;
	}
	

	public String getGeralLatitude() {
		return geralLatitude;
	}

	public void setGeralLatitude(String latitude) {
		this.geralLatitude = latitude;
	}
	
	
	public List<Titulo> getGeralTitulos() {
		return geralTitulos;
	}

	
	public void setGeralTitulos(List<Titulo> titulos) {
		this.geralTitulos = titulos;
	}
	/*******************************************************************************************************
	 * 
	 * FIM Bloco Informações Gerais
	 * 
	 *******************************************************************************************************/
	protected List<Autoria> autorias = new ArrayList<Autoria>();
	protected String producaoLocal;
	protected String producaoAno;
	protected String producaoEdicao;
	protected String producaoOutrasRes;
	protected String caracteristicasFisicas;
	protected String condicaoTopografica;
	protected String uso;
	protected Integer numAmbientes;
	protected Integer numPavimentos;
	protected Boolean alcova;
	protected Boolean porao;
	protected Boolean sotao;
	protected String descricaoOutros;
	protected String relevo;
	protected String caracteristicasAmbientais;
	protected String caracteristicasAntropico;
	protected String sitioDaPaisagem;
	protected String aguaProxima;
	protected String possuiVegetacao;
	protected String exposicao;
	protected String usoAtual;
	protected String descricaoNotas;
	protected String dimensoesEQuantificacoes;
	protected Double areaTotal;
	protected Double alturaFachadaFrontal;
	protected Double alturaFachadaSuperior;
	protected Double largura;
	protected Double profundidade;
	protected Double alturaDaCumeeira;
	protected Double alturaTotal;
	protected Double peDireitoTerreo;
	protected String peDireitoTipo;
	protected Double comprimento;
	protected Double altura;
	protected String conteudo;
	protected String meioDeAcesso;
	protected String estadoConservPreserv;
	protected String estadoPreser;
	protected String estadoConser;
	protected String estadoConservNotas;
	protected Boolean intervencaoDoBem;

	/**
	 * 
	 */
	public String cadastrarBemPatrimonial() {
		this.validacaoInstituicao();
		this.validacaoTitulo();
		if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {
			try {
				this.bemPatrimonial.setInstituicao(this.editarInstituicaoEJB
						.getInstituicao(geralNomeInstituicao));
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			
			//anexando Geral Info
						
			this.bemPatrimonial.setTitulos(geralTitulos);
			this.bemPatrimonial.setColecao(geralColecao);
			this.bemPatrimonial.setExterno(geralExterno);
			this.bemPatrimonial.setLatitude(geralLatitude);
			this.bemPatrimonial.setNumeroDeRegistro(this.geralNumeroRegistro);
			this.bemPatrimonial.setLongitude(geralLongitude);
			this.bemPatrimonial.setComplemento(geralComplemento);
			//fim Geral info
			
			
			
			
			
			
			
			
			
			this.bemPatrimonial.setAutorias(null);
			this.cadastrarBemPatrimonialEJB
					.cadastrarBemPatrimonial(this.bemPatrimonial);
			
			MensagensDeErro.getSucessMessage("cadastrarBemCadastrado",
			"resultado");
		} else {
			// MensagensDeErro.getErrorMessage("cadastrarBemInstituicaoErro",
			// "resultado");
		}
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
		if (this.geralTitulos.size() < this.getTiposTitulo().size() - 1) {
			this.geralTitulos.add(new Titulo());
		}

	}

	public void intervencoesAdd(AjaxBehaviorEvent event) {
		this.intervencoes.add(new Intervencao());
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
		if (this.geralTitulos.size() > 1) {
			this.geralTitulos.remove((int) index);
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

	public void excluirIntervencao(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		String[] list = event.getComponent().getClientId().split(":");
		Integer index = new Integer(list[2]);

		this.intervencoes.remove((int) index);

		this.dataTableIntervencao.processUpdates(context);
	}

	public int classificarBemPatrimonial() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		if (this.geralTipoDoBemPatrimonial == null
				|| this.geralTipoDoBemPatrimonial.equals("")) {
			return 0;
		}
		if (this.geralTipoDoBemPatrimonial.equals(bundle
				.getString("cadastrarBemTipoLista0"))) {
			return 1;
		}
		if (this.geralTipoDoBemPatrimonial.equals(bundle
				.getString("cadastrarBemTipoLista4"))) {
			return 2;
		}
		if (this.geralTipoDoBemPatrimonial.equals(bundle
				.getString("cadastrarBemTipoLista6"))) {
			return 3;
		}
		return 0;
	}

	public List<SelectItem> recuperarInstituicoes() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		List<SelectItem> listaItens = new ArrayList<SelectItem>();
		List<Instituicao> listaInstituicao = null;
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");
		if (usuario.isAdministrador()) {
			try {
				listaInstituicao = this.excluirInstituicaoEJB
						.listarTodasInstituicoes();
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			for (Instituicao a : listaInstituicao) {
				listaItens.add(new SelectItem(
						new String(a.getNome().getBytes())));
			}

		} else {
			try {
				listaInstituicao = this.cadastrarBemPatrimonialEJB
						.listarInstituicao(usuario);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			for (Instituicao a : listaInstituicao) {
				listaItens.add(new SelectItem(
						new String(a.getNome().getBytes())));
			}
		}
		if (!(listaItens.size() == 1)) {
			listaItens.add(
					0,
					new SelectItem(bundle
							.getString("cadastrarBemInstituicaoSelecione")));
		}
		if (listaItens.size() == 0) {
			MensagensDeErro.getErrorMessage("cadastrarBemInstituicaoErro",
					"resultado");
		}
		return listaItens;
	}

	public List<SelectItem> getTiposBem() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		List<SelectItem> tiposBem = new ArrayList<SelectItem>();
		tiposBem.add(new SelectItem(bundle
				.getString("cadastrarBemTipoListaSelecione")));
		if (this.geralNaturezaBem == null) {
			tiposBem.clear();
			return tiposBem;
		}
		if (this.geralNaturezaBem.equalsIgnoreCase(bundle
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

	public void validacaoInstituicao(AjaxBehaviorEvent e) {
		this.validacaoInstituicao();
	}

	public void validacaoInstituicao() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (this.geralNomeInstituicao.equals(bundle
				.getString("cadastrarBemInstituicaoSelecione"))) {
			MensagensDeErro.getErrorMessage("cadastrarBemInstituicaoErro",
					"validacaoInstituicao");

		}
	}

	public void validacaoTitulo(AjaxBehaviorEvent e) {
		this.validacaoTitulo();
	}

	public void validacaoTitulo() {
		if (this.geralTitulos.get(0).getValor().equals("")) {
			MensagensDeErro.getErrorMessage("cadastrarBemTituloErro",
					"resultado");

		}
	}

	

	public boolean isBotaRemoverTitulo() {
		return botaRemoverTitulo;
	}

	public void setBotaRemoverTitulo(boolean botaRemoverTitulo) {
		this.botaRemoverTitulo = botaRemoverTitulo;
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

	public String getCaracteristicasFisicas() {
		return caracteristicasFisicas;
	}

	public void setCaracteristicasFisicas(String caracteristicasFisicas) {
		this.caracteristicasFisicas = caracteristicasFisicas;
	}

	public String getCondicaoTopografica() {
		return condicaoTopografica;
	}

	public void setCondicaoTopografica(String condicaoTopografica) {
		this.condicaoTopografica = condicaoTopografica;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public Integer getNumAmbientes() {
		return numAmbientes;
	}

	public void setNumAmbientes(Integer numAmbientes) {
		this.numAmbientes = numAmbientes;
	}

	public Integer getNumPavimentos() {
		return numPavimentos;
	}

	public void setNumPavimentos(Integer numPavimentos) {
		this.numPavimentos = numPavimentos;
	}

	public Boolean getAlcova() {
		return alcova;
	}

	public void setAlcova(Boolean alcova) {
		this.alcova = alcova;
	}

	public Boolean getPorao() {
		return porao;
	}

	public void setPorao(Boolean porao) {
		this.porao = porao;
	}

	public Boolean getSotao() {
		return sotao;
	}

	public void setSotao(Boolean sotao) {
		this.sotao = sotao;
	}

	public String getDescricaoOutros() {
		return descricaoOutros;
	}

	public void setDescricaoOutros(String descricaoOutros) {
		this.descricaoOutros = descricaoOutros;
	}

	public String getRelevo() {
		return relevo;
	}

	public void setRelevo(String relevo) {
		this.relevo = relevo;
	}

	public String getCaracteristicasAmbientais() {
		return caracteristicasAmbientais;
	}

	public void setCaracteristicasAmbientais(String caracteristicasAmbientais) {
		this.caracteristicasAmbientais = caracteristicasAmbientais;
	}

	public String getCaracteristicasAntropico() {
		return caracteristicasAntropico;
	}

	public void setCaracteristicasAntropico(String caracteristicasAntropico) {
		this.caracteristicasAntropico = caracteristicasAntropico;
	}

	public String getSitioDaPaisagem() {
		return sitioDaPaisagem;
	}

	public void setSitioDaPaisagem(String sitioDaPaisagem) {
		this.sitioDaPaisagem = sitioDaPaisagem;
	}

	public String getAguaProxima() {
		return aguaProxima;
	}

	public void setAguaProxima(String aguaProxima) {
		this.aguaProxima = aguaProxima;
	}

	public String getPossuiVegetacao() {
		return possuiVegetacao;
	}

	public void setPossuiVegetacao(String possuiVegetacao) {
		this.possuiVegetacao = possuiVegetacao;
	}

	public String getExposicao() {
		return exposicao;
	}

	public void setExposicao(String exposicao) {
		this.exposicao = exposicao;
	}

	public String getUsoAtual() {
		return usoAtual;
	}

	public void setUsoAtual(String usoAtual) {
		this.usoAtual = usoAtual;
	}

	public String getDescricaoNotas() {
		return descricaoNotas;
	}

	public void setDescricaoNotas(String descricaoNotas) {
		this.descricaoNotas = descricaoNotas;
	}

	public String getDimensoesEQuantificacoes() {
		return dimensoesEQuantificacoes;
	}

	public void setDimensoesEQuantificacoes(String dimensoesEQuantificacoes) {
		this.dimensoesEQuantificacoes = dimensoesEQuantificacoes;
	}

	public Double getAreaTotal() {
		return areaTotal;
	}

	public void setAreaTotal(Double areaTotal) {
		this.areaTotal = areaTotal;
	}

	public Double getAlturaFachadaFrontal() {
		return alturaFachadaFrontal;
	}

	public void setAlturaFachadaFrontal(Double alturaFachadaFrontal) {
		this.alturaFachadaFrontal = alturaFachadaFrontal;
	}

	public Double getAlturaFachadaSuperior() {
		return alturaFachadaSuperior;
	}

	public void setAlturaFachadaSuperior(Double alturaFachadaSuperior) {
		this.alturaFachadaSuperior = alturaFachadaSuperior;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}

	public Double getAlturaDaCumeeira() {
		return alturaDaCumeeira;
	}

	public void setAlturaDaCumeeira(Double alturaDaCumeeira) {
		this.alturaDaCumeeira = alturaDaCumeeira;
	}

	public Double getAlturaTotal() {
		return alturaTotal;
	}

	public void setAlturaTotal(Double alturaTotal) {
		this.alturaTotal = alturaTotal;
	}

	public Double getPeDireitoTerreo() {
		return peDireitoTerreo;
	}

	public void setPeDireitoTerreo(Double peDireitoTerreo) {
		this.peDireitoTerreo = peDireitoTerreo;
	}

	public String getPeDireitoTipo() {
		return peDireitoTipo;
	}

	public void setPeDireitoTipo(String peDireitoTipo) {
		this.peDireitoTipo = peDireitoTipo;
	}

	public Double getComprimento() {
		return comprimento;
	}

	public void setComprimento(Double comprimento) {
		this.comprimento = comprimento;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getMeioDeAcesso() {
		return meioDeAcesso;
	}

	public void setMeioDeAcesso(String meioDeAcesso) {
		this.meioDeAcesso = meioDeAcesso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Intervencao> getIntervencoes() {
		return intervencoes;
	}

	public void setIntervencoes(List<Intervencao> intervencoes) {
		this.intervencoes = intervencoes;
	}

	public String getEstadoConservPreserv() {
		return estadoConservPreserv;
	}

	public void setEstadoConservPreserv(String estadoConservPreserv) {
		this.estadoConservPreserv = estadoConservPreserv;
	}

	public String getEstadoPreser() {
		return estadoPreser;
	}

	public void setEstadoPreser(String estadoPreser) {
		this.estadoPreser = estadoPreser;
	}

	public String getEstadoConser() {
		return estadoConser;
	}

	public void setEstadoConser(String estadoConser) {
		this.estadoConser = estadoConser;
	}

	public String getEstadoConservNotas() {
		return estadoConservNotas;
	}

	public void setEstadoConservNotas(String estadoConservNotas) {
		this.estadoConservNotas = estadoConservNotas;
	}

	public Boolean getIntervencaoDoBem() {
		return intervencaoDoBem;
	}

	public void setIntervencaoDoBem(Boolean intervencaoDoBem) {
		this.intervencaoDoBem = intervencaoDoBem;
	}

	public SerialHtmlDataTable getDataTableIntervencao() {
		return dataTableIntervencao;
	}

	public void setDataTableIntervencao(SerialHtmlDataTable dataTableIntervencao) {
		this.dataTableIntervencao = dataTableIntervencao;
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
