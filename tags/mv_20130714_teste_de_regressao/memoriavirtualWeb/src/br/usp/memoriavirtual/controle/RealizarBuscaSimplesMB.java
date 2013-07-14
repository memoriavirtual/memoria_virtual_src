package br.usp.memoriavirtual.controle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArqueologico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArquitetonico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemNatural;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "realizarBuscaSimplesMB")
@SessionScoped
public class RealizarBuscaSimplesMB implements Serializable, BeanComMidia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4130356176853401265L;
	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;
	private String busca;
	private List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
	private BemPatrimonial bem;
	private BemArqueologico bemArqueologico = null;
	private BemArquitetonico bemArquitetonico = null;
	private BemNatural bemNatural = null;
	private boolean arquitetonico;
	private boolean arqueologico;
	private boolean natural;
	private ArrayList<Integer> apresentaMidias = new ArrayList<Integer>();

	public RealizarBuscaSimplesMB() {
	}

	public String buscar() {
		try {
			this.bens = realizarBuscaEJB.buscar(this.busca);
		} catch (Exception e) {
			e.printStackTrace();
			MensagensDeErro.getErrorMessage("realizarBuscaSimplesErro", "resultado");
			return null;
		}

		return "resultadosbusca";
	}

	public String excluir() {

		// Inicializando Managed Bean
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();

		ExcluirBemPatrimonialMB managedBean = (ExcluirBemPatrimonialMB) resolver
				.getValue(facesContext.getELContext(), null,
						"excluirBemPatrimonialMB");

		managedBean.selecionarBem(this.bem);
		return "/restrito/selecionarbemexclusao.jsf";

	}

	public String editar() {

		// Inicializando Managed Bean
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();

		EditarBemPatrimonialMB managedBean = (EditarBemPatrimonialMB) resolver
				.getValue(facesContext.getELContext(), null,
						"editarBemPatrimonialMB");

		managedBean.anexarBemPatrimonial(this.bem);
		return "/restrito/editarbempatrimonial.jsf";

	}

	public boolean permissao() {

		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		if (usuario == null)
			return false;

		try {
			if (usuario.isAdministrador())
				return true;
			else if (this.realizarBuscaEJB.possuiAcesso(usuario,
					this.bem.getInstituicao()))
				return true;
			else
				return false;
		} catch (ModeloException m) {
			m.printStackTrace();
		}
		return false;
	}

	public String resultado(BemPatrimonial b) {
		this.bem = b;
		this.determinaTipo();
		this.busca = null;

		for (int i = 0; i < this.bem.getContainerMultimidia().getMultimidia()
				.size(); ++i) {

			this.apresentaMidias.add(i, i);
			this.apresentaMidias.trimToSize();

		}

		if (this.bem.getTipoDoBemPatrimonial().equals("BemArqueologico")) {
			try {
				this.bemArqueologico = this.realizarBuscaEJB
						.buscarBemArqueologico(this.bem);
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		}

		if (this.bem.getTipoDoBemPatrimonial().equals("BemArquitetonico")) {
			try {
				this.bemArquitetonico = this.realizarBuscaEJB
						.buscarBemArquitetonico(this.bem);
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		}

		if (this.bem.getTipoDoBemPatrimonial().equals("BemNatural")) {
			try {
				this.bemNatural = this.realizarBuscaEJB
						.buscarBemNatural(this.bem);
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		}

		return "bempatrimonial";

	}

	public String voltar() {
		return "resultadosbusca.jsf";
	}

	public void determinaTipo() {

		if (this.bem.getTipoDoBemPatrimonial().equalsIgnoreCase("arqueologico")) {

			this.arqueologico = true;
			this.arquitetonico = false;
			this.natural = false;
			try {
				this.bemArqueologico = realizarBuscaEJB
						.buscarBemArqueologico(bem);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("erro", "resultado");
			}

		} else if (this.bem.getTipoDoBemPatrimonial().equalsIgnoreCase(
				"arquitetonico")) {

			this.arqueologico = false;
			this.arquitetonico = true;
			this.natural = false;
			try {
				this.bemArquitetonico = realizarBuscaEJB
						.buscarBemArquitetonico(bem);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("erro", "resultado");
			}

		} else if (this.bem.getTipoDoBemPatrimonial().equalsIgnoreCase(
				"natural")) {

			this.arqueologico = false;
			this.arquitetonico = false;
			this.natural = true;
			try {
				this.bemNatural = realizarBuscaEJB.buscarBemNatural(bem);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("erro", "resultado");
			}

		}

	}

	public void download(Integer index) {

		System.out.println("oloco");

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		Multimidia midia = this.bem.getContainerMultimidia().getMultimidia()
				.get(index);

		response.reset();
		response.setContentType(midia.getContentType());
		response.setHeader("Content-disposition", "attachment; filename="
				+ midia.getNome());

		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {

			input = new BufferedInputStream(new ByteArrayInputStream(
					midia.getContent()));
			output = new BufferedOutputStream(response.getOutputStream());

			for (int length; (length = input.read(midia.getContent())) > 0;)
				output.write(midia.getContent(), 0, length);

			input.close();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public boolean isRendSair() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) context.getExternalContext()
				.getSessionMap().get("usuario");
		if (usuario != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRendLogin() {
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario usuario = (Usuario) context.getExternalContext()
				.getSessionMap().get("usuario");
		if (usuario != null) {
			return false;
		} else {
			return true;
		}
	}

	public BemPatrimonial getBem() {
		return bem;
	}

	public void setBem(BemPatrimonial bem) {
		this.bem = bem;
	}

	public BemArqueologico getBemArqueologico() {
		return bemArqueologico;
	}

	public void setBemArqueologico(BemArqueologico bemArqueologico) {
		this.bemArqueologico = bemArqueologico;
	}

	public BemArquitetonico getBemArquitetonico() {
		return bemArquitetonico;
	}

	public void setBemArquitetonico(BemArquitetonico bemArquitetonico) {
		this.bemArquitetonico = bemArquitetonico;
	}

	public BemNatural getBemNatural() {
		return bemNatural;
	}

	public void setBemNatural(BemNatural bemNatural) {
		this.bemNatural = bemNatural;
	}

	public boolean isArquitetonico() {
		return arquitetonico;
	}

	public void setArquitetonico(boolean arquitetonico) {
		this.arquitetonico = arquitetonico;
	}

	public boolean isArqueologico() {
		return arqueologico;
	}

	public void setArqueologico(boolean arqueologico) {
		this.arqueologico = arqueologico;
	}

	public boolean isNatural() {
		return natural;
	}

	public void setNatural(boolean natural) {
		this.natural = natural;
	}

	@Override
	public List<Multimidia> recuperaColecaoMidia() {
		return this.bem.getContainerMultimidia().getMultimidia();
	}

	@Override
	public void adicionarMidia(Multimidia midia) {

	}

	@Override
	public String removeMidia(Multimidia midia) {
		return null;
	}

	@Override
	public String removeMidia(int index) {
		return null;
	}

	@Override
	public ArrayList<Integer> getApresentaMidias() {
		return this.apresentaMidias;
	}

	@Override
	public void setApresentaMidias(ArrayList<Integer> apresentaMidias) {
		this.apresentaMidias = apresentaMidias;
	}

	@Override
	public boolean isRenderCell(int index) {
		return false;
	}

	public String url(Integer index) {
		return "/multimidia?bean=realizarBuscaSimplesMB&indice="
				+ index.toString() + "&thumb=true&type=false";
	}

}
