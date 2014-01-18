package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
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