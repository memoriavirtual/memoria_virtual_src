/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;


import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.Autoria.TipoAutoria;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArqueologico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArquitetonico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemNatural;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Diagnostico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.DisponibilidadeUsoProtecao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.HistoricoProcedencia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Intervencao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Pesquisador;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Producao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

/**
 * @author mac
 *
 */
public abstract class GerenciarBemPatrimonial implements Serializable, BeanComMidia {

	@EJB
	protected EditarAutorRemote editarAutorEJB;
	@EJB
	protected CadastrarBemPatrimonialRemote cadastrarBemPatrimonialEJB;
	@EJB
	protected ExcluirInstituicaoRemote excluirInstituicaoEJB;
	@EJB
	protected EditarInstituicaoRemote editarInstituicaoEJB;

	private static final long serialVersionUID = 7413170360811077491L;

	protected SerialHtmlDataTable dataTableIntervencao = new SerialHtmlDataTable();
	protected SerialHtmlDataTable dataTablePesquisador = new SerialHtmlDataTable();
	protected SerialHtmlDataTable dataTableFontesInformacao = new SerialHtmlDataTable();

	protected boolean botaRemoverTitulo = false;

	protected List<Intervencao> intervencoes = new ArrayList<Intervencao>();
	protected List<Pesquisador> pesquisadores = new ArrayList<Pesquisador>();
	protected List<String> fontesInformacao = new ArrayList<String>();
	protected List<GerenciarBemPatrimonial.ApresentaAutoria> apresentaAutorias = new ArrayList<GerenciarBemPatrimonial.ApresentaAutoria>();

	
	protected ArrayList<Multimidia> midias = new ArrayList<Multimidia>();
	protected ArrayList<Integer> ApresentaMidias = new ArrayList<Integer>();
	
	protected BemPatrimonial bemPatrimonial = new BemPatrimonial();

	protected SerialHtmlDataTable dataTableTitulos = new SerialHtmlDataTable();
	protected boolean geralExterno;
	
	protected long id = 0;
	protected String geralNomeInstituicao;
	protected String geralNaturezaBem;
	protected String geralTipoDoBemPatrimonial = "";
	protected String geralNumeroRegistro;
	protected String geralColecao;
	protected String geralComplemento;
	protected String geralLatitude;
	protected String geralLongitude;
	protected List<Titulo> geralTitulos = new ArrayList<Titulo>();

	protected List<Autor> autores = new ArrayList<Autor>();
	protected SerialHtmlDataTable dataTableAutoria = new SerialHtmlDataTable();
	protected boolean cadastrarAutor = false;
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
	protected String areaTotal;
	protected String alturaFachadaFrontal;
	protected String alturaFachadaSuperior;
	protected String largura;
	protected String profundidade;
	protected String alturaDaCumeeira;
	protected String alturaTotal;
	protected String peDireitoTerreo;
	protected String peDireitoTipo;
	protected String comprimento;
	protected String altura;
	protected String conteudo;
	protected String meioDeAcesso;
	protected String estadoConservPreserv;
	protected String estadoPreser;
	protected String estadoConser;
	protected String estadoConservNotas;
	protected Boolean intervencaoDoBem;
	protected String disponibilidadeDoBem;
	protected String condicoesDeAcesso;
	protected String dataDeRetorno;
	protected String condicoesDeReproducao;
	protected String notasUsoAproveitamento;
	protected String protecao;
	protected String instituicaoProtetora;
	protected String legislacaoNprocesso;

	protected String tipoDeAquisicao;
	protected String valorVenalEpocaTransacao;
	protected String dataAquisicaoDocumento;
	protected String documentoDeAquisicao;
	protected String primeiroPropietario;
	protected String historico;
	protected String intrumentoDePesquisa;
	protected String assunto;
	protected String descritores;

	
	
	
	public String salvarBemPatrimonial() {

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		this.validacaoInstituicao();
		this.validacaoTitulo();
		if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {
			try {
				this.bemPatrimonial.setInstituicao(this.editarInstituicaoEJB
						.getInstituicao(geralNomeInstituicao));
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			if(this.geralTipoDoBemPatrimonial == null){
				this.geralTipoDoBemPatrimonial = "";
			}
			if (this.geralTipoDoBemPatrimonial.equalsIgnoreCase(bundle
					.getString("cadastrarBemTipoLista0"))) {
				// System.out.println("arqueologico");
				this.bemPatrimonial = new BemArqueologico(condicaoTopografica,
						sitioDaPaisagem, aguaProxima, possuiVegetacao,
						exposicao, usoAtual, descricaoOutros, descricaoNotas,
						this.areaTotal, this.comprimento, this.altura,
						this.largura, this.profundidade);
				this.bemPatrimonial.setDiagnostico(new Diagnostico(
						this.estadoConservPreserv, this.estadoConservNotas));

			} else if (this.geralTipoDoBemPatrimonial.equalsIgnoreCase(bundle
					.getString("cadastrarBemTipoLista4"))) {
				// System.out.println("edificado");
				this.bemPatrimonial = new BemArquitetonico(condicaoTopografica,
						this.uso, this.numPavimentos, numAmbientes, alcova,
						porao, sotao, descricaoOutros, areaTotal,
						alturaFachadaFrontal, this.alturaFachadaSuperior,
						this.largura, profundidade, alturaTotal,
						peDireitoTerreo, peDireitoTipo);
				this.bemPatrimonial.setDiagnostico(new Diagnostico(
						this.estadoPreser, this.estadoConser,
						this.estadoConservNotas));
			} else if (this.geralTipoDoBemPatrimonial.equalsIgnoreCase(bundle
					.getString("cadastrarBemTipoLista6"))) {
				// System.out.println("natural");
				this.bemPatrimonial = new BemNatural(relevo,
						caracteristicasAntropico, caracteristicasAmbientais);
				this.bemPatrimonial.setDiagnostico(new Diagnostico(
						this.estadoConservPreserv, this.estadoConservNotas));
			} else {
				this.bemPatrimonial.setTipoDoBemPatrimonial(new String(BemPatrimonial.TipoDoBemPatrimonial.NORMAL.name())); 
				this.bemPatrimonial.setDiagnostico(new Diagnostico(
						this.estadoConservPreserv, this.estadoConservNotas));
			}

			// anexando Geral Info

			this.bemPatrimonial.setTitulos(geralTitulos);
			this.bemPatrimonial.setColecao(geralColecao);
			this.bemPatrimonial.setExterno(geralExterno);
			this.bemPatrimonial.setLatitude(geralLatitude);
			this.bemPatrimonial.setNumeroDeRegistro(this.geralNumeroRegistro);
			this.bemPatrimonial.setLongitude(geralLongitude);
			this.bemPatrimonial.setComplemento(geralComplemento);
			// fim Geral info

			// anexando autorias
			for (int i = 0; i < this.autorias.size(); i++) {
				this.autorias.get(i).setBemPatrimonial(this.bemPatrimonial);
				this.autorias.get(i)
						.setTipoAutoria(
								this.getEnumTipoAutoria(this.apresentaAutorias
										.get(i).tipoAutoria));
			}
			this.bemPatrimonial.setAutorias(autorias);
			// fim autorias
			// anexando produção

			this.bemPatrimonial.setProducao(new Producao(this.producaoLocal,
					this.producaoAno, this.producaoEdicao,
					this.producaoOutrasRes));
			// fim anexando produção
			// System.out.println("normal");
			// anexando descrição
			this.bemPatrimonial
					.setCaracteristicasFisTecExec(this.caracteristicasFisicas);

			// fim anexando descrição

			// anexando Intervencao e diagnostico
			this.bemPatrimonial.setIntervencoes(intervencoes);
			this.bemPatrimonial.setDiagnostico(new Diagnostico(
					this.estadoPreser, this.estadoConser,
					this.estadoConservNotas));
			// fim anexando intervencao e diagnostico
			// anexando Disponibilidade Uso e Protecão

			this.bemPatrimonial
					.setDisponibilidadeUsoProtecao(new DisponibilidadeUsoProtecao(
							this.disponibilidadeDoBem, this.condicoesDeAcesso,
							this.condicoesDeReproducao, this.dataDeRetorno,
							this.notasUsoAproveitamento, this.protecao,
							this.legislacaoNprocesso, this.instituicaoProtetora));
			// Fim anexando Disponibilidade Uso e Protecão
			// anexado historio e procerdencia

			this.bemPatrimonial
					.setHisttoricoProcedencia(new HistoricoProcedencia(
							this.tipoDeAquisicao,
							this.valorVenalEpocaTransacao,
							this.documentoDeAquisicao,
							this.primeiroPropietario, historico,
							this.intrumentoDePesquisa));

			// fim anexando historico e procedencia

			// anexando assuntos
			List<Assunto> assun = new ArrayList<Assunto>();
			
			String[] a = this.assunto.split(" ");
			for (int i = 0; i < a.length; i++) {
				assun.add(new Assunto());
				assun.get(i).setAssunto(a[i]);
			}
			this.bemPatrimonial.setAssuntos(new TreeSet<Assunto>(assun));
			// fim assuntos

			// anexando descritores
			List<Descritor> descr = new ArrayList<Descritor>();
			String[] b = this.descritores.split(" ");
			for (int i = 0; i < b.length; i++) {
				descr.add(new Descritor());
				descr.get(i).setDescritor(b[i]);
			}
			this.bemPatrimonial.setDescritores(new TreeSet<Descritor>(descr));
			// fim descritores

			// adcionando fontes de informação
			this.bemPatrimonial.setFontesInformacao(this.fontesInformacao);
			// fim adcionando fontes de informação
			this.bemPatrimonial.setPesquisadores(this.pesquisadores);
			ContainerMultimidia c = new ContainerMultimidia();
			for(Multimidia i : midias){
				c.addMultimidia(i);
			}
			
			this.bemPatrimonial.setId(id);
			this.bemPatrimonial.setContainerMultimidia(c);
			try {
				this.cadastrarBemPatrimonialEJB
						.salvarBemPatrimonial(this.bemPatrimonial);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("cadastrarBemInstituicaoErro",
					 "resultado");
				e.printStackTrace();
				return null;
			}

//			MensagensDeErro.getSucessMessage("cadastrarBemCadastrado",
//					"resultado");
		} else {
			// MensagensDeErro.getErrorMessage("cadastrarBemInstituicaoErro",
			// "resultado");
		}
		return null;

	}
	
	
	

	@Override
	public List<Multimidia> recuperaColecaoMidia() {
		return this.midias;
	}

	public void adicionarMidia (Multimidia midia) {
		this.midias.add( midia);
		
		if((this.midias.size() % 4 ) == 1  ){
			Integer mult = this.midias.size() -1;
			this.ApresentaMidias.add(mult);
			
		}
	}

	public ArrayList<Multimidia> getMidias() {
		return midias;
	}

	public void setMidias(ArrayList<Multimidia> midias) {
		this.midias = midias;
	}

	public String removeMidia(String midia) {
		return null;
	}

	@Override
	public String removeMidia(int index) {
		this.midias.remove(index);
		
		if(this.midias.size() % 4  == 0){
			this.ApresentaMidias.remove(this.ApresentaMidias.size() -1);
		} 
		return null;
	}
	
	@Override
	public String removeMidia(Multimidia midia) {
		this.midias.remove(midia);
		return null;
	}
	
	protected String getTipoAutoria(Autoria.TipoAutoria a ) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		String valor = new String();

			valor = bundle.getString("cadastrarAutorListaTipoAutoria" + (a.ordinal() + 1));


		return valor;
	}
	
	public List<SelectItem> getTiposAutoria() {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		List<SelectItem> tipos = new ArrayList<SelectItem>();

		for (int i = 0; i < 20; i++) {
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

	public void fonteInformacaoAdd(AjaxBehaviorEvent event) {
		this.fontesInformacao.add(new String());
	}

	public void intervencoesAdd(AjaxBehaviorEvent event) {
		this.intervencoes.add(new Intervencao());
	}

	public void adicionarPesquisador(AjaxBehaviorEvent event) {
		this.pesquisadores.add(new Pesquisador());

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

	public void excluirFonteInformacao(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		String[] list = event.getComponent().getClientId().split(":");
		Integer index = new Integer(list[2]);

		this.fontesInformacao.remove((int) index);

		this.dataTableFontesInformacao.processUpdates(context);
	}

	public void excluirPesquisador(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		String[] list = event.getComponent().getClientId().split(":");
		Integer index = new Integer(list[2]);

		this.pesquisadores.remove((int) index);

		this.dataTablePesquisador.processUpdates(context);
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

	protected Autoria.TipoAutoria getEnumTipoAutoria(String a) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		int i;
		for (i = 1; i <= 19; i++) {

			if (bundle.getString("cadastrarAutorListaTipoAutoria" + i)
					.equalsIgnoreCase(a)) {
				break;
			}
		}
		switch (i) {
		case 1:
			return TipoAutoria.COAUTOR;

		case 2:
			return TipoAutoria.ORGANIZADOR;

		case 3:
			return TipoAutoria.TRADUTOR;

		case 4:

			return TipoAutoria.EDITOR;
		case 5:

			return TipoAutoria.DIRETOR;
		case 6:

			return TipoAutoria.PREFACIADOR;
		case 7:

			return TipoAutoria.COORDENADOR;
		case 8:

			return TipoAutoria.COMPILADOR;
		case 9:

			return TipoAutoria.ILUSTRADOR;
		case 10:

			return TipoAutoria.ENTREVISTADO;
		case 11:

			return TipoAutoria.AUTOR_INSTITUCIONAL;
		case 12:

			return TipoAutoria.ENTIDADE_PRODUTORA;
		case 13:

			return TipoAutoria.AGENCIA;
		case 14:

			return TipoAutoria.ESTUDIO;
		case 15:

			return TipoAutoria.FOTOGRAFO;
		case 16:

			return TipoAutoria.FIGURINISTA;
		case 17:

			return TipoAutoria.FABRICANTE;
		case 18:

			return TipoAutoria.PALESTRANTE;
		case 19:

			return TipoAutoria.AUTOR;
		default:

			return TipoAutoria.AUTOR;
		}

	}

	public boolean isBotaRemoverTitulo() {
		return botaRemoverTitulo;
	}

	public void setBotaRemoverTitulo(boolean botaRemoverTitulo) {
		this.botaRemoverTitulo = botaRemoverTitulo;
	}

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

	public List<GerenciarBemPatrimonial.ApresentaAutoria> getApresentaAutorias() {
		return apresentaAutorias;
	}

	public void setApresentaAutorias(
			List<GerenciarBemPatrimonial.ApresentaAutoria> apresentaAutorias) {
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

	public String getAreaTotal() {
		return areaTotal;
	}

	public void setAreaTotal(String areaTotal) {
		this.areaTotal = areaTotal;
	}

	public String getAlturaFachadaFrontal() {
		return alturaFachadaFrontal;
	}

	public void setAlturaFachadaFrontal(String alturaFachadaFrontal) {
		this.alturaFachadaFrontal = alturaFachadaFrontal;
	}

	public String getAlturaFachadaSuperior() {
		return alturaFachadaSuperior;
	}

	public void setAlturaFachadaSuperior(String alturaFachadaSuperior) {
		this.alturaFachadaSuperior = alturaFachadaSuperior;
	}

	public String getLargura() {
		return largura;
	}

	public void setLargura(String largura) {
		this.largura = largura;
	}

	public String getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(String profundidade) {
		this.profundidade = profundidade;
	}

	public String getAlturaDaCumeeira() {
		return alturaDaCumeeira;
	}

	public void setAlturaDaCumeeira(String alturaDaCumeeira) {
		this.alturaDaCumeeira = alturaDaCumeeira;
	}

	public String getAlturaTotal() {
		return alturaTotal;
	}

	public void setAlturaTotal(String alturaTotal) {
		this.alturaTotal = alturaTotal;
	}

	public String getPeDireitoTerreo() {
		return peDireitoTerreo;
	}

	public void setPeDireitoTerreo(String peDireitoTerreo) {
		this.peDireitoTerreo = peDireitoTerreo;
	}

	public String getPeDireitoTipo() {
		return peDireitoTipo;
	}

	public void setPeDireitoTipo(String peDireitoTipo) {
		this.peDireitoTipo = peDireitoTipo;
	}

	public String getComprimento() {
		return comprimento;
	}

	public void setComprimento(String comprimento) {
		this.comprimento = comprimento;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
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

	public List<Pesquisador> getPesquisadores() {
		return pesquisadores;
	}

	public void setPesquisadores(List<Pesquisador> pesquisadores) {
		this.pesquisadores = pesquisadores;
	}

	public SerialHtmlDataTable getDataTablePesquisador() {
		return dataTablePesquisador;
	}

	public void setDataTablePesquisador(SerialHtmlDataTable dataTablePesquisador) {
		this.dataTablePesquisador = dataTablePesquisador;
	}

	public String getDisponibilidadeDoBem() {
		return disponibilidadeDoBem;
	}

	public void setDisponibilidadeDoBem(String disponibilidadeDoBem) {
		this.disponibilidadeDoBem = disponibilidadeDoBem;
	}

	public String getCondicoesDeAcesso() {
		return condicoesDeAcesso;
	}

	public void setCondicoesDeAcesso(String condicoesDeAcesso) {
		this.condicoesDeAcesso = condicoesDeAcesso;
	}

	public String getDataDeRetorno() {
		return dataDeRetorno;
	}

	public void setDataDeRetorno(String dataDeRetorno) {
		this.dataDeRetorno = dataDeRetorno;
	}

	public String getNotasUsoAproveitamento() {
		return notasUsoAproveitamento;
	}

	public void setNotasUsoAproveitamento(String notasUsoAproveitamento) {
		this.notasUsoAproveitamento = notasUsoAproveitamento;
	}

	public String getProtecao() {
		return protecao;
	}

	public void setProtecao(String protecao) {
		this.protecao = protecao;
	}

	public String getInstituicaoProtetora() {
		return instituicaoProtetora;
	}

	public void setInstituicaoProtetora(String instituicaoProtetora) {
		this.instituicaoProtetora = instituicaoProtetora;
	}

	public String getLegislacaoNprocesso() {
		return legislacaoNprocesso;
	}

	public void setLegislacaoNprocesso(String legislacaoNprocesso) {
		this.legislacaoNprocesso = legislacaoNprocesso;
	}

	public String getCondicoesDeReproducao() {
		return condicoesDeReproducao;
	}

	public void setCondicoesDeReproducao(String condicoesDeReproducao) {
		this.condicoesDeReproducao = condicoesDeReproducao;
	}

	public String getTipoDeAquisicao() {
		return tipoDeAquisicao;
	}

	public void setTipoDeAquisicao(String tipoDeAquisicao) {
		this.tipoDeAquisicao = tipoDeAquisicao;
	}

	public String getValorVenalEpocaTransacao() {
		return valorVenalEpocaTransacao;
	}

	public void setValorVenalEpocaTransacao(String valorVenalEpocaTransacao) {
		this.valorVenalEpocaTransacao = valorVenalEpocaTransacao;
	}

	public String getDataAquisicaoDocumento() {
		return dataAquisicaoDocumento;
	}

	public void setDataAquisicaoDocumento(String dataAquisicaoDocumento) {
		this.dataAquisicaoDocumento = dataAquisicaoDocumento;
	}

	public String getDocumentoDeAquisicao() {
		return documentoDeAquisicao;
	}

	public void setDocumentoDeAquisicao(String documentoDeAquisicao) {
		this.documentoDeAquisicao = documentoDeAquisicao;
	}

	public String getPrimeiroPropietario() {
		return primeiroPropietario;
	}

	public void setPrimeiroPropietario(String primeiroPropietario) {
		this.primeiroPropietario = primeiroPropietario;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getIntrumentoDePesquisa() {
		return intrumentoDePesquisa;
	}

	public void setIntrumentoDePesquisa(String intrumentoDePesquisa) {
		this.intrumentoDePesquisa = intrumentoDePesquisa;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getDescritores() {
		return descritores;
	}

	public void setDescritores(String descritores) {
		this.descritores = descritores;
	}

	public SerialHtmlDataTable getDataTableFontesInformacao() {
		return dataTableFontesInformacao;
	}

	public void setDataTableFontesInformacao(
			SerialHtmlDataTable dataTableFontesInformacao) {
		this.dataTableFontesInformacao = dataTableFontesInformacao;
	}

	public List<String> getFontesInformacao() {
		return fontesInformacao;
	}

	public void setFontesInformacao(List<String> fontesInformacao) {
		this.fontesInformacao = fontesInformacao;
	}

	public class ApresentaAutoria implements Serializable {
		/**
		 * 
		 */
		protected static final long serialVersionUID = 1L;
		protected String nomeAutor = "";
		protected String tipoAutoria = "";

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
		protected static final long serialVersionUID = 1L;

	}


	public ArrayList<Integer> getApresentaMidias() {
		return ApresentaMidias;
	}

	public void setApresentaMidias(ArrayList<Integer> apresentaMidias) {
		ApresentaMidias = apresentaMidias;
	}
	public boolean  isRenderCell(int index) {
		if(this.midias.size() > index ){
			return true;
		}
		return false;
	}
}
