package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
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
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.StringContainer;

public class CadastrarBemPatrimonialMB implements Serializable, BeanComMidia {

	private static final long serialVersionUID = 4487901192049535944L;
	protected BemPatrimonial bemPatrimonial = new BemPatrimonial();
	protected String instituicao = "";
	protected List<StringContainer> fontesInformacao = new ArrayList<StringContainer>();
	protected List<Titulo> titulos = new ArrayList<Titulo>();
	protected List<Autoria> autorias = new ArrayList<Autoria>();
	protected Producao producao = new Producao();
	protected DisponibilidadeUsoProtecao disponibilidadeUsoProtecao = new DisponibilidadeUsoProtecao();
	protected HistoricoProcedencia historicoProcedencia = new HistoricoProcedencia();
	protected Diagnostico diagnostico = new Diagnostico();
	protected List<Intervencao> intervencoes = new ArrayList<Intervencao>();
	protected List<Pesquisador> pesquisadores = new ArrayList<Pesquisador>();
	protected List<BemPatrimonial> bensRelacionados = new ArrayList<BemPatrimonial>();
	protected ContainerMultimidia containerMultimidia = new ContainerMultimidia();
	protected List<Autor> autores = new ArrayList<Autor>();
	protected Autor autor = new Autor();
	protected ArrayList<Integer> ApresentaMidias = new ArrayList<Integer>();
	protected ArrayList<Multimidia> midias = new ArrayList<Multimidia>();
	protected String assuntos = "";
	protected String descritores = "";
	private String busca = "";
	protected List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaSimplesEJB;

	@EJB
	private CadastrarBemPatrimonialRemote cadastrarBemPatrimonialEJB;

	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;

	@EJB
	private EditarAutorRemote editarAutorEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@ManagedProperty(value = "#{mensagensMB}")
	private MensagensDeErro mensagensMB;

	private boolean especificarUso = true;
	private String usoInput = "";

	public CadastrarBemPatrimonialMB() {
		this.titulos.add(new Titulo());
	}

	public String cadastrarBemPatrimonial() {

		if (this.bemPatrimonial.getTituloPrincipal() != "") {

			this.bemPatrimonial.setHistoricoProcedencia(historicoProcedencia);
			this.bemPatrimonial.setTitulos(titulos);
			this.bemPatrimonial.setAutorias(autorias);
			this.bemPatrimonial.setProducao(producao);
			this.bemPatrimonial
					.setDisponibilidadeUsoProtecao(disponibilidadeUsoProtecao);
			this.bemPatrimonial.setDiagnostico(diagnostico);
			this.bemPatrimonial.setIntervencoes(intervencoes);
			this.bemPatrimonial.setPesquisadores(pesquisadores);
			this.setContainerMultimidia(containerMultimidia);
			this.setBensRelacionados(bensRelacionados);

			try {

				for (Autoria a : this.autorias) {
					Autor autor = this.cadastrarBemPatrimonialEJB
							.recuperarAutor(a.getAutor().getNome());
					a.setAutor(autor);
				}

				Instituicao i = this.cadastrarBemPatrimonialEJB
						.recuperarInstituicao(this.instituicao);
				this.bemPatrimonial.setInstituicao(i);
				Set<Assunto> assuntosSet = new TreeSet<Assunto>();
				String assuntosArray[] = assuntos.split(" ");
				Assunto a = new Assunto();
				for (String s : assuntosArray) {
					a.setAssunto(s);
					assuntosSet.add(a);
				}

				Set<Descritor> descritoresSet = new TreeSet<Descritor>();
				String descritoresArray[] = this.descritores.split(" ");
				Descritor d = new Descritor();
				for (String s : descritoresArray) {
					d.setDescritor(s);
					descritoresSet.add(d);
				}

				this.bemPatrimonial.setAssuntos(assuntosSet);
				this.bemPatrimonial.setDescritores(descritoresSet);

				List<String> fontesInformacaoLista = new ArrayList<String>();
				for (StringContainer s : this.fontesInformacao) {
					fontesInformacaoLista.add(s.getValor());
				}

				this.containerMultimidia.setMultimidia(this.midias);
				this.bemPatrimonial
						.setContainerMultimidia(this.containerMultimidia);

				this.cadastrarBemPatrimonialEJB
						.cadastrarBemPatrimonial(bemPatrimonial);

				this.zerar();

				MensagensDeErro.getSucessMessage("cadastrarBemCadastrado",
						"resultado");

			} catch (ModeloException e) {
				MensagensDeErro
						.getErrorMessage("cadastrarBemErro", "resultado");
				e.printStackTrace();
				return null;
			}
		} else {
			MensagensDeErro.getErrorMessage("cadastrarBemErro", "resultado");
			return null;
		}

		return null;

	}

	public void listarBemPatrimonial(AjaxBehaviorEvent event) {

	}

	public String cancelar() {
		this.zerar();
		return "/restrito/index.jsf";
	}

	public String zerar() {
		bemPatrimonial = null;
		instituicao = "";
		fontesInformacao = null;
		titulos = null;
		autorias = null;
		producao = null;
		disponibilidadeUsoProtecao = null;
		historicoProcedencia = null;
		diagnostico = null;
		intervencoes = null;
		pesquisadores = null;
		bensRelacionados = null;
		containerMultimidia = null;
		autores = null;
		autor = null;
		ApresentaMidias = null;
		midias = null;
		assuntos = "";
		descritores = "";
		busca = "";
		bens = null;
		return null;
	}

	public void usoEspecificar(AjaxBehaviorEvent e) {

	}

	public String adicionarTitulo() {
		this.titulos.add(new Titulo());
		return null;
	}

	public void removerTitulo(Titulo t) {
		this.titulos.remove(t);
	}

	public List<SelectItem> getTiposTitulo() {

		List<SelectItem> tipos = new ArrayList<SelectItem>();

		for (Titulo.TipoTitulo t : Titulo.TipoTitulo.values()) {
			tipos.add(new SelectItem(t.toString(), t.toString()));
		}

		return tipos;
	}

	public List<SelectItem> getTiposBem() {

		List<SelectItem> tipos = new ArrayList<SelectItem>();

		for (BemPatrimonial.TipoDoBemPatrimonial t : BemPatrimonial.TipoDoBemPatrimonial
				.values()) {
			tipos.add(new SelectItem(t.toString(), t.toString()));
		}

		return tipos;
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
				listaInstituicao = excluirInstituicaoEJB
						.listarTodasInstituicoes();
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			for (Instituicao a : listaInstituicao) {
				listaItens.add(new SelectItem(a.getId(), a.getNome()));
			}

		} else {
			try {
				listaInstituicao = this.cadastrarBemPatrimonialEJB
						.listarInstituicao(usuario);
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			for (Instituicao a : listaInstituicao) {
				listaItens.add(new SelectItem(a.getId(), a.getNome()));
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

	public void adicionarAutoria() {
		Autoria autoria = new Autoria();
		Autor autor = new Autor();
		autoria.setAutor(autor);
		this.autorias.add(autoria);
	}

	public List<SelectItem> listarAutores() {
		List<SelectItem> autores = new ArrayList<SelectItem>();
		try {
			List<Autor> autoresLista = this.editarAutorEJB.listarAutores("");
			for (Autor a : autoresLista) {
				autores.add(new SelectItem(new Long(a.getId()).toString(), a
						.getNome() + " " + a.getSobrenome()));
			}
		} catch (Exception e) {
			return null;
		}
		return autores;
	}

	public void selecionarAutoria(Autor autor) {
		this.autor = autor;
	}

	public List<SelectItem> getTiposAutoria() {

		List<SelectItem> tipos = new ArrayList<SelectItem>();

		for (Autoria.TipoAutoria t : Autoria.TipoAutoria.values()) {
			tipos.add(new SelectItem(t, t.toString()));
		}

		return tipos;
	}

	public void adicionarIntervencoes(AjaxBehaviorEvent e) {
		this.intervencoes.add(new Intervencao());
	}

	public void excluirIntervencao(Intervencao i) {
		this.intervencoes.remove(i);
	}

	public void excluirAutoria(Autoria a) {
		this.autorias.remove(a);
	}

	public void adicionarFontesInformacao(AjaxBehaviorEvent e) {
		this.fontesInformacao.add(new StringContainer(""));
	}

	public void removerFontesInformacao(StringContainer s) {
		this.fontesInformacao.remove(s);
	}

	public void adicionarPesquisador(AjaxBehaviorEvent e) {
		this.pesquisadores.add(new Pesquisador());
	}

	public void removerPesquisador(Pesquisador p) {
		this.pesquisadores.remove(p);
	}

	public void listarBensPatrimoniais(AjaxBehaviorEvent e) {
		this.bens.clear();
		try {
			this.bens = realizarBuscaSimplesEJB.buscar(this.busca);
		} catch (Exception ex) {

		}
	}

	public void anexarBemPatrimonial(BemPatrimonial b) {
		this.bensRelacionados.add(b);
	}

	public void removerBemAnexo(BemPatrimonial b) {
		this.bensRelacionados.remove(b);
	}

	// getters e setters
	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	public void setBemPatrimonial(BemPatrimonial bemPatrimonial) {
		this.bemPatrimonial = bemPatrimonial;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getDescritores() {
		return descritores;
	}

	public void setDescritores(String descritores) {
		this.descritores = descritores;
	}

	public String getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(String assuntos) {
		this.assuntos = assuntos;
	}

	public List<StringContainer> getFontesInformacao() {
		return fontesInformacao;
	}

	public void setFontesInformacao(List<StringContainer> fontesInformacao) {
		this.fontesInformacao = fontesInformacao;
	}

	public List<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}

	public List<Autoria> getAutorias() {
		return autorias;
	}

	public void setAutorias(List<Autoria> autorias) {
		this.autorias = autorias;
	}

	public Producao getProducao() {
		return producao;
	}

	public void setProducao(Producao producao) {
		this.producao = producao;
	}

	public DisponibilidadeUsoProtecao getDisponibilidadeUsoProtecao() {
		return disponibilidadeUsoProtecao;
	}

	public void setDisponibilidadeUsoProtecao(
			DisponibilidadeUsoProtecao disponibilidadeUsoProtecao) {
		this.disponibilidadeUsoProtecao = disponibilidadeUsoProtecao;
	}

	public HistoricoProcedencia getHistoricoProcedencia() {
		return historicoProcedencia;
	}

	public void setHistoricoProcedencia(
			HistoricoProcedencia historicoProcedencia) {
		this.historicoProcedencia = historicoProcedencia;
	}

	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}

	public List<Intervencao> getIntervencoes() {
		return intervencoes;
	}

	public void setIntervencoes(List<Intervencao> intervencoes) {
		this.intervencoes = intervencoes;
	}

	public List<Pesquisador> getPesquisadores() {
		return pesquisadores;
	}

	public void setPesquisadores(List<Pesquisador> pesquisadores) {
		this.pesquisadores = pesquisadores;
	}

	public List<BemPatrimonial> getBensRelacionados() {
		return bensRelacionados;
	}

	public void setBensRelacionados(List<BemPatrimonial> bensRelacionados) {
		this.bensRelacionados = bensRelacionados;
	}

	public ContainerMultimidia getContainerMultimidia() {
		return containerMultimidia;
	}

	public void setContainerMultimidia(ContainerMultimidia containerMultimidia) {
		this.containerMultimidia = containerMultimidia;
	}

	public boolean isEspecificarUso() {
		return especificarUso;
	}

	public void setEspecificarUso(boolean especificarUso) {
		this.especificarUso = especificarUso;
	}

	public String getUsoInput() {
		return usoInput;
	}

	public void setUsoInput(String usoInput) {
		this.usoInput = usoInput;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public ArrayList<Integer> getApresentaMidias() {
		return ApresentaMidias;
	}

	public void setApresentaMidias(ArrayList<Integer> apresentaMidias) {
		ApresentaMidias = apresentaMidias;
	}

	public List<Multimidia> recuperaColecaoMidia() {
		return this.midias;
	}

	public void adicionarMidia(Multimidia midia) {
		this.midias.add(midia);

		if ((this.midias.size() % 4) == 1) {
			Integer mult = this.midias.size() - 1;
			this.ApresentaMidias.add(mult);

		}
	}

	public String removeMidia(String midia) {
		Integer i = new Integer(midia);
		return this.removeMidia(i);
	}

	@Override
	public String removeMidia(int index) {
		this.midias.remove(index);

		if (this.midias.size() % 4 == 0) {
			this.ApresentaMidias.remove(this.ApresentaMidias.size() - 1);
		}
		return null;
	}

	@Override
	public String removeMidia(Multimidia midia) {
		this.midias.remove(midia);
		return null;
	}

	public String removeMidia(Long midia) {
		int index = midia.intValue();
		return this.removeMidia(index);
	}

	public boolean isRenderCell(int index) {
		if (this.midias.size() > index) {
			return true;
		}
		return false;
	}

	public ArrayList<Multimidia> getMidias() {
		return midias;
	}

	public void setMidias(ArrayList<Multimidia> midias) {
		this.midias = midias;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public List<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(List<BemPatrimonial> bens) {
		this.bens = bens;
	}

}
