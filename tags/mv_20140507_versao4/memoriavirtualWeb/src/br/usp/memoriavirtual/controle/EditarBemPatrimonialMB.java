package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;
import br.usp.memoriavirtual.utils.StringContainer;

@ManagedBean(name = "editarBemPatrimonialMB")
@SessionScoped
public class EditarBemPatrimonialMB extends CadastrarBemPatrimonialMB implements
		Serializable {

	private static final long serialVersionUID = 2482974978856128676L;

	private String id = "";

	@EJB
	private EditarBemPatrimonialRemote editarBemPatrimonialEJB;

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaSimplesEJB;

	@EJB
	private ExcluirInstituicaoRemote excluirInstituicaoEJB;

	@EJB
	private CadastrarBemPatrimonialRemote cadastrarBemPatrimonialEJB;

	@EJB
	private UtilMultimidiaRemote utilMultimidiaEJB;

	private MensagensMB mensagens;

	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;

	public EditarBemPatrimonialMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
	}

	public String editar() {

		if (this.validar()) {
			try {

				this.bemPatrimonial.getFontesInformacao().clear();

				for (StringContainer s : this.fontesInformacao) {
					this.bemPatrimonial.getFontesInformacao().add(s.getValor());
				}

				Instituicao i = this.editarInstituicaoEJB
						.getInstituicao(new Long(this.instituicao).longValue());
				this.bemPatrimonial.setInstituicao(i);

				this.assuntos.trim();
				Set<Assunto> assuntosSet = new TreeSet<Assunto>();
				String assuntosArray[] = this.assuntos.split("\\s+");

				for (String s : assuntosArray) {
					Assunto a = new Assunto();
					a.setAssunto(s);
					assuntosSet.add(a);
				}
				this.bemPatrimonial.setAssuntos(assuntosSet);

				this.descritores.trim();
				Set<Descritor> descritoresSet = new TreeSet<Descritor>();
				String descritoresArray[] = this.descritores.split("\\s+");

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

				this.editarBemPatrimonialEJB
						.editarBemPatrimonial(bemPatrimonial);

				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));

				return this.redirecionar(
						"/restrito/editarbempatrimonialmultimidia.jsf", true);

			} catch (ModeloException e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
				return null;
			}
		} else {
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return null;
		}
	}

	public String selecionar() {
		try {
			this.bemPatrimonial = cadastrarBemPatrimonialEJB
					.getBemPatrimonial(new Long(this.id).longValue());

			this.instituicao = new Long(this.bemPatrimonial.getInstituicao()
					.getId()).toString();

			this.campos = this.utilMultimidiaEJB
					.listarCampos(this.bemPatrimonial.getContainerMultimidia());
			for (Descritor d : this.bemPatrimonial.getDescritores()) {
				this.descritores += d.getDescritor() + " ";
			}

			for (Assunto a : this.bemPatrimonial.getAssuntos()) {
				this.assuntos += a.getAssunto() + " ";
			}

			for (String s : this.bemPatrimonial.getFontesInformacao()) {
				this.fontesInformacao.add(new StringContainer(s));
			}

			return this
					.redirecionar("/restrito/editarbempatrimonial.jsf", true);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			return null;
		}
	}

	@Override
	public boolean validar() {
		return this.validarTituloPrincipal();
	}

	@Override
	public String removerMidia(Long id) {
		try {
			this.utilMultimidiaEJB.excluirMultimidia(id.longValue());
			MVModeloCamposMultimidia remover = null;
			for (MVModeloCamposMultimidia c : this.campos) {
				if (c.getId().equals(id)) {
					remover = c;
				}
			}
			this.campos.remove(remover);
			return this.redirecionar(null, false);
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return this.redirecionar(null, false);
		}
	}

	// getters e setters

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

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}
}