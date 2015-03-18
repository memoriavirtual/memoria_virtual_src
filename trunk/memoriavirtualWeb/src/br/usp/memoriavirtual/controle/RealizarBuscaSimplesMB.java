package br.usp.memoriavirtual.controle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RealizarBuscaSimplesRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.StringContainer;

@ManagedBean(name = "realizarBuscaSimplesMB")
@SessionScoped
public class RealizarBuscaSimplesMB implements BeanMemoriaVirtual, Serializable {

	private static final long serialVersionUID = 4130356176853401265L;

	@EJB
	private CadastrarBemPatrimonialRemote cadastrarBemPatrimonialEJB;

	@EJB
	private RealizarBuscaSimplesRemote realizarBuscaEJB;

	@EJB
	private UtilMultimidiaRemote utilMultimidiaEJB;

	private List<MVModeloCamposMultimidia> campos = new ArrayList<MVModeloCamposMultimidia>();
	private String busca;
	private List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();
	private BemPatrimonial bemPatrimonial;
	private String assuntos = "";
	private String descritores = "";
	private List<StringContainer> fontesInformacao = new ArrayList<StringContainer>();
	private boolean proximaPaginaDisponivel;
	private Integer pagina = 1;
	private ArrayList<String> paginas;

	private UIData controlePagina;

	private MensagensMB mensagens;

	public RealizarBuscaSimplesMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(facesContext.getELContext(), null, "mensagensMB");
	}

	private void buscarNovaPagina(Integer pagina) {
		try {
			if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")!=null)
				this.bens = realizarBuscaEJB.buscar(this.busca, pagina);
			else
				this.bens = realizarBuscaEJB.buscarExterno(this.busca, pagina);

			if (pagina.intValue() == realizarBuscaEJB.getNumeroDePaginasBusca()
					.intValue())
				proximaPaginaDisponivel = false;
			else
				proximaPaginaDisponivel = true;
			
			this.pagina = pagina;
		} catch (Exception e) {
			e.printStackTrace();
			MensagensDeErro.getErrorMessage("realizarBuscaSimplesErro",
					"resultado");
		}
	}

	public String buscar() {

		try {
			if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")!=null)
				this.bens = realizarBuscaEJB.buscar(this.busca, pagina);
			else
				this.bens = realizarBuscaEJB.buscarExterno(this.busca, pagina);

			if (pagina == realizarBuscaEJB.getNumeroDePaginasBusca())
				proximaPaginaDisponivel = false;
			else
				proximaPaginaDisponivel = true;

			paginas = new ArrayList<String>();

			for (int i = 0; i < realizarBuscaEJB.getNumeroDePaginasBusca(); i++) {
				paginas.add(new Integer(i + 1).toString());
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

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();

		ExcluirBemPatrimonialMB managedBean = (ExcluirBemPatrimonialMB) resolver
				.getValue(facesContext.getELContext(), null,
						"excluirBemPatrimonialMB");
		managedBean.setId(new Long(this.bemPatrimonial.getId()).toString());
		managedBean.selecionar();
		return "/restrito/selecionarbemexclusao.jsf";

	}

	public String editar() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();

		EditarBemPatrimonialMB managedBean = (EditarBemPatrimonialMB) resolver
				.getValue(facesContext.getELContext(), null,
						"editarBemPatrimonialMB");

		managedBean.setId(new Long(this.bemPatrimonial.getId()).toString());
		managedBean.selecionar();
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
					this.bemPatrimonial.getInstituicao()))
				return true;
			else
				return false;
		} catch (ModeloException m) {
			m.printStackTrace();
		}
		return false;
	}

	public String resultado(BemPatrimonial b) {

		try {
			this.bemPatrimonial = b;
			this.campos = this.utilMultimidiaEJB.listarCampos(this.bemPatrimonial.getContainerMultimidia());
			
			for (Descritor d : this.bemPatrimonial.getDescritores()) {
				this.descritores += d.getDescritor() + " ";
			}

			for (Assunto a : this.bemPatrimonial.getAssuntos()) {
				this.assuntos += a.getAssunto() + " ";
			}

			for (String s : this.bemPatrimonial.getFontesInformacao()) {
				this.fontesInformacao.add(new StringContainer(s));
			}

			return this.redirecionar("/bempatrimonial.jsf", true);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			return null;
		}
	}

	public String selecionaPagina(Integer numeroPagina) {
		buscarNovaPagina(numeroPagina);
		return "resultadosbusca";
	}

	public String proximaPagina() {
		pagina++;
		buscarNovaPagina(pagina);
		return "resultadosbusca";
	}

	public String paginaAnterior() {
		pagina--;
		buscarNovaPagina(pagina);
		return "resultadosbusca";
	}

	public String voltar() {
		return this.buscar();
	}

	public void download(Integer index) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		Multimidia midia = this.bemPatrimonial.getContainerMultimidia()
				.getMultimidia().get(index);

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

	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	public void setBemPatrimonial(BemPatrimonial bem) {
		this.bemPatrimonial = bem;
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

	@Override
	public String traduzir(String chave) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, MVControleMemoriaVirtual.bundleName);
		return bundle.getString(chave);
	}

	@Override
	public String redirecionar(String pagina, boolean redirect) {
		return redirect ? pagina + "?faces-redirect=true" : pagina;
	}

	@Override
	public String cancelar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validar() {
		return false;
	}

	public String getContentType(Long id) {
		try {
			return this.utilMultimidiaEJB.getContentType(id.longValue());
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	public String imagemDisplay(Long id) {
		try {
			if (id.equals(null) || id == null) {
				throw new ModeloException(
						"Campo ID para multimidia n√£o pode ser null");
			}
			String tipo = this.utilMultimidiaEJB.getContentType(id.longValue());
			return tipo.contains("image") ? "" : "none";
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	public String videoDisplay(Long id) {
		try {
			if (id.equals(null) || id == null) {
				throw new ModeloException(
						"Campo ID para multimidia n√£o pode ser null");
			}
			String tipo = this.utilMultimidiaEJB.getContentType(id.longValue());
			return tipo.contains("video") ? "" : "none";
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	public String midiaDisplay(Long id) {
		try {
			if (id.equals(null) || id == null) {
				throw new ModeloException(
						"Campo ID para multimidia n√£o pode ser null");
			}
			String tipo = this.utilMultimidiaEJB.getContentType(id.longValue());
			return !tipo.contains("image") && !tipo.contains("video") ? ""
					: "none";
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public String getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(String assuntos) {
		this.assuntos = assuntos;
	}

	public String getDescritores() {
		return descritores;
	}

	public void setDescritores(String descritores) {
		this.descritores = descritores;
	}

	public List<StringContainer> getFontesInformacao() {
		return fontesInformacao;
	}

	public void setFontesInformacao(List<StringContainer> fontesInformacao) {
		this.fontesInformacao = fontesInformacao;
	}

	public List<MVModeloCamposMultimidia> getCampos() {
		return campos;
	}

	public void setCampos(List<MVModeloCamposMultimidia> campos) {
		this.campos = campos;
	}

	@Override
	public void validarCampo(String nomeCampoMensagem, String nomeCampo,
			String campo) {
		//N„o h· requisito para validaÁ„o do campo de busca		
	}

}
