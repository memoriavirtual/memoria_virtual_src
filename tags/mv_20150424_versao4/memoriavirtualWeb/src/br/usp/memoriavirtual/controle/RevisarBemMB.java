package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Revisao;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.RevisarBemRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;
import br.usp.memoriavirtual.utils.StringContainer;

@ManagedBean(name = "revisarbemMB")
@ViewScoped
public class RevisarBemMB implements Serializable {

	private static final long serialVersionUID = -8483697267945797748L;
	
	@EJB
	private RevisarBemRemote revisar;
	
	@EJB
	private UtilMultimidiaRemote utilMultimidiaEJB;
	
	@EJB
	private EditarBemPatrimonialRemote editarBemEJB;
	
	private String id = "";
	
	private BemPatrimonial b;
	
	private String descritores = "";
	
	private String assuntos = "";
	
	private List<StringContainer> fontesInformacao = new ArrayList<StringContainer>();
	
	private List<Revisao> revisoesAnteriores = new ArrayList<Revisao>();
	
	private String anotacao;
	
	
	private List<MVModeloCamposMultimidia> campos = new ArrayList<MVModeloCamposMultimidia>();
	
	private MensagensMB mensagens;
	
	public RevisarBemMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(facesContext.getELContext(), null, "mensagensMB");
	}
	
	@PostConstruct
	public void init(){
		id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
		try{
			this.b = revisar.getBemPatrimonial(Long.parseLong(id));
			this.revisoesAnteriores = b.getRevisoes();
			this.campos = this.utilMultimidiaEJB.listarCampos(this.b.getContainerMultimidia());
		}catch(Exception e){
			e.getStackTrace();
		}
		
		for (Descritor d : this.b.getDescritores()) {
			this.descritores += d.getDescritor() + " ";
		}

		for (Assunto a : this.b.getAssuntos()) {
			this.assuntos += a.getAssunto() + " ";
		}

		for (String s : this.b.getFontesInformacao()) {
			this.fontesInformacao.add(new StringContainer(s));
		}
	}

	
	public String imagemDisplay(Long id) {
		try {
			if (id.equals(null) || id == null) {
				throw new ModeloException(
						"Campo ID para multimidia não pode ser null");
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
						"Campo ID para multimidia não pode ser null");
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
						"Campo ID para multimidia não pode ser null");
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
	
	public String getContentType(Long id) {
		try {
			return this.utilMultimidiaEJB.getContentType(id.longValue());
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}
	
	public String traduzir(String chave) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, MVControleMemoriaVirtual.bundleName);
		return bundle.getString(chave);
	}
	
	public String revisar(){
		Revisao rev = new Revisao();
		rev.setDataRevisao(new java.sql.Date(new java.util.Date().getTime()));
		rev.setAnotacao(anotacao);
		rev.setBemPatrimonial(b);
		b.setRevisao(true);
		b.setExterno(true);
		
		try {
			editarBemEJB.editarBemPatrimonial(b);
			revisar.inserirRevisao(rev);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getMensagens().mensagemErro(this.traduzir("sucessoRevisao"));
		return "index";
	}
	
	public String cancelar(){
		return "index";
	}
	
	public String voltar(){
		return "selecionabempatrimonialrevisao";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public BemPatrimonial getB() {
		return b;
	}

	public void setB(BemPatrimonial b) {
		this.b = b;
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

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public List<Revisao> getRevisoesAnteriores() {
		return revisoesAnteriores;
	}

	public void setRevisoesAnteriores(List<Revisao> revisoesAnteriores) {
		this.revisoesAnteriores = revisoesAnteriores;
	}

	public String getAnotacao() {
		return anotacao;
	}

	public void setAnotacao(String anotacao) {
		this.anotacao = anotacao;
	}
}
