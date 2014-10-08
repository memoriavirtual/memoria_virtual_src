package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MVModeloAcao;
import br.usp.memoriavirtual.utils.MVModeloEmailParser;
import br.usp.memoriavirtual.utils.MVModeloEmailTemplates;
import br.usp.memoriavirtual.utils.MVModeloMapeamentoUrl;
import br.usp.memoriavirtual.utils.MVModeloParametrosEmail;
import br.usp.memoriavirtual.utils.MensagensDeErro;

@ManagedBean(name = "excluirBemPatrimonialMB")
@SessionScoped
public class ExcluirBemPatrimonialMB extends EditarBemPatrimonialMB implements Serializable {

	private static final long serialVersionUID = -5120759550692482010L;
	private String id = "";

	private List<SelectItem> usuariosAprovadores = null;
	
	@EJB
	private ExcluirBemPatrimonialRemote excluirBemPatrimonialEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	
	@EJB
	private CadastrarBemPatrimonialRemote cadastrarBemPatrimonialEJB;
	
	private MensagensMB mensagens;
	private Integer validade = 1;
	private String justificativa = "";
	private String usuarioAprovador = "";

	public ExcluirBemPatrimonialMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(facesContext.getELContext(), null, "mensagensMB");
	}

	public String selecionar() {
		updateAprovadores();
		Long id = new Long(this.id);
		if(id>0){
			try {
				this.bemPatrimonial = cadastrarBemPatrimonialEJB.getBemPatrimonial(id);
			} catch (ModeloException e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
				return null;
			}			
			return this.redirecionar("/restrito/excluirbempatrimonial.jsf", true);
		}else{
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			return null;
		}
	}

	public String solicitarExclusao() {
		if(validar()){
			try {
				int dias = new Integer(this.validade).intValue();
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(new Date());
				calendario.add(Calendar.DATE, dias);
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String expiraEm = dateFormat.format(calendario.getTime());
				Aprovacao aprovacao = new Aprovacao();
				aprovacao.setAcao(MVModeloAcao.excluir_bem);
				Usuario analista = memoriaVirtualEJB.getUsuario(this.usuarioAprovador);
				aprovacao.setAnalista(analista);
				aprovacao.setDados("id;" + this.bemPatrimonial.getId()
						+ ";justificativa;" + this.justificativa);
				aprovacao.setExpiraEm(calendario.getTime());
				Usuario solicitante = (Usuario) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSessionMap().get("usuario");
				aprovacao.setSolicitante(solicitante);

				Long idAprovacao = this.excluirBemPatrimonialEJB.solicitarExclusao(bemPatrimonial, aprovacao);

				Map<String, String> tags = new HashMap<String, String>();
				tags.put(MVModeloParametrosEmail.ANALISTA000.toString(),
						analista.getNomeCompleto());
				tags.put(MVModeloParametrosEmail.EXPIRACAO000.toString(),
						expiraEm);
				tags.put(MVModeloParametrosEmail.BEM000.toString(),
						this.bemPatrimonial.getTituloPrincipal());
				tags.put(MVModeloParametrosEmail.REG000.toString(),
						this.bemPatrimonial.getNumeroRegistro());
				tags.put(MVModeloParametrosEmail.JUSTIFICATIVA000.toString(),
						this.justificativa);
				tags.put(MVModeloParametrosEmail.SOLICITANTE000.toString(),
						solicitante.getNomeCompleto());

				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put("id", idAprovacao.toString());
				String url = this.memoriaVirtualEJB.getUrl(
						MVModeloMapeamentoUrl.excluirBem, parametros);

				tags.put(MVModeloParametrosEmail.URL000.toString(), url);

				String email = new MVModeloEmailParser().getMensagem(tags,
						MVModeloEmailTemplates.excluirBem);
				String assunto = this.traduzir("excluirBemoAssuntoEmail");
				this.memoriaVirtualEJB.enviarEmail(analista.getEmail(),
						assunto, email);
				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
			} catch (Exception e) {
				e.printStackTrace();
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			}
			
		}
		return null;
	}

	@Override
	public boolean validar(){
		return validarValidade() && validarUsuario() && validarJustificativa();		
	}
	
	public boolean validarValidade(){		
		if(this.validade<1 || this.validade >30){
			MensagensDeErro.getErrorMessage("erroValidadeInvalida", "validacao-validade");
			return false;
		}
		return true;
	}
	
	public boolean validarUsuario(){
		if(usuarioAprovador.equals("disabled"))
			return false;
		else
			return true;
	}
	
	public boolean validarJustificativa(){
		if(justificativa.isEmpty())
			return false;
		else
			return true;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String nome) {
		this.id = nome;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}
	
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}


	public String getUsuarioAprovador() {
		return usuarioAprovador;
	}


	public void setUsuarioAprovador(String usuarioAprovador) {
		this.usuarioAprovador = usuarioAprovador;
	}
	
	public void updateAprovadores(){
		usuariosAprovadores = new ArrayList<SelectItem>();
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		try {
			List<Usuario> listaUsuarios = this.excluirBemPatrimonialEJB.listarUsuariosAprovadores(this.bemPatrimonial.getInstituicao(), usuario);
			for(Usuario u : listaUsuarios){
				usuariosAprovadores.add(new SelectItem(u.getId(),u.getNomeCompleto()));
			}
		} catch (ModeloException e) {
			e.printStackTrace();
		}		
	}
	
	public List<SelectItem> getUsuariosAprovadores() {
		return usuariosAprovadores;
	}

	public Integer getValidade() {
		return validade;
	}

	public void setValidade(Integer validade) {
		this.validade = validade;
	}
}
