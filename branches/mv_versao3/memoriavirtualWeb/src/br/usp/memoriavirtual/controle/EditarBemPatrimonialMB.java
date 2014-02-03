package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
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
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.utils.AutoriaUtil;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.StringContainer;

@SessionScoped
public class EditarBemPatrimonialMB extends CadastrarBemPatrimonialMB implements
		Serializable {

	private static final long serialVersionUID = 2482974978856128676L;

	private String nome = "";

	@EJB
	private EditarBemPatrimonialRemote editarBemPatrimonialEJB;

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaSimplesEJB;

	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;

	@EJB
	private CadastrarBemPatrimonialRemote cadastrarBemPatrimonialEJB;

	public EditarBemPatrimonialMB() {
	}

	public String editarBemPatrimonial() {

		if (this.bemPatrimonial.getTituloPrincipal().length() > 0) {
			try {

				this.bemPatrimonial.getFontesInformacao().clear();

				for (StringContainer s : this.fontesInformacao) {
					this.bemPatrimonial.getFontesInformacao().add(s.getValor());
				}

				this.bemPatrimonial.setBensRelacionados(bensRelacionados);

				this.autorias.clear();

				for (AutoriaUtil a : this.autoriasUtil) {

					Autoria autoria = new Autoria();
					Autor autor = this.cadastrarBemPatrimonialEJB
							.recuperarAutor(a.getAutor());
					autoria.setAutor(autor);
					autoria.setTipoAutoria(a.getTipo());
					autoria.setBemPatrimonial(this.bemPatrimonial);
					this.autorias.add(autoria);

				}

				this.bemPatrimonial.setAutorias(this.autorias);

				Instituicao i = this.cadastrarBemPatrimonialEJB
						.recuperarInstituicao(this.instituicao);

				this.bemPatrimonial.setInstituicao(i);

				this.assuntos.trim();
				Set<Assunto> assuntosSet = new TreeSet<Assunto>();
				String assuntosArray[] = this.assuntos.split(" ");

				for (String s : assuntosArray) {
					Assunto a = new Assunto();
					a.setAssunto(s);
					assuntosSet.add(a);
				}
				this.bemPatrimonial.setAssuntos(assuntosSet);

				this.descritores.trim();
				Set<Descritor> descritoresSet = new TreeSet<Descritor>();
				String descritoresArray[] = this.descritores.split(" ");

				for (String s : descritoresArray) {
					Descritor d = new Descritor();
					d.setDescritor(s);
					descritoresSet.add(d);
				}
				this.bemPatrimonial.setDescritores(descritoresSet);

				List<String> fontesInformacaoLista = new ArrayList<String>();
				for (StringContainer s : this.fontesInformacao) {
					if (s.getValor().length() > 0)
						fontesInformacaoLista.add(s.getValor());
				}

				this.bemPatrimonial.setFontesInformacao(fontesInformacaoLista);

				this.containerMultimidia.setMultimidia(this.midias);

				for (Multimidia m : this.midias) {
					m.setContainerMultimidia(containerMultimidia);
				}

				this.bemPatrimonial
						.setContainerMultimidia(this.containerMultimidia);

				this.editarBemPatrimonialEJB
						.editarBemPatrimonial(bemPatrimonial);

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

	public void listarBens() {
		try {
			this.bens = realizarBuscaSimplesEJB.buscar(this.nome);
		} catch (ModeloException e1) {
			e1.printStackTrace();
			MensagensDeErro.getErrorMessage("erro", "resultado");
		}
	}

	public String selecionarBem(BemPatrimonial bem) {

		this.bemPatrimonial = bem;

		this.ApresentaMidias.clear();
		
		if (bem.getContainerMultimidia() != null) {
			if (bem.getContainerMultimidia().getMultimidia() != null) {
				for (Integer i = 0; i < bem.getContainerMultimidia()
						.getMultimidia().size(); ++i) {
					this.ApresentaMidias.add(i);
				}

				this.containerMultimidia = bem.getContainerMultimidia();
				this.midias.clear();
				this.midias
						.addAll(bem.getContainerMultimidia().getMultimidia());
			}
		} else {
			this.containerMultimidia = new ContainerMultimidia();
			this.midias.clear();
		}

		for (String s : bem.getFontesInformacao()) {
			this.fontesInformacao.add(new StringContainer(s));
		}

		Iterator<Assunto> a = bem.getAssuntos().iterator();
		while (a.hasNext()) {
			this.assuntos += (a.next().getAssunto() + " ");
		}

		this.assuntos.trim();

		Iterator<Descritor> d = bem.getDescritores().iterator();
		while (d.hasNext()) {
			this.descritores += (d.next().getDescritor() + " ");
		}

		this.descritores.trim();

		if (this.bemPatrimonial.getProducao() == null)
			this.bemPatrimonial.setProducao(new Producao());

		if (this.bemPatrimonial.getDiagnostico() == null)
			this.bemPatrimonial.setDiagnostico(new Diagnostico());

		if (this.bemPatrimonial.getDisponibilidadeUsoProtecao() == null)
			this.bemPatrimonial
					.setDisponibilidadeUsoProtecao(new DisponibilidadeUsoProtecao());

		if (this.bemPatrimonial.getHistoricoProcedencia() == null)
			this.bemPatrimonial
					.setHistoricoProcedencia(new HistoricoProcedencia());

		this.autoriasUtil.clear();
		for (Autoria au : bem.getAutorias()) {
			this.autoriasUtil.add(new AutoriaUtil(new Long(au.getAutor()
					.getId()).toString(), au.getTipoAutoria()));
		}

		this.nome = "";
		this.bens.clear();

		this.bensRelacionados.addAll(bem.getBensRelacionados());

		return "editarbempatrimonial.jsf";
	}

	public String selecionarBem() {
		try {
			this.listarBens();
			if (this.bens.size() == 1) {
				return this.selecionarBem(this.bens.get(0));
			} else {
				MensagensDeErro.getErrorMessage("erro", "resultado");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MensagensDeErro.getErrorMessage("erro", "resultado");
			return null;
		}
	}

	public String adicionarTitulo() {
		this.bemPatrimonial.getTitulos().add(new Titulo());
		return null;
	}

	public String removerTitulo(Titulo t) {
		this.bemPatrimonial.getTitulos().remove(t);
		return null;
	}

	public String zerar() {
		this.nome = "";
		super.zerar();
		return null;
	}

	public String adicionarIntervencoes() {
		this.bemPatrimonial.getIntervencoes().add(new Intervencao());
		return null;
	}

	public String removerIntervencao(Intervencao i) {
		this.bemPatrimonial.getIntervencoes().remove(i);
		return null;
	}

	public String adicionarPesquisador() {
		this.bemPatrimonial.getPesquisadores().add(new Pesquisador());
		return null;
	}

	public String removerPesquisador(Pesquisador p) {
		this.bemPatrimonial.getPesquisadores().remove(p);
		return null;
	}

	// getters e setters

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	public void setBemPatrimonial(BemPatrimonial bem) {
		this.bemPatrimonial = bem;
	}

	public List<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(List<BemPatrimonial> bens) {
		this.bens = bens;
	}
}