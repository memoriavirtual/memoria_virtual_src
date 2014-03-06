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
import javax.faces.component.UIData;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "realizarBuscaSimplesMB")
@SessionScoped
public class RealizarBuscaSimplesMB implements Serializable, BeanComMidia {

	private static final long serialVersionUID = 4130356176853401265L;
	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;
	
	private String busca;
	private List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
	private BemPatrimonial bem;
	private ArrayList<Integer> apresentaMidias = new ArrayList<Integer>();

	private boolean proximaPaginaDisponivel;
	private Integer pagina = 1;
	private ArrayList<String> paginas;
	
	private UIData controlePagina;

	public RealizarBuscaSimplesMB() {

	}

	private void buscarNovaPagina(Integer pagina){
		try {
			this.bens = realizarBuscaEJB.buscar(this.busca,pagina);
			
			if(pagina.intValue() == realizarBuscaEJB.getNumeroDePaginasBusca().intValue())	
				proximaPaginaDisponivel = false;
			else
				proximaPaginaDisponivel = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			MensagensDeErro.getErrorMessage("realizarBuscaSimplesErro",
					"resultado");
		}	
	}
	
	public String buscar() {

		try {
			this.bens = realizarBuscaEJB.buscar(this.busca,pagina);
			
			if(pagina == realizarBuscaEJB.getNumeroDePaginasBusca())	
				proximaPaginaDisponivel = false;
			else
				proximaPaginaDisponivel = true;
			
			paginas  = new ArrayList<String>();
			
			for(int i=0;i<realizarBuscaEJB.getNumeroDePaginasBusca();i++){
				paginas.add(new Integer(i+1).toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			MensagensDeErro.getErrorMessage("realizarBuscaSimplesErro",
					"resultado");
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

		managedBean.selecionarBem(this.bem);
		return "/restrito/editarbempatrimonial.jsf?faces-redirect=true";

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

		for (int i = 0; i < this.bem.getContainerMultimidia().getMultimidia()
				.size(); ++i) {

			this.apresentaMidias.add(i, i);
			this.apresentaMidias.trimToSize();

		}

		return "bempatrimonial";

	}
	
	public String selecionaPagina(){
		pagina = new Integer((String) controlePagina.getRowData());
		buscarNovaPagina(pagina);
		return "resultadosbusca";
	}
	
	public String proximaPagina(){
		pagina++;
		buscarNovaPagina(pagina);
		return "resultadosbusca";
	}
	
	public String paginaAnterior(){
		pagina--;
		buscarNovaPagina(pagina);
		return "resultadosbusca";
	}
	
	public String voltar() {

		this.apresentaMidias.clear();
		return this.buscar();
	}

	public void download(Integer index) {

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

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public ArrayList<String> getPaginas() {
		return paginas;
	}

	public void setPaginas(ArrayList<String> paginas) {
		this.paginas = paginas;
	}

	public String url(Integer index) {
		return "/multimidia?bean=realizarBuscaSimplesMB&indice="
				+ index.toString() + "&thumb=true&type=false";
	}

	public boolean isProximaPaginaDisponivel() {
		return proximaPaginaDisponivel;
	}

	public void setProximaPaginaDisponivel(boolean proximaPaginaDisponivel) {
		this.proximaPaginaDisponivel = proximaPaginaDisponivel;
	}
	
	public UIData getControlePagina() {
		return controlePagina;
	}

	public void setControlePagina(UIData controlePagina) {
		this.controlePagina = controlePagina;
	}

	public RealizarBuscaSimplesRemote getRealizarBuscaEJB() {
		return realizarBuscaEJB;
	}

	public void setRealizarBuscaEJB(RealizarBuscaSimplesRemote realizarBuscaEJB) {
		this.realizarBuscaEJB = realizarBuscaEJB;
	}

}
